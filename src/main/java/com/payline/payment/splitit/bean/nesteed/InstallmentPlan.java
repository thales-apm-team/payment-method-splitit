package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class InstallmentPlan {
    @SerializedName("InstallmentPlanNumber")
    private String installmentPlanNumber;
    @SerializedName("InstallmentPlanStatus")
    private InstallmentPlanStatus installmentPlanStatus;
    @SerializedName("ActiveCard")
    private ActiveCard activeCard;
    @SerializedName("Amount")
    private AmountResponse amount;

    private InstallmentPlan() {
    }


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

}
