package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.InstallmentPlan;

import java.net.URL;

public class InitiateResponse {
    @SerializedName("ResponseHeader")
    ResponseHeader responseHeader;
    @SerializedName("InstallmentPlanNumber")
    String installmentPlanNumber;
    @SerializedName("CheckoutUrl")
    URL checkoutUrl;
    @SerializedName("SessionId")
    String sessionId;
    @SerializedName("InstallmentPlan")
    InstallmentPlan installmentPlan;


    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public String getInstallmentPlanNumber() {
        return installmentPlanNumber;
    }

    public URL getCheckoutUrl() {
        return checkoutUrl;
    }

    public String getSessionId() {
        return sessionId;
    }

    public InstallmentPlan getInstallmentPlan() {
        return installmentPlan;
    }
}
