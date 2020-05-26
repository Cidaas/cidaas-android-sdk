package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.SmartPush;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.SmartPush.AuthenticateSmartPushRequestEntity;

import static org.junit.Assert.assertTrue;


public class AuthenticateSmartPushRequestEntityTest {

    DeviceInfoEntity deviceInfo;

    AuthenticateSmartPushRequestEntity authenticateSmartPushRequestEntity;

    @Before
    public void setUp() {
        authenticateSmartPushRequestEntity = new AuthenticateSmartPushRequestEntity();
    }

    @Test
    public void getStatusID() {
        authenticateSmartPushRequestEntity.setStatusId("Status_ID");
        assertTrue(authenticateSmartPushRequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getVerifierPassword() {
        authenticateSmartPushRequestEntity.setVerifierPassword("Password");
        assertTrue(authenticateSmartPushRequestEntity.getVerifierPassword().equals("Password"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        authenticateSmartPushRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(authenticateSmartPushRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(authenticateSmartPushRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(authenticateSmartPushRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(authenticateSmartPushRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(authenticateSmartPushRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        authenticateSmartPushRequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(authenticateSmartPushRequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme