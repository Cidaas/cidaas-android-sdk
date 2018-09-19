package com.example.cidaasv2.Service.Repository.Verification.BackupCode;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeMFAResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class BackupCodeVerificationServiceTest {

    Context context;
    BackupCodeVerificationService backupCodeVerificationService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
      backupCodeVerificationService=new BackupCodeVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        BackupCodeVerificationService result = BackupCodeVerificationService.getShared(null);

        Assert.assertTrue(result instanceof BackupCodeVerificationService);
    }

    @Test
    public void testSetupBackupCodeMFA() throws Exception {

        backupCodeVerificationService.setupBackupCodeMFA("baseurl", "accessToken", new Result<SetupBackupCodeMFAResponseEntity>() {
            @Override
            public void success(SetupBackupCodeMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateBackupCodeMFA() throws Exception {

        backupCodeVerificationService.initiateBackupCodeMFA("baseurl", new InitiateBackupCodeMFARequestEntity(), new Result<InitiateBackupCodeMFAResponseEntity>() {
            @Override
            public void success(InitiateBackupCodeMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticateBackupCodeMFA() throws Exception {

        backupCodeVerificationService.authenticateBackupCodeMFA("baseurl", new AuthenticateBackupCodeRequestEntity(), new Result<AuthenticateBackupCodeResponseEntity>() {
            @Override
            public void success(AuthenticateBackupCodeResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
