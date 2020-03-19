package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse extends Response {
    @SerializedName("SessionId")
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

}
