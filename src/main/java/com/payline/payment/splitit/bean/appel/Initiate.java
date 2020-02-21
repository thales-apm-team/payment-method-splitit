package com.payline.payment.splitit.bean.appel;

import com.google.gson.Gson;
import com.payline.payment.splitit.bean.*;

public class Initiate {
    RequestHeader requestHeader;
    PlanData planData;
    BillingAddress billingAddress;
    ConsumerData consumerData;
    PaymentWizardData paymentWizardData;
    RedirectUrl redirectUrl;
    EventsEndpoints eventsEndpoints;

    public static class InitiateBuilder {
        RequestHeader requestHeader;
        PlanData planData;
        BillingAddress billingAddress;
        ConsumerData consumerData;
        PaymentWizardData paymentWizardData;
        RedirectUrl redirectUrl;
        EventsEndpoints eventsEndpoints;

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

        public InitiateBuilder withEventsEndpoints(EventsEndpoints eventsEndpoints) {
            this.eventsEndpoints = eventsEndpoints;
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
            initiate.eventsEndpoints = eventsEndpoints;
            return initiate;
        }
    }

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
