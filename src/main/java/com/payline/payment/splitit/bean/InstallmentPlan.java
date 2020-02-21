package com.payline.payment.splitit.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class InstallmentPlan {
    @SerializedName("InstallmentPlanNumber")
    String installmentPlanNumber;
    @SerializedName("InstallmentPlanStatus")
    InstallmentPlanStatus installmentPlanStatus;

    public String getInstallmentPlanNumber() {
        return installmentPlanNumber;
    }

    public InstallmentPlanStatus getInstallmentPlanStatus() {
        return installmentPlanStatus;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
