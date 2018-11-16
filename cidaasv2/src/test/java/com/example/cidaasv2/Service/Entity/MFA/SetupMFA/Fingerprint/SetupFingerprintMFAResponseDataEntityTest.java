package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint;

import org.junit.Assert;
import org.junit.Test;

public class SetupFingerprintMFAResponseDataEntityTest {
    SetupFingerprintMFAResponseDataEntity setupFingerprintMFAResponseDataEntity=new SetupFingerprintMFAResponseDataEntity();


    @Test
    public void setQrCode() throws Exception {

        setupFingerprintMFAResponseDataEntity.setQrCode("Test");

        Assert.assertEquals("Test",setupFingerprintMFAResponseDataEntity.getQrCode());
    }

    @Test
    public void setOtpauth() throws Exception {

        setupFingerprintMFAResponseDataEntity.setOtpauth("Test");

        Assert.assertEquals("Test",setupFingerprintMFAResponseDataEntity.getOtpauth());
    }
    @Test
    public void setQueryString() throws Exception {

        setupFingerprintMFAResponseDataEntity.setQueryString("Test");

        Assert.assertEquals("Test",setupFingerprintMFAResponseDataEntity.getQueryString());
    }
    @Test
    public void setStatusId() throws Exception {

        setupFingerprintMFAResponseDataEntity.setStatusId("StatusId");

        Assert.assertEquals("StatusId",setupFingerprintMFAResponseDataEntity.getStatusId());
    }
    @Test
    public void setVerifierId() throws Exception {

        setupFingerprintMFAResponseDataEntity.setVerifierId("VerifierId");

        Assert.assertEquals("VerifierId",setupFingerprintMFAResponseDataEntity.getVerifierId());
    }
    @Test
    public void setRandomNumber() throws Exception {

        setupFingerprintMFAResponseDataEntity.setRandomNumber("Test");

        Assert.assertEquals("Test",setupFingerprintMFAResponseDataEntity.getRandomNumber());
    }

    @Test
    public void setCurrent_status() throws Exception {

        setupFingerprintMFAResponseDataEntity.setCurrent_status("Test");

        Assert.assertEquals("Test",setupFingerprintMFAResponseDataEntity.getCurrent_status());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme