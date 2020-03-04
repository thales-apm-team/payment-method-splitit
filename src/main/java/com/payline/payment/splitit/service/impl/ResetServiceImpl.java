package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.bean.nesteed.RequestHeader;
import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.request.Cancel;
import com.payline.payment.splitit.bean.response.CancelResponse;
import com.payline.payment.splitit.utils.Constants;
import com.payline.payment.splitit.utils.http.HttpClient;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.reset.request.ResetRequest;
import com.payline.pmapi.bean.reset.response.ResetResponse;
import com.payline.pmapi.bean.reset.response.impl.ResetResponseFailure;
import com.payline.pmapi.bean.reset.response.impl.ResetResponseSuccess;
import com.payline.pmapi.service.ResetService;

public class ResetServiceImpl implements ResetService {
    // todo le private
    HttpClient client = HttpClient.getInstance();

    @Override
    public ResetResponse resetRequest(ResetRequest resetRequest) {
        // create reset request object
        try {

            // todo meme remarque que le refundService, j'm bien mais si tu decides de faire des constructeurs dans RequestConfiguration, oubli pas ici
            RequestConfiguration configuration = new RequestConfiguration(
                    resetRequest.getContractConfiguration()
                    , resetRequest.getEnvironment()
                    , resetRequest.getPartnerConfiguration()
            );

            RequestHeader requestHeader = new RequestHeader.RequestHeaderBuilder()
                    .withSessionId(resetRequest.getRequestContext().getSensitiveRequestData().get(Constants.RequestContextKeys.SESSION_ID))
                    .withApiKey(resetRequest.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.API_KEY))
                    .build();

            Cancel cancel = new Cancel.CancelBuilder()
                    .withRequestHeader(requestHeader)
                    .withInstallmentPlanNumber(resetRequest.getPartnerTransactionId())
                    .withRefundUnderCancelation(Cancel.RefundUnderCancelation.valueOf(resetRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.REFUND_UNDER_CANCELATION).getValue()))
                    .build();

        // call http method reset
            try {
                CancelResponse cancelResponse = client.cancel(configuration, cancel);
                // ResetResponseSuccess
                if (cancelResponse.getResponseHeader().isSucceeded()) {
                    return ResetResponseSuccess.ResetResponseSuccessBuilder.aResetResponseSuccess()
                            .withPartnerTransactionId(cancelResponse.getInstallmentPlan().getInstallmentPlanNumber())
                            .withStatusCode(String.valueOf(cancelResponse.getInstallmentPlan().getInstallmentPlanStatus().getCode()))
                            .build();
                    // ResetResponseFailure
                } else {
                    return ResetResponseFailure.ResetResponseFailureBuilder.aResetResponseFailure()
                            .withErrorCode(cancelResponse.getResponseHeader().getErrors().get(0).getErrorCode())
                            .withFailureCause(FailureCause.INVALID_DATA)
                            .withPartnerTransactionId(cancelResponse.getInstallmentPlan().getInstallmentPlanNumber())
                            .build();
                }
            } catch (Exception e) { // todo catch(Exception) se fait pas trop + meme remarque que le RefundService
                // ResetResponseFailure
                return ResetResponseFailure.ResetResponseFailureBuilder.aResetResponseFailure()
                        .withFailureCause(FailureCause.INVALID_DATA)
                        .build();
            }
        } catch (Exception e) { // todo catch(Exception)
            // ResetResponseFailure
            return ResetResponseFailure.ResetResponseFailureBuilder.aResetResponseFailure()
                    .withFailureCause(FailureCause.INVALID_DATA)
                    .build();
        }
    }

    @Override
    public boolean canMultiple() {
        return false;
    } // todo meme consigne que pour le RefundService

    @Override
    public boolean canPartial() {
        return false;
    }// todo meme consigne que pour le RefundService (meme si une annulation partielle parait chelou
}
