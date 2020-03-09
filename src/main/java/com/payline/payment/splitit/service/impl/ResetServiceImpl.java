package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.request.Cancel;
import com.payline.payment.splitit.bean.request.RequestHeader;
import com.payline.payment.splitit.bean.response.CancelResponse;
import com.payline.payment.splitit.exception.InvalidDataException;
import com.payline.payment.splitit.exception.PluginException;
import com.payline.payment.splitit.utils.Constants;
import com.payline.payment.splitit.utils.http.HttpClient;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.reset.request.ResetRequest;
import com.payline.pmapi.bean.reset.response.ResetResponse;
import com.payline.pmapi.bean.reset.response.impl.ResetResponseFailure;
import com.payline.pmapi.bean.reset.response.impl.ResetResponseSuccess;
import com.payline.pmapi.logger.LogManager;
import com.payline.pmapi.service.ResetService;
import org.apache.logging.log4j.Logger;


public class ResetServiceImpl implements ResetService {
    private HttpClient client = HttpClient.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(ResetServiceImpl.class);

    @Override
    public ResetResponse resetRequest(ResetRequest resetRequest) {
        // create reset request object
        try {
            RequestConfiguration configuration = RequestConfiguration.build(resetRequest);

            if (resetRequest.getRequestContext() == null || resetRequest.getRequestContext().getSensitiveRequestData() == null
                    || resetRequest.getRequestContext().getSensitiveRequestData().get(Constants.RequestContextKeys.SESSION_ID) == null) {
                throw new InvalidDataException("Missing or Invalid RedirectionPaymentRequest.requestContext");
            }

            if (resetRequest.getPartnerTransactionId() == null) {
                throw new InvalidDataException("Missing or Invalid ResetService.partnerTransactionId");
            }

            if (resetRequest.getContractConfiguration() == null
                    || Cancel.RefundUnderCancellation.valueOf(resetRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.REFUND_UNDER_CANCELLATION).getValue()) != Cancel.RefundUnderCancellation.ONLY_IF_A_FULL_REFUND_IS_POSSIBLE
                    || Cancel.RefundUnderCancellation.valueOf(resetRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.REFUND_UNDER_CANCELLATION).getValue()) != Cancel.RefundUnderCancellation.NO_REFUNDS) {

            }

            RequestHeader requestHeader = new RequestHeader.RequestHeaderBuilder()
                    .withSessionId(resetRequest.getRequestContext().getSensitiveRequestData().get(Constants.RequestContextKeys.SESSION_ID))
                    .withApiKey(resetRequest.getPartnerConfiguration().getProperty(Constants.PartnerConfigurationKeys.API_KEY))
                    .build();

            Cancel cancel = new Cancel.CancelBuilder()
                    .withRequestHeader(requestHeader)
                    .withInstallmentPlanNumber(resetRequest.getPartnerTransactionId())
                    .withRefundUnderCancellation(Cancel.RefundUnderCancellation.valueOf(resetRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.REFUND_UNDER_CANCELLATION).getValue()))
                    .build();

            // call http method reset
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
        } catch (PluginException e) {
            return e.toResetResponseFailureBuilder().build();
        } catch (RuntimeException e) {
            // ResetResponseFailure
            LOGGER.error("unexpected plugin error");
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