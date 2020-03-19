package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.utils.Constants;
import com.payline.payment.splitit.utils.properties.ReleaseProperties;
import com.payline.pmapi.bean.paymentform.request.PaymentFormConfigurationRequest;
import com.payline.pmapi.bean.paymentform.response.configuration.impl.PaymentFormConfigurationResponseSpecific;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class PaymentFormConfigurationServiceImplTest {

    @InjectMocks
    private PaymentFormConfigurationServiceImpl service = new PaymentFormConfigurationServiceImpl();

    @Mock
    private ReleaseProperties releaseProperties;
    Locale locale = Locale.getDefault();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getPaymentFormConfiguration() {
        PaymentFormConfigurationRequest paymentFormConfigurationRequest = MockUtils.aPaymentFormConfigurationRequest();

        PaymentFormConfigurationResponseSpecific paymentFormConfigurationResponse = (PaymentFormConfigurationResponseSpecific) service.getPaymentFormConfiguration(paymentFormConfigurationRequest);

        Assertions.assertNotNull(paymentFormConfigurationResponse.getPaymentForm());
        System.out.println(paymentFormConfigurationResponse.getPaymentForm().getDescription());
        System.out.println(paymentFormConfigurationResponse.getPaymentForm().getButtonText());
    }
}