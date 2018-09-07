package com.example.cidaasv2.Controller.Repository.Configuration.FIDO;

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
public class FIDOConfigurationControllerTest {

    Context context;

    FIDOConfigurationController shared;

    FIDOConfigurationController fIDOConfigurationController;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        fIDOConfigurationController=new FIDOConfigurationController(context);
    }

    @Test
    public void testGenerateChallenge() throws Exception {
        fIDOConfigurationController.generateChallenge();
    }

    @Test
    public void testGetShared() throws Exception {
        FIDOConfigurationController result = FIDOConfigurationController.getShared(null);
        Assert.assertTrue(result instanceof FIDOConfigurationController);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme