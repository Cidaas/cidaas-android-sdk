package com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent;

import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class ResumeConsentResponseEntityTest {
    @Mock
    ResumeConsentResponseDataEntity data;
    @InjectMocks
    ResumeConsentResponseEntity resumeConsentResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getSuccess()
    {
        resumeConsentResponseEntity=new ResumeConsentResponseEntity();
        resumeConsentResponseEntity.setSuccess(true);
        assertTrue(resumeConsentResponseEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        resumeConsentResponseEntity=new ResumeConsentResponseEntity();
        resumeConsentResponseEntity.setStatus(417);
        assertTrue(resumeConsentResponseEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        resumeConsentResponseEntity= new ResumeConsentResponseEntity();
        resumeConsentResponseEntity.setSuccess(true);
        resumeConsentResponseEntity.setData(data);
        Assert.assertEquals(resumeConsentResponseEntity.getData(),data);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme