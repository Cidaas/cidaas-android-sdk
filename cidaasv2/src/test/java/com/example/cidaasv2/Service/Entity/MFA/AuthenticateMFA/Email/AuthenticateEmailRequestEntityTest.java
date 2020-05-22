package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateEmailRequestEntityTest {
    private AuthenticateEmailRequestEntity authenticateEmailRequestEntity;

    @Before
    public void setUp() {
        authenticateEmailRequestEntity = new AuthenticateEmailRequestEntity();
    }

    @Test
    public void getStatusID()
    {
        authenticateEmailRequestEntity.setStatus_id("Status_ID");
        assertTrue(authenticateEmailRequestEntity.getStatus_id().equals("Status_ID"));
    }

    @Test
    public void getCode()
    {
        authenticateEmailRequestEntity.setCode("Password");
        assertTrue(authenticateEmailRequestEntity.getCode().equals("Password"));
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

        authenticateEmailRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(authenticateEmailRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(authenticateEmailRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(authenticateEmailRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(authenticateEmailRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(authenticateEmailRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

}
