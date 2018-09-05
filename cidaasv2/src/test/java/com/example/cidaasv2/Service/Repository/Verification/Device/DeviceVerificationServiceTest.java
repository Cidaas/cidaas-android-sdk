package com.example.cidaasv2.Service.Repository.Verification.Device;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeviceVerificationServiceTest {

    Context context;
    DeviceVerificationService deviceVerificationService;

    @Before
    public void setUp() {
      deviceVerificationService=new DeviceVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        DeviceVerificationService result = DeviceVerificationService.getShared(null);
        Assert.assertEquals(new DeviceVerificationService(null), result);
    }

    @Test
    public void testValidateDevice() throws Exception {

        deviceVerificationService.validateDevice("baseurl", "intermediateId", "statusId", "codeVerifier", null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme