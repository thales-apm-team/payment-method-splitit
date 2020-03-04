package com.payline.payment.splitit.bean.response;

import com.google.gson.GsonBuilder;
import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.bean.InstallmentPlanStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CancelResponseTest {

    @Test
    void nonEmpty() {
        CancelResponse cancelResponse = new GsonBuilder().create().fromJson(MockUtils.responseCancelSuccess(), CancelResponse.class);

        Assertions.assertNotNull(cancelResponse.getResponseHeader());
        Assertions.assertTrue(cancelResponse.getResponseHeader().isSucceeded());
        Assertions.assertTrue(cancelResponse.getResponseHeader().getErrors().isEmpty());
        Assertions.assertNotNull(cancelResponse.getInstallmentPlan());
        Assertions.assertNotNull(cancelResponse.getInstallmentPlan().getInstallmentPlanNumber());
        Assertions.assertNotNull(cancelResponse.getInstallmentPlan().getInstallmentPlanStatus());
        Assertions.assertNotNull(cancelResponse.getInstallmentPlan().getInstallmentPlanStatus().getCode());
        Assertions.assertNotNull(cancelResponse.getInstallmentPlan().getInstallmentPlanStatus().getDescription());
        Assertions.assertEquals(MockUtils.getInstallmentPlanNumber(), cancelResponse.getInstallmentPlan().getInstallmentPlanNumber());
        Assertions.assertEquals(InstallmentPlanStatus.Code.CANCELED, cancelResponse.getInstallmentPlan().getInstallmentPlanStatus().getCode());

    }

    @Test
    void nonEmptyKO() {
        CancelResponse cancelResponse = new GsonBuilder().create().fromJson(MockUtils.responseCancelFailure(), CancelResponse.class);

        Assertions.assertNotNull(cancelResponse.getResponseHeader());
        Assertions.assertFalse(cancelResponse.getResponseHeader().isSucceeded());
        Assertions.assertFalse(cancelResponse.getResponseHeader().getErrors().isEmpty());
        Assertions.assertNotNull(cancelResponse.getInstallmentPlan());
        Assertions.assertNotNull(cancelResponse.getInstallmentPlan().getInstallmentPlanNumber());
        Assertions.assertNotNull(cancelResponse.getInstallmentPlan().getInstallmentPlanStatus());
        Assertions.assertNotNull(cancelResponse.getInstallmentPlan().getInstallmentPlanStatus().getCode());
        Assertions.assertNotNull(cancelResponse.getInstallmentPlan().getInstallmentPlanStatus().getDescription());
        Assertions.assertEquals(MockUtils.getInstallmentPlanNumber(), cancelResponse.getInstallmentPlan().getInstallmentPlanNumber());
        Assertions.assertEquals(InstallmentPlanStatus.Code.CANCELED, cancelResponse.getInstallmentPlan().getInstallmentPlanStatus().getCode());
    }

}