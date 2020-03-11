package com.payline.payment.splitit.bean.nesteed;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AmountTest {

    @Test
    void testToString() {
        Amount amount = new Amount.AmountBuilder().withCurrency("EUR").withValue("1234").build();
        String expected = "Amount{value='1234', currencyCode='EUR'}";
        Assertions.assertEquals(expected, amount.toString());
    }
}