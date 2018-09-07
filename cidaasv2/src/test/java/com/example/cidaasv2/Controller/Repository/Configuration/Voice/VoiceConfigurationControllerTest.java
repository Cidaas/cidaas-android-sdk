package com.example.cidaasv2.Controller.Repository.Configuration.Voice;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFARequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class VoiceConfigurationControllerTest {
    Context context;
    VoiceConfigurationController shared;
    VoiceConfigurationController voiceConfigurationController;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        voiceConfigurationController=new VoiceConfigurationController(context);
    }

    @Test
    public void testGenerateChallenge() throws Exception {
        voiceConfigurationController.generateChallenge();
    }

    @Test
    public void testGetShared() throws Exception {
        VoiceConfigurationController result = VoiceConfigurationController.getShared(context);
        Assert.assertTrue(result instanceof VoiceConfigurationController);
    }

   /* @Test
    public void testConfigureVoice() throws Exception {
        voiceConfigurationController.configureVoice("sub", "baseurl", new File(getClass().getResource("/com/example/cidaasv2/Controller/Repository/Configuration/Voice/PleaseReplaceMeWithTestFile.txt").getFile()), new SetupVoiceMFARequestEntity(), null);
    }

    @Test
    public void testLoginWithVoice() throws Exception {
        voiceConfigurationController.LoginWithVoice(new File(getClass().getResource("/com/example/cidaasv2/Controller/Repository/Configuration/Voice/PleaseReplaceMeWithTestFile.txt").getFile()), "baseurl", "clientId", "trackId", "requestId", null, null);
    }*/
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme