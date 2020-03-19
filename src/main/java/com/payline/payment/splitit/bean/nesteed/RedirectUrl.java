package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class RedirectUrl {
    @SerializedName("Succeeded")
    private String succeeded;
    @SerializedName("Failed")
    private String failed;
    @SerializedName("Canceled")
    private String canceled;

    private RedirectUrl(RedirectUrlBuilder builder) {
        succeeded = builder.succeeded;
        failed = builder.failed;
        canceled = builder.canceled;
    }

    public static class RedirectUrlBuilder {
        String succeeded;
        String failed;
        String canceled;


        public RedirectUrlBuilder withSucceeded(String succeeded) {
            this.succeeded = succeeded;
            return this;
        }

        public RedirectUrlBuilder withCanceled(String canceled) {
            this.canceled = canceled;
            return this;
        }

        public RedirectUrlBuilder withFailed(String failed) {
            this.failed = failed;
            return this;
        }

        public RedirectUrl build() {
            return new RedirectUrl(this);
        }
    }

    public String getSucceeded() {
        return succeeded;
    }

    public String getCanceled() {
        return canceled;
    }

    public String getFailed() {
        return failed;
    }


}
