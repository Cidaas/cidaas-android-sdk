package com.example.cidaasv2.Service.Repository.RequestId;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class RequestIdServiceTest {

    Context context;
    RequestIdService requestIdService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
       requestIdService=new RequestIdService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        RequestIdService result = RequestIdService.getShared(null);

        Assert.assertTrue(result instanceof RequestIdService);
    }

    @Test
    public void testGetRequestID() throws Exception {

        requestIdService.getRequestID(null, new Result<AuthRequestResponseEntity>() {
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
