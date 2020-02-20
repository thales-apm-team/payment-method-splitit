package com.payline.payment.moneytrack.bean;

import com.payline.pmapi.bean.common.Amount;

import java.util.Date;

public class PlanData {
    Amount amount;
    int numberOfInstallments;
    String refOrderNumber;
    Boolean autoCapture;
    Amount firstInstallmentAmount;
    String purchaseMethode = "ECommerce";
    Boolean attempt3DSecure = false;
    Date firstChargeDate;

    public static class PlanDataBuilder {
        Amount amount;
        int numberOfInstallments;
        String refOrderNumber;
        Boolean autoCapture;
        Amount firstInstallmentAmount;
        Boolean attempt3DSecure = false;
        Date firstChargeDate;

        public PlanDataBuilder withAmount(Amount amount) {
            this.amount = amount;
            return this;
        }

        public PlanDataBuilder withNumberOfInstallments(int numberOfInstallments) {
            this.numberOfInstallments = numberOfInstallments;
            return this;
        }

        public PlanDataBuilder withRefOrderNumber(String refOrderNumber) {
            this.refOrderNumber = refOrderNumber;
            return this;
        }

        public PlanDataBuilder withAutoCapture(Boolean autoCapture) {
            this.autoCapture = autoCapture;
            return this;
        }

        public PlanDataBuilder withFirstInstallmentAmount(Amount firstInstallmentAmount) {
            this.firstInstallmentAmount = firstInstallmentAmount;
            return this;
        }

        public PlanDataBuilder withAttempt3DSecure(Boolean attempt3DSecure) {
            this.attempt3DSecure = attempt3DSecure;
            return this;
        }

        public PlanDataBuilder withFirstChargeDate(Date firstChargeDate) {
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

    @Override
    public String toString() {
        return "PlanData{" +
                "amount=" + amount +
                ", numberOfInstallments=" + numberOfInstallments +
                ", refOrderNumber='" + refOrderNumber + '\'' +
                ", autoCapture=" + autoCapture +
                ", firstInstallmentAmount=" + firstInstallmentAmount +
                ", purchaseMethode='" + purchaseMethode + '\'' +
                ", attempt3DSecure=" + attempt3DSecure +
                ", firstChargeDate=" + firstChargeDate +
                '}';
    }
}
