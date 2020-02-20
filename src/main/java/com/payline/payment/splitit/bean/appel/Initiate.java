package com.payline.payment.splitit.bean.appel;

import com.payline.payment.splitit.bean.*;

public class Initiate {
    RequestHeader requestHeader;
    PlanData planData;
    BillingAddress billingAddress;
    ConsumerData consumerData;
    PaymentWizardData paymentWizardData;
    RedirectUrl redirectUrl;
    String createSucceeded;

    public static class InitiateBuilder {
        RequestHeader requestHeader;
        PlanData planData;
        BillingAddress billingAddress;
        ConsumerData consumerData;
        PaymentWizardData paymentWizardData;
        RedirectUrl redirectUrl;
        String createSucceeded;

        public InitiateBuilder withRequestHeader(RequestHeader requestHeader) {
            this.requestHeader = requestHeader;
            return this;
        }

        public InitiateBuilder withPlanData(PlanData planData) {
            this.planData = planData;
            return this;
        }

        public InitiateBuilder withBillingAddress( BillingAddress billingAddress) {
            this.billingAddress = billingAddress;
            return this;
        }

        public InitiateBuilder withConsumerData (ConsumerData consumerData) {
            this.consumerData = consumerData;
            return this;
        }

        public InitiateBuilder withPaymentWizardData(PaymentWizardData paymentWizardData) {
            this.paymentWizardData = paymentWizardData;
            return this;
        }

        public InitiateBuilder withRedirectUrl(RedirectUrl redirectUrl) {
            this.redirectUrl = redirectUrl;
            return this;
        }

        public InitiateBuilder withCreateSucceeded(String createSucceeded) {
            this.createSucceeded = createSucceeded;
            return this;
        }

        public Initiate build() {
            Initiate initiate = new Initiate();
            initiate.requestHeader = requestHeader;
            initiate.planData = planData;
            initiate.billingAddress = billingAddress;
            initiate.consumerData = consumerData;
            initiate.paymentWizardData = paymentWizardData;
            initiate.redirectUrl = redirectUrl;
            initiate.createSucceeded = createSucceeded;
            return initiate;
        }
    }

    @Override
    public String toString() {
        return "Initiate{" +
                "requestHeader=" + requestHeader +
                ", billingAddress=" + billingAddress +
                ", consumerData=" + consumerData +
                ", paymentWizardData=" + paymentWizardData +
                ", redirectUrl=" + redirectUrl +
                ", createSucceeded='" + createSucceeded + '\'' +
                '}';
    }
}
