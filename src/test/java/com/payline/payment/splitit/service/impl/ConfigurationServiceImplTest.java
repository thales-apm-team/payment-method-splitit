package com.payline.payment.splitit.service.impl;

import com.google.gson.GsonBuilder;
import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.bean.response.LoginResponse;
import com.payline.payment.splitit.exception.InvalidDataException;
import com.payline.payment.splitit.utils.Constants;
import com.payline.payment.splitit.utils.http.HttpClient;
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
    }

    @Test
    void check() {
        LoginResponse loginResponse = new GsonBuilder().create().fromJson(MockUtils.responseLogin(), LoginResponse.class);
        Mockito.doReturn(loginResponse).when(client).checkConnection(any(), any());
        Map errors = configurationServiceImpl.check(MockUtils.aContractParametersCheckRequest());
        Assertions.assertEquals(0, errors.size());
    }

    @Test
    void checkLoginKO() {
        LoginResponse loginResponse = new GsonBuilder().create().fromJson(MockUtils.responseLogin(), LoginResponse.class);
        Mockito.doThrow(new InvalidDataException("erreur?")).when(client).checkConnection(any(), any());
        Map errors = configurationServiceImpl.check(MockUtils.aContractParametersCheckRequest());
        Assertions.assertEquals(1, errors.size());
        Assertions.assertNotNull(errors.get(ContractParametersCheckRequest.GENERIC_ERROR));
    }

    @Test
    void checkEmptyPassword() {
        LoginResponse loginResponse = new GsonBuilder().create().fromJson(MockUtils.responseLogin(), LoginResponse.class);
        Mockito.doReturn(loginResponse).when(client).checkConnection(any(), any());
        Map<String, String> param = new HashMap<>();
        param.put(Constants.ContractConfigurationKeys.USERNAME, "monextTest");
        ContractParametersCheckRequest request = MockUtils.aContractParametersCheckRequestBuilder()
                .withAccountInfo(param)
                .build();
        Map errors = configurationServiceImpl.check(request);
        Assertions.assertEquals(1, errors.size());
        Assertions.assertNotNull(errors.get(Constants.ContractConfigurationKeys.PASSWORD));
    }

    @Test
    void checkEmptyLogin() {
        LoginResponse loginResponse = new GsonBuilder().create().fromJson(MockUtils.responseLogin(), LoginResponse.class);
        Mockito.doReturn(loginResponse).when(client).checkConnection(any(), any());
        Map<String, String> param = new HashMap<>();
        param.put(Constants.ContractConfigurationKeys.PASSWORD, "eZ7HJddV");
        ContractParametersCheckRequest request = MockUtils.aContractParametersCheckRequestBuilder()
                .withAccountInfo(param)
                .build();
        Map errors = configurationServiceImpl.check(request);
        Assertions.assertEquals(1, errors.size());
        Assertions.assertNotNull(errors.get(Constants.ContractConfigurationKeys.USERNAME));
    }

    @Test
    void checkLoginResponseKO() {
        LoginResponse loginResponse = new GsonBuilder().create().fromJson(MockUtils.responseLoginKO(), LoginResponse.class);
        Mockito.doReturn(loginResponse).when(client).checkConnection(any(), any());
        Map errors = configurationServiceImpl.check(MockUtils.aContractParametersCheckRequest());
        // username and password
        System.out.println(errors);
        Assertions.assertEquals(2, errors.size());
        Assertions.assertNotNull(errors.get(Constants.ContractConfigurationKeys.USERNAME));
        Assertions.assertNotNull(errors.get(Constants.ContractConfigurationKeys.PASSWORD));
    }

    @Test
    void getReleaseInformation() {
    }

    @Test
    void getName() {
    }
}