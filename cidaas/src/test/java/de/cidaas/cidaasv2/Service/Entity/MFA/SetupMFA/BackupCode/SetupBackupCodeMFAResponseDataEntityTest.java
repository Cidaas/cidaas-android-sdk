package de.cidaas.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.BackupCode.BackupCodeData;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeMFAResponseDataEntity;

public class SetupBackupCodeMFAResponseDataEntityTest {
    //Field backupCodes of type BackupCodeData[] - was not mocked since Mockito doesn't mock arrays
    SetupBackupCodeMFAResponseDataEntity setupBackupCodeMFAResponseDataEntity = new SetupBackupCodeMFAResponseDataEntity();

    @Test
    public void testGetBackupCodes() throws Exception {
        BackupCodeData data = new BackupCodeData();
        data.setCode("Code");


        BackupCodeData[] result = {data};
        setupBackupCodeMFAResponseDataEntity.setBackupCodes(result);

        Assert.assertEquals("Code", setupBackupCodeMFAResponseDataEntity.getBackupCodes()[0].getCode());
    }

    @Test
    public void setStatusId() throws Exception {

        setupBackupCodeMFAResponseDataEntity.setStatusId("StatusId");

        Assert.assertEquals("StatusId", setupBackupCodeMFAResponseDataEntity.getStatusId());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme