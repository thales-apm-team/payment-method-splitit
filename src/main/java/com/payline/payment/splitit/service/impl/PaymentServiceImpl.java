package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.nesteed.*;
import com.payline.payment.splitit.bean.request.Initiate;
import com.payline.payment.splitit.bean.response.InitiateResponse;
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
import com.payline.pmapi.service.PaymentService;

import java.util.HashMap;
import java.util.Map;

import static com.payline.payment.splitit.utils.Constants.ContractConfigurationKeys.NUMBER_OF_INSTALLMENTS;
import static com.payline.payment.splitit.utils.Constants.ContractConfigurationKeys.REQUESTED_NUMBER_OF_INSTALLMENTS;

public class PaymentServiceImpl implements PaymentService {
    private HttpClient httpClient = HttpClient.getInstance();

    @Override
    public PaymentResponse paymentRequest(PaymentRequest request) {
        try {
            // POST /Initiate
            Initiate initiate = initiateCreate(request);
            return initiateCall(request, initiate);
            // POST /Initiate doesn't worked
        } catch (Exception e) {
            return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                    .withFailureCause(FailureCause.INVALID_DATA)
                    .build();
        }
    }


    public PaymentResponse initiateCall(PaymentRequest request, Initiate initiate) {
        final RequestConfiguration configuration = configurationCreate(request);

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

        } catch (Exception e) {
            return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                    .withFailureCause(FailureCause.INVALID_DATA)
                    .build();
        }
    }

    public Initiate initiateCreate(PaymentRequest request) {
        RequestHeader requestHeader = new RequestHeader.RequestHeaderBuilder()
                .withSessionId(request.getRequestContext().getSensitiveRequestData().get(Constants.RequestContextKeys.SESSION_ID))
                .withApiKey(request.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.API_KEY))
                .build();

        PlanData planData = new PlanData.PlanDataBuilder()
                .withAmount(new Amount.AmountBuilder().withCurrency(request.getAmount().getCurrency().getCurrencyCode()).withValue(String.valueOf(request.getAmount().getAmountInSmallestUnit())).build())
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

    public RequestConfiguration configurationCreate(PaymentRequest request) {
        return new RequestConfiguration(
                request.getContractConfiguration()
                , request.getEnvironment()
                , request.getPartnerConfiguration()
        );
    }

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
