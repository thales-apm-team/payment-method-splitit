package com.payline.payment.splitit.bean;

import com.google.gson.annotations.SerializedName;

public class Amount {
    @SerializedName("Value")
    String value;
    @SerializedName("CurrencyCode")
    String currencyCode;

    public static class AmountBuilder {
        String value;
        String currencyCode;

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
