package de.cidaas.cidaasv2.Service.Repository.Verification.FIDO;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


@RunWith(RobolectricTestRunner.class)
public class FIDOVerificationServiceTest {
    Context context;
    FIDOVerificationService fIDOVerificationService;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
    }

    @Test
    public void testGetShared() throws Exception {
        FIDOVerificationService result = FIDOVerificationService.getShared(null);
        Assert.assertTrue(result instanceof FIDOVerificationService);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme