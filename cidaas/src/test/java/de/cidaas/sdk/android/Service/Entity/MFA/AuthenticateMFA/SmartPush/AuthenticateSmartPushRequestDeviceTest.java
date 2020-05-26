package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.SmartPush;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.SmartPush.AuthenticateSmartPushRequestDevice;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateSmartPushRequestDeviceTest {
    @Test
    public void getDeviceId() {

        AuthenticateSmartPushRequestDevice authenticateSmartPushRequestDevice = new AuthenticateSmartPushRequestDevice();
        authenticateSmartPushRequestDevice.setDeviceId("Device ID");
        assertTrue(authenticateSmartPushRequestDevice.getDeviceId() == "Device ID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme