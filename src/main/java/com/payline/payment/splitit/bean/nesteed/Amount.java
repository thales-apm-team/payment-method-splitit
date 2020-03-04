package com.payline.payment.splitit.bean.nesteed;

import com.google.gson.annotations.SerializedName;

// todo: passe en private tout ce que tu peux, plus c'est encapsulé mieux c'est
public class Amount {
    @SerializedName("Value")
    String value;
    @SerializedName("CurrencyCode")
    String currencyCode;

    // todo oubli pas le constructeur private pour ecraser le public, sinon on pourrais creer une instance vide sans passer par le Builder

    // todo: passe en private tout ce que tu peux, plus c'est encapsulé mieux c'est (meme dans le builder)
    public static class AmountBuilder {
        String value;
        String currencyCode;

        public AmountBuilder withValue(String value) {
            this.value = value;
            return this;
        }

        public AmountBuilder withCurrency(String currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public Amount build() {
            Amount amount = new Amount();
            amount.value = value;
            amount.currencyCode = currencyCode;
            return amount;
        }
    }

    public String getValue() {
        return value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

}
