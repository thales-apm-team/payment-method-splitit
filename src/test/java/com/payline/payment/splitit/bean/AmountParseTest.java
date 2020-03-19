package com.payline.payment.splitit.bean;

import com.payline.payment.splitit.MockUtils;
import com.payline.pmapi.bean.common.Amount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Currency;

class AmountParseTest {

    private double number = 123.4;
    private Currency currency = Currency.getInstance("EUR");

    @Test
    void splitFullAmount() {
        Amount amount = MockUtils.aPaylineAmount(1234);
        String parse = AmountParse.split(amount);
        Assertions.assertEquals("12.34", parse);
    }

    @Test
    void splitEqualAmount() {
        Amount amount = MockUtils.aPaylineAmount(34);
        String parse = AmountParse.split(amount);
        Assertions.assertEquals("0.34", parse);
    }

    @Test
    void splitLessAmount() {
        Amount amount = MockUtils.aPaylineAmount(4);
        String parse = AmountParse.split(amount);
        Assertions.assertEquals("0.04", parse);
    }

    @Test
    void splitZeroAmount() {
        Amount amount = MockUtils.aPaylineAmount(0);
        String parse = AmountParse.split(amount);
        Assertions.assertEquals("0.00", parse);
    }

    @Test
    void splitYenCurrency() {
        Amount amount = MockUtils.aPaylineAmountYen(1234);
        String parse = AmountParse.split(amount);
        Assertions.assertEquals("1234", parse);
    }

    @Test
    void transfoBigInt() {
        com.payline.payment.splitit.bean.nesteed.Amount amount = MockUtils.aSplitItAmount("12.34");
        BigInteger parse = AmountParse.transfoBigInt(amount.getValue());
        Assertions.assertEquals(BigInteger.valueOf(1234), parse);
    }

    @Test
    void transfoBigIntNoDot() {
        com.payline.payment.splitit.bean.nesteed.Amount amount = MockUtils.aSplitItAmount("1234");
        BigInteger parse = AmountParse.transfoBigInt(amount.getValue());
        Assertions.assertEquals(BigInteger.valueOf(1234), parse);
    }

    @Test
    void transfoBigIntdotLastPlace() {
        com.payline.payment.splitit.bean.nesteed.Amount amount = MockUtils.aSplitItAmount("1234.");
        BigInteger parse = AmountParse.transfoBigInt(amount.getValue());
        Assertions.assertEquals(BigInteger.valueOf(1234), parse);
    }

    @Test
    void transfoBigIntDotFirstPlace() {
        com.payline.payment.splitit.bean.nesteed.Amount amount = MockUtils.aSplitItAmount(".1234");
        BigInteger parse = AmountParse.transfoBigInt(amount.getValue());
        Assertions.assertEquals(BigInteger.valueOf(1234), parse);
    }

    @Test
    void splitDouble() {
        String parse = AmountParse.split(number, currency);
        Assertions.assertEquals("1.234", parse);
    }

    @Test
    void splitDoubleWithoutInt() {
        String parse = AmountParse.split(0.1234, currency);
        Assertions.assertEquals("0.001234", parse);
    }

    @Test
    void splitDoubleWithoutDot() {
        String parse = AmountParse.split((double) 1234, currency);
        Assertions.assertEquals("12.340", parse);
    }
}