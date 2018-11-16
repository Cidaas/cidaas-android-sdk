package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face;

import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceResponseDataEntity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class EnrollFaceMFAResponseEntityTest {

    EnrollFaceResponseDataEntity data;
    EnrollFaceMFAResponseEntity enrollFaceMFAResponseEntity;

    @Before
    public void setUp() {
        
        enrollFaceMFAResponseEntity=new EnrollFaceMFAResponseEntity();
    }

    @Test
    public void setSuccess(){
        enrollFaceMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(enrollFaceMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        enrollFaceMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,enrollFaceMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new EnrollFaceResponseDataEntity();

        data.setSub("Test");
        enrollFaceMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",enrollFaceMFAResponseEntity.getData().getSub());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme