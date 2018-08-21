package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class AuthenticateSmartPushResponseEntityTest {
    @Mock
    AuthenticateSmartPushResponseDataEntity data;
    @InjectMocks
    AuthenticateSmartPushResponseEntity authenticateSmartPushResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getSuccess()
    {
        authenticateSmartPushResponseEntity.setSuccess(true);
        assertTrue(authenticateSmartPushResponseEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        authenticateSmartPushResponseEntity.setStatus(417);
        assertTrue(authenticateSmartPushResponseEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        authenticateSmartPushResponseEntity.setSuccess(true);
        authenticateSmartPushResponseEntity.setData(data);
        Assert.assertEquals(authenticateSmartPushResponseEntity.getData(),data);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme