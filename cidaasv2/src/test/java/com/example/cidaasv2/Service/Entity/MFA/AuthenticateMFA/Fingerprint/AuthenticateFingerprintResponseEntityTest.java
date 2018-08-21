package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class AuthenticateFingerprintResponseEntityTest {
    @Mock
    AuthenticateFingerprintResponseDataEntity data;
    @InjectMocks
    AuthenticateFingerprintResponseEntity authenticateFingerprintResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getSuccess()
    {
        authenticateFingerprintResponseEntity.setSuccess(true);
        assertTrue(authenticateFingerprintResponseEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        authenticateFingerprintResponseEntity.setStatus(417);
        assertTrue(authenticateFingerprintResponseEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        authenticateFingerprintResponseEntity.setSuccess(true);
        authenticateFingerprintResponseEntity.setData(data);
        Assert.assertEquals(authenticateFingerprintResponseEntity.getData(),data);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme