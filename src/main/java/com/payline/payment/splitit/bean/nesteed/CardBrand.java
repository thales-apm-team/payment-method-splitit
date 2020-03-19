package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class CardBrand {
    @SerializedName("Code")
    private String code;
    @SerializedName("Id")
    int id;
    @SerializedName("Description")
    private String description;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
