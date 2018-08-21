package com.example.cidaasv2.Service.Entity.ConsentManagement;

import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class ConsentResponseEntityTest {
    @Mock
    ConsentResponseDataEntity data;
    @InjectMocks
    ConsentResponseEntity consentResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getSuccess()
    {
        consentResponseEntity=new ConsentResponseEntity();
        consentResponseEntity.setSuccess(true);
        assertTrue(consentResponseEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        consentResponseEntity=new ConsentResponseEntity();
        consentResponseEntity.setStatus(417);
        assertTrue(consentResponseEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        consentResponseEntity= new ConsentResponseEntity();
        consentResponseEntity.setSuccess(true);
        consentResponseEntity.setData(data);
        Assert.assertEquals(consentResponseEntity.getData(),data);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme