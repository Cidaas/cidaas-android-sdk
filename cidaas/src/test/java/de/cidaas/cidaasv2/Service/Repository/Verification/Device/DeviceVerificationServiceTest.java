package de.cidaas.cidaasv2.Service.Repository.Verification.Device;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


@RunWith(RobolectricTestRunner.class)
public class DeviceVerificationServiceTest {

    Context context;
    DeviceVerificationService deviceVerificationService;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        deviceVerificationService = new DeviceVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        DeviceVerificationService result = DeviceVerificationService.getShared(null);

        Assert.assertTrue(result instanceof DeviceVerificationService);

    }

    @Test
    public void testValidateDevice() throws Exception {

        deviceVerificationService.validateDevice("baseurl", "intermediateId", "statusId", "codeVerifier", null, new Result<ValidateDeviceResponseEntity>() {
            @Override
            public void success(ValidateDeviceResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
