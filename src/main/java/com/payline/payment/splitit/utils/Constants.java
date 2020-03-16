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
        public static String NUMBEROFINSTALLMENTS = "numberOfInstallments";
        public static String ATTEMPS3DSECURE = "attempt3DSecure";
        public static String FIRSTCHARGEDATE = "firstChargeDate";
        public static String REQUESTEDNUMBEROFINSTALLMENTSDEFAULT = "requestedNumberOfInstallments";
        public static String REQUESTEDNUMBEROFINSTALLMENTS2 = "requestedNumberOfInstallments";
        public static String REQUESTEDNUMBEROFINSTALLMENTS3 = "requestedNumberOfInstallments";
        public static String REQUESTEDNUMBEROFINSTALLMENTS4 = "requestedNumberOfInstallments";
        public static String REQUESTEDNUMBEROFINSTALLMENTS5 = "requestedNumberOfInstallments";
        public static String REQUESTEDNUMBEROFINSTALLMENTS6 = "requestedNumberOfInstallments";
        public static String REQUESTEDNUMBEROFINSTALLMENTS7 = "requestedNumberOfInstallments";
        public static String REQUESTEDNUMBEROFINSTALLMENTS8 = "requestedNumberOfInstallments";
        public static String REQUESTEDNUMBEROFINSTALLMENTS9 = "requestedNumberOfInstallments";
        public static String REQUESTEDNUMBEROFINSTALLMENTS10 = "requestedNumberOfInstallments";
        public static String REQUESTEDNUMBEROFINSTALLMENTS11 = "requestedNumberOfInstallments";
        public static String REQUESTEDNUMBEROFINSTALLMENTS12 = "requestedNumberOfInstallments";
        public static String REFUNDSTRATEGY = "refundStrategy";
        public static String REFUNDUNDERCANCELLATION = "refundUndercanCellation";
        public static String FIRSTINSTALLMENTAMOUNT = "firstInstallmentAmount";

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