package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class InstallmentPlan {
    @SerializedName("InstallmentPlanNumber")
    String installmentPlanNumber;
    @SerializedName("InstallmentPlanStatus")
    InstallmentPlanStatus installmentPlanStatus;
    @SerializedName("ActiveCard")
    ActiveCard activeCard;
    @SerializedName("Amount")
    AmountResponse amount;

    public ActiveCard getActiveCard() {
        return activeCard;
    }

    public String getInstallmentPlanNumber() {
        return installmentPlanNumber;
    }

    public InstallmentPlanStatus getInstallmentPlanStatus() {
        return installmentPlanStatus;
    }

    public AmountResponse getAmount() {
        return amount;
    }

    public static class ActiveCard {
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

    public static class AmountResponse {
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

    public static class CardBrand {
        @SerializedName("Code")
        String code;
        @SerializedName("Id")
        int id;
        @SerializedName("Description")
        String description;

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

    public static class Currency {
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

}
