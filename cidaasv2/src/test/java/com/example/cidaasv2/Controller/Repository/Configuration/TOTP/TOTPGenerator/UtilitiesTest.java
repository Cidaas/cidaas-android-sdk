package com.example.cidaasv2.Controller.Repository.Configuration.TOTP.TOTPGenerator;

import org.junit.Assert;
import org.junit.Test;

public class UtilitiesTest {

    @Test
    public void testMillisToSeconds() throws Exception {
        long result = Utilities.millisToSeconds(0L);
        Assert.assertEquals(0L, result);
    }

    @Test
    public void testSecondsToMillis() throws Exception {
        long result = Utilities.secondsToMillis(0L);
        Assert.assertEquals(0L, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme