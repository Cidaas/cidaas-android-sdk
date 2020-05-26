package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.TOTP;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.TOTP.InitiateTOTPMFARequestDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateTOTPMFARequestDataEntityTest {
    InitiateTOTPMFARequestDataEntity initiateTOTPMFARequestDataEntity = new InitiateTOTPMFARequestDataEntity();

    @Test
    public void getDeviceId() {
        initiateTOTPMFARequestDataEntity.setDeviceId("DeviceID");
        assertTrue(initiateTOTPMFARequestDataEntity.getDeviceId() == "DeviceID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme