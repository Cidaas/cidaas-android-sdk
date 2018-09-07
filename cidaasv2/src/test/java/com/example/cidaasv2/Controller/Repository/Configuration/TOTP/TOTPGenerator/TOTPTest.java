package com.example.cidaasv2.Controller.Repository.Configuration.TOTP.TOTPGenerator;

import com.example.cidaasv2.BuildConfig;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class TOTPTest {

   /* @Test
    public void testGenerateTOTP() throws Exception {
        String result = TOTP.generateTOTP("key", "time", "6");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGenerateTOTP256() throws Exception {
        String result = TOTP.generateTOTP256("key", "time", "returnDigits");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGenerateTOTP512() throws Exception {
        String result = TOTP.generateTOTP512("key", "time", "returnDigits");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testGenerateTOTP2() throws Exception {
        String result = TOTP.generateTOTP("key", "time", "returnDigits", "crypto");
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }*/

    @Test
    public void testMain() throws Exception {
        TOTP.main(new String[]{"args"});
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme