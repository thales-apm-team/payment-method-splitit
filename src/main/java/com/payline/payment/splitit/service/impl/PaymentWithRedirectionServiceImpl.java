package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.bean.InstallmentPlanStatus;
import com.payline.payment.splitit.bean.QueryCriteria;
import com.payline.payment.splitit.bean.RequestHeader;
import com.payline.payment.splitit.bean.request.Get;
import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.response.GetResponse;
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
import com.payline.pmapi.service.PaymentWithRedirectionService;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class PaymentWithRedirectionServiceImpl implements PaymentWithRedirectionService {
    private HttpClient httpClient = HttpClient.getInstance();

    @Override
    public PaymentResponse finalizeRedirectionPayment(RedirectionPaymentRequest redirectionPaymentRequest) {
        final RequestConfiguration configuration = configurationCreate(redirectionPaymentRequest);

        RequestHeader requestHeader = new RequestHeader.RequestHeaderBuilder()
                .withSessionId(redirectionPaymentRequest.getRequestContext().getSensitiveRequestData().get(Constants.RequestContextKeys.SESSION_ID))
                .withApiKey(redirectionPaymentRequest.getRequestContext().getRequestData().get(Constants.PartnerConfigurationKeys.API_KEY))
                .build();

        // on lance le get et on verifie le champ Code
        String partnerTransactionId = redirectionPaymentRequest.getRequestContext().getRequestData().get(Constants.RequestContextKeys.INSTALLMENT_PLAN_NUMBER);

        Get get = new Get.GetBuilder()
                .withRequestHearder(requestHeader)
                .withQueryCriteria(new QueryCriteria.QueryCriteriaBuilder()
                        .withInstallmentPlanNumber(partnerTransactionId)
                        .build())
                .build();

        try {
            GetResponse getResponse = httpClient.get(configuration, get);

            // mauvais installmentPlanNumber => PlanData liste vide
            if (getResponse.getResponseHeader().isSucceeded()
                    && getResponse.getPlansList().isEmpty()) {
                return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                        .withFailureCause(FailureCause.INVALID_DATA)
                        .build();

            } else if (getResponse.getResponseHeader().isSucceeded()
                    && getResponse.getPlansList().get(0).getInstallmentPlanStatus().getCode().equals(InstallmentPlanStatus.Code.IN_PROGRESS)) {

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

            } else if (getResponse.getResponseHeader().isSucceeded()
                    && !getResponse.getPlansList().get(0).getInstallmentPlanStatus().getCode().equals(InstallmentPlanStatus.Code.IN_PROGRESS)) {
                return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                        .withErrorCode(String.valueOf(getResponse.getPlansList().get(0).getInstallmentPlanStatus().getCode()))
                        .withFailureCause(FailureCause.INVALID_DATA)
                        .build();

            } else if (!getResponse.getResponseHeader().isSucceeded()) {
                return PluginUtils.paymentResponseFailure(getResponse.getResponseHeader().getErrors().get(0).getErrorCode());
            }
            // echec du get
        } catch (Exception e) {
            return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                    .withFailureCause(FailureCause.INVALID_DATA)
                    .build();
        }
        return null;
    }

    @Override
    public PaymentResponse handleSessionExpired(TransactionStatusRequest transactionStatusRequest) {
        return null;
    }

    public RequestConfiguration configurationCreate(RedirectionPaymentRequest request) {
        return new RequestConfiguration(
                request.getContractConfiguration()
                , request.getEnvironment()
                , request.getPartnerConfiguration()
        );
    }

}
