package com.payline.payment.splitit.utils.http;

import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.bean.PlanData;
import com.payline.payment.splitit.bean.appel.Initiate;
import com.payline.payment.splitit.bean.appel.Login;
import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.response.InitiateResponse;
import com.payline.pmapi.bean.payment.ContractConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static com.payline.payment.splitit.utils.Constants.ContractConfigurationKeys.*;
import static org.mockito.ArgumentMatchers.any;

class HttpClientTest {

    @Spy
    @InjectMocks
    private HttpClient client = HttpClient.getInstance();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void checkConnection() {
        StringResponse stringResponse = MockUtils.mockStringResponse(200
                , "OK"
                , MockUtils.responseLogin()
                , null);
        Mockito.doReturn(stringResponse).when(client).post(any(), any(), any());


        RequestConfiguration configuration = new RequestConfiguration(
                MockUtils.aContractConfiguration()
                , MockUtils.anEnvironment()
                , MockUtils.aPartnerConfiguration()
        );

        ContractConfiguration contractConfiguration = MockUtils.aContractConfiguration();

        Login login = new Login.LoginBuilder().withUsername(contractConfiguration.getProperty(USERNAME).getValue())
                .withPassword(contractConfiguration.getProperty(PASSWORD).getValue()).build();
        System.out.println(login);
        Assertions.assertDoesNotThrow(() -> client.checkConnection(configuration, login));
    }

    @Test
    void initiate() {
        StringResponse stringResponse = MockUtils.mockStringResponse(200
                , "OK"
                , MockUtils.responseInitiate()
                , null);
        Mockito.doReturn(stringResponse).when(client).post(any(), any(), any());


        RequestConfiguration configuration = new RequestConfiguration(
                MockUtils.aContractConfiguration()
                , MockUtils.anEnvironment()
                , MockUtils.aPartnerConfiguration()
        );

        Initiate initiate = new Initiate.InitiateBuilder().build();
        InitiateResponse response = client.initiate(configuration, initiate);
        Assertions.assertEquals("36718353567647855177",response.getInstallmentPlan().getInstallmentPlanNumber());
    }
}