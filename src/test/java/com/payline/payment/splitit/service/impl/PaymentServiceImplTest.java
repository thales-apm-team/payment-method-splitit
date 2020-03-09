package com.payline.payment.splitit.service.impl;

import com.google.gson.GsonBuilder;
import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.bean.response.GetResponse;
import com.payline.payment.splitit.bean.response.InitiateResponse;
import com.payline.payment.splitit.utils.http.HttpClient;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseRedirect;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseSuccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService = new PaymentServiceImpl();

    @Mock
    private HttpClient client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    void paymentRequest() {
//        InitiateResponse initiateResponse = new GsonBuilder().create().fromJson(MockUtils.responseInitiate(), InitiateResponse.class);
//        Mockito.doReturn(initiateResponse).when(client).initiate(any(), any());
//        PaymentResponse response = paymentService.paymentRequest(
//                MockUtils.aPaylinePaymentRequest()
//        );
//        Assertions.assertEquals(PaymentResponseRedirect.class, response.getClass());
//    }

    @Test
    void initiateCall() {
    }

    @Test
    void initiateCreate() {
    }

    @Test
    void loginCreate() {
    }

    @Test
    void configurationCreate() {
    }

    @Test
    void initiateResponseSuccess() {
    }
}