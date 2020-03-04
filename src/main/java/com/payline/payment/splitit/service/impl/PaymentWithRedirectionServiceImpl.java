package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.bean.nesteed.InstallmentPlanStatus;
import com.payline.payment.splitit.bean.nesteed.QueryCriteria;
import com.payline.payment.splitit.bean.nesteed.RequestHeader;
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
                .withApiKey(redirectionPaymentRequest.getRequestContext().getRequestData().get(Constants.PartnerConfigurationKeys.API_KEY)) // todo attention tu vas pas chercher l'info au bon endroit (regarde la Doc que t'as fait)
                .build();

        String partnerTransactionId = redirectionPaymentRequest.getRequestContext().getRequestData().get(Constants.RequestContextKeys.INSTALLMENT_PLAN_NUMBER);

        Get get = new Get.GetBuilder()
                .withRequestHearder(requestHeader)
                .withQueryCriteria(new QueryCriteria.QueryCriteriaBuilder()
                        .withInstallmentPlanNumber(partnerTransactionId)
                        .build())
                .build();

        // POST /Get and look at the champ Code // todo the "champ"?
        try {
            GetResponse getResponse = httpClient.get(configuration, get);

            // wrong installmentPlanNumber => PlanData list empty // todo hésite pas a detailler plus (fait des phrases), c'est bien d'avoir ce comm ici mais il est pas super explicite
            if (getResponse.getResponseHeader().isSucceeded()
                    && getResponse.getPlansList().isEmpty()) {
                return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                        .withFailureCause(FailureCause.INVALID_DATA)
                        .build();

                // todo on doit pouvoir faire plus lisible avec tous ces if
            } else if (getResponse.getResponseHeader().isSucceeded()
                    && getResponse.getPlansList().get(0).getInstallmentPlanStatus().getCode().equals(InstallmentPlanStatus.Code.IN_PROGRESS)) {

                /* todo essaye de verifier la non nullité des objets, ici: getResponse.getPlansList().get(0)
                    j'aurais tendance a dire c'est pas grave c'est catché mais au moins on peut afficher le message qu'on veux pour debuguer plus facilement
                    Par exemple, dans equesn on a: if (paymentRequest.getAmount() == null
                    || paymentRequest.getAmount().getCurrency() == null
                    || paymentRequest.getAmount().getAmountInSmallestUnit() == null) {     throw new InvalidDataException("Missing or invalid paymentRequest.amount");          }
                    et au moins dans le catche en bas de méthode le message est hyper clair
                 */

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

                // todo pas mal, si l'annulation arrive desuite apres on est pas obligé de redemander un token... fait un commentaire!
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
            // get Failure
        } catch (Exception e) {// todo catch(Exception) se fait pas trop
            return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                    .withFailureCause(FailureCause.INVALID_DATA)
                    .build();
        }
        return null; // todo wow!
    }

    // todo: faut implementer cette méthode, elle fait la meme chose que finalyze mais est appelé en cas de timeout
    @Override
    public PaymentResponse handleSessionExpired(TransactionStatusRequest transactionStatusRequest) {
        return null;
    }

    // todo pareil que pour le PaymentService
    public RequestConfiguration configurationCreate(RedirectionPaymentRequest request) {
        return new RequestConfiguration(
                request.getContractConfiguration()
                , request.getEnvironment()
                , request.getPartnerConfiguration()
        );
    }

}
