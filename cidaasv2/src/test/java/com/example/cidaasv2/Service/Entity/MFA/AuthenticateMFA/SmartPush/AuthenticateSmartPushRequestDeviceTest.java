package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush;

import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushRequestDevice;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateSmartPushRequestDeviceTest {
    @Test
    public void getDeviceId()
    {

        AuthenticateSmartPushRequestDevice authenticateSmartPushRequestDevice=new AuthenticateSmartPushRequestDevice();
        authenticateSmartPushRequestDevice.setDeviceId("Device ID");
        assertTrue(authenticateSmartPushRequestDevice.getDeviceId()=="Device ID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme