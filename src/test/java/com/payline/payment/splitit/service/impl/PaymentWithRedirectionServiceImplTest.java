package com.payline.payment.splitit.service.impl;

import com.google.gson.GsonBuilder;
import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.bean.response.GetResponse;
import com.payline.payment.splitit.utils.http.HttpClient;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseSuccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

class PaymentWithRedirectionServiceImplTest {
    @InjectMocks
    PaymentWithRedirectionServiceImpl paymentWithRedirectionService = new PaymentWithRedirectionServiceImpl();

    private static String sessionId = "9b358c4a-1237-46a7-8167-b62f66dd4a8d";
    private static String apiKey = "f661600c-5f1a-4d4c-829d-768fbc40be6c";
    private static String installmentPlanNumber = "81061838427155704842";

    @Mock
    private HttpClient client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void finalizeRedirectionPaymentOK() {
        GetResponse getResponse = new GsonBuilder().create().fromJson(MockUtils.responseGetOK("InProgress"), GetResponse.class);
        Mockito.doReturn(getResponse).when(client).get(any(), any());
        PaymentResponse response = paymentWithRedirectionService.finalizeRedirectionPayment(
                MockUtils.aRedirectionPaymentRequestBuilder()
                        .withRequestContext(MockUtils.aRequestContext(sessionId, apiKey, installmentPlanNumber))
                        .build());
        Assertions.assertEquals(PaymentResponseSuccess.class, response.getClass());
    }

    @Test
    void finalizeRedirectionPaymentNoInstallmentPlanNumber() {
        GetResponse getResponse = new GsonBuilder().create().fromJson(MockUtils.responseGetNoInstallmentPlanNumber(), GetResponse.class);
        Mockito.doReturn(getResponse).when(client).get(any(), any());
        PaymentResponse response = paymentWithRedirectionService.finalizeRedirectionPayment(
                MockUtils.aRedirectionPaymentRequestBuilder()
                        .withRequestContext(MockUtils.aRequestContext(sessionId, apiKey, null))
                        .build());
        Assertions.assertEquals(PaymentResponseFailure.class, response.getClass());
    }

    @Test
    void finalizeRedirectionPaymentNotInProgress() {
        GetResponse getResponse = new GsonBuilder().create().fromJson(MockUtils.responseGetOK("Canceled"), GetResponse.class);
        Mockito.doReturn(getResponse).when(client).get(any(), any());
        PaymentResponse response = paymentWithRedirectionService.finalizeRedirectionPayment(
                MockUtils.aRedirectionPaymentRequestBuilder()
                        .withRequestContext(MockUtils.aRequestContext(sessionId, apiKey, installmentPlanNumber))
                        .build());
        Assertions.assertEquals(PaymentResponseFailure.class, response.getClass());
    }

    @Test
    void finalizeRedirectionPaymentGetNotSuccess() {
        GetResponse getResponse = new GsonBuilder().create().fromJson(MockUtils.responseGetNotSuccess(), GetResponse.class);
        Mockito.doReturn(getResponse).when(client).get(any(), any());
        PaymentResponse response = paymentWithRedirectionService.finalizeRedirectionPayment(
                MockUtils.aRedirectionPaymentRequestBuilder()
                        .withRequestContext(MockUtils.aRequestContext(sessionId, apiKey, installmentPlanNumber))
                        .build());
        Assertions.assertEquals(PaymentResponseFailure.class, response.getClass());
    }

    @Test
    void handleSessionExpired() {
    }
}