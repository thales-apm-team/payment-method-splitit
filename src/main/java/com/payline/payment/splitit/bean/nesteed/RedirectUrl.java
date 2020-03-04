package com.payline.payment.splitit.bean.nesteed;


import com.google.gson.annotations.SerializedName;

public class RedirectUrl {
    @SerializedName("Succeeded")
    String succeeded;
    @SerializedName("Failed")
    String failed;
    @SerializedName("Canceled")
    String canceled;


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
            RedirectUrl redirectUrl = new RedirectUrl();
            redirectUrl.succeeded = succeeded;
            redirectUrl.canceled = canceled;
            redirectUrl.failed = failed;
            return redirectUrl;
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
