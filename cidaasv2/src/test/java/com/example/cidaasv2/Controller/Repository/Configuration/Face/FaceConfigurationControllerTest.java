package com.example.cidaasv2.Controller.Repository.Configuration.Face;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class FaceConfigurationControllerTest {
    Context context;
    FaceConfigurationController shared;

    FaceConfigurationController faceConfigurationController;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        faceConfigurationController=new FaceConfigurationController(context);
    }

    @Test
    public void testGenerateChallenge() throws Exception {
        faceConfigurationController.generateChallenge();
    }

    @Test
    public void testGetShared() throws Exception {
        FaceConfigurationController result = FaceConfigurationController.getShared(null);
        Assert.assertTrue(result instanceof FaceConfigurationController);
    }

    @Test
    public void testConfigureFace() throws Exception {
    //    faceConfigurationController.ConfigureFace(new File(getClass().getResource("/com/example/cidaasv2/Controller/Repository/Configuration/Face/PleaseReplaceMeWithTestFile.txt").getFile()), "sub", "baseurl", new SetupFaceMFARequestEntity(), null);
    }

    @Test
    public void testLoginWithFace() throws Exception {
        //faceConfigurationController.LoginWithFace(new File(getClass().getResource("/com/example/cidaasv2/Controller/Repository/Configuration/Face/PleaseReplaceMeWithTestFile.txt").getFile()), "baseurl", "clientId", "trackId", "requestId", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme