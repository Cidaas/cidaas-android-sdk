package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Face;

import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Face.InitiateFaceMFAResponseDataEntity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class InitiateFaceMFAResponseEntityTest {
    @Mock
    InitiateFaceMFAResponseDataEntity data;
    @InjectMocks
    InitiateFaceMFAResponseEntity initiateFaceMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setSuccess(){
        initiateFaceMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(initiateFaceMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        initiateFaceMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,initiateFaceMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new InitiateFaceMFAResponseDataEntity();

        data.setStatusId("Test");
        initiateFaceMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",initiateFaceMFAResponseEntity.getData().getStatusId());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme