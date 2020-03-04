package com.payline.payment.splitit.bean.request;

import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.bean.QueryCriteria;
import com.payline.payment.splitit.bean.RequestHeader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetTest {
    RequestHeader requestHeader = MockUtils.requestHeaderTest();
    QueryCriteria queryCriteria = new QueryCriteria.QueryCriteriaBuilder()
            .withInstallmentPlanNumber(MockUtils.getInstallmentPlanNumber())
            .build();

    public Get creation() {
        return new Get.GetBuilder()
                .withRequestHearder(requestHeader)
                .withQueryCriteria(queryCriteria)
                .build();
    }

    @Test
    void creationTest() {
        Get get = creation();

        Assertions.assertNotNull(get.getRequestHeader().getApiKey());
        Assertions.assertNotNull(get.getRequestHeader().getSessionId());
        Assertions.assertNotNull(get.getQueryCriteria().getInstallmentPlanNumber());
        Assertions.assertEquals(get.getRequestHeader().getApiKey(), MockUtils.getApiKey());
        Assertions.assertEquals(get.getRequestHeader().getSessionId(), MockUtils.getSessionId());
        Assertions.assertEquals(get.getQueryCriteria().getInstallmentPlanNumber(), MockUtils.getInstallmentPlanNumber());
    }

    @Test
    void setSessionId() {
        Get get = creation();
        String newSessionId = "42";

        get.setSessionId(newSessionId);
        Assertions.assertEquals(get.getRequestHeader().getSessionId(), newSessionId);
    }

    @Test
    void testToString() {
        Get get = creation();
        String expected =
                "{" +
                        "\"RequestHeader\":" +
                                requestHeader.toString() + "," +
                        "\"QueryCriteria\":" +
                                queryCriteria.toString() +
                        "}";

        Assertions.assertEquals(get.toString(), expected);
    }
}