package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class RequestHeader {
    @SerializedName("SessionId")
    private String sessionId;
    @SerializedName("ApiKey")
    private String apiKey;

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private RequestHeader(RequestHeaderBuilder builder) {
        sessionId = builder.sessionId;
        apiKey = builder.apiKey;
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
            return new RequestHeader(this);
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getApiKey() {
        return apiKey;
    }

}
