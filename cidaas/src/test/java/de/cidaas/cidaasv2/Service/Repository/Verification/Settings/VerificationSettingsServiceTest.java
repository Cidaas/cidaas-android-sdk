package de.cidaas.cidaasv2.Service.Repository.Verification.Settings;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;

import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class VerificationSettingsServiceTest {

    Context context;

    VerificationSettingsService verificationSettingsService;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        verificationSettingsService = new VerificationSettingsService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        VerificationSettingsService result = VerificationSettingsService.getShared(null);
        Assert.assertTrue(result instanceof VerificationSettingsService);
    }

    @Test
    public void testGetmfaList() throws Exception {

        verificationSettingsService.getmfaList("baseurl", "sub", "userDeviceID", null, new Result<MFAListResponseEntity>() {
            @Override
            public void success(MFAListResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testGetmfaListnull() throws Exception {

        verificationSettingsService.getmfaList("", "", "userDeviceID", null, new Result<MFAListResponseEntity>() {
            @Override
            public void success(MFAListResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme