package com.payline.payment.splitit.utils;


import com.google.gson.JsonParser;
import com.payline.pmapi.bean.common.Amount;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Currency;
import java.util.stream.Collectors;

public class PluginUtils {
    static JsonParser parser = new JsonParser();


    private PluginUtils() {
        // ras.
    }

    public static String truncate(String value, int length) {
        if (value != null && value.length() > length) {
            value = value.substring(0, length);
        }
        return value;
    }

    /**
     * Convert an InputStream into a String
     *
     * @param stream the InputStream to convert
     * @return the converted String encoded in UTF-8
     */
    public static String inputStreamToString(InputStream stream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        return br.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Return a string which was converted from cents to currency amount
     *
     * @param amount the amount in cents
     * @return Amount as String
     */
    public static String createStringAmount(BigInteger amount, Currency currency) {
        int nbDigits = currency.getDefaultFractionDigits();

        StringBuilder sb = new StringBuilder();
        sb.append(amount);

        for (int i = sb.length(); i < 3; i++) {
            sb.insert(0, "0");
        }

        sb.insert(sb.length() - nbDigits, ".");
        return sb.toString();
    }

    public static String createStringAmount(Amount amount) {
        return createStringAmount(amount.getAmountInSmallestUnit(), amount.getCurrency());
    }

    /**
     * Check if a String is null or empty
     *
     * @param value the String to check
     * @return
     */
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }


    // todo javadoc
    public static PaymentResponseFailure paymentResponseFailure(String errorCode) {
        FailureCause cause;

        switch (errorCode) {
            case "599":
                cause = FailureCause.COMMUNICATION_ERROR;
                break;

            case "400":
                cause = FailureCause.INVALID_FIELD_FORMAT;
                break;

            case "704":
                cause = FailureCause.SESSION_EXPIRED;
                break;

            default:
                cause = FailureCause.INVALID_DATA;
        }
        return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                .withErrorCode(errorCode)
                .withFailureCause(cause)
                .build();
    }

}