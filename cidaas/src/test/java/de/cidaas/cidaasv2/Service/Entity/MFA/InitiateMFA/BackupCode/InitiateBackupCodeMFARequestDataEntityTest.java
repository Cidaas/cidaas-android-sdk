package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFARequestDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateBackupCodeMFARequestDataEntityTest {

    InitiateBackupCodeMFARequestDataEntity initiateBackupCodeMFARequestDataEntity = new InitiateBackupCodeMFARequestDataEntity();

    @Test
    public void getDeviceId() {
        initiateBackupCodeMFARequestDataEntity.setDeviceId("DeviceID");
        assertTrue(initiateBackupCodeMFARequestDataEntity.getDeviceId() == "DeviceID");

    }
}


//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme