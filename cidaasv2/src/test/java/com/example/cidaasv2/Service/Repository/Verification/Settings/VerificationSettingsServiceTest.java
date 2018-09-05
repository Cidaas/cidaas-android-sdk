package com.example.cidaasv2.Service.Repository.Verification.Settings;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VerificationSettingsServiceTest {

    Context context;

    VerificationSettingsService verificationSettingsService;

    @Before
    public void setUp() {
 verificationSettingsService=new VerificationSettingsService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        VerificationSettingsService result = VerificationSettingsService.getShared(null);
        Assert.assertEquals(new VerificationSettingsService(null), result);
    }

    @Test
    public void testGetmfaList() throws Exception {

        verificationSettingsService.getmfaList("baseurl", "sub", "userDeviceID", null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme