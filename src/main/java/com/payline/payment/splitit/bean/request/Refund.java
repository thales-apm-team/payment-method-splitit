package com.payline.payment.splitit.bean.request;

import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.Amount;

public class Refund extends Request {
    public enum refundStrategyEnum {
        NoRefunds, FutureInstallmentsFirst, FutureInstallmentsLast, FutureInstallmentsNotAllowed
    }

    @SerializedName("InstallmentPlanNumber")
    private String installmentPlanNumber;
    @SerializedName("Amount")
    private Amount amount;
    @SerializedName("RefundStrategy")
    private refundStrategyEnum refundStrategy;

    private Refund(RefundBuilder builder) {
        super(builder);
        installmentPlanNumber = builder.installmentPlanNumber;
        amount = builder.amount;
        refundStrategy = builder.refundStrategy;
    }

    public static class RefundBuilder extends RequestBuilder<RefundBuilder> {
        private String installmentPlanNumber;
        private Amount amount;
        private refundStrategyEnum refundStrategy = refundStrategyEnum.NoRefunds;

        public RefundBuilder withInstallmentPlanNumber(String installmentPlanNumber) {
            this.installmentPlanNumber = installmentPlanNumber;
            return this;
        }

        public RefundBuilder withAmount(Amount amount) {
            this.amount = amount;
            return this;
        }

        public RefundBuilder withrefundStrategy(refundStrategyEnum refundStrategy) {
            this.refundStrategy = refundStrategy;
            return this;
        }

        public Refund build() {
            return new Refund(this);
        }
    }


    public String getInstallmentPlanNumber() {
        return installmentPlanNumber;
    }

    public Amount getAmount() {
        return amount;
    }

    public refundStrategyEnum getRefundStrategy() {
        return refundStrategy;
    }

    public String toString() {
        return super.toString();
    }
}
