package com.example.cidaasv2.Controller.Repository.Configuration.BackupCode;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeRequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class BackupCodeConfigurationControllerTest {

    Context context;

    BackupCodeConfigurationController shared;

    BackupCodeConfigurationController backupCodeConfigurationController;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        backupCodeConfigurationController=new BackupCodeConfigurationController(context);
    }

    @Test
    public void testGetShared() throws Exception {
        BackupCodeConfigurationController result = BackupCodeConfigurationController.getShared(null);
        Assert.assertTrue(result instanceof BackupCodeConfigurationController);
    }

    @Test
    public void testConfigureBackupCode() throws Exception {
        backupCodeConfigurationController.configureBackupCode("sub", "baseurl", null);
    }

    @Test
    public void testLoginWithBackupCode() throws Exception {
        backupCodeConfigurationController.loginWithBackupCode("code", "baseurl", "trackId", "clientId", "requestId", null, null);
    }

    @Test
    public void testVerifyBackupCode() throws Exception {
        backupCodeConfigurationController.verifyBackupCode("baseurl", "clientId", new AuthenticateBackupCodeRequestEntity(), new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme