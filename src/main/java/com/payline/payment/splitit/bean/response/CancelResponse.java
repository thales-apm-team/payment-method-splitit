package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.InstallmentPlan;

public class CancelResponse {
    // todo les private
    // todo faudrait faire une Class mère du genre "Response" qui contiendrait le ResponseHeader

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
