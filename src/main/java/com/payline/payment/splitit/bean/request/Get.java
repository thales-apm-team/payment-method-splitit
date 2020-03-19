package com.payline.payment.splitit.bean.request;

import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.QueryCriteria;

public class Get extends Request {
    @SerializedName("QueryCriteria")
    private QueryCriteria queryCriteria;

    private Get(GetBuilder builder) {
        super(builder);
        queryCriteria = builder.queryCriteria;
    }

    public static class GetBuilder extends RequestBuilder<Get.GetBuilder> {
        private QueryCriteria queryCriteria;

        public GetBuilder withQueryCriteria(QueryCriteria queryCriteria) {
            this.queryCriteria = queryCriteria;
            return this;
        }

        public Get build() {
            return new Get(this);
        }
    }

    public QueryCriteria getQueryCriteria() {
        return queryCriteria;
    }

    public String toString() {
        return super.toString();
    }
}
