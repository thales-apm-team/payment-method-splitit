package com.payline.payment.splitit.bean;

import com.google.gson.annotations.SerializedName;

public class Currency {
    @SerializedName("Symbol")
    String symbol;
    @SerializedName("Id")
    String id;
    @SerializedName("Code")
    String code;
    @SerializedName("Description")
    String description;

    public String getSymbol() {
        return symbol;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
