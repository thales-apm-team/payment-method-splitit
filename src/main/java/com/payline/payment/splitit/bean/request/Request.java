package com.payline.payment.splitit.bean.request;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.RequestHeader;

public abstract class Request {
    @SerializedName("RequestHeader")
    protected RequestHeader requestHeader;

    protected Request(RequestBuilder<?> builder) {
        requestHeader = builder.requestHeader;
    }

    public static class RequestBuilder<T extends RequestBuilder> {
        private RequestHeader requestHeader;

        public T withRequestHeader(RequestHeader requestHeader) {
            this.requestHeader = requestHeader;
            return (T) this;
        }
    }

    public void setSessionId(String sessionId) {
        requestHeader.setSessionId(sessionId);
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
