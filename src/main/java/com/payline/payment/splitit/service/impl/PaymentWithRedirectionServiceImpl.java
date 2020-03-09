package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.nesteed.InstallmentPlanStatus;
import com.payline.payment.splitit.bean.nesteed.QueryCriteria;
import com.payline.payment.splitit.bean.request.Get;
import com.payline.payment.splitit.bean.request.RequestHeader;
import com.payline.payment.splitit.bean.response.GetResponse;
import com.payline.payment.splitit.exception.InvalidDataException;
import com.payline.payment.splitit.exception.PluginException;
import com.payline.payment.splitit.utils.Constants;
import com.payline.payment.splitit.utils.PluginUtils;
import com.payline.payment.splitit.utils.http.HttpClient;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.payment.RequestContext;
import com.payline.pmapi.bean.payment.request.RedirectionPaymentRequest;
import com.payline.pmapi.bean.payment.request.TransactionStatusRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.buyerpaymentidentifier.Card;
import com.payline.pmapi.bean.payment.response.buyerpaymentidentifier.impl.CardPayment;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseSuccess;
import com.payline.pmapi.logger.LogManager;
import com.payline.pmapi.service.PaymentWithRedirectionService;
import org.apache.logging.log4j.Logger;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class PaymentWithRedirectionServiceImpl implements PaymentWithRedirectionService {
    private HttpClient httpClient = HttpClient.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(PaymentWithRedirectionServiceImpl.class);

    @Override
    public PaymentResponse finalizeRedirectionPayment(RedirectionPaymentRequest redirectionPaymentRequest) {
        try {
            final RequestConfiguration configuration = RequestConfiguration.build(redirectionPaymentRequest);

            if (redirectionPaymentRequest.getRequestContext() == null || redirectionPaymentRequest.getRequestContext().getSensitiveRequestData() == null
                    || redirectionPaymentRequest.getRequestContext().getSensitiveRequestData().get(Constants.RequestContextKeys.SESSION_ID) == null
                    || redirectionPaymentRequest.getRequestContext().getRequestData() == null
                    || redirectionPaymentRequest.getRequestContext().getRequestData().get(Constants.RequestContextKeys.INSTALLMENT_PLAN_NUMBER) == null) {
                throw new InvalidDataException("Missing or Invalid RedirectionPaymentRequest.requestContext");
            }

            if (redirectionPaymentRequest.getPartnerConfiguration() == null
                    || redirectionPaymentRequest.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.API_KEY) == null) {
                throw new InvalidDataException("Missing or Invalid redirectionPaymentRequest.PartnerConfiguration");
            }

            RequestHeader requestHeader = new RequestHeader.RequestHeaderBuilder()
                    .withSessionId(redirectionPaymentRequest.getRequestContext().getSensitiveRequestData().get(Constants.RequestContextKeys.SESSION_ID))
                    .withApiKey(redirectionPaymentRequest.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.API_KEY))
                    .build();

            String partnerTransactionId = redirectionPaymentRequest.getRequestContext().getRequestData().get(Constants.RequestContextKeys.INSTALLMENT_PLAN_NUMBER);

            Get get = new Get.GetBuilder()
                    .withRequestHeader(requestHeader)
                    .withQueryCriteria(new QueryCriteria.QueryCriteriaBuilder()
                            .withInstallmentPlanNumber(partnerTransactionId)
                            .build())
                    .build();

            // POST /Get and look at the field Code
            GetResponse getResponse = httpClient.get(configuration, get);

            if (getResponse.getResponseHeader().isSucceeded()) {
                // wrong installmentPlanNumber will return a PlanData list empty in the response
                if (getResponse.getPlansList().isEmpty()) {
                    return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                            .withFailureCause(FailureCause.INVALID_DATA)
                            .build();
                } else if (getResponse.getPlansList().get(0).getInstallmentPlanStatus().getCode().equals(InstallmentPlanStatus.Code.IN_PROGRESS)) {
                    YearMonth date = YearMonth.of(Integer.parseInt(getResponse.getPlansList().get(0).getActiveCard().getCardExpYear()),
                            Integer.parseInt(getResponse.getPlansList().get(0).getActiveCard().getCardExpMonth()));

                    Card card = Card.CardBuilder
                            .aCard()
                            .withBrand(getResponse.getPlansList().get(0).getActiveCard().getCardBrand().getCode())
                            .withExpirationDate(date)
                            .withHolder(getResponse.getPlansList().get(0).getActiveCard().getFullName())
                            .withPan(getResponse.getPlansList().get(0).getActiveCard().getCardNumber())
                            .withPanType(Card.PanType.CARD_PAN)
                            .build();

                    CardPayment cardPayment = CardPayment.CardPaymentBuilder
                            .aCardPayment()
                            .withCard(card)
                            .build();

                    // adding the SessionId for the next call, like that we don't have to ask it again
                    Map<String, String> requestSensitiveData = new HashMap<>();
                    requestSensitiveData.put(Constants.RequestContextKeys.SESSION_ID,
                            redirectionPaymentRequest.getRequestContext().getSensitiveRequestData().get(Constants.RequestContextKeys.SESSION_ID));
                    RequestContext context = RequestContext.RequestContextBuilder.aRequestContext()
                            .withSensitiveRequestData(requestSensitiveData)
                            .build();

                    return PaymentResponseSuccess.PaymentResponseSuccessBuilder.aPaymentResponseSuccess()
                            .withRequestContext(context)
                            .withPartnerTransactionId(partnerTransactionId)
                            .withStatusCode(String.valueOf(getResponse.getPlansList().get(0).getInstallmentPlanStatus().getCode()))
                            .withTransactionDetails(cardPayment)
                            .build();
                } else {
                    return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                            .withErrorCode(String.valueOf(getResponse.getPlansList().get(0).getInstallmentPlanStatus().getCode()))
                            .withFailureCause(FailureCause.INVALID_DATA)
                            .build();
                }
            } else {
                return PluginUtils.paymentResponseFailure(getResponse.getResponseHeader().getErrors().get(0).getErrorCode());
            }
            // get Failure
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

    @Override
    public PaymentResponse handleSessionExpired(TransactionStatusRequest transactionStatusRequest) {
        return null;
    }

}
