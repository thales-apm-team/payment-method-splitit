package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.bean.nesteed.Amount;
import com.payline.payment.splitit.bean.nesteed.RequestHeader;
import com.payline.payment.splitit.bean.request.Refund;
import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.response.MyRefundResponse;
import com.payline.payment.splitit.utils.Constants;
import com.payline.payment.splitit.utils.http.HttpClient;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.refund.request.RefundRequest;
import com.payline.pmapi.bean.refund.response.RefundResponse;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseFailure;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseSuccess;
import com.payline.pmapi.service.RefundService;

public class RefundServiceImpl implements RefundService {
    // todo le private (relis bien partout, jss sur que j'en ai raté)
    HttpClient client = HttpClient.getInstance();

    @Override
    public RefundResponse refundRequest(RefundRequest refundRequest) {
        try {
            // todo si tu mets un constructeur pour les paymentRequest et RedirectionPaymentRequest tu peux faire pareil ici (sinon comme ca c'est bien)
            RequestConfiguration configuration = new RequestConfiguration(
                    refundRequest.getContractConfiguration()
                    , refundRequest.getEnvironment()
                    , refundRequest.getPartnerConfiguration()
            );

            RequestHeader requestHeader = new RequestHeader.RequestHeaderBuilder()
                    .withApiKey(refundRequest.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.API_KEY))
                    .withSessionId(refundRequest.getRequestContext().getSensitiveRequestData().get(Constants.RequestContextKeys.SESSION_ID))
                    .build();

            Amount amount = new Amount.AmountBuilder()
                    .withValue(refundRequest.getAmount().getAmountInSmallestUnit().toString())
                    .withCurrency(refundRequest.getAmount().getCurrency().toString())
                    .build();

            // create refund request object
            Refund refund = new Refund.RefundBuilder()
                    .withRequestHeader(requestHeader)
                    .withInstallmentPlanNumber(refundRequest.getPartnerTransactionId())
                    .withAmount(amount)
                    .withrefundStrategy(Refund.refundStrategyEnum.valueOf(refundRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.REFUND_STRATEGY).getValue()))
                    .build();

            // call http method refund
            try {
                MyRefundResponse refundResponse = client.refund(configuration, refund);

                // PaymentRefundSuccess
                if (refundResponse.getResponseHeader().isSucceeded()) {
                    return RefundResponseSuccess.RefundResponseSuccessBuilder.aRefundResponseSuccess()
                            .withPartnerTransactionId(refundResponse.getInstallmentPlan().getInstallmentPlanNumber())
                            .withStatusCode(String.valueOf(refundResponse.getInstallmentPlan().getInstallmentPlanStatus().getCode()))
                            .build();
                }
                // return RefundResponseFailure
                return RefundResponseFailure.RefundResponseFailureBuilder.aRefundResponseFailure()
                        .withErrorCode(refundResponse.getResponseHeader().getErrors().get(0).getErrorCode())
                        .withFailureCause(FailureCause.INVALID_DATA)
                        .withPartnerTransactionId(refundResponse.getInstallmentPlan().getInstallmentPlanNumber())
                        .build();
            } catch (Exception e) {// todo catch(Exception) se fait pas trop (tes 2catch font la meme chose, tu peux virer ce try/catch et garder que celui qui englobe la methode
                return RefundResponseFailure.RefundResponseFailureBuilder.aRefundResponseFailure()
                        .withFailureCause(FailureCause.INVALID_DATA)
                        .build();
            }
            // refund impossible
        } catch (Exception e) { // todo catch(Exception) se fait pas trop
            return RefundResponseFailure.RefundResponseFailureBuilder.aRefundResponseFailure()
                    .withFailureCause(FailureCause.INVALID_DATA)
                    .build();
        }
    }

    @Override
    public boolean canMultiple() {
        return false;
    }   // todo c'est vrai jte l'ai pas fait faire mais il faut documenter si on peut faire plusieurs remboursement sur une transaction et mettre true/false en fonction

    @Override
    public boolean canPartial() {
        return false;
    }// todo c'est vrai jte l'ai pas fait faire mais il faut documenter si on peut faire un remboursement partiel sur une transaction et mettre true/false en fonction
}
