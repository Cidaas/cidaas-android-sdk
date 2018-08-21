package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class AuthenticatePatternResponseEntityTest {
    @Mock
    AuthenticatePatternResponseDataEntity data;
    @InjectMocks
    AuthenticatePatternResponseEntity authenticatePatternResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getSuccess()
    {
        authenticatePatternResponseEntity.setSuccess(true);
        assertTrue(authenticatePatternResponseEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        authenticatePatternResponseEntity.setStatus(417);
        assertTrue(authenticatePatternResponseEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        authenticatePatternResponseEntity.setSuccess(true);
        authenticatePatternResponseEntity.setData(data);
        Assert.assertEquals(authenticatePatternResponseEntity.getData(),data);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme