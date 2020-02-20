package com.payline.payment.splitit.bean;

public class PaymentWizardData {

    // ATTENTION du type "2,3,4", 1<requestedNumberOfInstallments<13
    String requestednumberOfInstallments;
    Boolean isOpenedInIframe;

    public static class PaymentWizardDataBuilder {
        String requestednumberOfInstallments;
        Boolean isOpenedInIframe = false;

        public PaymentWizardDataBuilder withRequestednumberOfInstallments(String requestednumberOfInstallments) {
            this.requestednumberOfInstallments = requestednumberOfInstallments;
            return this;
        }

        public PaymentWizardDataBuilder withIsOpenedInIframe(Boolean isOpenedInIframe) {
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

    @Override
    public String toString() {
        return "PaymentWizardData{" +
                "requestednumberOfInstallments='" + requestednumberOfInstallments + '\'' +
                ", isOpenedInIframe=" + isOpenedInIframe +
                '}';
    }
}
