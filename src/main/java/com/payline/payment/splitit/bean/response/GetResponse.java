package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.InstallmentPlan;

import java.util.List;

public class GetResponse extends Response {
    @SerializedName("PlansList")
    private List<InstallmentPlan> plansList;

    public List<InstallmentPlan> getPlansList() {
        return plansList;
    }
}
