package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.FIDO;

import org.junit.Assert;
import org.junit.Test;

public class SetupFIDOMFAResponseDataEntityTest {

    SetupFIDOMFAResponseDataEntity setupFIDOResponseDataEntity=new SetupFIDOMFAResponseDataEntity();


    @Test
    public void setQrCode() throws Exception {

        setupFIDOResponseDataEntity.setQrCode("Test");

        Assert.assertEquals("Test",setupFIDOResponseDataEntity.getQrCode());
    }

    @Test
    public void setOtpauth() throws Exception {

        setupFIDOResponseDataEntity.setOtpauth("Test");

        Assert.assertEquals("Test",setupFIDOResponseDataEntity.getOtpauth());
    }
    @Test
    public void setQueryString() throws Exception {

        setupFIDOResponseDataEntity.setQueryString("Test");

        Assert.assertEquals("Test",setupFIDOResponseDataEntity.getQueryString());
    }
    @Test
    public void setStatusId() throws Exception {

        setupFIDOResponseDataEntity.setStatusId("StatusId");

        Assert.assertEquals("StatusId",setupFIDOResponseDataEntity.getStatusId());
    }
    @Test
    public void setVerifierId() throws Exception {

        setupFIDOResponseDataEntity.setVerifierId("VerifierId");

        Assert.assertEquals("VerifierId",setupFIDOResponseDataEntity.getVerifierId());
    }
    @Test
    public void setRandomNumber() throws Exception {

        setupFIDOResponseDataEntity.setRandomNumber("Test");

        Assert.assertEquals("Test",setupFIDOResponseDataEntity.getRandomNumber());
    }

    @Test
    public void setCurrent_status() throws Exception {

        setupFIDOResponseDataEntity.setCurrent_status("Test");

        Assert.assertEquals("Test",setupFIDOResponseDataEntity.getCurrent_status());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme