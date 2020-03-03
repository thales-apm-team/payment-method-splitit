package com.payline.payment.splitit.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class QueryCriteria {
    @SerializedName("InstallmentPlanNumber")
    String installmentPlanNumber;

    public static class QueryCriteriaBuilder {
        String installmentPlanNumber;

        public QueryCriteriaBuilder withInstallmentPlanNumber(String installmentPlanNumber) {
            this.installmentPlanNumber = installmentPlanNumber;
            return this;
        }

        public QueryCriteria build() {
            QueryCriteria queryCriteria = new QueryCriteria();
            queryCriteria.installmentPlanNumber = installmentPlanNumber;
            return queryCriteria;
        }
    }

    public String getInstallmentPlanNumber() {
        return installmentPlanNumber;
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
