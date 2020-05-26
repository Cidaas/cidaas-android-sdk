package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.Fingerprint;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseDataEntity;

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