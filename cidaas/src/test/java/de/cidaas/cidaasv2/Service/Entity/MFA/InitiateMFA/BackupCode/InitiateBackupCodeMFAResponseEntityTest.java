package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class InitiateBackupCodeMFAResponseEntityTest {

    InitiateBackupCodeMFAResponseDataEntity data;

    InitiateBackupCodeMFAResponseEntity initiateBackupCodeMFAResponseEntity;

    @Before
    public void setUp() {
        initiateBackupCodeMFAResponseEntity = new InitiateBackupCodeMFAResponseEntity();
    }


    @Test
    public void setSuccess() {
        initiateBackupCodeMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(initiateBackupCodeMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        initiateBackupCodeMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, initiateBackupCodeMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new InitiateBackupCodeMFAResponseDataEntity();

        data.setStatusId("Test");
        initiateBackupCodeMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", initiateBackupCodeMFAResponseEntity.getData().getStatusId());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme