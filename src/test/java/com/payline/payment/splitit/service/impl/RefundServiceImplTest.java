package com.payline.payment.splitit.service.impl;

import com.google.gson.GsonBuilder;
import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.bean.response.MyRefundResponse;
import com.payline.payment.splitit.utils.http.HttpClient;
import com.payline.pmapi.bean.refund.response.RefundResponse;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseFailure;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseSuccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

class RefundServiceImplTest {

    @InjectMocks
    RefundServiceImpl refundService = new RefundServiceImpl();

    @Mock
    private HttpClient client;


    private static String sessionId = "9b358c4a-1237-46a7-8167-b62f66dd4a8d";
    private static String apiKey = "f661600c-5f1a-4d4c-829d-768fbc40be6c";
    private static String installmentPlanNumber = "81061838427155704842";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void refundRequestSuccess() {
        MyRefundResponse responseRefund = new GsonBuilder().create().fromJson(MockUtils.responseRefundSuccess(), MyRefundResponse.class);
        Mockito.doReturn(responseRefund).when(client).refund(any(), any());
        RefundResponse response = refundService.refundRequest(
                MockUtils.aPaylineRefundRequestBuilder()
                        .withRequestContext(MockUtils.aRequestContext(sessionId, apiKey, installmentPlanNumber))
                        .build());
        Assertions.assertEquals(RefundResponseSuccess.class, response.getClass());
    }

    @Test
    void refundRequestError() {
        MyRefundResponse responseRefund = new GsonBuilder().create().fromJson(MockUtils.responseRefundError(), MyRefundResponse.class);
        Mockito.doReturn(responseRefund).when(client).refund(any(), any());
        RefundResponse response = refundService.refundRequest(
                MockUtils.aPaylineRefundRequestBuilder()
                        .withRequestContext(MockUtils.aRequestContext(sessionId, apiKey, installmentPlanNumber))
                        .build());
        Assertions.assertEquals(RefundResponseFailure.class, response.getClass());
    }
}