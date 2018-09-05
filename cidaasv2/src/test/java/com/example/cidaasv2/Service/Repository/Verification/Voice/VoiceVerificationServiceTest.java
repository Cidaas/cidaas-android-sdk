package com.example.cidaasv2.Service.Repository.Verification.Voice;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VoiceVerificationServiceTest {
    Context context;
    VoiceVerificationService voiceVerificationService;

    @Before
    public void setUp() {
       voiceVerificationService=new VoiceVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        VoiceVerificationService result = VoiceVerificationService.getShared(null);
        Assert.assertEquals(new VoiceVerificationService(null), result);
    }

    @Test
    public void testScannedVoice() throws Exception {

        voiceVerificationService.scannedVoice("baseurl", "usagePass", "statusId", "AccessToken", null);
    }

    @Test
    public void testSetupVoiceMFA() throws Exception {

        voiceVerificationService.setupVoiceMFA("baseurl", "accessToken", "codeChallenge", null, null);
    }

    @Test
    public void testEnrollVoice() throws Exception {

        voiceVerificationService.enrollVoice("baseurl", "accessToken", null, null);
    }

    @Test
    public void testInitiateVoice() throws Exception {

        voiceVerificationService.initiateVoice("baseurl", "codeChallenge", null, null);
    }

    @Test
    public void testAuthenticateVoice() throws Exception {
       
        voiceVerificationService.authenticateVoice("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme