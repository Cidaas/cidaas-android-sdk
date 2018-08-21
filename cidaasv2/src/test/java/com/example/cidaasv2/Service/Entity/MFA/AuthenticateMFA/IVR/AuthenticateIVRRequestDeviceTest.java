package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR;

import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRRequestDevice;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateIVRRequestDeviceTest {
    @Test
    public void getDeviceId()
    {

        AuthenticateIVRRequestDevice authenticateIVRRequestDevice=new AuthenticateIVRRequestDevice();
        authenticateIVRRequestDevice.setDeviceId("Device ID");
        assertTrue(authenticateIVRRequestDevice.getDeviceId()=="Device ID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme