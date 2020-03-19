package com.payline.payment.splitit.bean.request;

import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.bean.nesteed.EventsEndpoints;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InitiateTest {
    private String createSucceeded = "https://www.async-success.com/";


    public Initiate creation(String requestNumberOfInstallments) {
        return new Initiate.InitiateBuilder()
                .withRequestHeader(MockUtils.requestHeaderTest())
                .withPlanData(MockUtils.planDatatest())
                .withBillingAddress(MockUtils.addressTest())
                .withConsumerData(MockUtils.consumerDataTest())
                .withPaymentWizardData(MockUtils.paymentWizardDataTest(requestNumberOfInstallments))
                .withRedirectUrl(MockUtils.redirectUrlTest())
                .withEventsEndpoints(new EventsEndpoints.EventEndpointsBuilder().withCreateSucceeded(createSucceeded).build())
                .build();
    }

    @Test
    void creationTest() {
        Initiate initiate = creation(MockUtils.getRequestedNumberOfInstallments());

        Assertions.assertNotNull(initiate.getRequestHeader().getSessionId());
        Assertions.assertNotNull(initiate.getRequestHeader().getApiKey());
        Assertions.assertNotNull(initiate.getPlanData().getAmount().getValue());
        Assertions.assertNotNull(initiate.getPlanData().getAmount().getCurrencyCode());
        Assertions.assertNotNull(initiate.getPlanData().getFirstChargeDate());
        Assertions.assertNotNull(initiate.getPlanData().getFirstInstallmentAmount().getCurrencyCode());
        Assertions.assertNotNull(initiate.getPlanData().getFirstInstallmentAmount().getValue());
        Assertions.assertNotNull(initiate.getPlanData().getNumberOfInstallments());
        Assertions.assertNotNull(initiate.getPlanData().getNumberOfInstallments());
        Assertions.assertNotNull(initiate.getPlanData().getPurchaseMethod());
        Assertions.assertNotNull(initiate.getPlanData().getRefOrderNumber());
        Assertions.assertTrue(initiate.getPlanData().isAttempt3DSecure());
        Assertions.assertTrue(initiate.getPlanData().isAutoCapture());

        Assertions.assertNotNull(initiate.getBillingAddress().getAddressLine());
        Assertions.assertNotNull(initiate.getBillingAddress().getAddressLine2());
        Assertions.assertNotNull(initiate.getBillingAddress().getCity());
        Assertions.assertNotNull(initiate.getBillingAddress().getCountry());
        Assertions.assertNotNull(initiate.getBillingAddress().getZip());
        Assertions.assertNotNull(initiate.getBillingAddress().getState());
        Assertions.assertNotNull(initiate.getConsumerData().getFullName());
        Assertions.assertNotNull(initiate.getConsumerData().getEmail());
        Assertions.assertNotNull(initiate.getConsumerData().getPhoneNumber());
        Assertions.assertNotNull(initiate.getConsumerData().getCultureName());
        Assertions.assertNotNull(initiate.getPaymentWizardData().getRequestednumberOfInstallments());
        Assertions.assertEquals(initiate.getPaymentWizardData().getRequestednumberOfInstallments(), MockUtils.getRequestedNumberOfInstallments());
        Assertions.assertFalse(initiate.getPaymentWizardData().isOpenedInIframe());
        Assertions.assertNotNull(initiate.getRedirectUrl().getSucceeded());
        Assertions.assertNotNull(initiate.getRedirectUrl().getCanceled());
        Assertions.assertNotNull(initiate.getRedirectUrl().getFailed());
        Assertions.assertNotNull(initiate.getEventsEndpoints().getCreateSucceeded());


    }

    @Test
    void setSessionId() {
        Initiate initiate = creation(MockUtils.getRequestedNumberOfInstallments());
        String newSessionId = "42";

        initiate.setSessionId(newSessionId);
        Assertions.assertEquals(initiate.getRequestHeader().getSessionId(), newSessionId);
    }

    @Test
    void testToString() {
        Initiate initiate = creation(MockUtils.getRequestedNumberOfInstallments());
        String expected = MockUtils.callInitiate(MockUtils.getRequestedNumberOfInstallments(), MockUtils.getFirstChargeDateOneMonth());

        Assertions.assertEquals(expected, initiate.toString());
        Assertions.assertEquals(MockUtils.getRequestedNumberOfInstallments(), initiate.getPaymentWizardData().getRequestednumberOfInstallments());
    }

    @Test
    void testToStringRequestedNumberOfInstallmentDefault() {
        Initiate initiate = creation(MockUtils.getRequestedNumberOfInstallmentsDefault());
        String expected = MockUtils.callInitiate(MockUtils.getRequestedNumberOfInstallmentsDefault(), MockUtils.getFirstChargeDateOneMonth());

        Assertions.assertEquals(expected, initiate.toString());
        Assertions.assertEquals(MockUtils.getRequestedNumberOfInstallmentsDefault(), initiate.getPaymentWizardData().getRequestednumberOfInstallments());
    }

    @Test
    void testToStringRequestedNumberOfInstallment() {
        Initiate initiate = creation(MockUtils.getRequestedNumberOfInstallments());
        String expected = MockUtils.callInitiate(MockUtils.getRequestedNumberOfInstallments(), MockUtils.getFirstChargeDateOneMonth());

        Assertions.assertEquals(expected, initiate.toString());
        Assertions.assertEquals(MockUtils.getRequestedNumberOfInstallments(), initiate.getPaymentWizardData().getRequestednumberOfInstallments());
    }

    @Test
    void testToStringFirstChargeDateOneMonth() {
        Initiate initiate = creation(MockUtils.getRequestedNumberOfInstallments());
        String expected = MockUtils.callInitiate(MockUtils.getRequestedNumberOfInstallments(), MockUtils.getFirstChargeDateOneMonth());

        Assertions.assertEquals(expected, initiate.toString());
        Assertions.assertEquals(MockUtils.getFirstChargeDateOneMonth(), initiate.getPlanData().getFirstChargeDate());
    }
}