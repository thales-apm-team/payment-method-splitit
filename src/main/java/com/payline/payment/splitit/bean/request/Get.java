package com.payline.payment.splitit.bean.request;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.QueryCriteria;
import com.payline.payment.splitit.bean.nesteed.RequestHeader;

public class Get {
    // todo les private et le builder private
    @SerializedName("RequestHeader")
    RequestHeader requestHeader;
    @SerializedName("QueryCriteria")
    QueryCriteria queryCriteria;

    public void setSessionId(String sessionId){
        this.requestHeader.setSessionId(sessionId);
    }

    public static class GetBuilder {
        RequestHeader requestHeader;
        QueryCriteria queryCriteria;

        public GetBuilder withRequestHearder(RequestHeader requestHearder) {
            this.requestHeader = requestHearder;
            return this;
        }

        public GetBuilder withQueryCriteria(QueryCriteria queryCriteria) {
            this.queryCriteria = queryCriteria;
            return this;
        }

        public Get build() {
            Get get = new Get();
            get.requestHeader = requestHeader;
            get.queryCriteria = queryCriteria;
            return  get;
        }
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public QueryCriteria getQueryCriteria() {
        return queryCriteria;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
