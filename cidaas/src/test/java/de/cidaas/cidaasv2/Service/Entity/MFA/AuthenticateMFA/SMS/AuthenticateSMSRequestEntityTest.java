package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSRequestEntity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateSMSRequestEntityTest {

    DeviceInfoEntity deviceInfo;

    AuthenticateSMSRequestEntity authenticateSMSRequestEntity;

    @Before
    public void setUp() {
        authenticateSMSRequestEntity = new AuthenticateSMSRequestEntity();
    }

    @Test
    public void getStatusID() {
        authenticateSMSRequestEntity.setStatusId("Status_ID");
        assertTrue(authenticateSMSRequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getVerifierPassword() {
        authenticateSMSRequestEntity.setCode("Password");
        assertTrue(authenticateSMSRequestEntity.getCode().equals("Password"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        authenticateSMSRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(authenticateSMSRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(authenticateSMSRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(authenticateSMSRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(authenticateSMSRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(authenticateSMSRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme