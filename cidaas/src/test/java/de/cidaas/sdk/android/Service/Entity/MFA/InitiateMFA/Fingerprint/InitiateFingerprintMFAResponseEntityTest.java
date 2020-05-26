package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.Fingerprint;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseDataEntity;
import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseEntity;


public class InitiateFingerprintMFAResponseEntityTest {
    InitiateFingerprintMFAResponseDataEntity data;

    InitiateFingerprintMFAResponseEntity initiateFingerprintMFAResponseEntity;

    @Before
    public void setUp() {
        initiateFingerprintMFAResponseEntity = new InitiateFingerprintMFAResponseEntity();
    }

    @Test
    public void setSuccess() {
        initiateFingerprintMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(initiateFingerprintMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        initiateFingerprintMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, initiateFingerprintMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new InitiateFingerprintMFAResponseDataEntity();

        data.setStatusId("Test");
        initiateFingerprintMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", initiateFingerprintMFAResponseEntity.getData().getStatusId());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme