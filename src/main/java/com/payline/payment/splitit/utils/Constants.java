package com.payline.payment.splitit.utils;

/**
 * Support for constants used everywhere in the plugin sources.
 */
public class Constants {

    /**
     * Keys for the entries in ContractConfiguration map.
     */
    public static class ContractConfigurationKeys {
        public static String USERNAME = "userName";
        public static String PASSWORD = "password";
        public static String NUMBER_OF_INSTALLMENTS = "numberOfInstallments";
        public static String FIRST_INSTALLMENT_AMOUNT = "firstInstallmentAmount";
        public static String ATTEMPS_3D_SECURE = "attempt3DSecure";
        public static String FIRST_CHARGE_DATE = "firstChargeDate";
        public  static String REQUESTED_NUMBER_OF_INSTALLMENTS = "requestedNumberOfInstallments";
        public  static String REFUND_STRATEGY = "refundStrategy";
        public static String REFUND_UNDER_CANCELATION = "refundUndercancelation";

        /* Static utility class : no need to instantiate it (Sonar bug fix) */
        private ContractConfigurationKeys() {
        }
    }

    /**
     * Keys for the entries in PartnerConfiguration maps.
     */
    public static class PartnerConfigurationKeys {
        public static final String URL = "URL";
        public static final String API_KEY = "Api key";

        /* Static utility class : no need to instantiate it (Sonar bug fix) */
        private PartnerConfigurationKeys() {
        }
    }

    /**
     * Keys for the entries in RequestContext data.
     */
    public static class RequestContextKeys {
        public static final String INSTALLMENT_PLAN_NUMBER = "installment plan number";
        public static final String SESSION_ID = "Session id";

        /* Static utility class : no need to instantiate it (Sonar bug fix) */
        private RequestContextKeys() {
        }
    }

    /* Static utility class : no need to instantiate it (Sonar bug fix) */
    private Constants() {
    }

}
