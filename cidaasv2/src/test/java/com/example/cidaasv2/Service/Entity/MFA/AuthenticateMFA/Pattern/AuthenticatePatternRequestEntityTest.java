package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticatePatternRequestEntityTest {

    DeviceInfoEntity deviceInfo;

    AuthenticatePatternRequestEntity authenticatePatternRequestEntity;

    @Before
    public void setUp() {
authenticatePatternRequestEntity=new AuthenticatePatternRequestEntity();
    }

    @Test
    public void getStatusID()
    {
        authenticatePatternRequestEntity.setStatusId("Status_ID");
        assertTrue(authenticatePatternRequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getVerifierPassword()
    {
        authenticatePatternRequestEntity.setVerifierPassword("Password");
        assertTrue(authenticatePatternRequestEntity.getVerifierPassword().equals("Password"));
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

        authenticatePatternRequestEntity.setDeviceInfo(deviceInfo);

/*        assertTrue(authenticatePatternRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(authenticatePatternRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(authenticatePatternRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(authenticatePatternRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(authenticatePatternRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));*/
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme