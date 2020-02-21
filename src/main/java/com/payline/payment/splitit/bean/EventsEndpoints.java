package com.payline.payment.splitit.bean;

public class EventsEndpoints {
    String createSucceeded;

    public static class EnventEndpointsBuilder {
        String createSucceeded;

        public EnventEndpointsBuilder withCreateSucceeded(String createSucceeded) {
            this.createSucceeded = createSucceeded;
            return this;
        }
        public EventsEndpoints build() {
            EventsEndpoints eventsEndpoints = new EventsEndpoints();
            eventsEndpoints.createSucceeded = createSucceeded;
            return eventsEndpoints;
        }
    }
}
