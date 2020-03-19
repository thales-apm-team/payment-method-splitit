package com.payline.payment.splitit.bean.response;

import com.google.gson.GsonBuilder;
import com.payline.payment.splitit.MockUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LoginResponseTest {

    @Test
    void nonEmpty() {
        LoginResponse response = new GsonBuilder().create().fromJson(MockUtils.responseLogin(), LoginResponse.class);

        Assertions.assertNotNull(response.getResponseHeader());
        Assertions.assertTrue(response.getResponseHeader().isSucceeded());
        Assertions.assertNull(response.getResponseHeader().getErrors());
        Assertions.assertNotNull(response.getSessionId());
    }

    @Test
    void nonEmptyKO() {
        LoginResponse response = new GsonBuilder().create().fromJson(MockUtils.responseLoginKO(), LoginResponse.class);

        Assertions.assertNotNull(response.getResponseHeader());
        Assertions.assertFalse(response.getResponseHeader().isSucceeded());
        Assertions.assertNotNull(response.getResponseHeader().getErrors());
        Assertions.assertNotNull(response.getResponseHeader().getErrors().get(0).getErrorCode());
        Assertions.assertNotNull(response.getResponseHeader().getErrors().get(0).getMessage());
        Assertions.assertNotNull(response.getResponseHeader().getErrors().get(0).getAdditionalInfo());
        Assertions.assertNull(response.getSessionId());
    }
}