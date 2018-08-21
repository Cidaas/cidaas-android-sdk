package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class AuthenticateFaceResponseEntityTest {
    @Mock
    AuthenticateFaceResponseDataEntity data;
    @InjectMocks
    AuthenticateFaceResponseEntity authenticateFaceResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getSuccess()
    {
        authenticateFaceResponseEntity.setSuccess(true);
        assertTrue(authenticateFaceResponseEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        authenticateFaceResponseEntity.setStatus(417);
        assertTrue(authenticateFaceResponseEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        authenticateFaceResponseEntity.setSuccess(true);
        authenticateFaceResponseEntity.setData(data);
        Assert.assertEquals(authenticateFaceResponseEntity.getData(),data);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme