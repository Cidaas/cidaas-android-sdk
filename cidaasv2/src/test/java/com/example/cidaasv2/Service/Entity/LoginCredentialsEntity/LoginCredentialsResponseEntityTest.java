package com.example.cidaasv2.Service.Entity.LoginCredentialsEntity;

import com.example.cidaasv2.Service.Entity.AccessTokenEntity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class LoginCredentialsResponseEntityTest {

    AccessTokenEntity data;
   
    LoginCredentialsResponseEntity loginCredentialsResponseEntity;

    @Before
    public void setUp() {
        
        loginCredentialsResponseEntity=new LoginCredentialsResponseEntity();
    }

    @Test
    public void setSuccess(){
        loginCredentialsResponseEntity.setSuccess(true);
        Assert.assertTrue(loginCredentialsResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        loginCredentialsResponseEntity.setStatus(27);
        Assert.assertEquals(27,loginCredentialsResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new AccessTokenEntity();

        data.setCode("Code");
        loginCredentialsResponseEntity.setData(data);
        Assert.assertEquals("Code",loginCredentialsResponseEntity.getData().getCode());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme