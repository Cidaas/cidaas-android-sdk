package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern;

import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternResponseDataEntity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class EnrollPatternMFAResponseEntityTest {
    @Mock
    EnrollPatternResponseDataEntity data;
    @InjectMocks
    EnrollPatternMFAResponseEntity enrollPatternMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setSuccess(){
        enrollPatternMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(enrollPatternMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        enrollPatternMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27,enrollPatternMFAResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new EnrollPatternResponseDataEntity();

        data.setSub("Test");
        enrollPatternMFAResponseEntity.setData(data);
        Assert.assertEquals("Test",enrollPatternMFAResponseEntity.getData().getSub());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme