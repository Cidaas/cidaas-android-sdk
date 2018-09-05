package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateFingerprintRequestEntityTest {

    DeviceInfoEntity deviceInfo;

    AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity;

    @Before
    public void setUp() {
          authenticateFingerprintRequestEntity=new AuthenticateFingerprintRequestEntity();
    }
    @Test
    public void getStatusID()
    {
        authenticateFingerprintRequestEntity.setStatusId("Status_ID");
        assertTrue(authenticateFingerprintRequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getVerifierPassword()
    {
        authenticateFingerprintRequestEntity.setVerifierPassword("Password");
        assertTrue(authenticateFingerprintRequestEntity.getVerifierPassword().equals("Password"));
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

        authenticateFingerprintRequestEntity.setDeviceInfo(deviceInfo);

        /*assertTrue(authenticateFingerprintRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(authenticateFingerprintRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(authenticateFingerprintRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(authenticateFingerprintRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(authenticateFingerprintRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));*/
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme