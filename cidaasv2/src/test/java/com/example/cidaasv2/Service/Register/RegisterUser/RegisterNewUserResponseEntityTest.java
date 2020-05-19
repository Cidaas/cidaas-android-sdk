package com.example.cidaasv2.Service.Register.RegisterUser;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class RegisterNewUserResponseEntityTest {
   
    RegisterNewUserDataEntity data;

    RegisterNewUserResponseEntity registerNewUserResponseEntity;

    @Before
    public void setUp() {
        
        registerNewUserResponseEntity=new RegisterNewUserResponseEntity();
       
    }

    

    @Test
    public void setSuccess(){
        registerNewUserResponseEntity.setSuccess(true);
        Assert.assertTrue(registerNewUserResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        registerNewUserResponseEntity.setStatus(27);
        Assert.assertEquals(27,registerNewUserResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new RegisterNewUserDataEntity();

        data.setSub("Code");
        registerNewUserResponseEntity.setData(data);
        Assert.assertEquals("Code",registerNewUserResponseEntity.getData().getSub());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme