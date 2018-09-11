package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class EnrollFaceResponseDataEntityTest {

    EnrollFaceResponseDataEntity enrollFaceResponseDataEntity=new EnrollFaceResponseDataEntity();

    @Test
    public void getSub()
    {
        enrollFaceResponseDataEntity.setSub("test");
        assertTrue(enrollFaceResponseDataEntity.getSub()=="test");
    }
    @Test
    public void geDescription()
    {
        enrollFaceResponseDataEntity.setTrackingCode("test");
        assertTrue(enrollFaceResponseDataEntity.getTrackingCode()=="test");

    }
    @Test
    public void getUsageType()
    {
        enrollFaceResponseDataEntity.setUsageType("UsageType");
        assertTrue(enrollFaceResponseDataEntity.getUsageType()=="UsageType");

    }
    @Test
    public void getVerificationType()
    {
        enrollFaceResponseDataEntity.setVerificationType("verificationType");
        assertTrue(enrollFaceResponseDataEntity.getVerificationType()=="verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme