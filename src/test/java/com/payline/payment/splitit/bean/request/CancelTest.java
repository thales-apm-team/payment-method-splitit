package com.payline.payment.splitit.bean.request;

import com.payline.payment.splitit.MockUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CancelTest {

    public Cancel creation() {
        return new Cancel.CancelBuilder()
                .withRequestHeader(MockUtils.requestHeaderTest())
                .withInstallmentPlanNumber(MockUtils.getInstallmentPlanNumber())
                .withRefundUnderCancelation(Cancel.RefundUnderCancelation.OnlyIfAFullRefundIsPossible)
                .build();
    }

    @Test
    void cancelNonEmpty() {
        Cancel cancel = creation();

        Assertions.assertNotNull(cancel.getRequestHeader());
        Assertions.assertNotNull(cancel.getRequestHeader().getSessionId());
        Assertions.assertNotNull(cancel.getRequestHeader().getApiKey());
        Assertions.assertNotNull(cancel.getInstallmentPlanNumber());
        Assertions.assertNotNull(cancel.getRefundUnderCancelation());
        Assertions.assertEquals(MockUtils.getSessionId(), cancel.getRequestHeader().getSessionId());
        Assertions.assertEquals(MockUtils.getApiKey(), cancel.getRequestHeader().getApiKey());
        Assertions.assertEquals(MockUtils.getInstallmentPlanNumber(), cancel.getInstallmentPlanNumber());
        Assertions.assertEquals(Cancel.RefundUnderCancelation.OnlyIfAFullRefundIsPossible, cancel.getRefundUnderCancelation());
    }

    @Test
    void testToString() {
        Cancel cancel = creation();
        String expected = MockUtils.callCancel();

        Assertions.assertEquals(expected, cancel.toString());
    }
}