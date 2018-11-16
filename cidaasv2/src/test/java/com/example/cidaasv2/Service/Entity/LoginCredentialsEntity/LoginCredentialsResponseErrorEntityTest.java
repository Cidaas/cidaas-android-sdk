package com.example.cidaasv2.Service.Entity.LoginCredentialsEntity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class LoginCredentialsResponseErrorEntityTest {
 
    LoginCredentialsErrorDataEntity error;
  
    LoginCredentialsResponseErrorEntity loginCredentialsResponseErrorEntity;

    @Before
    public void setUp() {
        loginCredentialsResponseErrorEntity=new LoginCredentialsResponseErrorEntity();
    }

    @Test
    public void setSuccess(){
        loginCredentialsResponseErrorEntity.setSuccess(true);
        Assert.assertTrue(loginCredentialsResponseErrorEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        loginCredentialsResponseErrorEntity.setStatus(27);
        Assert.assertEquals(27,loginCredentialsResponseErrorEntity.getStatus());

    }

    @Test
    public void setConsentURL(){
        loginCredentialsResponseErrorEntity.setConsentUrl("URL");
        Assert.assertEquals("URL",loginCredentialsResponseErrorEntity.getConsentUrl());

    }

    @Test
    public void setData(){
        error=new LoginCredentialsErrorDataEntity();

        error.setError("Error");
        loginCredentialsResponseErrorEntity.setError(error);
        Assert.assertEquals("Error",loginCredentialsResponseErrorEntity.getError().getError());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme