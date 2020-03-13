package com.payline.payment.splitit.service.impl;

import com.google.gson.GsonBuilder;
import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.bean.response.LoginResponse;
import com.payline.payment.splitit.exception.InvalidDataException;
import com.payline.payment.splitit.utils.Constants;
import com.payline.payment.splitit.utils.http.HttpClient;
import com.payline.payment.splitit.utils.properties.ReleaseProperties;
import com.payline.pmapi.bean.configuration.ReleaseInformation;
import com.payline.pmapi.bean.configuration.parameter.AbstractParameter;
import com.payline.pmapi.bean.configuration.request.ContractParametersCheckRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class ConfigurationServiceImplTest {
    @InjectMocks
    ConfigurationServiceImpl configurationServiceImpl = new ConfigurationServiceImpl();
    @Mock
    private ReleaseProperties releaseProperties;
    Locale locale = Locale.getDefault();

    @Mock
    private HttpClient client;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getParameters() {
        List<AbstractParameter> liste = configurationServiceImpl.getParameters(locale);

        // then: each parameter has a unique key, a label and a description. List box parameters have at least 1 possible value.
        List<String> keys = new ArrayList<String>();
        for (AbstractParameter param : liste) {
            // 2 different parameters should not have the same key
            assertFalse(keys.contains(param.getKey()));
            keys.add(param.getKey());

            // each parameter should have a label and a description
            assertNotNull(param.getLabel());
            assertFalse(param.getLabel().contains("???"));
            assertNotNull(param.getDescription());
            assertFalse(param.getDescription().contains("???"));

//             in case of a ListBoxParameter, it should have at least 1 value
//            if( param instanceof ListBoxParameter){
//                assertFalse( ((ListBoxParameter) param).getList().isEmpty() );
//            }
        }
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
        Assertions.assertEquals(2, errors.size());
        Assertions.assertNotNull(errors.get(Constants.ContractConfigurationKeys.USERNAME));
        Assertions.assertNotNull(errors.get(Constants.ContractConfigurationKeys.PASSWORD));
    }

    @Test
    void getReleaseInformation() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String version = "M.m.p";

        // given: the release properties are OK
        doReturn(version).when(releaseProperties).get("release.version");
        Calendar cal = new GregorianCalendar();
        cal.set(2019, Calendar.AUGUST, 19);
        doReturn(formatter.format(cal.getTime())).when(releaseProperties).get("release.date");

        // when: calling the method getReleaseInformation
        ReleaseInformation releaseInformation = configurationServiceImpl.getReleaseInformation();

        // then: releaseInformation contains the right values
        assertEquals(version, releaseInformation.getVersion());
        assertEquals(2019, releaseInformation.getDate().getYear());
        assertEquals(Month.AUGUST, releaseInformation.getDate().getMonth());
        assertEquals(19, releaseInformation.getDate().getDayOfMonth());
    }

    @Test
    void getName() {
        // when: calling the method getName
        String name = configurationServiceImpl.getName(Locale.getDefault());

        // then: the method returns the name
        assertNotNull(name);
    }
}