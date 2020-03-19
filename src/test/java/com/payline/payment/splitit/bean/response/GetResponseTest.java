package com.payline.payment.splitit.bean.response;

import com.google.gson.GsonBuilder;
import com.payline.payment.splitit.MockUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetResponseTest {

    @Test
    void nonEmpty() {
        GetResponse response = new GsonBuilder().create().fromJson(MockUtils.responseGetOK("Cleared"), GetResponse.class);

        Assertions.assertNotNull(response.getPlansList().get(0).getInstallmentPlanNumber());
        Assertions.assertNotNull(response.getPlansList().get(0).getInstallmentPlanStatus());
        Assertions.assertNotNull(response.getPlansList().get(0).getInstallmentPlanStatus().getCode());
        Assertions.assertNotNull(response.getPlansList().get(0).getInstallmentPlanStatus().getDescription());
        Assertions.assertNotNull(response.getPlansList().get(0).getInstallmentPlanStatus().getId());
        Assertions.assertNotNull(response.getPlansList().get(0).getAmount().getValue());
        Assertions.assertNotNull(response.getPlansList().get(0).getAmount().getCurrency().getSymbol());
        Assertions.assertNotNull(response.getPlansList().get(0).getAmount().getCurrency().getId());
        Assertions.assertNotNull(response.getPlansList().get(0).getAmount().getCurrency().getCode());
        Assertions.assertNotNull(response.getPlansList().get(0).getAmount().getCurrency().getDescription());
        Assertions.assertNotNull(response.getPlansList().get(0).getActiveCard().getCardNumber());
        Assertions.assertNotNull(response.getPlansList().get(0).getActiveCard().getCardBrand().getCode());
        Assertions.assertNotNull(response.getPlansList().get(0).getActiveCard().getCardBrand().getDescription());
        Assertions.assertNotNull(response.getPlansList().get(0).getActiveCard().getCardExpMonth());
        Assertions.assertNotNull(response.getPlansList().get(0).getActiveCard().getCardExpYear());
        Assertions.assertNotNull(response.getPlansList().get(0).getActiveCard().getFullName());
    }

}