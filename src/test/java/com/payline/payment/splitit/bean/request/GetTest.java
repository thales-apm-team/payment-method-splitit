package com.payline.payment.splitit.bean.request;

import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.bean.nesteed.QueryCriteria;
import com.payline.payment.splitit.bean.nesteed.RequestHeader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetTest {
    QueryCriteria queryCriteria = new QueryCriteria.QueryCriteriaBuilder()
            .withInstallmentPlanNumber(MockUtils.getInstallmentPlanNumber())
            .build();

    public Get creation() {
        return new Get.GetBuilder()
                .withRequestHearder(MockUtils.requestHeaderTest())
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
        String expected = MockUtils.callGet();

        System.out.println(get.toString());
        System.out.println(expected);

        Assertions.assertEquals(expected, get.toString());
    }
}