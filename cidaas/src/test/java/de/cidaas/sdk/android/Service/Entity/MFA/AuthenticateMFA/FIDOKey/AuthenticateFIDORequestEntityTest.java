package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.FIDOKey;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.FIDOKey.AuthenticateFIDORequestEntity;

import static org.junit.Assert.assertTrue;


public class AuthenticateFIDORequestEntityTest {


    AuthenticateFIDORequestEntity authenticateFIDORequestEntity;

    @Before
    public void setUp() {
        authenticateFIDORequestEntity = new AuthenticateFIDORequestEntity();
    }

    @Test
    public void getStatusID() {

        authenticateFIDORequestEntity.setStatusId("Status_ID");
        assertTrue(authenticateFIDORequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getVerifierPassword() {
        authenticateFIDORequestEntity.setCode("Password");
        assertTrue(authenticateFIDORequestEntity.getCode().equals("Password"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        authenticateFIDORequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(authenticateFIDORequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(authenticateFIDORequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(authenticateFIDORequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(authenticateFIDORequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(authenticateFIDORequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme