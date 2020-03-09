package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

public class InstallmentPlan {
    @SerializedName("InstallmentPlanNumber")
    private String installmentPlanNumber;
    @SerializedName("InstallmentPlanStatus")
    private InstallmentPlanStatus installmentPlanStatus;
    @SerializedName("ActiveCard")
    private ActiveCard activeCard;
    @SerializedName("Amount")
    private AmountResponse amount;

    private InstallmentPlan() {
    }


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

    public static class AmountResponse {
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

    public static class CardBrand {
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

    public static class Currency {
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

}
