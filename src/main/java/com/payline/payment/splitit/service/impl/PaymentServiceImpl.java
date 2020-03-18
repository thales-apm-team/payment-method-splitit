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
import com.payline.pmapi.bean.payment.ContractConfiguration;
import com.payline.pmapi.bean.payment.RequestContext;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseRedirect;
import com.payline.pmapi.logger.LogManager;
import com.payline.pmapi.service.PaymentService;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.payline.payment.splitit.utils.Constants.ContractConfigurationKeys.*;

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

                return initiateResponseSuccess(responseInitiate, initiate.getRequestHeader().getSessionId());
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
        PlanData planData;
        String chargeDate = "";

        if (request.getPartnerConfiguration() == null || request.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.API_KEY) == null) {
            throw new InvalidDataException(("Missing or invalid PaymentRequest.PartnerConfiguration"));
        }

        if (request.getAmount() == null || request.getAmount().getAmountInSmallestUnit() == null
                || request.getAmount().getCurrency() == null) {
            throw new InvalidDataException("Missing or invalid PaymentRequest.Amount");
        }

        if (request.getContractConfiguration().getProperty(NUMBEROFINSTALLMENTS).getValue() == null) {
            throw new InvalidDataException("Missing or invalid PaymentRequest.ContractConfiguration");
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

        // if there is no date, it's the date of the initialisation
        if (request.getPaymentFormContext() != null && request.getPaymentFormContext().getPaymentFormParameter() != null) {
            chargeDate = computeDate(request);
        }


        RequestHeader requestHeader = new RequestHeader.RequestHeaderBuilder()
                .withSessionId(request.getRequestContext().getSensitiveRequestData().get(Constants.RequestContextKeys.SESSION_ID))
                .withApiKey(request.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.API_KEY))
                .build();

        // if the FIRSTINSTALLMENTAMOUNT is null
        if (request.getContractConfiguration().getProperty(FIRSTINSTALLMENTAMOUNT) == null) {
            planData = new PlanData.PlanDataBuilder()
                    .withAmount(new Amount.AmountBuilder().withCurrency(request.getAmount().getCurrency().getCurrencyCode()).withValue(AmountParse.split(request.getAmount())).build())
                    .withNumberOfInstallments(request.getContractConfiguration().getProperty(NUMBEROFINSTALLMENTS).getValue())
                    .withRefOrderNumber(request.getTransactionId())
                    .withAutoCapture(request.isCaptureNow())
                    .withFirstChargeDate(chargeDate)
                    .build();
        } else {
            Amount firstInstallmentAmount = new Amount.AmountBuilder()
                    .withCurrency(request.getAmount().getCurrency().toString())
                    .withValue(AmountParse.split(request.getAmount().getAmountInSmallestUnit().doubleValue(), request.getAmount().getCurrency()))
                    .build();


            planData = new PlanData.PlanDataBuilder()
                    .withAmount(new Amount.AmountBuilder().withCurrency(request.getAmount().getCurrency().getCurrencyCode()).withValue(AmountParse.split(request.getAmount())).build())
                    .withNumberOfInstallments(request.getContractConfiguration().getProperty(NUMBEROFINSTALLMENTS).getValue())
                    .withRefOrderNumber(request.getTransactionId())
                    .withFirstInstallmentAmount(firstInstallmentAmount)
                    .withAutoCapture(request.isCaptureNow())
                    .withFirstChargeDate(chargeDate)
                    .build();
        }

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
                .withRequestednumberOfInstallments(createChainRequestedNumberOfInstallments(request.getContractConfiguration()))
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
     * @param initiateResponse InitiateResponse
     * @param sessionId        the sessionId
     * @return PaymentResponseRedirect
     */
    public PaymentResponseRedirect initiateResponseSuccess(InitiateResponse initiateResponse, String sessionId) {
        PaymentResponseRedirect.RedirectionRequest redirectionRequest = PaymentResponseRedirect.RedirectionRequest
                .RedirectionRequestBuilder.aRedirectionRequest()
                .withRequestType(PaymentResponseRedirect.RedirectionRequest.RequestType.GET)
                .withUrl(initiateResponse.getCheckoutUrl())
                .build();

        Map<String, String> requestData = new HashMap<>();
        requestData.put(Constants.RequestContextKeys.INSTALLMENT_PLAN_NUMBER, initiateResponse.getInstallmentPlan().getInstallmentPlanNumber());
        Map<String, String> requestSensitiveData = new HashMap<>();
        requestSensitiveData.put(Constants.RequestContextKeys.SESSION_ID, sessionId);
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

    /**
     * Create the field requested number of installments
     *
     * @param configuration ContractConfiguration
     * @return String
     */
    public String createChainRequestedNumberOfInstallments(ContractConfiguration configuration) {
        if (configuration.getProperty(REQUESTEDNUMBEROFINSTALLMENTSDEFAULT).getValue().equals("true")) {
            return "2,3,4,5,6,7,8,9,10,11,12";
        }
        StringBuilder chain = new StringBuilder();
        List<String> requestedNumberOfInstallmentsList = new ArrayList<>();
        for (int i = 2; i < 13; i++) {
            requestedNumberOfInstallmentsList.add("REQUESTEDNUMBEROFINSTALLMENTS" + i);
            if (configuration.getProperty(requestedNumberOfInstallmentsList.get(i - 2)).getValue().equals("true")) {
                chain.append(i).append(",");
            }
        }
        // delete the last coma
        return chain.substring(0, chain.length() - 1);
    }

    /**
     * compute the date of the first charge date
     * date have format yyyy-MM-jj
     *
     * @param request PaymentRequest
     * @return String
     */
    public String computeDate(PaymentRequest request) {
        final Date date = new Date();
        final String dateFormat = "yyyy-MM-dd";
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String chargeDate = "";

        switch (request.getPaymentFormContext().getPaymentFormParameter().get(Constants.FormConfigurationKeys.OPTIONS)) {
            case FIRSTCHARGEDATEONEWEEK:
                chargeDate = localDateTime.plusWeeks(1).format(DateTimeFormatter.ofPattern(dateFormat));
                break;
            case FIRSTCHARGEDATETWOWEEKS:
                chargeDate = localDateTime.plusWeeks(2).format(DateTimeFormatter.ofPattern(dateFormat));
                break;
            case FIRSTCHARGEDATEONEMONTH:
                chargeDate = localDateTime.plusMonths(1).format(DateTimeFormatter.ofPattern(dateFormat));
                break;
            case FIRSTCHARGEDATETWOMONTHS:
                chargeDate = localDateTime.plusMonths(2).format(DateTimeFormatter.ofPattern(dateFormat));
                break;
            // if nothing is said, we say it's the current date
            case FIRSTCHARGEDATENOW:
            default:
                break;
        }
        return chargeDate;
    }
}
