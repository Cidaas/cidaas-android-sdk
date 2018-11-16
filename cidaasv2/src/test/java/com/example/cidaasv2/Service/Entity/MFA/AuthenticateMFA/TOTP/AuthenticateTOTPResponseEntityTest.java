package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateTOTPResponseEntityTest {

    AuthenticateTOTPResponseDataEntity data;

    AuthenticateTOTPResponseEntity authenticateTOTPResponseEntity;

    @Before
    public void setUp() {
    authenticateTOTPResponseEntity=new AuthenticateTOTPResponseEntity();
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