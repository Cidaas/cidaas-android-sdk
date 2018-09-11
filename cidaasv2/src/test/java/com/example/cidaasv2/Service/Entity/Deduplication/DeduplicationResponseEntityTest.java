package com.example.cidaasv2.Service.Entity.Deduplication;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class DeduplicationResponseEntityTest {

    DeduplicationResponseDataEntity data;

    DeduplicationResponseEntity deduplicationResponseEntity;

    @Before
    public void setUp() {
        
        deduplicationResponseEntity=new DeduplicationResponseEntity();
    }

    @Test
    public void setSuccess(){
        deduplicationResponseEntity.setSuccess(true);
        Assert.assertTrue(deduplicationResponseEntity.isSuccess());

    }

    @Test
    public void setStatus(){
        deduplicationResponseEntity.setStatus(27);
        Assert.assertEquals(27,deduplicationResponseEntity.getStatus());

    }

    @Test
    public void setData(){
        data=new DeduplicationResponseDataEntity();

        data.setEmail("Code");
        deduplicationResponseEntity.setData(data);
        Assert.assertEquals("Code",deduplicationResponseEntity.getData().getEmail());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme