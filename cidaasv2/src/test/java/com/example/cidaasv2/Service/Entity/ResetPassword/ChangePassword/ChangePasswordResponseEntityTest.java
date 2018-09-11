package com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ChangePasswordResponseEntityTest {
  
    ChangePasswordResponseDataEntity data;
  
    ChangePasswordResponseEntity changePasswordResponseEntity;

    @Before
    public void setUp() {
        
        changePasswordResponseEntity=new ChangePasswordResponseEntity();
    }

    @Test
    public void setSuccess(){
        changePasswordResponseEntity.setSuccess(true);
        Assert.assertTrue(changePasswordResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        changePasswordResponseEntity.setStatus(27);
        Assert.assertEquals(27,changePasswordResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new ChangePasswordResponseDataEntity();

        data.setChanged(true);
        changePasswordResponseEntity.setData(data);
        Assert.assertEquals(true,changePasswordResponseEntity.getData().isChanged());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme