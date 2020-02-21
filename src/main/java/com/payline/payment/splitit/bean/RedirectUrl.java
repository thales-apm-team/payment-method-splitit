package com.payline.payment.splitit.bean;


public class RedirectUrl {
    String succeeded;
    String canceled;
    String failed;

    public static class RedirectUrlBuilder {
        String succeeded;
        String canceled;
        String failed;

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
