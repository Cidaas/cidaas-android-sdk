package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;


import static junit.framework.Assert.assertTrue;

public class EnrollIVRMFARequestEntityTest {

    EnrollIVRMFARequestEntity enrollIVRMFARequestEntity;

    @Before
    public void setUp() {

        enrollIVRMFARequestEntity = new EnrollIVRMFARequestEntity();
    }


    @Test
    public void getStatusID() {
        enrollIVRMFARequestEntity.setStatusId("Status_ID");
        assertTrue(enrollIVRMFARequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getCode() {
        enrollIVRMFARequestEntity.setCode("Code");
        assertTrue(enrollIVRMFARequestEntity.getCode().equals("Code"));
    }

    @Test
    public void setSub() {
        enrollIVRMFARequestEntity.setSub("Sub");
        assertTrue(enrollIVRMFARequestEntity.getSub().equals("Sub"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        enrollIVRMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(enrollIVRMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(enrollIVRMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(enrollIVRMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(enrollIVRMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(enrollIVRMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme