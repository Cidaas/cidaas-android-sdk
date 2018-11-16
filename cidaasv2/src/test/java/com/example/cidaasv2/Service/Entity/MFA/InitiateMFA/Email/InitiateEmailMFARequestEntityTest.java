package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;

public class InitiateEmailMFARequestEntityTest {

    InitiateEmailMFARequestEntity initiateEmailMFARequestEntity;

    @Before
    public void setUp() {
       initiateEmailMFARequestEntity=new InitiateEmailMFARequestEntity();
    }



    @Test
    public void getUsageType()
    {
        initiateEmailMFARequestEntity.setUsageType("UsageType");
        assertTrue(initiateEmailMFARequestEntity.getUsageType().equals("UsageType"));
    }

    @Test
    public void getVerificationType()
    {
        initiateEmailMFARequestEntity.setVerificationType("VerificationType");
        assertTrue(initiateEmailMFARequestEntity.getVerificationType().equals("VerificationType"));
    }

    @Test
    public void setSub()
    {
        initiateEmailMFARequestEntity.setSub("Sub");
        assertTrue(initiateEmailMFARequestEntity.getSub().equals("Sub"));
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

        initiateEmailMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(initiateEmailMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(initiateEmailMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(initiateEmailMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(initiateEmailMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(initiateEmailMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId()
    {
        initiateEmailMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(initiateEmailMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme