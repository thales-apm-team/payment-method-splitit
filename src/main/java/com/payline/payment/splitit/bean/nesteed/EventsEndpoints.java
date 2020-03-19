package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class EventsEndpoints {
    @SerializedName("CreateSucceeded")
    private String createSucceeded;

    private EventsEndpoints(EventEndpointsBuilder builder) {
        createSucceeded = builder.createSucceeded;
    }

    public static class EventEndpointsBuilder {
        private String createSucceeded;

        public EventEndpointsBuilder withCreateSucceeded(String createSucceeded) {
            this.createSucceeded = createSucceeded;
            return this;
        }

        public EventsEndpoints build() {
            return new EventsEndpoints(this);
        }
    }

    public String getCreateSucceeded() {
        return createSucceeded;
    }

}
