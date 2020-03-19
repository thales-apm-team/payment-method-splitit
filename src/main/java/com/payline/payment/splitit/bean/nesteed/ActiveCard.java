package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class ActiveCard {
    @SerializedName("CardNumber")
    private String cardNumber;
    @SerializedName("CardBrand")
    private CardBrand cardBrand;
    @SerializedName("CardHolderFullName")
    private String fullName;
    @SerializedName("CardExpMonth")
    private String cardExpMonth;
    @SerializedName("CardExpYear")
    private String cardExpYear;


    public String getCardNumber() {
        return cardNumber;
    }

    public CardBrand getCardBrand() {
        return cardBrand;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCardExpMonth() {
        return cardExpMonth;
    }

    public String getCardExpYear() {
        return cardExpYear;
    }
}
