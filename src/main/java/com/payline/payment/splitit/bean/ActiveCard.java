package com.payline.payment.splitit.bean;

import com.google.gson.annotations.SerializedName;

public class ActiveCard {
    @SerializedName("CardNumber")
    String cardNumber;
    @SerializedName("CardBrand")
    CardBrand cardBrand;
    @SerializedName("CardHolderFullName")
    String fullName;
    @SerializedName("CardExpMonth")
    String cardExpMonth;
    @SerializedName("CardExpYear")
    String cardExpYear;


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
