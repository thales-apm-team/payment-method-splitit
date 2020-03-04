package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class PaymentWizardData {

    // ATTENTION du type "2,3,4", 1<requestedNumberOfInstallments<13
    @SerializedName("RequestedNumberOfInstallments")
    String requestednumberOfInstallments;
    @SerializedName("IsOpenedInIframe")
    boolean isOpenedInIframe;

    public static class PaymentWizardDataBuilder {
        String requestednumberOfInstallments;
        boolean isOpenedInIframe = false;

        public PaymentWizardDataBuilder withRequestednumberOfInstallments(String requestednumberOfInstallments) {
            this.requestednumberOfInstallments = requestednumberOfInstallments;
            return this;
        }

        public PaymentWizardDataBuilder withIsOpenedInIframe(boolean isOpenedInIframe) {
            this.isOpenedInIframe = isOpenedInIframe;
            return this;
        }

        public PaymentWizardData build() {
            PaymentWizardData paymentWizardData = new PaymentWizardData();
            paymentWizardData.requestednumberOfInstallments = requestednumberOfInstallments;
            paymentWizardData.isOpenedInIframe = isOpenedInIframe;
            return paymentWizardData;
        }
    }

    public String getRequestednumberOfInstallments() {
        return requestednumberOfInstallments;
    }

    public boolean isOpenedInIframe() {
        return isOpenedInIframe;
    }

}
