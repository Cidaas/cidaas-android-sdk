package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SetupBackupCodeRequestEntityTest {
      SetupBackupCodeRequestEntity setupBackupCodeRequestEntity;

    @Before
    public void setUp() {

        setupBackupCodeRequestEntity=new SetupBackupCodeRequestEntity();
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

        setupBackupCodeRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(setupBackupCodeRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(setupBackupCodeRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(setupBackupCodeRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(setupBackupCodeRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(setupBackupCodeRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme