package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateIVRResponseEntityTest {

    AuthenticateIVRResponseDataEntity data;

    AuthenticateIVRResponseEntity authenticateIVRResponseEntity;

    @Before
    public void setUp() {
      authenticateIVRResponseEntity=new AuthenticateIVRResponseEntity();
    }


    @Test
    public void getSuccess()
    {
        authenticateIVRResponseEntity.setSuccess(true);
        assertTrue(authenticateIVRResponseEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        authenticateIVRResponseEntity.setStatus(417);
        assertTrue(authenticateIVRResponseEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        authenticateIVRResponseEntity.setSuccess(true);
        authenticateIVRResponseEntity.setData(data);
        Assert.assertEquals(authenticateIVRResponseEntity.getData(),data);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme