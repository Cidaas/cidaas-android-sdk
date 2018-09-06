package com.example.cidaasv2.Service.Repository.Consent;

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
public class ConsentServiceTest {

    Context context;
    ConsentService consentService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
     consentService=new ConsentService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        ConsentService result = ConsentService.getShared(null);
        Assert.assertTrue(result instanceof ConsentService);
    }

    @Test
    public void testGetConsentDetails() throws Exception {

        consentService.getConsentDetails("baseurl", "consentName", null);
    }

    @Test
    public void testAcceptConsent() throws Exception {

       // consentService.acceptConsent("baseurl", null, null);
    }

    @Test
    public void testResumeConsent() throws Exception {

//        consentService.resumeConsent("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme