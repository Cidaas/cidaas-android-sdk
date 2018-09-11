package com.example.cidaasv2.Service.Register.RegistrationSetup;

import org.junit.Assert;
import org.junit.Test;

public class RegistrationSetupFieldDefenitionTest {
    RegistrationSetupFieldDefenition registrationSetupFieldDefenition = new RegistrationSetupFieldDefenition();

    @Test
    public void testGetAttributesKeys() throws Exception {
        String[] result = registrationSetupFieldDefenition.getAttributesKeys();
//        Assert.assertArrayEquals(new String[]{"replaceMeWithExpectedResult"}, result);
        Assert.assertArrayEquals(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme