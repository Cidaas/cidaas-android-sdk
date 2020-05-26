package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.BackupCode;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.BackupCode.AuthenticateBackupCodeRequestDevice;

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