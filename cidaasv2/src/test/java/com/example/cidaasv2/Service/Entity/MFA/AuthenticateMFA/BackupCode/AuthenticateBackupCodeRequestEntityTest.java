package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateBackupCodeRequestEntityTest {

    DeviceInfoEntity deviceInfo;

    AuthenticateBackupCodeRequestEntity authenticateBackupCodeRequestEntity=new AuthenticateBackupCodeRequestEntity();

    @Before
    public void setUp() {

    }


    @Test
    public void getStatusID()
    {
        authenticateBackupCodeRequestEntity.setStatusId("Status_ID");
        assertTrue(authenticateBackupCodeRequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getVerifierPassword()
    {
        authenticateBackupCodeRequestEntity.setVerifierPassword("Password");
        assertTrue(authenticateBackupCodeRequestEntity.getVerifierPassword().equals("Password"));
    }


    @Test
    public void getDeviceInfoEntity()
    {
        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        authenticateBackupCodeRequestEntity.setDeviceInfo(deviceInfo);

/*        assertTrue(authenticateBackupCodeRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(authenticateBackupCodeRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(authenticateBackupCodeRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(authenticateBackupCodeRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(authenticateBackupCodeRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));*/
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme