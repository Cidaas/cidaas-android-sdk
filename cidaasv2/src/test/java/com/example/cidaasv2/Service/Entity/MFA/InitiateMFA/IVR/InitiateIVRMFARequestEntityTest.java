package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class InitiateIVRMFARequestEntityTest {

    InitiateIVRMFARequestEntity initiateIVRMFARequestEntity;

    @Before
    public void setUp() {
        initiateIVRMFARequestEntity=new InitiateIVRMFARequestEntity();

    }



    @Test
    public void setPhysicalVerification()
    {
        initiateIVRMFARequestEntity.setPhysicalVerificationId("physicalVerification");
        assertTrue(initiateIVRMFARequestEntity.getPhysicalVerificationId().equals("physicalVerification"));
    }

    @Test
    public void getUsageType()
    {
        initiateIVRMFARequestEntity.setUsageType("UsageType");
        assertTrue(initiateIVRMFARequestEntity.getUsageType().equals("UsageType"));
    }

    @Test
    public void getVerificationType()
    {
        initiateIVRMFARequestEntity.setVerificationType("VerificationType");
        assertTrue(initiateIVRMFARequestEntity.getVerificationType().equals("VerificationType"));
    }

    @Test
    public void setSub()
    {
        initiateIVRMFARequestEntity.setSub("Sub");
        assertTrue(initiateIVRMFARequestEntity.getSub().equals("Sub"));
    }



    @Test
    public void getDeviceInfoEntity()
    {
        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        initiateIVRMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(initiateIVRMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(initiateIVRMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(initiateIVRMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(initiateIVRMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(initiateIVRMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId()
    {
        initiateIVRMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(initiateIVRMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme