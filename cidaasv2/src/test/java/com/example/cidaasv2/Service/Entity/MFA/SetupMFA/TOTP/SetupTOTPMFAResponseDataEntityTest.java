package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP;

import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP.SetupTOTPMFAResponseDataEntity;

import org.junit.Assert;
import org.junit.Test;

public class SetupTOTPMFAResponseDataEntityTest {
    SetupTOTPMFAResponseDataEntity setupTOTPResponseDataEntity=new SetupTOTPMFAResponseDataEntity();


    @Test
    public void setQrCode() throws Exception {

        setupTOTPResponseDataEntity.setQrCode("Test");

        Assert.assertEquals("Test",setupTOTPResponseDataEntity.getQrCode());
    }

    @Test
    public void setOtpauth() throws Exception {

        setupTOTPResponseDataEntity.setOtpauth("Test");

        Assert.assertEquals("Test",setupTOTPResponseDataEntity.getOtpauth());
    }
    @Test
    public void setQueryString() throws Exception {

        setupTOTPResponseDataEntity.setQueryString("Test");

        Assert.assertEquals("Test",setupTOTPResponseDataEntity.getQueryString());
    }
    @Test
    public void setStatusId() throws Exception {

        setupTOTPResponseDataEntity.setStatusId("StatusId");

        Assert.assertEquals("StatusId",setupTOTPResponseDataEntity.getStatusId());
    }
    @Test
    public void setVerifierId() throws Exception {

        setupTOTPResponseDataEntity.setVerifierId("VerifierId");

        Assert.assertEquals("VerifierId",setupTOTPResponseDataEntity.getVerifierId());
    }
    @Test
    public void setRandomNumber() throws Exception {

        setupTOTPResponseDataEntity.setRandomNumber("Test");

        Assert.assertEquals("Test",setupTOTPResponseDataEntity.getRandomNumber());
    }

    @Test
    public void setCurrent_status() throws Exception {

        setupTOTPResponseDataEntity.setCurrent_status("Test");

        Assert.assertEquals("Test",setupTOTPResponseDataEntity.getCurrent_status());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme