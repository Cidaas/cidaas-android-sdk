package de.cidaas.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin;

import junit.framework.Assert;

import org.junit.Test;

public class ResumeLoginResponseDataEntityTest {

    ResumeLoginResponseDataEntity resumeLoginResponseDataEntity = new ResumeLoginResponseDataEntity();

    @Test
    public void setAccess_token() {
        resumeLoginResponseDataEntity.setCode("Code");
        Assert.assertEquals("Code", resumeLoginResponseDataEntity.getCode());
    }

    @Test
    public void setSession_state() {
        resumeLoginResponseDataEntity.setToken_type("Token_Type");
        Assert.assertEquals("Token_Type", resumeLoginResponseDataEntity.getToken_type());
    }

    @Test
    public void setViewtype() {
        resumeLoginResponseDataEntity.setViewtype("View_type");
        Assert.assertEquals("View_type", resumeLoginResponseDataEntity.getViewtype());
    }

    @Test
    public void setGrant_type() {
        resumeLoginResponseDataEntity.setGrant_type("GrantType");
        Assert.assertEquals("GrantType", resumeLoginResponseDataEntity.getGrant_type());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme