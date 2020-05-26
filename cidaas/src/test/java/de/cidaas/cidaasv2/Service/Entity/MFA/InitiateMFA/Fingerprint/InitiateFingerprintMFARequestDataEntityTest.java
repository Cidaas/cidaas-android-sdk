package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestDataEntity;

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