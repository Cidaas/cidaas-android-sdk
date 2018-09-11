package com.example.cidaasv2.Controller.Repository.Consent;

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
public class ConsentControllerTest {

    Context context;
    ConsentController shared;
    ConsentController consentController;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        consentController=new ConsentController(context);
    }

    @Test
    public void testGetShared() throws Exception {
        ConsentController result = ConsentController.getShared(null);
        Assert.assertTrue(result instanceof ConsentController);
    }

    @Test
    public void testGetConsentURL() throws Exception {
        consentController.getConsentURL("consentName", "consentVersion", null);
    }

    @Test
    public void testGetConsentDetails() throws Exception {
        consentController.getConsentDetails("baseurl", "consentName", null);
    }

    @Test
    public void testAcceptConsent() throws Exception {
        consentController.acceptConsent("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme