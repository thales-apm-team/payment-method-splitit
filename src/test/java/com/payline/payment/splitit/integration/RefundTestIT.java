package com.payline.payment.splitit.integration;

import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.service.impl.ConfigurationServiceImpl;
import com.payline.payment.splitit.service.impl.RefundServiceImpl;
import com.payline.pmapi.bean.payment.ContractProperty;
import com.payline.pmapi.bean.payment.PaymentFormContext;
import com.payline.pmapi.bean.refund.request.RefundRequest;
import com.payline.pmapi.bean.refund.response.RefundResponse;
import com.payline.pmapi.bean.refund.response.impl.RefundResponseSuccess;
import com.payline.pmapi.integration.AbstractPaymentIntegration;
import com.payline.pmapi.service.ConfigurationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class RefundTestIT extends AbstractPaymentIntegration {

    private ConfigurationService configurationService = new ConfigurationServiceImpl();
    private RefundServiceImpl refundService = new RefundServiceImpl();

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
    void refundTestIT() {
        // Login
        Map<String, String> errors = configurationService.check(MockUtils.aContractParametersCheckRequest());
        Assertions.assertEquals(0, errors.size());

        // initialise a refund
        RefundRequest request = createDefaultRefundRequest();
        RefundResponse paymentResponseFromRefundRequest = refundService.refundRequest(request);
        Assertions.assertEquals(RefundResponseSuccess.class, paymentResponseFromRefundRequest.getClass());
    }

    public RefundRequest createDefaultRefundRequest() {
        return MockUtils.aPaylineRefundRequest();
    }
}
