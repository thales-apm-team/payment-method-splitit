package com.payline.payment.splitit.bean.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("ResponseHeader")
    ResponseHeader responseHeader;
    @SerializedName("SessionId")
    String sessionId;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public String getSessionId() {
        return sessionId;
    }

    public static class LoginResponseBuilder {
        ResponseHeader responseHeader;
        String sessionId;

        public LoginResponseBuilder withResponseHeader(ResponseHeader responseHeader) {
            this.responseHeader = responseHeader;
            return this;
        }

        public LoginResponseBuilder withSessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public LoginResponse build() {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.responseHeader = responseHeader;
            loginResponse.sessionId = sessionId;
            return loginResponse;
        }
    }
}
