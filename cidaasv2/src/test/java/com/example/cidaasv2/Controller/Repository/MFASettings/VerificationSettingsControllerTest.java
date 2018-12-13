package com.example.cidaasv2.Controller.Repository.MFASettings;

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
public class VerificationSettingsControllerTest {
    Context context;
    VerificationSettingsController shared;
    VerificationSettingsController mFAListSettingsController;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        mFAListSettingsController=new VerificationSettingsController(context);
    }

    @Test
    public void testGetShared() throws Exception {
        VerificationSettingsController result = VerificationSettingsController.getShared(null);
        Assert.assertTrue(result instanceof VerificationSettingsController);
    }

    @Test
    public void testGetmfaList() throws Exception {
        mFAListSettingsController.getmfaList("baseurl", "sub", null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme