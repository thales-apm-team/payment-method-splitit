package com.payline.payment.moneytrack.bean;

public class RequestHeader {
    String sessionId;
    String apiKey;

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

    @Override
    public String toString() {
        return "RequestHeader{" +
                "sessionId='" + sessionId + '\'' +
                ", apiKey='" + apiKey + '\'' +
                '}';
    }
}
