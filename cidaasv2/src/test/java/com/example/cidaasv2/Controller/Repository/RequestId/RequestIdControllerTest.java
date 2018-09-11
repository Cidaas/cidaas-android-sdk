package com.example.cidaasv2.Controller.Repository.RequestId;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class RequestIdControllerTest {
    Context context;
    RequestIdController shared;
    RequestIdController requestIdController;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        requestIdController=new RequestIdController(context);
    }

    @Test
    public void testGenerateChallenge() throws Exception {
        requestIdController.generateChallenge();
    }

    @Test
    public void testGetShared() throws Exception {
        RequestIdController result = RequestIdController.getShared(null);
        Assert.assertTrue(result instanceof RequestIdController);
    }

    @Test
    public void testGetRequestId() throws Exception {
        requestIdController.getRequestId(null, new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(AuthRequestResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme