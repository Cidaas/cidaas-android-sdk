package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;


public class AuthenticateEmailResponseDataEntityTest {

    AuthenticateEmailResponseDataEntity authenticateEmailResponseDataEntity=new AuthenticateEmailResponseDataEntity();
    @Test
    public void getSub()
    {
        authenticateEmailResponseDataEntity.setSub("test");
        assertTrue(authenticateEmailResponseDataEntity.getSub()=="test");
    }
    @Test
    public void geDescription()
    {
        authenticateEmailResponseDataEntity.setTrackingCode("test");
        assertTrue(authenticateEmailResponseDataEntity.getTrackingCode()=="test");

    }
    @Test
    public void getUsageType()
    {
        authenticateEmailResponseDataEntity.setUsageType("UsageType");
        assertTrue(authenticateEmailResponseDataEntity.getUsageType()=="UsageType");

    }
    @Test
    public void getVerificationType()
    {
        authenticateEmailResponseDataEntity.setVerificationType("verificationType");
        assertTrue(authenticateEmailResponseDataEntity.getVerificationType()=="verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme