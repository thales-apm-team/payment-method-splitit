package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.InstallmentPlan;

public class MyRefundResponse {
    @SerializedName("ResponseHeader")
    ResponseHeader responseHeader;
    @SerializedName("InstallmentPlan")
    InstallmentPlan installmentPlan;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public InstallmentPlan getInstallmentPlan() {
        return installmentPlan;
    }
}
