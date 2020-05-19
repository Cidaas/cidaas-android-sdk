package com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ResumeLoginResponseEntityTest {
    ResumeLoginResponseDataEntity data;
    
    ResumeLoginResponseEntity resumeLoginResponseEntity;

    @Before
    public void setUp() {
        
        resumeLoginResponseEntity=new ResumeLoginResponseEntity();
    }

    @Test
    public void setSuccess(){
        resumeLoginResponseEntity.setSuccess(true);
        Assert.assertTrue(resumeLoginResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        resumeLoginResponseEntity.setStatus(27);
        Assert.assertEquals(27,resumeLoginResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new ResumeLoginResponseDataEntity();

        data.setCode("Code");
        resumeLoginResponseEntity.setData(data);
        Assert.assertEquals("Code",resumeLoginResponseEntity.getData().getCode());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme