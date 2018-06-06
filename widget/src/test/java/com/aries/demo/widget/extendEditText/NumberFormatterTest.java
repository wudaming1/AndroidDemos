package com.aries.demo.widget.extendEditText;

import org.junit.Assert;
import org.junit.Test;

/**
 * Author wudaming
 * Created on 2018/6/5
 */
public class NumberFormatterTest {

    @Test
    public void testPhoneFormatter() {
        String[] strings = {"152", "152 ", "152 0", "1520 1", "1520", "15201 55", "152 0155 "
                , "15201555981", "152 0155 5981", "152015559811520155598115201555981"};
        String[] targets = {"152", "152", "152 0", "152 01", "152 0", "152 0155", "152 0155"
                , "152 0155 5981", "152 0155 5981", "152 0155 5981"};

        PhoneFormatter formatter = new PhoneFormatter();
        testEqual(strings, targets, "手机号格式化错误！", formatter);

    }

    @Test
    public void textBankFormatter() {
        String[] strings = {"1520", "1520 ", "15201", "1520 1", "15201 5", "15201555", "1520 1555 "
                , "15201555981", "1520 1555 9811", "1520 1555 9811 "};
        String[] targets = {"1520", "1520", "1520 1", "1520 1", "1520 15", "1520 1555", "1520 1555"
                , "1520 1555 981", "1520 1555 9811", "1520 1555 9811"};

        NumberFormatter formatter = new BankFormatter();
        testEqual(strings, targets, "银行卡号格式化错误！", formatter);
    }

    private void testEqual(String[] sources, String[] targets, String error, NumberFormatter formatter) {
        for (int i = 0; i < sources.length; i++) {
            Assert.assertEquals(error, targets[i]
                    , formatter.getFormattedText(sources[i], " "));
        }
    }
}
