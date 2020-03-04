package com.payline.payment.splitit.bean.request;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.Amount;
import com.payline.payment.splitit.bean.nesteed.RequestHeader;

public class Refund {
    // todo les private et le builder private
    public enum refundStrategyEnum {
        NoRefunds, FutureInstallmentsFirst, FutureInstallmentsLast, FutureInstallmentsNotAllowed
    }

    @SerializedName("RequestHeader")
    RequestHeader requestHeader;
    @SerializedName("InstallmentPlanNumber")
    String installmentPlanNumber;
    @SerializedName("Amount")
    Amount amount;
    @SerializedName("RefundStrategy")
    public refundStrategyEnum refundStrategy;

    public static class RefundBuilder {
        RequestHeader requestHeader;
        String installmentPlanNumber;
        Amount amount;
        refundStrategyEnum refundStrategy = refundStrategyEnum.NoRefunds;

        public RefundBuilder withRequestHeader(RequestHeader requestHeader) {
            this.requestHeader = requestHeader;
            return this;
        }

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
            Refund refund = new Refund();
            refund.requestHeader = requestHeader;
            refund.installmentPlanNumber = installmentPlanNumber;
            refund.amount = amount;
            refund.refundStrategy = refundStrategy;
            return refund;
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

    public Amount getAmount() {
        return amount;
    }

    public refundStrategyEnum getRefundStrategy() {
        return refundStrategy;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
