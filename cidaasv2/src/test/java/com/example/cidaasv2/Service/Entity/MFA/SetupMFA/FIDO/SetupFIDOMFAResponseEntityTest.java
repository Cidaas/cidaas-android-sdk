package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.FIDO;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class SetupFIDOMFAResponseEntityTest {
  
    SetupFIDOMFAResponseDataEntity data;
 
    SetupFIDOMFAResponseEntity setupFIDOMFAResponseEntity;

    @Before
    public void setUp() {
        
        setupFIDOMFAResponseEntity=new SetupFIDOMFAResponseEntity();
    }


    @Test
    public void setSuccess(){
        setupFIDOMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(setupFIDOMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        setupFIDOMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,setupFIDOMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new SetupFIDOMFAResponseDataEntity();

        data.setStatusId("Test");
        setupFIDOMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",setupFIDOMFAResponseEntity.getData().getStatusId());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme