package com.example.cidaasv2.Service.Repository.Verification.BackupCode;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BackupCodeVerificationServiceTest {

    Context context;
    BackupCodeVerificationService backupCodeVerificationService;

    @Before
    public void setUp() {
      backupCodeVerificationService=new BackupCodeVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        BackupCodeVerificationService result = BackupCodeVerificationService.getShared(null);
        Assert.assertEquals(new BackupCodeVerificationService(null), result);
    }

    @Test
    public void testSetupBackupCodeMFA() throws Exception {

        backupCodeVerificationService.setupBackupCodeMFA("baseurl", "accessToken", null);
    }

    @Test
    public void testInitiateBackupCodeMFA() throws Exception {

        backupCodeVerificationService.initiateBackupCodeMFA("baseurl", null, null);
    }

    @Test
    public void testAuthenticateBackupCodeMFA() throws Exception {

        backupCodeVerificationService.authenticateBackupCodeMFA("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme