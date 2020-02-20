package com.payline.payment.moneytrack.service.impl;

import com.payline.payment.moneytrack.MockUtils;
import com.payline.payment.moneytrack.utils.Constants;
import com.payline.payment.moneytrack.utils.http.HttpClient;
import com.payline.pmapi.bean.configuration.request.ContractParametersCheckRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

class ConfigurationServiceImplTest {
    @InjectMocks
    ConfigurationServiceImpl configurationServiceImpl = new ConfigurationServiceImpl();

    @Mock
    private HttpClient client;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getParameters() {
//        ConfigurationService.
    }

    @Test
    void check() {
        Mockito.doNothing().when(client).checkConnection(any(), any());
        Map errors = configurationServiceImpl.check(MockUtils.aContractParametersCheckRequest());
        Assertions.assertEquals(0, errors.size());
    }

    @Test
    void checkEmptyPassword() {
        Mockito.doNothing().when(client).checkConnection(any(), any());

         Map<String,String> param = new HashMap<>();
         param.put(Constants.ContractConfigurationKeys.USERNAME, "monextTest");
        ContractParametersCheckRequest request = MockUtils.aContractParametersCheckRequestBuilder()
                .withAccountInfo(param)
                .build();
        Map errors = configurationServiceImpl.check(request);
        Assertions.assertEquals(1, errors.size());
        Assertions.assertNotNull(errors.get(Constants.ContractConfigurationKeys.PASSWORD));
    }

    @Test
    void getReleaseInformation() {
    }

    @Test
    void getName() {
    }
}