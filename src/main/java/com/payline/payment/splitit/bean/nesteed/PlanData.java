package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class PlanData {
    @SerializedName("Amount")
    private Amount amount;
    @SerializedName("NumberOfInstallments")
    private String numberOfInstallments;
    @SerializedName("RefOrderNumber")
    private String refOrderNumber;
    @SerializedName("AutoCapture")
    private boolean autoCapture;
    @SerializedName("FirstInstallmentAmount")
    private Amount firstInstallmentAmount;
    @SerializedName("PurchaseMethod")
    private String purchaseMethod = "ECommerce";
    @SerializedName("Attempt3DSecure")
    private boolean attempt3DSecure;
    @SerializedName("FirstChargeDate")
    private String firstChargeDate;

    private PlanData() {
    }

    public static class PlanDataBuilder {
        private Amount amount;
        private String numberOfInstallments;
        private String refOrderNumber;
        private boolean autoCapture;
        private Amount firstInstallmentAmount;
        private boolean attempt3DSecure = false;
        private String firstChargeDate;

        public PlanDataBuilder withAmount(Amount amount) {
            this.amount = amount;
            return this;
        }

        public PlanDataBuilder withNumberOfInstallments(String numberOfInstallments) {
            this.numberOfInstallments = numberOfInstallments;
            return this;
        }

        public PlanDataBuilder withRefOrderNumber(String refOrderNumber) {
            this.refOrderNumber = refOrderNumber;
            return this;
        }

        public PlanDataBuilder withAutoCapture(boolean autoCapture) {
            this.autoCapture = autoCapture;
            return this;
        }

        public PlanDataBuilder withFirstInstallmentAmount(Amount firstInstallmentAmount) {
            this.firstInstallmentAmount = firstInstallmentAmount;
            return this;
        }

        public PlanDataBuilder withAttempt3DSecure(boolean attempt3DSecure) {
            this.attempt3DSecure = attempt3DSecure;
            return this;
        }

        public PlanDataBuilder withFirstChargeDate(String firstChargeDate) {
            this.firstChargeDate = firstChargeDate;
            return this;
        }

        public PlanData build() {
            PlanData planData = new PlanData();
            planData.amount = amount;
            planData.numberOfInstallments = numberOfInstallments;
            planData.refOrderNumber = refOrderNumber;
            planData.autoCapture = autoCapture;
            planData.firstInstallmentAmount = firstInstallmentAmount;
            planData.attempt3DSecure = attempt3DSecure;
            planData.firstChargeDate = firstChargeDate;
            return planData;
        }
    }

    public Amount getAmount() {
        return amount;
    }

    public String getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public String getRefOrderNumber() {
        return refOrderNumber;
    }

    public boolean isAutoCapture() {
        return autoCapture;
    }

    public Amount getFirstInstallmentAmount() {
        return firstInstallmentAmount;
    }

    public String getPurchaseMethod() {
        return purchaseMethod;
    }

    public boolean isAttempt3DSecure() {
        return attempt3DSecure;
    }

    public String getFirstChargeDate() {
        return firstChargeDate;
    }

}
