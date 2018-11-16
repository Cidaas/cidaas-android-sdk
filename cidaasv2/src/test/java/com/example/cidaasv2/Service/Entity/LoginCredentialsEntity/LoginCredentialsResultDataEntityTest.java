package com.example.cidaasv2.Service.Entity.LoginCredentialsEntity;

import junit.framework.Assert;

import org.junit.Test;

public class LoginCredentialsResultDataEntityTest {


    LoginCredentialsResultDataEntity loginCredentialsResultDataEntity=new LoginCredentialsResultDataEntity();


    @Test
    public void TestResult()
    {
        loginCredentialsResultDataEntity.setToken_type("Token_type");
        Assert.assertEquals("Token_type",loginCredentialsResultDataEntity.getToken_type());
    }

    @Test
    public void settest()
    {
        loginCredentialsResultDataEntity.setExpires_in("Expires_in");
        Assert.assertEquals("Expires_in",loginCredentialsResultDataEntity.getExpires_in());
    }


    @Test
    public void setAccess_token()
    {
        loginCredentialsResultDataEntity.setAccess_token("Access_Token");
        Assert.assertEquals("Access_Token",loginCredentialsResultDataEntity.getAccess_token());
    }

    @Test
    public void setSession_state()
    {
        loginCredentialsResultDataEntity.setSession_state("SessionState");
        Assert.assertEquals("SessionState",loginCredentialsResultDataEntity.getSession_state());
    }

    @Test
    public void setViewtype()
    {
        loginCredentialsResultDataEntity.setViewtype("View_type");
        Assert.assertEquals("View_type",loginCredentialsResultDataEntity.getViewtype());
    }

    @Test
    public void setGrant_type()
    {
        loginCredentialsResultDataEntity.setGrant_type("GrantType");
        Assert.assertEquals("GrantType",loginCredentialsResultDataEntity.getGrant_type());
    }
}



//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme