package com.payline.payment.splitit.bean;

import com.payline.pmapi.bean.common.Amount;

import java.math.BigInteger;
import java.util.Currency;
import java.util.Locale;

public class AmountParse {
    private static Locale locale = Locale.getDefault();

    // can't create an amountParse object
    private AmountParse() {
    }

    // put the coma on the right place depending of the currency of Monext
    // add 0 if needed, before or after
    public static String split(Amount amount) {
        String chain = amount.getAmountInSmallestUnit().toString();
        int currency = amount.getCurrency().getDefaultFractionDigits();

        if (currency == 0) {
            return chain;
        }

        if (chain.length() <= currency) {
            while (chain.length() < currency + 1) {
                chain = "0" + chain;
            }
        }
        return chain.substring(0, chain.length() - currency) + "." + chain.substring(chain.length() - currency);
    }

    public static String split(Double amount, Currency currency) {
        String chain = String.valueOf(amount);
        if (currency.getDefaultFractionDigits() == 0) {
            return chain;
        }
        String tmp = "";
        int indexDot = chain.indexOf('.');

        return tmp + chain.substring(0, chain.length() - currency.getDefaultFractionDigits() - indexDot + 1)
        + "." + chain.substring(chain.length() - currency.getDefaultFractionDigits() - indexDot + 1, chain.length() - indexDot + 1)
        + chain.substring(chain.length() - indexDot + 2);
    }


    // delete the coma for Monext API
    public static BigInteger transfoBigInt(String amount) {
        if (!amount.contains(".")) {
            return BigInteger.valueOf(Integer.parseInt(amount));
        }
        return BigInteger.valueOf(Integer.parseInt(amount.substring(0, amount.indexOf('.')) + amount.substring(amount.indexOf('.') + 1)));
    }
}
