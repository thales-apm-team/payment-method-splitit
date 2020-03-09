package com.payline.payment.splitit.bean.nesteed;
import com.google.gson.annotations.SerializedName;

public class QueryCriteria {
    @SerializedName("InstallmentPlanNumber")
    private String installmentPlanNumber;

    private QueryCriteria() {
    }

    public static class QueryCriteriaBuilder {
        private String installmentPlanNumber;

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

}
