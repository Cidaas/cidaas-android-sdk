package de.cidaas.cidaasv2.Service.Repository.Verification.Voice;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Helper.Genral.DBHelper;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceRequestEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFARequestEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFAResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFARequestEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFAResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFARequestEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFAResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.Scanned.ScannedRequestEntity;
import de.cidaas.sdk.android.cidaas.Service.Scanned.ScannedResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class VoiceVerificationServiceTest {
    Context context;
    VoiceVerificationService voiceVerificationService;
    EnrollVoiceMFARequestEntity enrollVoiceMFARequestEntity;

    @Before
    public void setUp() {

        context = RuntimeEnvironment.application;
        DBHelper.setConfig(context);
        voiceVerificationService = new VoiceVerificationService(context);
        enrollVoiceMFARequestEntity = new EnrollVoiceMFARequestEntity();
        // enrollVoiceMFARequestEntity.setDeviceInfo(DBHelper.getShared().getDeviceInfo());
        enrollVoiceMFARequestEntity.setAudioFile(null);
        enrollVoiceMFARequestEntity.setStatusId("status id");


        WebAuthError.getShared(context);

    }

    @Test
    public void testGetShared() throws Exception {
        VoiceVerificationService result = VoiceVerificationService.getShared(null);
        Assert.assertTrue(result instanceof VoiceVerificationService);
    }

    @Test
    public void testScannedVoice() throws Exception {


        ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
        scannedRequestEntity.setClient_id("client");
        scannedRequestEntity.setStatusId("sid");
        scannedRequestEntity.setUsage_pass("upass");
        scannedRequestEntity.setDeviceInfo(new DeviceInfoEntity());

        voiceVerificationService.scannedVoice("baseurl", scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
            @Override
            public void success(ScannedResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupVoiceMFA() throws Exception {

        voiceVerificationService.setupVoiceMFA("baseurl", "accessToken", new SetupVoiceMFARequestEntity(), null, new Result<SetupVoiceMFAResponseEntity>() {
            @Override
            public void success(SetupVoiceMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollVoice() throws Exception {

        voiceVerificationService.enrollVoice("baseurl", "accessToken", new EnrollVoiceMFARequestEntity(), null, new Result<EnrollVoiceMFAResponseEntity>() {
            @Override
            public void success(EnrollVoiceMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateVoice() throws Exception {

        voiceVerificationService.initiateVoice("baseurl", new InitiateVoiceMFARequestEntity(), null, new Result<InitiateVoiceMFAResponseEntity>() {
            @Override
            public void success(InitiateVoiceMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticateVoice() throws Exception {

        voiceVerificationService.authenticateVoice("baseurl", new AuthenticateVoiceRequestEntity(), null, new Result<AuthenticateVoiceResponseEntity>() {
            @Override
            public void success(AuthenticateVoiceResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
