package com.payline.payment.splitit.bean.request;

import com.google.gson.annotations.SerializedName;

public class RequestHeader {
    @SerializedName("SessionId")
    private String sessionId;
    @SerializedName("ApiKey")
    private String apiKey;

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private RequestHeader() {
    }

    public static class RequestHeaderBuilder {
        private String sessionId;
        private String apiKey;

        public RequestHeaderBuilder withSessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public RequestHeaderBuilder withApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public RequestHeader build() {
            RequestHeader requestHeader = new RequestHeader();
            requestHeader.sessionId = sessionId;
            requestHeader.apiKey = apiKey;
            return requestHeader;
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getApiKey() {
        return apiKey;
    }

}
