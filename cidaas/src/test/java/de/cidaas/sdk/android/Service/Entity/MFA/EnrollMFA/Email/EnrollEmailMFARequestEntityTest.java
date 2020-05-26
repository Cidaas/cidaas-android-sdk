package de.cidaas.sdk.android.Service.Entity.MFA.EnrollMFA.Email;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.Email.EnrollEmailMFARequestEntity;

import static junit.framework.Assert.assertTrue;

public class EnrollEmailMFARequestEntityTest {


    EnrollEmailMFARequestEntity enrollEmailMFARequestEntity;

    @Before
    public void setUp() {
        enrollEmailMFARequestEntity = new EnrollEmailMFARequestEntity();
    }


    @Test
    public void getStatusID() {
        enrollEmailMFARequestEntity.setStatusId("Status_ID");
        assertTrue(enrollEmailMFARequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getCode() {
        enrollEmailMFARequestEntity.setCode("Code");
        assertTrue(enrollEmailMFARequestEntity.getCode().equals("Code"));
    }

    @Test
    public void setSub() {
        enrollEmailMFARequestEntity.setSub("Sub");
        assertTrue(enrollEmailMFARequestEntity.getSub().equals("Sub"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        enrollEmailMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(enrollEmailMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(enrollEmailMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(enrollEmailMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(enrollEmailMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(enrollEmailMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme