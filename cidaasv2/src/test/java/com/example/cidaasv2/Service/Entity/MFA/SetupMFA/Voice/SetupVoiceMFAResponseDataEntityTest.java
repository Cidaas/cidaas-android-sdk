package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice;

import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFAResponseDataEntity;

import org.junit.Assert;
import org.junit.Test;

public class SetupVoiceMFAResponseDataEntityTest {
    SetupVoiceMFAResponseDataEntity setupVoiceResponseDataEntity=new SetupVoiceMFAResponseDataEntity();


    @Test
    public void setQrCode() throws Exception {

        setupVoiceResponseDataEntity.setQrCode("Test");

        Assert.assertEquals("Test",setupVoiceResponseDataEntity.getQrCode());
    }

    @Test
    public void setOtpauth() throws Exception {

        setupVoiceResponseDataEntity.setOtpauth("Test");

        Assert.assertEquals("Test",setupVoiceResponseDataEntity.getOtpauth());
    }
    @Test
    public void setQueryString() throws Exception {

        setupVoiceResponseDataEntity.setQueryString("Test");

        Assert.assertEquals("Test",setupVoiceResponseDataEntity.getQueryString());
    }
    @Test
    public void setStatusId() throws Exception {

        setupVoiceResponseDataEntity.setStatusId("StatusId");

        Assert.assertEquals("StatusId",setupVoiceResponseDataEntity.getStatusId());
    }
    @Test
    public void setVerifierId() throws Exception {

        setupVoiceResponseDataEntity.setVerifierId("VerifierId");

        Assert.assertEquals("VerifierId",setupVoiceResponseDataEntity.getVerifierId());
    }
    @Test
    public void setRandomNumber() throws Exception {

        setupVoiceResponseDataEntity.setRandomNumber("Test");

        Assert.assertEquals("Test",setupVoiceResponseDataEntity.getRandomNumber());
    }

    @Test
    public void setCurrent_status() throws Exception {

        setupVoiceResponseDataEntity.setCurrent_status("Test");

        Assert.assertEquals("Test",setupVoiceResponseDataEntity.getCurrent_status());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme