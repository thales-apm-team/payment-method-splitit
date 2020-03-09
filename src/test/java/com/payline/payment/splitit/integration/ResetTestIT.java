package com.payline.payment.splitit.integration;

import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.service.impl.ConfigurationServiceImpl;
import com.payline.payment.splitit.service.impl.ResetServiceImpl;
import com.payline.pmapi.bean.payment.ContractProperty;
import com.payline.pmapi.bean.payment.PaymentFormContext;
import com.payline.pmapi.bean.reset.request.ResetRequest;
import com.payline.pmapi.bean.reset.response.ResetResponse;
import com.payline.pmapi.bean.reset.response.impl.ResetResponseSuccess;
import com.payline.pmapi.integration.AbstractPaymentIntegration;
import com.payline.pmapi.service.ConfigurationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ResetTestIT extends AbstractPaymentIntegration {

    private ConfigurationService configurationService = new ConfigurationServiceImpl();
    private ResetServiceImpl resetService = new ResetServiceImpl();


    @Override
    protected Map<String, ContractProperty> generateParameterContract() {
        return null;
    }

    @Override
    protected PaymentFormContext generatePaymentFormContext() {
        return null;
    }

    @Override
    protected String payOnPartnerWebsite(String s) {
        return null;
    }

    @Override
    protected String cancelOnPartnerWebsite(String s) {
        return null;
    }

    @Test
    void resetTestIT() {
        // Login
        Map<String, String> errors = configurationService.check(MockUtils.aContractParametersCheckRequest());
        Assertions.assertEquals(0, errors.size());

        // initialise a refund
        ResetRequest request = createDefaultResetRequest();
        ResetResponse paymentResponseFromResetRequest = resetService.resetRequest(request);
        Assertions.assertEquals(ResetResponseSuccess.class, paymentResponseFromResetRequest.getClass());
    }

    public ResetRequest createDefaultResetRequest() {
        return MockUtils.aPaylineResetRequest();
    }
}
