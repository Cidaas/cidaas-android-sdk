package com.example.cidaasv2.Service.Repository.Verification.FIDO;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FIDOVerificationServiceTest {
    Context context;
    FIDOVerificationService fIDOVerificationService;

    @Before
    public void setUp() {

    }

    @Test
    public void testGetShared() throws Exception {
        FIDOVerificationService result = FIDOVerificationService.getShared(null);
        Assert.assertTrue(result instanceof FIDOVerificationService);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme