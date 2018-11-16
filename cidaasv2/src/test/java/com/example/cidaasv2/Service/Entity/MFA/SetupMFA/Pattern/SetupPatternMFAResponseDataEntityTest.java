package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class SetupPatternMFAResponseDataEntityTest {
    SetupPatternMFAResponseDataEntity setupPatternResponseDataEntity=new SetupPatternMFAResponseDataEntity();


    @Test
    public void setQrCode() throws Exception {

        setupPatternResponseDataEntity.setQrCode("Test");

        Assert.assertEquals("Test",setupPatternResponseDataEntity.getQrCode());
    }

    @Test
    public void setOtpauth() throws Exception {

        setupPatternResponseDataEntity.setOtpauth("Test");

        Assert.assertEquals("Test",setupPatternResponseDataEntity.getOtpauth());
    }
    @Test
    public void setQueryString() throws Exception {

        setupPatternResponseDataEntity.setQueryString("Test");

        Assert.assertEquals("Test",setupPatternResponseDataEntity.getQueryString());
    }
    @Test
    public void setStatusId() throws Exception {

        setupPatternResponseDataEntity.setStatusId("StatusId");

        Assert.assertEquals("StatusId",setupPatternResponseDataEntity.getStatusId());
    }
    @Test
    public void setVerifierId() throws Exception {

        setupPatternResponseDataEntity.setVerifierId("VerifierId");

        Assert.assertEquals("VerifierId",setupPatternResponseDataEntity.getVerifierId());
    }
    @Test
    public void setRandomNumber() throws Exception {

        setupPatternResponseDataEntity.setRandomNumber("Test");

        Assert.assertEquals("Test",setupPatternResponseDataEntity.getRandomNumber());
    }

    @Test
    public void setCurrent_status() throws Exception {

        setupPatternResponseDataEntity.setCurrent_status("Test");

        Assert.assertEquals("Test",setupPatternResponseDataEntity.getCurrent_status());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme