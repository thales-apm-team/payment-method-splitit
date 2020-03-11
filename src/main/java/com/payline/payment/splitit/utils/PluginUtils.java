package com.payline.payment.splitit.utils;


import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.request.Login;
import com.payline.payment.splitit.bean.response.LoginResponse;
import com.payline.payment.splitit.utils.http.HttpClient;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;

public class PluginUtils {
    private static HttpClient client = HttpClient.getInstance();


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
     * Check if a String is null or empty
     *
     * @param value the String to check
     * @return
     */
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }


    /**
     * Map some errors codes for create the paymentResponseFailure
     *
     * @param errorCode
     * @return
     */
    public static PaymentResponseFailure paymentResponseFailure(String errorCode) {
        FailureCause cause;

        switch (errorCode) {
            case "599":
                cause = FailureCause.COMMUNICATION_ERROR;
                break;

            case "400":
                cause = FailureCause.INVALID_FIELD_FORMAT;
                break;

//            case "704":
//                cause = FailureCause.SESSION_EXPIRED;
//                break;

            default:
                cause = FailureCause.INVALID_DATA;
        }
        return PaymentResponseFailure.PaymentResponseFailureBuilder.aPaymentResponseFailure()
                .withErrorCode(errorCode)
                .withFailureCause(cause)
                .build();
    }


    /**
     * Try to login with the userName and the Password of the merchant
     *
     * @param configuration
     * @return client.checkConnection
     */
    public static LoginResponse tryLogin(RequestConfiguration configuration) {
        // create login request object
        Login login = new Login.LoginBuilder()
                .withUsername(configuration.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.USERNAME).getValue())
                .withPassword(configuration.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.PASSWORD).getValue())
                .build();
        // call checkout connection
        return client.checkConnection(configuration, login);
    }

}