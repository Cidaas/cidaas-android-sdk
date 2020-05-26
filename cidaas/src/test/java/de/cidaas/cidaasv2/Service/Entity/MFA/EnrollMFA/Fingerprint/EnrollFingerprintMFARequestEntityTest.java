package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;


import static junit.framework.Assert.assertTrue;

public class EnrollFingerprintMFARequestEntityTest {

    EnrollFingerprintMFARequestEntity enrollFingerprintMFARequestEntity;

    @Before
    public void setUp() {

        enrollFingerprintMFARequestEntity = new EnrollFingerprintMFARequestEntity();
    }


    @Test
    public void getStatusID() {
        enrollFingerprintMFARequestEntity.setStatusId("Status_ID");
        assertTrue(enrollFingerprintMFARequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getCode() {
        enrollFingerprintMFARequestEntity.setVerifierPassword("Code");
        assertTrue(enrollFingerprintMFARequestEntity.getVerifierPassword().equals("Code"));
    }

    @Test
    public void setSub() {
        enrollFingerprintMFARequestEntity.setSub("Sub");
        assertTrue(enrollFingerprintMFARequestEntity.getSub().equals("Sub"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        enrollFingerprintMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(enrollFingerprintMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(enrollFingerprintMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(enrollFingerprintMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(enrollFingerprintMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(enrollFingerprintMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        enrollFingerprintMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(enrollFingerprintMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme