package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class Amount {
    @SerializedName("Value")
    private String value;
    @SerializedName("CurrencyCode")
    private String currencyCode;

    private Amount(AmountBuilder builder) {
        value = builder.value;
        currencyCode = builder.currencyCode;
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
            return new Amount(this);
        }
    }

    public String getValue() {
        return value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value='" + value + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
