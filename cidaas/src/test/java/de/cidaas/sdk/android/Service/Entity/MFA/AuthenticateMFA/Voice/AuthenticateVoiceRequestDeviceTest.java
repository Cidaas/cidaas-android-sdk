package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.Voice;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.Voice.AuthenticateVoiceRequestDevice;

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