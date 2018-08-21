package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS;

import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSRequestDevice;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateSMSRequestDeviceTest {
    @Test
    public void getDeviceId()
    {

        AuthenticateSMSRequestDevice authenticateSMSRequestDevice=new AuthenticateSMSRequestDevice();
        authenticateSMSRequestDevice.setDeviceId("Device ID");
        assertTrue(authenticateSMSRequestDevice.getDeviceId()=="Device ID");

    }
    
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme