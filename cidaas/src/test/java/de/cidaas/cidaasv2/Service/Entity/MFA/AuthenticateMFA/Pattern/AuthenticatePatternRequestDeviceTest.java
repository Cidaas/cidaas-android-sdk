package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternRequestDevice;

import static junit.framework.TestCase.assertTrue;

public class AuthenticatePatternRequestDeviceTest {
    @Test
    public void getDeviceId() {

        AuthenticatePatternRequestDevice authenticatePatternRequestDevice = new AuthenticatePatternRequestDevice();
        authenticatePatternRequestDevice.setDeviceId("Device ID");
        assertTrue(authenticatePatternRequestDevice.getDeviceId() == "Device ID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme