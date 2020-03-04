package com.payline.payment.splitit.bean.request;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.RequestHeader;

public class Cancel {
    public enum RefundUnderCancelation {
        @SerializedName("OnlyIfAFullRefundIsPossible")
        ONLY_IF_A_FULL_REFUND_IS_POSSIBLE,
        @SerializedName("NoRefunds")
        NO_REFUNDS
    }

    @SerializedName("RequestHeader")
    RequestHeader requestHeader;
    @SerializedName("InstallmentPlanNumber")
    String installmentPlanNumber;
    @SerializedName("RefundUnderCancelation")
    RefundUnderCancelation refundUnderCancelation;

    public static class CancelBuilder {
        RequestHeader requestHeader;
        String installmentPlanNumber;
        RefundUnderCancelation refundUnderCancelation;

        public CancelBuilder withRequestHeader(RequestHeader requestHeader) {
            this.requestHeader = requestHeader;
            return this;
        }

        public CancelBuilder withInstallmentPlanNumber(String installmentPlanNumber) {
            this.installmentPlanNumber = installmentPlanNumber;
            return this;
        }

        public CancelBuilder withRefundUnderCancelation(RefundUnderCancelation refundUnderCancelation) {
            this.refundUnderCancelation = refundUnderCancelation;
            return this;
        }

        public Cancel build() {
            Cancel cancel = new Cancel();
            cancel.requestHeader = requestHeader;
            cancel.installmentPlanNumber = installmentPlanNumber;
            cancel.refundUnderCancelation = refundUnderCancelation;
            return cancel;
        }
    }

    public void setSessionId(String sessionId) {
        this.requestHeader.setSessionId(sessionId);
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public String getInstallmentPlanNumber() {
        return installmentPlanNumber;
    }

    public RefundUnderCancelation getRefundUnderCancelation() {
        return refundUnderCancelation;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
