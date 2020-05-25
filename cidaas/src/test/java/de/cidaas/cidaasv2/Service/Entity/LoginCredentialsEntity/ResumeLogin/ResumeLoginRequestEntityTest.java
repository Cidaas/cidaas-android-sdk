package de.cidaas.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin;

import junit.framework.Assert;

import org.junit.Test;

public class ResumeLoginRequestEntityTest {


    ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

    @Test
    public void setTrack_id() {
        resumeLoginRequestEntity.setTrack_id("Test");
        Assert.assertEquals("Test", resumeLoginRequestEntity.getTrack_id());
    }

    @Test
    public void setSub() {
        resumeLoginRequestEntity.setSub("Sub");
        Assert.assertEquals("Sub", resumeLoginRequestEntity.getSub());
    }

    @Test
    public void setClient_id() {
        resumeLoginRequestEntity.setClient_id("ClientId");
        Assert.assertEquals("ClientId", resumeLoginRequestEntity.getClient_id());
    }

    @Test
    public void setTrackingCode() {
        resumeLoginRequestEntity.setTrackingCode("Test");
        Assert.assertEquals("Test", resumeLoginRequestEntity.getTrackingCode());
    }

    @Test
    public void setRequestId() {
        resumeLoginRequestEntity.setRequestId("RequestId");
        Assert.assertEquals("RequestId", resumeLoginRequestEntity.getRequestId());
    }


    @Test
    public void setVerificationType() {
        resumeLoginRequestEntity.setVerificationType("Test");
        Assert.assertEquals("Test", resumeLoginRequestEntity.getVerificationType());
    }

    @Test
    public void setUsageType() {
        resumeLoginRequestEntity.setUsageType("Test");
        Assert.assertEquals("Test", resumeLoginRequestEntity.getUsageType());
    }

    @Test
    public void setUserDeviceId() {
        resumeLoginRequestEntity.setUserDeviceId("Test");
        Assert.assertEquals("Test", resumeLoginRequestEntity.getUserDeviceId());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme