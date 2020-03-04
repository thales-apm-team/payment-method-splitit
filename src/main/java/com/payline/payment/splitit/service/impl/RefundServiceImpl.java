package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.bean.Amount;
import com.payline.payment.splitit.bean.RequestHeader;
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
    HttpClient client = HttpClient.getInstance();

    @Override
    public RefundResponse refundRequest(RefundRequest refundRequest) {
        try {
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

            // creer l'objet refund request
            Refund refund = new Refund.RefundBuilder()
                    .withRequestHeader(requestHeader)
                    .withInstallmentPlanNumber(refundRequest.getPartnerTransactionId())
                    .withAmount(amount)
//                    .withrefundStrategy(Refund.refundStrategyEnum.get(test))
                    .withrefundStrategy(Refund.refundStrategyEnum.valueOf(refundRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.REFUND_STRATEGY).getValue()))
//                    .withrefundStrategy(Refund.refundStrategyEnum.valueOf(refundRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.REFUND_STRATEGY)))
                    .build();

            // on appel la methode http
            try {
                MyRefundResponse refundResponse = client.refund(configuration, refund);

                // en fonction de la reponse, renvoyer une PaymentRefundSuccess ou Failure
                if (refundResponse.getResponseHeader().isSucceeded()) {
                    // return RefundResponseSuccess
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
            } catch (Exception e) {
                return RefundResponseFailure.RefundResponseFailureBuilder.aRefundResponseFailure()
                        .withFailureCause(FailureCause.INVALID_DATA)
                        .build();
            }
            // refund impossible
        } catch (Exception e) {
            return RefundResponseFailure.RefundResponseFailureBuilder.aRefundResponseFailure()
                    .withFailureCause(FailureCause.INVALID_DATA)
                    .build();
        }
    }

    @Override
    public boolean canMultiple() {
        return false;
    }

    @Override
    public boolean canPartial() {
        return false;
    }
}
