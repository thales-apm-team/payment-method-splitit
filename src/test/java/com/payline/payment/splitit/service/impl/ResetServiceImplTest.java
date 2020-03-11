package com.payline.payment.splitit.service.impl;

import com.google.gson.GsonBuilder;
import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.bean.response.CancelResponse;
import com.payline.payment.splitit.utils.http.HttpClient;
import com.payline.pmapi.bean.reset.response.ResetResponse;
import com.payline.pmapi.bean.reset.response.impl.ResetResponseFailure;
import com.payline.pmapi.bean.reset.response.impl.ResetResponseSuccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

class ResetServiceImplTest {

    @InjectMocks
    ResetServiceImpl resetService = new ResetServiceImpl();

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
    void resetRequest() {
        CancelResponse responseCancel = new GsonBuilder().create().fromJson(MockUtils.responseCancelSuccess(), CancelResponse.class);
        Mockito.doReturn(responseCancel).when(client).cancel(any(), any());
        ResetResponse response = resetService.resetRequest(
                MockUtils.aPaylineResetRequestBuilder()
                        .withRequestContext(MockUtils.aRequestContext(sessionId, apiKey, installmentPlanNumber))
                        .build());
        Assertions.assertEquals(ResetResponseSuccess.class, response.getClass());
    }

    @Test
    void resetRequestFailure() {
        CancelResponse responseCancel = new GsonBuilder().create().fromJson(MockUtils.responseCancelFailure(), CancelResponse.class);
        Mockito.doReturn(responseCancel).when(client).cancel(any(), any());
        ResetResponse response = resetService.resetRequest(
                MockUtils.aPaylineResetRequestBuilder()
                        .withRequestContext(MockUtils.aRequestContext(sessionId, apiKey, installmentPlanNumber))
                        .build());
        Assertions.assertEquals(ResetResponseFailure.class, response.getClass());
    }

}