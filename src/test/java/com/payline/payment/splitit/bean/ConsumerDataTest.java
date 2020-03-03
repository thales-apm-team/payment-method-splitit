package com.payline.payment.splitit.bean;

import com.payline.payment.splitit.MockUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConsumerDataTest {

    public ConsumerData creation() {
        return MockUtils.consumerDataTest();
    }

    @Test
    void creationTest() {
        ConsumerData consumerData = creation();

        Assertions.assertNotNull(consumerData.getFullName());
        Assertions.assertNotNull(consumerData.getEmail());
        Assertions.assertNotNull(consumerData.getPhoneNumber());
        Assertions.assertNotNull(consumerData.getCultureName());
        Assertions.assertEquals(consumerData.getFullName(), MockUtils.getFullName());
        Assertions.assertEquals(consumerData.getEmail(), MockUtils.getEmail());
        Assertions.assertEquals(consumerData.getPhoneNumber(), MockUtils.getPhoneNumber());
        Assertions.assertEquals(consumerData.getCultureName(), MockUtils.getCultureName());
    }

    @Test
    void testToString() {
        ConsumerData consumerData = creation();
        String expected = "" +
                "{" +
                    "\"FullName\":\"" + MockUtils.getFullName() + "\"," +
                    "\"Email\":\"" + MockUtils.getEmail() + "\"," +
                    "\"PhoneNumber\":\"" + MockUtils.getPhoneNumber() + "\"," +
                    "\"CultureName\":\"" + MockUtils.getCultureName() + "\"" +
                "}";

        Assertions.assertEquals(expected, consumerData.toString());
    }
}