package com.payline.payment.splitit.bean.request;

import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.*;

public class Initiate extends Request {

    @SerializedName("PlanData")
    private PlanData planData;
    @SerializedName("BillingAddress")
    private BillingAddress billingAddress;
    @SerializedName("ConsumerData")
    private ConsumerData consumerData;
    @SerializedName("PaymentWizardData")
    private PaymentWizardData paymentWizardData;
    @SerializedName("RedirectUrls")
    private RedirectUrl redirectUrl;
    @SerializedName("EventsEndpoints")
    private EventsEndpoints eventsEndpoints;

    private Initiate(InitiateBuilder builder) {
        super(builder);
        planData = builder.planData;
        billingAddress = builder.billingAddress;
        consumerData = builder.consumerData;
        paymentWizardData = builder.paymentWizardData;
        redirectUrl = builder.redirectUrl;
        eventsEndpoints = builder.eventsEndpoints;
    }

    public static class InitiateBuilder extends RequestBuilder<InitiateBuilder> {
        private PlanData planData;
        private BillingAddress billingAddress;
        private ConsumerData consumerData;
        private PaymentWizardData paymentWizardData;
        private RedirectUrl redirectUrl;
        private EventsEndpoints eventsEndpoints;

        public InitiateBuilder withPlanData(PlanData planData) {
            this.planData = planData;
            return this;
        }

        public InitiateBuilder withBillingAddress(BillingAddress billingAddress) {
            this.billingAddress = billingAddress;
            return this;
        }

        public InitiateBuilder withConsumerData(ConsumerData consumerData) {
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

        public InitiateBuilder withEventsEndpoints(EventsEndpoints eventsEndpoints) {
            this.eventsEndpoints = eventsEndpoints;
            return this;
        }

        public Initiate build() {
            return new Initiate(this);
        }
    }

    public PlanData getPlanData() {
        return planData;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public ConsumerData getConsumerData() {
        return consumerData;
    }

    public PaymentWizardData getPaymentWizardData() {
        return paymentWizardData;
    }

    public RedirectUrl getRedirectUrl() {
        return redirectUrl;
    }

    public EventsEndpoints getEventsEndpoints() {
        return eventsEndpoints;
    }

    public String toString() {
        return super.toString();
    }

}
