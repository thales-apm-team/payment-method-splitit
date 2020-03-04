package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class EventsEndpoints {
    @SerializedName("CreateSucceeded")
    String createSucceeded;

    public static class EventEndpointsBuilder {
        String createSucceeded;

        public EventEndpointsBuilder withCreateSucceeded(String createSucceeded) {
            this.createSucceeded = createSucceeded;
            return this;
        }
        public EventsEndpoints build() {
            EventsEndpoints eventsEndpoints = new EventsEndpoints();
            eventsEndpoints.createSucceeded = createSucceeded;
            return eventsEndpoints;
        }
    }

    public String getCreateSucceeded() {
        return createSucceeded;
    }

}
