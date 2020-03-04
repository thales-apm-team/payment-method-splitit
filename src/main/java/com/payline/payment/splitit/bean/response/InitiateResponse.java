package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.InstallmentPlan;

import java.net.URL;

public class InitiateResponse {
    @SerializedName("ResponseHeader")
    ResponseHeader responseHeader;
    @SerializedName("CheckoutUrl")
    URL checkoutUrl;
    @SerializedName("SessionId")
    String sessionId;
    @SerializedName("InstallmentPlan")
    InstallmentPlan installmentPlan;


    public ResponseHeader getResponseHeader() {
        return responseHeader;
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
