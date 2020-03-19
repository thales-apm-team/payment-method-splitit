package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class Currency {
    @SerializedName("Symbol")
    private String symbol;
    @SerializedName("Id")
    private String id;
    @SerializedName("Code")
    private String code;
    @SerializedName("Description")
    private String description;

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
