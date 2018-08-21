package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face;

import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceRequestDevice;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateFaceRequestDeviceTest {
    @Test
    public void getDeviceId()
    {

        AuthenticateFaceRequestDevice authenticateFaceRequestDevice=new AuthenticateFaceRequestDevice();
        authenticateFaceRequestDevice.setDeviceId("Device ID");
        assertTrue(authenticateFaceRequestDevice.getDeviceId()=="Device ID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme