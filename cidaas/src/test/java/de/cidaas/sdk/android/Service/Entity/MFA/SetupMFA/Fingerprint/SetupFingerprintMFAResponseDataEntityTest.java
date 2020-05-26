package de.cidaas.sdk.android.Service.Entity.MFA.SetupMFA.Fingerprint;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.SetupMFA.Fingerprint.SetupFingerprintMFAResponseDataEntity;


public class SetupFingerprintMFAResponseDataEntityTest {
    SetupFingerprintMFAResponseDataEntity setupFingerprintMFAResponseDataEntity = new SetupFingerprintMFAResponseDataEntity();


    @Test
    public void setStatusId() throws Exception {

        setupFingerprintMFAResponseDataEntity.setSt("StatusId");

        Assert.assertEquals("StatusId", setupFingerprintMFAResponseDataEntity.getSt());
    }

    @Test
    public void setsecret() throws Exception {

        setupFingerprintMFAResponseDataEntity.setSecret("VerifierId");

        Assert.assertEquals("VerifierId", setupFingerprintMFAResponseDataEntity.getSecret());
    }

    @Test
    public void setRandomNumber() throws Exception {

        setupFingerprintMFAResponseDataEntity.setRns("Test");

        Assert.assertEquals("Test", setupFingerprintMFAResponseDataEntity.getRns());
    }

    @Test
    public void setCurrent_status() throws Exception {

        setupFingerprintMFAResponseDataEntity.setCurrent_status("Test");

        Assert.assertEquals("Test", setupFingerprintMFAResponseDataEntity.getCurrent_status());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme