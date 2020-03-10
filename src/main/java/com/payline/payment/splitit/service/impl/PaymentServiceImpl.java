package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.bean.AmountParse;
import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.nesteed.*;
import com.payline.payment.splitit.bean.request.Initiate;
import com.payline.payment.splitit.bean.request.RequestHeader;
import com.payline.payment.splitit.bean.response.InitiateResponse;
import com.payline.payment.splitit.exception.InvalidDataException;
import com.payline.payment.splitit.exception.PluginException;
import com.payline.payment.splitit.utils.Constants;
import com.payline.payment.splitit.utils.PluginUtils;
import com.payline.payment.splitit.utils.http.HttpClient;
import com.payline.pmapi.bean.common.Buyer;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.payment.RequestContext;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseRedirect;
import com.payline.pmapi.logger.LogManager;
import com.payline.pmapi.service.PaymentService;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.payline.payment.splitit.utils.Constants.ContractConfigurationKeys.NUMBER_OF_INSTALLMENTS;
import static com.payline.payment.splitit.utils.Constants.ContractConfigurationKeys.REQUESTED_NUMBER_OF_INSTALLMENTS;

public class PaymentServiceImpl implements PaymentService {
    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);
    private HttpClient httpClient = HttpClient.getInstance();

    @Override
    public PaymentResponse paymentRequest(PaymentRequest request) {

        try {
            // POST /Initiate
            Initiate initiate = initiateCreate(request);
            return initiateCall(request, initiate);
            // POST /Initiate doesn't worked
        } catch (PluginException e) {
            return e.toPaymentResponseFailureBuilder().build();
        } catch (RuntimeException e) {
            LOGGER.error("unexpected plugin error", e);
            return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                    .withErrorCode(PluginException.runtimeErrorCode(e))
                    .withFailureCause(FailureCause.INTERNAL_ERROR)
                    .build();
        }
    }

    /**
     * Call the initiate method from HttpClient and return the appropriate response
     *
     * @param request  PaymentRequest
     * @param initiate the initiate request
     * @return PaymentResponseRedirect or PaymentResponseFailure
     */
    public PaymentResponse initiateCall(PaymentRequest request, Initiate initiate) {
        final RequestConfiguration configuration = RequestConfiguration.build(request);
        try {
            InitiateResponse responseInitiate = httpClient.initiate(configuration, initiate);
            // if everything is ok, then return a redirectionResponseSuccess
            if (responseInitiate.getResponseHeader().isSucceeded()
                    && responseInitiate.getInstallmentPlan().getInstallmentPlanStatus().getCode().equals(InstallmentPlanStatus.Code.INITIALIZING)) {

                return initiateResponseSuccess(responseInitiate);
                // else return the right responseError
            } else {
                return PluginUtils.paymentResponseFailure(responseInitiate.getResponseHeader().getErrors().get(0).getErrorCode());
            }

        } catch (PluginException e) {
            return e.toPaymentResponseFailureBuilder().build();
        } catch (RuntimeException e) {
            LOGGER.error("unexpected plugin error", e);
            return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                    .withErrorCode(PluginException.runtimeErrorCode(e))
                    .withFailureCause(FailureCause.INTERNAL_ERROR)
                    .build();
        }
    }


    /**
     * Build the initiate object from the request
     *
     * @param request PaymentRequest
     * @return Initiate
     */
    public Initiate initiateCreate(PaymentRequest request) {
        if (request.getRequestContext() == null || request.getRequestContext().getSensitiveRequestData() == null
                || request.getRequestContext().getSensitiveRequestData().get(Constants.RequestContextKeys.SESSION_ID) == null) {
            throw new InvalidDataException("Missing or invalid PaymentRequest.RequestContext");
        }

        if (request.getPartnerConfiguration() == null || request.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.API_KEY) == null) {
            throw new InvalidDataException(("Missing or invalid PaymentRequest.PartnerConfiguration"));
        }

        if (request.getAmount() == null || request.getAmount().getAmountInSmallestUnit() == null
                || request.getAmount().getCurrency() == null) {
            throw new InvalidDataException("Missing or invalid PaymentRequest.Amount");
        }

        if (request.getContractConfiguration().getProperty(NUMBER_OF_INSTALLMENTS).getValue() == null) {
            throw new InvalidDataException("Missing or invalid PaymentRequest.ContractConfiguration");
        }

        if (request.getContractConfiguration().getProperty(REQUESTED_NUMBER_OF_INSTALLMENTS).getValue() == null) {
            throw new InvalidDataException("Missing or invalid Requested Number of installment");
        }

        if (request.getTransactionId() == null) {
            throw new InvalidDataException("Missing or invalid refOrderNumber // PaymentRequest.TransactionId");
        }

        if (request.getBuyer() == null) {
            throw new InvalidDataException("Missing or invalid PaymentRequest.Buyer");
        }

        if (request.getBuyer().getAddressForType(Buyer.AddressType.BILLING) == null
                || request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getStreet1() == null
                || request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getStreet2() == null
                || request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getCity() == null
                || request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getState() == null
                || request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getCountry() == null
                || request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getZipCode() == null) {
            throw new InvalidDataException("Missing or invalid PaymentRequest.AddressForType");
        }

        if (request.getBuyer().getFullName().getFirstName() == null || request.getBuyer().getFullName().getLastName() == null
                || request.getBuyer().getEmail() == null || request.getBuyer().getPhoneNumberForType(Buyer.PhoneNumberType.CELLULAR) == null
                || request.getLocale() == null) {
            throw new InvalidDataException("Missing or invalid PaymentRequest.ConsumerData");
        }

        if (request.getEnvironment().getRedirectionReturnURL() == null) {
            throw new InvalidDataException("Missing or invalid RedirectionUrl");
        }


        RequestHeader requestHeader = new RequestHeader.RequestHeaderBuilder()
                .withSessionId(request.getRequestContext().getSensitiveRequestData().get(Constants.RequestContextKeys.SESSION_ID))
                .withApiKey(request.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.API_KEY))
                .build();


        PlanData planData = new PlanData.PlanDataBuilder()
//                .withAmount(new Amount.AmountBuilder().withCurrency(request.getAmount().getCurrency().getCurrencyCode()).withValue(String.valueOf(request.getAmount().getAmountInSmallestUnit())).build())
                .withAmount(new Amount.AmountBuilder().withCurrency(request.getAmount().getCurrency().getCurrencyCode()).withValue(AmountParse.split(request.getAmount())).build())
                .withNumberOfInstallments(request.getContractConfiguration().getProperty(NUMBER_OF_INSTALLMENTS).getValue())
                .withRefOrderNumber(request.getTransactionId())
                .withAutoCapture(request.isCaptureNow())
                .build();

        BillingAddress billingAddress = new BillingAddress.BillingAddressBuilder()
                .withAddressLine(request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getStreet1())
                .withAddressLine2(request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getStreet2())
                .withCity(request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getCity())
                .withState(request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getState())
                .withCountry(request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getCountry())
                .withZip(request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getZipCode())
                .build();

        ConsumerData consumerData = new ConsumerData.ConsumerDataBuilder()
                .withFullName(request.getBuyer().getFullName().getFirstName() + request.getBuyer().getFullName().getLastName())
                .withEmail(request.getBuyer().getEmail())
                .withPhoneNumber(request.getBuyer().getPhoneNumberForType(Buyer.PhoneNumberType.CELLULAR))
                .withCultureName(request.getLocale().toString())
                .build();

        PaymentWizardData paymentWizardData = new PaymentWizardData.PaymentWizardDataBuilder()
                .withRequestednumberOfInstallments(String.valueOf(request.getContractConfiguration().getProperty(REQUESTED_NUMBER_OF_INSTALLMENTS).getValue()))
                .withIsOpenedInIframe(false)
                .build();

        RedirectUrl redirectUrl = new RedirectUrl.RedirectUrlBuilder()
                .withSucceeded(request.getEnvironment().getRedirectionReturnURL())
                .withCanceled(request.getEnvironment().getRedirectionReturnURL())
                .withFailed(request.getEnvironment().getRedirectionReturnURL())
                .build();

        EventsEndpoints eventsEndpoints = new EventsEndpoints.EventEndpointsBuilder()
                .withCreateSucceeded(request.getEnvironment().getNotificationURL())
                .build();

        return new Initiate.InitiateBuilder()
                .withRequestHeader(requestHeader)
                .withPlanData(planData)
                .withBillingAddress(billingAddress)
                .withConsumerData(consumerData)
                .withPaymentWizardData(paymentWizardData)
                .withRedirectUrl(redirectUrl)
                .withEventsEndpoints(eventsEndpoints)
                .build();
    }

    /**
     * Build the PaymentResponseRedirect if everything is ok
     *
     * @param initiateResponse
     * @return PaymentResponseRedirect
     */
    public PaymentResponseRedirect initiateResponseSuccess(InitiateResponse initiateResponse) {
        PaymentResponseRedirect.RedirectionRequest redirectionRequest = PaymentResponseRedirect.RedirectionRequest
                .RedirectionRequestBuilder.aRedirectionRequest()
                .withRequestType(PaymentResponseRedirect.RedirectionRequest.RequestType.GET)
                .withUrl(initiateResponse.getCheckoutUrl())
                .build();

        Map<String, String> requestData = new HashMap<>();
        requestData.put(Constants.RequestContextKeys.INSTALLMENT_PLAN_NUMBER, initiateResponse.getInstallmentPlan().getInstallmentPlanNumber());
        Map<String, String> requestSensitiveData = new HashMap<>();
        requestSensitiveData.put(Constants.RequestContextKeys.SESSION_ID, initiateResponse.getSessionId());
        RequestContext context = RequestContext.RequestContextBuilder.aRequestContext()
                .withRequestData(requestData)
                .withSensitiveRequestData(requestSensitiveData)
                .build();

        return PaymentResponseRedirect.PaymentResponseRedirectBuilder.aPaymentResponseRedirect()
                .withPartnerTransactionId(initiateResponse.getInstallmentPlan().getInstallmentPlanNumber())
                .withRedirectionRequest(redirectionRequest)
                .withRequestContext(context)
                .withStatusCode(String.valueOf(initiateResponse.getInstallmentPlan().getInstallmentPlanStatus().getCode()))
                .build();
    }
}
