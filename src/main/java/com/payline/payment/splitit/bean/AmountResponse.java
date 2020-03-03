package com.payline.payment.splitit.bean;

import com.google.gson.annotations.SerializedName;

public class AmountResponse {
    @SerializedName("Value")
    String value;
    @SerializedName("Currency")
    Currency currency;

    public String getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }
}
