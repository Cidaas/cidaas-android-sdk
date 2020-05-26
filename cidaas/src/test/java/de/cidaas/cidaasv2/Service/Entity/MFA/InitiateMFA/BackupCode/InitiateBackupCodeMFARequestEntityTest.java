package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFARequestEntity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class InitiateBackupCodeMFARequestEntityTest {

    InitiateBackupCodeMFARequestEntity initiateBackupCodeMFARequestEntity;

    @Before
    public void setUp() {
        initiateBackupCodeMFARequestEntity = new InitiateBackupCodeMFARequestEntity();
    }


    @Test
    public void setPhysicalVerification() {
        initiateBackupCodeMFARequestEntity.setPhysicalVerificationId("physicalVerification");
        assertTrue(initiateBackupCodeMFARequestEntity.getPhysicalVerificationId().equals("physicalVerification"));
    }

    @Test
    public void getUsageType() {
        initiateBackupCodeMFARequestEntity.setUsageType("UsageType");
        assertTrue(initiateBackupCodeMFARequestEntity.getUsageType().equals("UsageType"));
    }

    @Test
    public void getVerificationType() {
        initiateBackupCodeMFARequestEntity.setVerificationType("VerificationType");
        assertTrue(initiateBackupCodeMFARequestEntity.getVerificationType().equals("VerificationType"));
    }

    @Test
    public void setSub() {
        initiateBackupCodeMFARequestEntity.setSub("Sub");
        assertTrue(initiateBackupCodeMFARequestEntity.getSub().equals("Sub"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        initiateBackupCodeMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(initiateBackupCodeMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(initiateBackupCodeMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(initiateBackupCodeMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(initiateBackupCodeMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(initiateBackupCodeMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        initiateBackupCodeMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(initiateBackupCodeMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme