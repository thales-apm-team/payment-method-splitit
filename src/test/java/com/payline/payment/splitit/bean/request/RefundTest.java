package com.payline.payment.splitit.bean.request;

import com.payline.payment.splitit.MockUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RefundTest {

    public Refund creation() {
        return new Refund.RefundBuilder()
                .withRequestHeader(MockUtils.requestHeaderTest())
                .withInstallmentPlanNumber(MockUtils.getInstallmentPlanNumber())
                .withAmount(MockUtils.amountTest())
                .withrefundStrategy(Refund.refundStrategyEnum.FutureInstallmentsFirst)
                .build();
    }

    @Test
    void refundTest() {
        Refund refund = creation();

        Assertions.assertNotNull(refund.getRequestHeader());
        Assertions.assertNotNull(refund.getRequestHeader().getSessionId());
        Assertions.assertNotNull(refund.getRequestHeader().getApiKey());
        Assertions.assertNotNull(refund.getInstallmentPlanNumber());
        Assertions.assertNotNull(refund.getAmount());
        Assertions.assertNotNull(refund.getAmount().getValue());
        Assertions.assertNotNull(refund.getAmount().getCurrencyCode());
        Assertions.assertNotNull(refund.getRefundStrategy());

        Assertions.assertEquals(refund.getRequestHeader().getSessionId(), MockUtils.getSessionId());
        Assertions.assertEquals(refund.getRequestHeader().getApiKey(), MockUtils.getApiKey());
        Assertions.assertEquals(refund.getInstallmentPlanNumber(), MockUtils.getInstallmentPlanNumber());
        Assertions.assertEquals(refund.getAmount().getValue(), MockUtils.getAmountValue());
        Assertions.assertEquals(refund.getAmount().getCurrencyCode(), MockUtils.getCurrency());
        Assertions.assertEquals(Refund.refundStrategyEnum.FutureInstallmentsFirst, refund.getRefundStrategy());
    }

    @Test
    void testToString() {
        String expected = MockUtils.appelRefund();
        Refund refund = creation();

        System.out.println(expected);
        System.out.println(refund);

        Assertions.assertEquals(expected, creation().toString());
    }

    @Test
    void getValue() {
        Refund refund = creation();

        Assertions.assertEquals(Refund.refundStrategyEnum.FutureInstallmentsFirst, refund.getRefundStrategy());
    }
}