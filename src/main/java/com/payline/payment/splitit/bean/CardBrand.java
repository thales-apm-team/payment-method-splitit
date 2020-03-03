package com.payline.payment.splitit.bean;

import com.google.gson.annotations.SerializedName;

public class CardBrand {
    @SerializedName("Code")
    String code;
    @SerializedName("Id")
    int id;
    @SerializedName("Description")
    String description;

//    public static class CardBrandBuilder {
//        String code;
//        int id;
//        String description;
//
//        public CardBrandBuilder withCode(String code) {
//            this.code = code;
//            return this;
//        }
//
//        public CardBrandBuilder withId(int id) {
//            this.id = id;
//            return this;
//        }
//
//        public CardBrandBuilder withDescription(String description) {
//            this.description = description;
//            return this;
//        }
//
//        public CardBrand build() {
//            CardBrand cardBrand = new CardBrand();
//            cardBrand.code = code;
//            cardBrand.description = description;
//            cardBrand.id = id;
//            return cardBrand;
//        }
//    }

    public String getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

}
