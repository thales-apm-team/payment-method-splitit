package com.payline.payment.splitit.bean.response;

import com.google.gson.GsonBuilder;
import com.payline.payment.splitit.MockUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InitiateResponseTest {

    @Test
    void testInitChamps() {
        InitiateResponse initiateResponse = new GsonBuilder().create().fromJson(MockUtils.responseInitiate(), InitiateResponse.class);

        Assertions.assertTrue(initiateResponse.getResponseHeader().isSucceeded());
        Assertions.assertEquals(0, initiateResponse.getResponseHeader().errors.size());
        Assertions.assertNotNull(initiateResponse.getInstallmentPlan().getInstallmentPlanNumber());
        Assertions.assertNotNull(initiateResponse.getInstallmentPlan().getInstallmentPlanStatus());
        Assertions.assertNotNull(initiateResponse.getInstallmentPlan().getInstallmentPlanStatus().getId());
        Assertions.assertNotNull(initiateResponse.getInstallmentPlan().getInstallmentPlanStatus().getDescription());
        Assertions.assertNotNull(initiateResponse.getInstallmentPlan().getInstallmentPlanStatus().getCode());
        Assertions.assertNotNull(initiateResponse.getInstallmentPlan().getAmount());
        Assertions.assertNotNull(initiateResponse.getInstallmentPlan().getAmount().getValue());
        Assertions.assertNotNull(initiateResponse.getInstallmentPlan().getAmount().getCurrency());
        Assertions.assertNotNull(initiateResponse.getCheckoutUrl());
    }
}