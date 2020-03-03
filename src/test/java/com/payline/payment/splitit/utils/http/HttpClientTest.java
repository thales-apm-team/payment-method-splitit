package com.payline.payment.splitit.utils.http;

import com.google.gson.GsonBuilder;
import com.payline.payment.splitit.MockUtils;
import com.payline.payment.splitit.bean.RequestHeader;
import com.payline.payment.splitit.bean.appel.Get;
import com.payline.payment.splitit.bean.appel.Initiate;
import com.payline.payment.splitit.bean.appel.Login;
import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.response.GetResponse;
import com.payline.payment.splitit.bean.response.InitiateResponse;
import com.payline.payment.splitit.bean.response.LoginResponse;
import com.payline.payment.splitit.exception.InvalidDataException;
import com.payline.pmapi.bean.payment.ContractConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static com.payline.payment.splitit.utils.Constants.ContractConfigurationKeys.PASSWORD;
import static com.payline.payment.splitit.utils.Constants.ContractConfigurationKeys.USERNAME;
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
    void checkConnectionSuccess() {
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
        Assertions.assertDoesNotThrow(() -> client.checkConnection(configuration, login));
    }

    @Test
    void checkConnectionNotSuccess() {
        StringResponse stringResponse = MockUtils.mockStringResponse(400
                , "KO"
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
        Assertions.assertThrows(InvalidDataException.class, ()-> client.checkConnection(configuration, login));
    }


    @Test
    void initiateSuccess() {
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

    @Test
    void initiateNotSuccess() {
        StringResponse stringResponse = MockUtils.mockStringResponse(400
                , "KO"
                , MockUtils.responseInitiate()
                , null);
        Mockito.doReturn(stringResponse).when(client).post(any(), any(), any());

        RequestConfiguration configuration = new RequestConfiguration(
                MockUtils.aContractConfiguration()
                , MockUtils.anEnvironment()
                , MockUtils.aPartnerConfiguration()
        );

        Initiate initiate = new Initiate.InitiateBuilder().build();
        Assertions.assertThrows(InvalidDataException.class, ()-> client.initiate(configuration, initiate));
    }

    @Test
    void initiateError703() {
        StringResponse stringResponse = MockUtils.mockStringResponse(200
                , "KO"
                , MockUtils.responseError703()
                , null);

        StringResponse stringResponseOK = MockUtils.mockStringResponse(200
                , "OK"
                , MockUtils.responseInitiate()
                , null);

        LoginResponse loginResponse = new GsonBuilder().create().fromJson(MockUtils.mockStringResponse(200
                , "OK"
                , MockUtils.responseLogin()
                , null).getContent(), LoginResponse.class);

        Mockito.doReturn(loginResponse).when(client).checkConnection(any(), any());

        Mockito.doReturn(stringResponse).doReturn(stringResponseOK).when(client).post(any(), any(), any());

        RequestConfiguration configuration = new RequestConfiguration(
                MockUtils.aContractConfiguration()
                , MockUtils.anEnvironment()
                , MockUtils.aPartnerConfiguration()
        );

        Initiate initiate = new Initiate.InitiateBuilder().withRequestHeader(new RequestHeader()).build();
        client.initiate(configuration, initiate);

        InitiateResponse response = client.initiate(configuration, initiate);
        Assertions.assertEquals("36718353567647855177",response.getInstallmentPlan().getInstallmentPlanNumber());
    }

    @Test
    void getSuccess() {
        StringResponse stringResponse = MockUtils.mockStringResponse(200
                , "OK"
                , MockUtils.responseGetOK("InProgress")
                , null);
        Mockito.doReturn(stringResponse).when(client).post(any(), any(), any());


        RequestConfiguration configuration = new RequestConfiguration(
                MockUtils.aContractConfiguration()
                , MockUtils.anEnvironment()
                , MockUtils.aPartnerConfiguration()
        );

        Get get = new Get.GetBuilder().build();
        GetResponse response = client.get(configuration, get);
        Assertions.assertEquals("81061838427155704844",response.getPlansList().get(0).getInstallmentPlanNumber());
    }

    @Test
    void getNotSuccess() {
        StringResponse stringResponse = MockUtils.mockStringResponse(400
                , "KO"
                , MockUtils.responseGetOK("InProgress")
                , null);
        Mockito.doReturn(stringResponse).when(client).post(any(), any(), any());


        RequestConfiguration configuration = new RequestConfiguration(
                MockUtils.aContractConfiguration()
                , MockUtils.anEnvironment()
                , MockUtils.aPartnerConfiguration()
        );

        Get get = new Get.GetBuilder().build();
        Assertions.assertThrows(InvalidDataException.class, ()-> client.get(configuration, get));
    }

    @Test
    void getError703() {
        StringResponse stringResponse = MockUtils.mockStringResponse(200
                , "KO"
                , MockUtils.responseError703()
                , null);

        StringResponse stringResponseOK = MockUtils.mockStringResponse(200
                , "OK"
                , MockUtils.responseGetOK("InProgress")
                , null);

        LoginResponse loginResponse = new GsonBuilder().create().fromJson(MockUtils.mockStringResponse(200
                , "OK"
                , MockUtils.responseLogin()
                , null).getContent(), LoginResponse.class);

        Mockito.doReturn(loginResponse).when(client).checkConnection(any(), any());

        Mockito.doReturn(stringResponse).doReturn(stringResponseOK).when(client).post(any(), any(), any());

        RequestConfiguration configuration = new RequestConfiguration(
                MockUtils.aContractConfiguration()
                , MockUtils.anEnvironment()
                , MockUtils.aPartnerConfiguration()
        );

        Get get = new Get.GetBuilder().withRequestHearder(new RequestHeader()).build();
        GetResponse response = client.get(configuration, get);
        Assertions.assertEquals("81061838427155704844",response.getPlansList().get(0).getInstallmentPlanNumber());
    }

//    @Test
//    void verifyPaymentSuccess() {
//        StringResponse stringResponse = MockUtils.mockStringResponse(200
//                , "OK"
//                , MockUtils.responseVerifyPayment()
//                , null);
//        Mockito.doReturn(stringResponse).when(client).post(any(), any(), any());
//
//
//        RequestConfiguration configuration = new RequestConfiguration(
//                MockUtils.aContractConfiguration()
//                , MockUtils.anEnvironment()
//                , MockUtils.aPartnerConfiguration()
//        );
//
//        VerifyPayment verifyPayment = new VerifyPayment.VerifyPaymentBuilder().build();
//        VerifyPaymentResponse response = client.verifyPayment(configuration, verifyPayment);
//        Assertions.assertTrue(response.isPaid());
//    }

//    @Test
//    void verifyPaymentNotSuccess() {
//        StringResponse stringResponse = MockUtils.mockStringResponse(400
//                , "KO"
//                , MockUtils.responseVerifyPayment()
//                , null);
//        Mockito.doReturn(stringResponse).when(client).post(any(), any(), any());
//
//
//        RequestConfiguration configuration = new RequestConfiguration(
//                MockUtils.aContractConfiguration()
//                , MockUtils.anEnvironment()
//                , MockUtils.aPartnerConfiguration()
//        );
//
//        VerifyPayment verifyPayment = new VerifyPayment.VerifyPaymentBuilder().build();
//        Assertions.assertThrows(InvalidDataException.class, ()-> client.verifyPayment(configuration, verifyPayment));
//    }
}