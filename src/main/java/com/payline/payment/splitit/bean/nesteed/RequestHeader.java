package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class RequestHeader {
    @SerializedName("SessionId")
    String sessionId;
    @SerializedName("ApiKey")
    String apiKey;

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public static class RequestHeaderBuilder {
        String sessionId;
        String apiKey;

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
