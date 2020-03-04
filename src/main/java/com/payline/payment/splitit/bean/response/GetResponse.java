package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.InstallmentPlan;

import java.util.List;

public class GetResponse {
    // todo les private
    @SerializedName("ResponseHeader")
    ResponseHeader responseHeader;
    @SerializedName("PlansList")
    List<InstallmentPlan> plansList;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public List<InstallmentPlan> getPlansList() {
        return plansList;
    }
}
