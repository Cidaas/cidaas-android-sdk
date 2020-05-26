package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFARequestEntity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class EnrollSmartPushMFARequestEntityTest {

    EnrollSmartPushMFARequestEntity enrollSmartPushMFARequestEntity;

    @Before
    public void setUp() {
        enrollSmartPushMFARequestEntity = new EnrollSmartPushMFARequestEntity();
    }


    @Test
    public void getStatusID() {
        enrollSmartPushMFARequestEntity.setStatusId("Status_ID");
        assertTrue(enrollSmartPushMFARequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getCode() {
        enrollSmartPushMFARequestEntity.setVerifierPassword("Code");
        assertTrue(enrollSmartPushMFARequestEntity.getVerifierPassword().equals("Code"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        enrollSmartPushMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(enrollSmartPushMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(enrollSmartPushMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(enrollSmartPushMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(enrollSmartPushMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(enrollSmartPushMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        enrollSmartPushMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(enrollSmartPushMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme