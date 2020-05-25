package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateFingerprintMFAResponseDataEntityTest {
    InitiateFingerprintMFAResponseDataEntity initiateFingerprintMFAResponseDataEntity = new InitiateFingerprintMFAResponseDataEntity();

    @Test
    public void getStatusId() {
        initiateFingerprintMFAResponseDataEntity.setStatusId("statusId");
        assertTrue(initiateFingerprintMFAResponseDataEntity.getStatusId() == "statusId");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme