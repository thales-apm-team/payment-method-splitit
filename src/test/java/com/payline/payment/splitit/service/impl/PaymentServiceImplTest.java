package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.utils.http.HttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService = new PaymentServiceImpl();

    @Mock
    private HttpClient client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void paymentRequest() {
    }

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