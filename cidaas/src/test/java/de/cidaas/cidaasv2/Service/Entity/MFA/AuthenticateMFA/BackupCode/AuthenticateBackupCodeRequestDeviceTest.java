package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode;

import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeRequestDevice;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateBackupCodeRequestDeviceTest {

    @Test
    public void getDeviceId() {

        AuthenticateBackupCodeRequestDevice authenticateBackupCodeRequestDevice = new AuthenticateBackupCodeRequestDevice();
        authenticateBackupCodeRequestDevice.setDeviceId("Device ID");
        assertTrue(authenticateBackupCodeRequestDevice.getDeviceId() == "Device ID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme