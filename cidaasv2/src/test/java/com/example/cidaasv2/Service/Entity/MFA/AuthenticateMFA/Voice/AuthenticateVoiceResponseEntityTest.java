package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class AuthenticateVoiceResponseEntityTest {
    @Mock
    AuthenticateVoiceResponseDataEntity data;
    @InjectMocks
    AuthenticateVoiceResponseEntity authenticateVoiceResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getSuccess()
    {
        authenticateVoiceResponseEntity.setSuccess(true);
        assertTrue(authenticateVoiceResponseEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        authenticateVoiceResponseEntity.setStatus(417);
        assertTrue(authenticateVoiceResponseEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        authenticateVoiceResponseEntity.setSuccess(true);
        authenticateVoiceResponseEntity.setData(data);
        Assert.assertEquals(authenticateVoiceResponseEntity.getData(),data);
    }
    
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme