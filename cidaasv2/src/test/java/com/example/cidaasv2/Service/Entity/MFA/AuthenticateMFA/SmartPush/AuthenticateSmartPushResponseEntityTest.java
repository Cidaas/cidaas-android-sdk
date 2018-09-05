package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateSmartPushResponseEntityTest {

    AuthenticateSmartPushResponseDataEntity data;

    AuthenticateSmartPushResponseEntity authenticateSmartPushResponseEntity;

    @Before
    public void setUp() {
     authenticateSmartPushResponseEntity=new AuthenticateSmartPushResponseEntity();
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