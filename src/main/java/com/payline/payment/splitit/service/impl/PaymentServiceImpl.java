package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.bean.*;
import com.payline.payment.splitit.bean.appel.Initiate;
import com.payline.payment.splitit.bean.appel.Login;
import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.response.InitiateResponse;
import com.payline.payment.splitit.exception.InvalidDataException;
import com.payline.payment.splitit.utils.Constants;
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

import static com.payline.payment.splitit.utils.Constants.ContractConfigurationKeys.*;

public class PaymentServiceImpl implements PaymentService {
    private HttpClient httpClient = HttpClient.getInstance();

    @Override
    public PaymentResponse paymentRequest(PaymentRequest request) {
        final RequestConfiguration configuration = new RequestConfiguration(
                request.getContractConfiguration()
                , request.getEnvironment()
                , request.getPartnerConfiguration()
        );

        Login login = new Login.LoginBuilder()
                .withUsername(request.getContractConfiguration().getProperty(USERNAME).getValue())
                .withPassword(request.getContractConfiguration().getProperty(PASSWORD).getValue())
                .build();

        // todo faire l'appel login
        // en fondction du result faire l'appel initiate

        // Appel du POST /Initiate

        RequestHeader requestHeader = new RequestHeader.RequestHeaderBuilder()
                .withSessionId(Constants.RequestContextKeys.SESSION_ID)
                .withApiKey(Constants.PartnerConfigurationKeys.API_KEY)
                .build();

        PlanData planData = new PlanData.PlanDataBuilder()
                .withAmount(request.getAmount())
                .withNumberOfInstallments(Integer.parseInt(request.getContractConfiguration().getProperty(NUMBER_OF_INSTALLMENTS).getValue()))
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
                .withRequestednumberOfInstallments(String.valueOf(request.getContractConfiguration().getProperty(REQUESTED_NUMBER_OF_INSTALLMENTS)))
                .withIsOpenedInIframe(false)
                .build();

        RedirectUrl redirectUrl = new RedirectUrl.RedirectUrlBuilder()
                .withSucceeded(request.getEnvironment().getRedirectionReturnURL())
                .withCanceled(request.getEnvironment().getRedirectionReturnURL())
                .withFailed(request.getEnvironment().getRedirectionReturnURL())
                .build();

        EventsEndpoints eventsEndpoints = new EventsEndpoints.EnventEndpointsBuilder()
                .withCreateSucceeded(request.getEnvironment().getNotificationURL())
                .build();

        Initiate initiate = new Initiate.InitiateBuilder()
                .withPlanData(planData)
                .withBillingAddress(billingAddress)
                .withConsumerData(consumerData)
                .withPaymentWizardData(paymentWizardData)
                .withRedirectUrl(redirectUrl)
                .withEventsEndpoints(eventsEndpoints)
                .build();

        try {
            InitiateResponse response = httpClient.initiate(configuration, initiate);

            if(response.getInstallmentPlan().getInstallmentPlanStatus().getCode() == InstallmentPlanStatus.Code.INITIALIZING) {

                PaymentResponseRedirect.RedirectionRequest redirectionRequest = PaymentResponseRedirect.RedirectionRequest
                        .RedirectionRequestBuilder.aRedirectionRequest()
                        .withRequestType(PaymentResponseRedirect.RedirectionRequest.RequestType.GET)
                        .withUrl(response.getCheckoutUrl())
                        .build();

                Map<String, String> requestData = new HashMap<>();
                requestData.put(Constants.RequestContextKeys.INSTALLMENT_PLAN_NUMBER, response.getInstallmentPlanNumber());
                Map<String, String> requestSensitiveData = new HashMap<>();
                requestSensitiveData.put(Constants.RequestContextKeys.SESSION_ID, response.getSessionId());
                RequestContext context = RequestContext.RequestContextBuilder.aRequestContext()
                        .withRequestData(requestData)
                        .withSensitiveRequestData(requestSensitiveData)
                        .build();

                return PaymentResponseRedirect.PaymentResponseRedirectBuilder.aPaymentResponseRedirect()
                        .withPartnerTransactionId(response.getInstallmentPlanNumber())
                        .withRedirectionRequest(redirectionRequest)
                        .withRequestContext(context)
                        .withStatusCode(String.valueOf(response.getInstallmentPlan().getInstallmentPlanStatus().getCode()))
                        .build();
            } else
                switch (response.getResponseHeader().getErrors().get(0).getErrorCode()) {
                    case "599": return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                            .withErrorCode(response.getResponseHeader().getErrors().get(0).getErrorCode())
                            .withFailureCause(FailureCause.COMMUNICATION_ERROR)
                            .build();

                    case "505":
                    case "511": return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                            .withErrorCode(response.getResponseHeader().getErrors().get(0).getErrorCode())
                            .withFailureCause(FailureCause.INVALID_DATA)
                            .build();

                    case "400": return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                            .withErrorCode(response.getResponseHeader().getErrors().get(0).getErrorCode())
                            .withFailureCause(FailureCause.INVALID_FIELD_FORMAT)
                            .build();

                    case "704": return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                            .withErrorCode(response.getResponseHeader().getErrors().get(0).getErrorCode())
                            .withFailureCause(FailureCause.SESSION_EXPIRED)
                            .build();

                    default:
                        System.out.println("UNKNOW ERROR");
                        return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                            .withErrorCode(response.getResponseHeader().getErrors().get(0).getErrorCode())
                            .withFailureCause(FailureCause.INVALID_DATA)
                            .build();
                }

        } catch (Exception e) {
            System.out.println(e);
        }

        // en fonction du resultat paymentResponseRedirect ou failure

//        request.getContractConfiguration().getProperty(MYKEY).getValue()

            return PaymentResponseFailure.PaymentResponseFailureBuilder
                    .aPaymentResponseFailure()
                    .build();
    }
}
