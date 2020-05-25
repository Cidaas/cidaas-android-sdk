package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRRequestEntity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateIVRRequestEntityTest {


    AuthenticateIVRRequestEntity authenticateIVRRequestEntity;

    @Before
    public void setUp() {
        authenticateIVRRequestEntity = new AuthenticateIVRRequestEntity();
    }

    @Test
    public void getStatusID() {
        authenticateIVRRequestEntity.setStatusId("Status_ID");
        assertTrue(authenticateIVRRequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getVerifierPassword() {
        authenticateIVRRequestEntity.setCode("Password");
        assertTrue(authenticateIVRRequestEntity.getCode().equals("Password"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        authenticateIVRRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(authenticateIVRRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(authenticateIVRRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(authenticateIVRRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(authenticateIVRRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(authenticateIVRRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme