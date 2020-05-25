package de.cidaas.cidaasv2.Controller.Repository.Configuration.FIDO;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


@RunWith(RobolectricTestRunner.class)

public class FIDOConfigurationControllerTest {

    Context context;

    FIDOConfigurationController shared;

    FIDOConfigurationController fIDOConfigurationController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        fIDOConfigurationController = new FIDOConfigurationController(context);
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