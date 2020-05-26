package de.cidaas.sdk.android.Service.Entity.MFA.SetupMFA.BackupCode;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import de.cidaas.sdk.android.service.entity.mfa.SetupMFA.BackupCode.SetupBackupCodeMFAResponseDataEntity;
import de.cidaas.sdk.android.service.entity.mfa.SetupMFA.BackupCode.SetupBackupCodeMFAResponseEntity;


public class SetupBackupCodeMFAResponseEntityTest {
    @Mock
    SetupBackupCodeMFAResponseDataEntity data;
    @InjectMocks
    SetupBackupCodeMFAResponseEntity setupBackupCodeMFAResponseEntity;

    @Before
    public void setUp() {

        setupBackupCodeMFAResponseEntity = new SetupBackupCodeMFAResponseEntity();
    }


    @Test
    public void setSuccess() {
        setupBackupCodeMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(setupBackupCodeMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        setupBackupCodeMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, setupBackupCodeMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new SetupBackupCodeMFAResponseDataEntity();

        data.setStatusId("Test");
        setupBackupCodeMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", setupBackupCodeMFAResponseEntity.getData().getStatusId());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme