package com.payline.payment.splitit.bean.request;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.payline.payment.splitit.bean.nesteed.*;

public class Initiate {
    @SerializedName("RequestHeader")
    RequestHeader requestHeader;
    @SerializedName("PlanData")
    PlanData planData;
    @SerializedName("BillingAddress")
    BillingAddress billingAddress;
    @SerializedName("ConsumerData")
    ConsumerData consumerData;
    @SerializedName("PaymentWizardData")
    PaymentWizardData paymentWizardData;
    @SerializedName("RedirectUrls")
    RedirectUrl redirectUrl;
    @SerializedName("EventsEndpoints")
    EventsEndpoints eventsEndpoints;

    public void setSessionId(String sessionId){
        this.requestHeader.setSessionId(sessionId);
    }

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

    public RequestHeader getRequestHeader() {
        return requestHeader;
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
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
