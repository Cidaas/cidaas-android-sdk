package com.example.cidaasv2.Service.Entity.ConsentManagement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class ConsentDetailsResultEntityTest {
    @Mock
    ConsentDetailsResultDataEntity data;
    @InjectMocks
    ConsentDetailsResultEntity consentDetailsResultEntity=new ConsentDetailsResultEntity();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getSuccess()
    {
        consentDetailsResultEntity.setSuccess(true);
        assertTrue(consentDetailsResultEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        consentDetailsResultEntity.setStatus(417);
        assertTrue(consentDetailsResultEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        consentDetailsResultEntity.setSuccess(true);
        consentDetailsResultEntity.setData(data);
        Assert.assertEquals(consentDetailsResultEntity.getData(),data);
    }
}



//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme