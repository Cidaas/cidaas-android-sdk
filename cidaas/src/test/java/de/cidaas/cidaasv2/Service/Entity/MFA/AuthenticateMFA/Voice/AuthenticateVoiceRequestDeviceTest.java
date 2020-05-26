package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceRequestDevice;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateVoiceRequestDeviceTest {
    @Test
    public void getDeviceId() {

        AuthenticateVoiceRequestDevice authenticateVoiceRequestDevice = new AuthenticateVoiceRequestDevice();
        authenticateVoiceRequestDevice.setDeviceId("Device ID");
        assertTrue(authenticateVoiceRequestDevice.getDeviceId() == "Device ID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme