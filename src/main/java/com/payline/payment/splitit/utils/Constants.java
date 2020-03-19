package com.payline.payment.splitit.utils;

/**
 * Support for constants used everywhere in the plugin sources.
 */
public class Constants {

    /**
     * Keys for the entries in ContractConfiguration map.
     */
    public static class ContractConfigurationKeys {
        public static final String USERNAME = "userName";
        public static final String PASSWORD = "password";
        public static final String NUMBEROFINSTALLMENTS = "numberOfInstallments";
        public static final String ATTEMPT3DSECURE = "attempt3DSecure";
        public static final String FIRSTCHARGEDATESHOPPER = "firstChargeDateShopper";
        public static final String REQUESTEDNUMBEROFINSTALLMENTSDEFAULT = "requestedNumberOfInstallments";
        public static final String REQUESTEDNUMBEROFINSTALLMENTS2 = "requestedNumberOfInstallments";
        public static final String REQUESTEDNUMBEROFINSTALLMENTS3 = "requestedNumberOfInstallments";
        public static final String REQUESTEDNUMBEROFINSTALLMENTS4 = "requestedNumberOfInstallments";
        public static final String REQUESTEDNUMBEROFINSTALLMENTS5 = "requestedNumberOfInstallments";
        public static final String REQUESTEDNUMBEROFINSTALLMENTS6 = "requestedNumberOfInstallments";
        public static final String REQUESTEDNUMBEROFINSTALLMENTS7 = "requestedNumberOfInstallments";
        public static final String REQUESTEDNUMBEROFINSTALLMENTS8 = "requestedNumberOfInstallments";
        public static final String REQUESTEDNUMBEROFINSTALLMENTS9 = "requestedNumberOfInstallments";
        public static final String REQUESTEDNUMBEROFINSTALLMENTS10 = "requestedNumberOfInstallments";
        public static final String REQUESTEDNUMBEROFINSTALLMENTS11 = "requestedNumberOfInstallments";
        public static final String REQUESTEDNUMBEROFINSTALLMENTS12 = "requestedNumberOfInstallments";
        public static final String REFUNDSTRATEGY = "refundStrategy";
        public static final String REFUNDUNDERCANCELLATION = "refundUndercanCellation";
        public static final String FIRSTINSTALLMENTAMOUNT = "firstInstallmentAmount";
        public static final String FIRSTCHARGEDATENOW = "firstChargeDateNow";
        public static final String FIRSTCHARGEDATEONEWEEK = "firstChargeDateOneWeek";
        public static final String FIRSTCHARGEDATETWOWEEKS = "firstChargeDateTwoWeeks";
        public static final String FIRSTCHARGEDATEONEMONTH = "firstChargeDateOneMonth";
        public static final String FIRSTCHARGEDATETWOMONTHS = "firstChargeDateTwoMonths";


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

    public static class FormConfigurationKeys {
        public static final String OPTIONS = "cleDesOptions";

        public static final String PAYMENTBUTTONTEXT = "paymentButtonText.label";
        public static final String PAYMENTBUTTONDESC = "paymentButtonText.description";

        /* Static utility class : no need to instantiate it (Sonar bug fix) */
        private FormConfigurationKeys() {
        }
    }

    /* Static utility class : no need to instantiate it (Sonar bug fix) */
    private Constants() {
    }

}