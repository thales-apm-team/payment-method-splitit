package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class Amount {
    @SerializedName("Value")
    private String value;
    @SerializedName("CurrencyCode")
    private String currencyCode;

    private  Amount() {
    }

    public static class AmountBuilder {
        private String value;
        private String currencyCode;

        public AmountBuilder withValue(String value) {
            this.value = value;
            return this;
        }

        public AmountBuilder withCurrency(String currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public Amount build() {
            Amount amount = new Amount();
            amount.value = value;
            amount.currencyCode = currencyCode;
            return amount;
        }
    }

    public String getValue() {
        return value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

}
