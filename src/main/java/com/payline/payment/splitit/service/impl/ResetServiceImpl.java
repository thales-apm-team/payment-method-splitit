package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.bean.RequestHeader;
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

    HttpClient client = HttpClient.getInstance();

    @Override
    public ResetResponse resetRequest(ResetRequest resetRequest) {
        // construire l'objet reset
        try {

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

        // faire l'appel http
            try {
                CancelResponse cancelResponse = client.cancel(configuration, cancel);
                // response success
                if (cancelResponse.getResponseHeader().isSucceeded()) {
                    return ResetResponseSuccess.ResetResponseSuccessBuilder.aResetResponseSuccess()
                            .withPartnerTransactionId(cancelResponse.getInstallmentPlan().getInstallmentPlanNumber())
                            .withStatusCode(String.valueOf(cancelResponse.getInstallmentPlan().getInstallmentPlanStatus().getCode()))
                            .build();
                    // response failure
                } else {
                    return ResetResponseFailure.ResetResponseFailureBuilder.aResetResponseFailure()
                            .withErrorCode(cancelResponse.getResponseHeader().getErrors().get(0).getErrorCode())
                            .withFailureCause(FailureCause.INVALID_DATA)
                            .withPartnerTransactionId(cancelResponse.getInstallmentPlan().getInstallmentPlanNumber())
                            .build();
                }
            } catch (Exception e) {
                // response failure
                return ResetResponseFailure.ResetResponseFailureBuilder.aResetResponseFailure()
                        .withFailureCause(FailureCause.INVALID_DATA)
                        .build();
            }
        } catch (Exception e) {
            return ResetResponseFailure.ResetResponseFailureBuilder.aResetResponseFailure()
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
