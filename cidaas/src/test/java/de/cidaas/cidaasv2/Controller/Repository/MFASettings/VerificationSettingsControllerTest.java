package de.cidaas.cidaasv2.Controller.Repository.MFASettings;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)

public class VerificationSettingsControllerTest {
    Context context;
    VerificationSettingsController shared;
    VerificationSettingsController mFAListSettingsController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        mFAListSettingsController = new VerificationSettingsController(context);
    }

    @Test
    public void testGetShared() throws Exception {
        VerificationSettingsController result = VerificationSettingsController.getShared(null);
        Assert.assertTrue(result instanceof VerificationSettingsController);
    }

    @Test
    public void testGetmfaList() throws Exception {
        mFAListSettingsController.getmfaList("sub", null);
    }
}