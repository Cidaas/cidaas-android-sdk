package de.cidaas.cidaasv2.Service.Entity.LoginCredentialsEntity;

import junit.framework.Assert;

import org.junit.Test;

public class LoginCredentialsErrorDataEntityTest {


    LoginCredentialsErrorDataEntity loginCredentialsErrorDataEntity = new LoginCredentialsErrorDataEntity();

    @Test
    public void setSub() {
        loginCredentialsErrorDataEntity.setSub("Sub");
        Assert.assertEquals("Sub", loginCredentialsErrorDataEntity.getSub());
    }

    @Test
    public void setError() {
        loginCredentialsErrorDataEntity.setError("Error");
        Assert.assertEquals("Error", loginCredentialsErrorDataEntity.getError());
    }

    @Test
    public void setError_description() {
        loginCredentialsErrorDataEntity.setError_description("Test");
        Assert.assertEquals("Test", loginCredentialsErrorDataEntity.getError_description());
    }

    @Test
    public void setView_type() {
        loginCredentialsErrorDataEntity.setView_type("Confirm Test");
        Assert.assertEquals("Confirm Test", loginCredentialsErrorDataEntity.getView_type());
    }

    @Test
    public void setRequestId() {
        loginCredentialsErrorDataEntity.setRequestId("RequestId");
        Assert.assertEquals("RequestId", loginCredentialsErrorDataEntity.getRequestId());
    }

    @Test
    public void setSuggested_url() {
        loginCredentialsErrorDataEntity.setSuggested_url("Test");
        Assert.assertEquals("Test", loginCredentialsErrorDataEntity.getSuggested_url());
    }

    @Test
    public void setTrack_id() {
        loginCredentialsErrorDataEntity.setTrack_id("TrackId");
        Assert.assertEquals("TrackId", loginCredentialsErrorDataEntity.getTrack_id());
    }

    @Test
    public void setClient_id() {
        loginCredentialsErrorDataEntity.setClient_id("ClientId");
        Assert.assertEquals("ClientId", loginCredentialsErrorDataEntity.getClient_id());
    }

    @Test
    public void setConsent_name() {
        loginCredentialsErrorDataEntity.setConsent_name("ConsentName");
        Assert.assertEquals("ConsentName", loginCredentialsErrorDataEntity.getConsent_name());
    }

}


//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme