package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.InstallmentPlan;

import java.net.URL;

public class InitiateResponse extends Response {
    @SerializedName("CheckoutUrl")
    private URL checkoutUrl;
    @SerializedName("SessionId")
    private String sessionId;
    @SerializedName("InstallmentPlan")
    private InstallmentPlan installmentPlan;

    public URL getCheckoutUrl() {
        return checkoutUrl;
    }

    public String getSessionId() {
        return sessionId;
    }

    public InstallmentPlan getInstallmentPlan() {
        return installmentPlan;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}