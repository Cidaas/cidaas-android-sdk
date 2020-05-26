package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;


public class AuthenticateTOTPRequestEntityTest {


    AuthenticateTOTPRequestEntity authenticateTOTPRequestEntity;

    @Before
    public void setUp() {
        authenticateTOTPRequestEntity = new AuthenticateTOTPRequestEntity();
    }

    @Test
    public void getStatusID() {
        authenticateTOTPRequestEntity.setStatusId("Status_ID");
        assertTrue(authenticateTOTPRequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getVerifierPassword() {
        authenticateTOTPRequestEntity.setVerifierPassword("Password");
        assertTrue(authenticateTOTPRequestEntity.getVerifierPassword().equals("Password"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        authenticateTOTPRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(authenticateTOTPRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(authenticateTOTPRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(authenticateTOTPRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(authenticateTOTPRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(authenticateTOTPRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        authenticateTOTPRequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(authenticateTOTPRequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme