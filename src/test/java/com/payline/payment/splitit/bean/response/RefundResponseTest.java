package com.payline.payment.splitit.bean.response;

import com.google.gson.GsonBuilder;
import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.bean.InstallmentPlanStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RefundResponseTest {

    @Test
    void nonEmpty() {
        MyRefundResponse response = new GsonBuilder().create().fromJson(MockUtils.responseRefundSuccess(), MyRefundResponse.class);

        Assertions.assertNotNull(response.getResponseHeader());
        Assertions.assertTrue(response.getResponseHeader().isSucceeded());
        Assertions.assertTrue(response.getResponseHeader().getErrors().isEmpty());
        Assertions.assertNotNull(response.getInstallmentPlan());
        Assertions.assertNotNull(response.getInstallmentPlan().getInstallmentPlanNumber());
        Assertions.assertNotNull(response.getInstallmentPlan().getInstallmentPlanStatus());
        Assertions.assertNotNull(response.getInstallmentPlan().getInstallmentPlanStatus().getCode());
        Assertions.assertNotNull(response.getInstallmentPlan().getInstallmentPlanStatus().getDescription());
        Assertions.assertEquals(response.getInstallmentPlan().getInstallmentPlanNumber(), MockUtils.getInstallmentPlanNumber());
        Assertions.assertEquals(InstallmentPlanStatus.Code.CLEARED, response.getInstallmentPlan().getInstallmentPlanStatus().getCode());
    }

    @Test
    void nonEmptyKO() {
        MyRefundResponse response = new GsonBuilder().create().fromJson(MockUtils.responseRefundError(), MyRefundResponse.class);

        Assertions.assertNotNull(response.getResponseHeader());
        Assertions.assertFalse(response.getResponseHeader().isSucceeded());
        Assertions.assertFalse(response.getResponseHeader().getErrors().isEmpty());
        Assertions.assertNotNull(response.getResponseHeader().getErrors().get(0).getErrorCode());
        Assertions.assertNotNull(response.getResponseHeader().getErrors().get(0).getMessage());
        Assertions.assertNotNull(response.getInstallmentPlan());
        Assertions.assertNotNull(response.getInstallmentPlan().getInstallmentPlanNumber());
        Assertions.assertNotNull(response.getInstallmentPlan().getInstallmentPlanStatus());
        Assertions.assertNotNull(response.getInstallmentPlan().getInstallmentPlanStatus().getCode());
        Assertions.assertNotNull(response.getInstallmentPlan().getInstallmentPlanStatus().getDescription());
        Assertions.assertEquals(response.getInstallmentPlan().getInstallmentPlanNumber(), MockUtils.getInstallmentPlanNumber());
        Assertions.assertEquals(InstallmentPlanStatus.Code.CLEARED, response.getInstallmentPlan().getInstallmentPlanStatus().getCode());
    }
}
