package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.FIDOKey;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class AuthenticateFIDOResponseEntityTest {
    @Mock
    AuthenticateFIDOResponseDataEntity data;
    @InjectMocks
    AuthenticateFIDOResponseEntity authenticateFIDOResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getSuccess()
    {
        authenticateFIDOResponseEntity.setSuccess(true);
        assertTrue(authenticateFIDOResponseEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        authenticateFIDOResponseEntity.setStatus(417);
        assertTrue(authenticateFIDOResponseEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        authenticateFIDOResponseEntity.setSuccess(true);
        authenticateFIDOResponseEntity.setData(data);
        Assert.assertEquals(authenticateFIDOResponseEntity.getData(),data);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme