package de.cidaas.sdk.android.Service.Entity.MFA.EnrollMFA.BackupCode;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.BackupCode.EnrollBackupCodeMFAResponseEntity;
import de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.BackupCode.EnrollBackupCodeResponseDataEntity;


public class EnrollBackupCodeMFAResponseEntityTest {

    EnrollBackupCodeResponseDataEntity data;

    EnrollBackupCodeMFAResponseEntity enrollBackupCodeMFAResponseEntity;

    @Before
    public void setUp() {
        enrollBackupCodeMFAResponseEntity = new EnrollBackupCodeMFAResponseEntity();
    }

    @Test
    public void setSuccess() {
        enrollBackupCodeMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(enrollBackupCodeMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        enrollBackupCodeMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, enrollBackupCodeMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new EnrollBackupCodeResponseDataEntity();

        data.setSub("Test");
        enrollBackupCodeMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", enrollBackupCodeMFAResponseEntity.getData().getSub());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme