package com.payline.payment.splitit.bean;

import com.google.gson.annotations.SerializedName;

public class InstallmentPlan {
    @SerializedName("InstallmentPlanNumber")
    String installmentPlanNumber;
    @SerializedName("InstallmentPlanStatus")
    InstallmentPlanStatus installmentPlanStatus;
    @SerializedName("ActiveCard")
    ActiveCard activeCard;
    @SerializedName("Amount")
    AmountResponse amount;
    @SerializedName("RefOrderNumber")
    String refOrderNumber;

    public ActiveCard getActiveCard() {
        return activeCard;
    }

    public String getInstallmentPlanNumber() {
        return installmentPlanNumber;
    }

    public InstallmentPlanStatus getInstallmentPlanStatus() {
        return installmentPlanStatus;
    }

    public AmountResponse getAmount() {
        return amount;
    }

    public String getRefOrderNumber() {
        return refOrderNumber;
    }
}
