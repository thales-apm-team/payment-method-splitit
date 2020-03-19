package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class QueryCriteria {
    @SerializedName("InstallmentPlanNumber")
    private String installmentPlanNumber;

    private QueryCriteria(QueryCriteriaBuilder builder) {
        installmentPlanNumber = builder.installmentPlanNumber;
    }

    public static class QueryCriteriaBuilder {
        private String installmentPlanNumber;

        public QueryCriteriaBuilder withInstallmentPlanNumber(String installmentPlanNumber) {
            this.installmentPlanNumber = installmentPlanNumber;
            return this;
        }

        public QueryCriteria build() {
            return new QueryCriteria(this);
        }
    }

    public String getInstallmentPlanNumber() {
        return installmentPlanNumber;
    }

}
