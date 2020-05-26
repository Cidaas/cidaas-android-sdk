package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.Fingerprint;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.Fingerprint.AuthenticateFingerprintRequestEntity;

import static org.junit.Assert.assertTrue;


public class AuthenticateFingerprintRequestEntityTest {


    AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity;

    @Before
    public void setUp() {
        authenticateFingerprintRequestEntity = new AuthenticateFingerprintRequestEntity();
    }

    @Test
    public void getStatusID() {
        authenticateFingerprintRequestEntity.setStatusId("Status_ID");
        assertTrue(authenticateFingerprintRequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getVerifierPassword() {
        authenticateFingerprintRequestEntity.setVerifierPassword("Password");
        assertTrue(authenticateFingerprintRequestEntity.getVerifierPassword().equals("Password"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        authenticateFingerprintRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(authenticateFingerprintRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(authenticateFingerprintRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(authenticateFingerprintRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(authenticateFingerprintRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(authenticateFingerprintRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        authenticateFingerprintRequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(authenticateFingerprintRequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme