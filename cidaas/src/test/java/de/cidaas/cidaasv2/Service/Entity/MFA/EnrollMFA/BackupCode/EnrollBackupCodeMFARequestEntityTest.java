package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.BackupCode;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.BackupCode.EnrollBackupCodeMFARequestEntity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class EnrollBackupCodeMFARequestEntityTest {

    EnrollBackupCodeMFARequestEntity enrollBackupCodeMFARequestEntity;

    @Before
    public void setUp() {
        enrollBackupCodeMFARequestEntity = new EnrollBackupCodeMFARequestEntity();
    }


    @Test
    public void getStatusID() {
        enrollBackupCodeMFARequestEntity.setStatusId("Status_ID");
        assertTrue(enrollBackupCodeMFARequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getCode() {
        enrollBackupCodeMFARequestEntity.setCode("Code");
        assertTrue(enrollBackupCodeMFARequestEntity.getCode().equals("Code"));
    }

    @Test
    public void setSub() {
        enrollBackupCodeMFARequestEntity.setSub("Sub");
        assertTrue(enrollBackupCodeMFARequestEntity.getSub().equals("Sub"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        enrollBackupCodeMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(enrollBackupCodeMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(enrollBackupCodeMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(enrollBackupCodeMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(enrollBackupCodeMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(enrollBackupCodeMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme