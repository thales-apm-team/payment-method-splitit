package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

// todo: passe en private tout ce que tu peux, plus c'est encapsulé mieux c'est
public class PlanData {
    @SerializedName("Amount")
    Amount amount;
    @SerializedName("NumberOfInstallments")
    String numberOfInstallments;
    @SerializedName("RefOrderNumber")
    String refOrderNumber;
    @SerializedName("AutoCapture")
    boolean autoCapture;
    @SerializedName("FirstInstallmentAmount")
    Amount firstInstallmentAmount;
    @SerializedName("PurchaseMethod")
    String purchaseMethode = "ECommerce";
    @SerializedName("Attempt3DSecure")
    boolean attempt3DSecure = false;
    @SerializedName("FirstChargeDate")
    Date firstChargeDate;

    // todo oubli pas le constructeur private pour ecraser le public, sinon on pourrais creer une instance vide sans passer par le Builder

    // todo: passe en private tout ce que tu peux, plus c'est encapsulé mieux c'est (meme dans le builder)
    public static class PlanDataBuilder {
        Amount amount;
        String numberOfInstallments;
        String refOrderNumber;
        boolean autoCapture;
        Amount firstInstallmentAmount;
        boolean attempt3DSecure = false;
        Date firstChargeDate;

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

    public String getPurchaseMethode() {
        return purchaseMethode;
    }

    public boolean isAttempt3DSecure() {
        return attempt3DSecure;
    }

    public Date getFirstChargeDate() {
        return firstChargeDate;
    }

}
