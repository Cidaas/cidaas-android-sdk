package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class AuthenticateTOTPResponseEntityTest {
    @Mock
    AuthenticateTOTPResponseDataEntity data;
    @InjectMocks
    AuthenticateTOTPResponseEntity authenticateTOTPResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getSuccess()
    {
        authenticateTOTPResponseEntity.setSuccess(true);
        assertTrue(authenticateTOTPResponseEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        authenticateTOTPResponseEntity.setStatus(417);
        assertTrue(authenticateTOTPResponseEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        authenticateTOTPResponseEntity.setSuccess(true);
        authenticateTOTPResponseEntity.setData(data);
        Assert.assertEquals(authenticateTOTPResponseEntity.getData(),data);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme