package com.payline.payment.splitit.bean.request;

import com.google.gson.annotations.SerializedName;

public class Cancel extends Request {
    public enum RefundUnderCancellation {
        OnlyIfAFullRefundIsPossible, NoRefunds;
    }

    @SerializedName("InstallmentPlanNumber")
    private String installmentPlanNumber;
    @SerializedName("RefundUnderCancelation")
    private RefundUnderCancellation refundUnderCancellation;

    private Cancel(CancelBuilder builder) {
        super(builder);
        installmentPlanNumber = builder.installmentPlanNumber;
        refundUnderCancellation = builder.refundUnderCancellation;
    }

    public static class CancelBuilder extends RequestBuilder<CancelBuilder> {
        private String installmentPlanNumber;
        private RefundUnderCancellation refundUnderCancellation;


        public CancelBuilder withInstallmentPlanNumber(String installmentPlanNumber) {
            this.installmentPlanNumber = installmentPlanNumber;
            return this;
        }

        public CancelBuilder withRefundUnderCancellation(RefundUnderCancellation refundUnderCancellation) {
            this.refundUnderCancellation = refundUnderCancellation;
            return this;
        }

        public Cancel build() {
            return new Cancel(this);
        }
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public String getInstallmentPlanNumber() {
        return installmentPlanNumber;
    }

    public RefundUnderCancellation getRefundUnderCancellation() {
        return refundUnderCancellation;
    }

    public String toString() {
        return super.toString();
    }
}