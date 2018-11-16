package com.example.cidaasv2.Service.Register.RegisterUserAccountVerification;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class RegisterUserAccountInitiateResponseEntityTest {

    RegisterUserAccountInitiateResponseDataEntity data;

    RegisterUserAccountInitiateResponseEntity registerUserAccountInitiateResponseEntity;

    @Before
    public void setUp() {
        registerUserAccountInitiateResponseEntity=new RegisterUserAccountInitiateResponseEntity();
    }


    @Test
    public void setSuccess(){
        registerUserAccountInitiateResponseEntity.setSuccess(true);
        Assert.assertTrue(registerUserAccountInitiateResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        registerUserAccountInitiateResponseEntity.setStatus(27);
        Assert.assertEquals(27,registerUserAccountInitiateResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new RegisterUserAccountInitiateResponseDataEntity();

        data.setAccvid("Code");
        registerUserAccountInitiateResponseEntity.setData(data);
        Assert.assertEquals("Code",registerUserAccountInitiateResponseEntity.getData().getAccvid());

    }

    
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme