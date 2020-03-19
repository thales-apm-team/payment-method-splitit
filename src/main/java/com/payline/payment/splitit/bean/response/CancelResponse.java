package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.InstallmentPlan;

public class CancelResponse extends Response {
    @SerializedName("InstallmentPlan")
    private InstallmentPlan installmentPlan;

    public InstallmentPlan getInstallmentPlan() {
        return installmentPlan;
    }
}
