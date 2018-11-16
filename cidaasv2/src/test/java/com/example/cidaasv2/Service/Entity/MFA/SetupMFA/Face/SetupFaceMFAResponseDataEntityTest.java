package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face;

import org.junit.Assert;
import org.junit.Test;

public class SetupFaceMFAResponseDataEntityTest {


    SetupFaceMFAResponseDataEntity setupFaceResponseDataEntity=new SetupFaceMFAResponseDataEntity();


    @Test
    public void setQrCode() throws Exception {

        setupFaceResponseDataEntity.setQrCode("Test");

        Assert.assertEquals("Test",setupFaceResponseDataEntity.getQrCode());
    }

    @Test
    public void setOtpauth() throws Exception {

        setupFaceResponseDataEntity.setOtpauth("Test");

        Assert.assertEquals("Test",setupFaceResponseDataEntity.getOtpauth());
    }
    @Test
    public void setQueryString() throws Exception {

        setupFaceResponseDataEntity.setQueryString("Test");

        Assert.assertEquals("Test",setupFaceResponseDataEntity.getQueryString());
    }
    @Test
    public void setStatusId() throws Exception {

        setupFaceResponseDataEntity.setStatusId("StatusId");

        Assert.assertEquals("StatusId",setupFaceResponseDataEntity.getStatusId());
    }
    @Test
    public void setVerifierId() throws Exception {

        setupFaceResponseDataEntity.setVerifierId("VerifierId");

        Assert.assertEquals("VerifierId",setupFaceResponseDataEntity.getVerifierId());
    }
    @Test
    public void setRandomNumber() throws Exception {

        setupFaceResponseDataEntity.setRandomNumber("Test");

        Assert.assertEquals("Test",setupFaceResponseDataEntity.getRandomNumber());
    }

    @Test
    public void setCurrent_status() throws Exception {

        setupFaceResponseDataEntity.setCurrent_status("Test");

        Assert.assertEquals("Test",setupFaceResponseDataEntity.getCurrent_status());
    }    
    
    
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme