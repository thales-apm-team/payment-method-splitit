package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class AmountResponse {
    @SerializedName("Value")
    private String value;
    @SerializedName("Currency")
    private Currency currency;

    public String getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }
}
