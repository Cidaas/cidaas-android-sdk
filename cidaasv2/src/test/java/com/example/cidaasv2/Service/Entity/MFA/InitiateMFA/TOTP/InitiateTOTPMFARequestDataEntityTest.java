package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class InitiateTOTPMFARequestDataEntityTest {
    InitiateTOTPMFARequestDataEntity initiateTOTPMFARequestDataEntity=new InitiateTOTPMFARequestDataEntity();

    @Test
    public void getDeviceId()
    {
        initiateTOTPMFARequestDataEntity.setDeviceId("DeviceID");
        assertTrue(initiateTOTPMFARequestDataEntity.getDeviceId()=="DeviceID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme