package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email;

import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailRequestDevice;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateEmailRequestDeviceTest {
    @Test
    public void getDeviceId()
    {

        AuthenticateEmailRequestDevice authenticateEmailRequestDevice=new AuthenticateEmailRequestDevice();
        authenticateEmailRequestDevice.setDeviceId("Device ID");
        assertTrue(authenticateEmailRequestDevice.getDeviceId()=="Device ID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme