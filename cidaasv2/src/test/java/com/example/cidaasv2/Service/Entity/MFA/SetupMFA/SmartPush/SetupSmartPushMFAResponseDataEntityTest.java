package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush;

import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush.SetupSmartPushMFAResponseDataEntity;

import org.junit.Assert;
import org.junit.Test;

public class SetupSmartPushMFAResponseDataEntityTest {
    SetupSmartPushMFAResponseDataEntity setupSmartPushResponseDataEntity=new SetupSmartPushMFAResponseDataEntity();


    @Test
    public void setQrCode() throws Exception {

        setupSmartPushResponseDataEntity.setQrCode("Test");

        Assert.assertEquals("Test",setupSmartPushResponseDataEntity.getQrCode());
    }

    @Test
    public void setOtpauth() throws Exception {

        setupSmartPushResponseDataEntity.setOtpauth("Test");

        Assert.assertEquals("Test",setupSmartPushResponseDataEntity.getOtpauth());
    }
    @Test
    public void setQueryString() throws Exception {

        setupSmartPushResponseDataEntity.setQueryString("Test");

        Assert.assertEquals("Test",setupSmartPushResponseDataEntity.getQueryString());
    }
    @Test
    public void setStatusId() throws Exception {

        setupSmartPushResponseDataEntity.setStatusId("StatusId");

        Assert.assertEquals("StatusId",setupSmartPushResponseDataEntity.getStatusId());
    }
    @Test
    public void setVerifierId() throws Exception {

        setupSmartPushResponseDataEntity.setVerifierId("VerifierId");

        Assert.assertEquals("VerifierId",setupSmartPushResponseDataEntity.getVerifierId());
    }
    @Test
    public void setRandomNumber() throws Exception {

        setupSmartPushResponseDataEntity.setRandomNumber("Test");

        Assert.assertEquals("Test",setupSmartPushResponseDataEntity.getRandomNumber());
    }

    @Test
    public void setCurrent_status() throws Exception {

        setupSmartPushResponseDataEntity.setCurrent_status("Test");

        Assert.assertEquals("Test",setupSmartPushResponseDataEntity.getCurrent_status());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme