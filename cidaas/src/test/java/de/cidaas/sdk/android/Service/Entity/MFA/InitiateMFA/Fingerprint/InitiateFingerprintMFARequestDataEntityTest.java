package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.Fingerprint;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateFingerprintMFARequestDataEntityTest {
    InitiateFingerprintMFARequestDataEntity initiateFingerprintMFARequestDataEntity = new InitiateFingerprintMFARequestDataEntity();

    @Test
    public void getDeviceId() {
        initiateFingerprintMFARequestDataEntity.setDeviceId("DeviceID");
        assertTrue(initiateFingerprintMFARequestDataEntity.getDeviceId() == "DeviceID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme