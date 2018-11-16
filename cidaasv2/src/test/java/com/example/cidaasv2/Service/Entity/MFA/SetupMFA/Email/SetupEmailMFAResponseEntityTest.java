package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class SetupEmailMFAResponseEntityTest {

    SetupEmailResponseDataEntity data;

    SetupEmailMFAResponseEntity setupEmailMFAResponseEntity;

    @Before
    public void setUp() {

        setupEmailMFAResponseEntity=new SetupEmailMFAResponseEntity();
    }


    @Test
    public void setSuccess(){
        setupEmailMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(setupEmailMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        setupEmailMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,setupEmailMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new SetupEmailResponseDataEntity();

        data.setStatusId("Test");
        setupEmailMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",setupEmailMFAResponseEntity.getData().getStatusId());

    }
}


//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme