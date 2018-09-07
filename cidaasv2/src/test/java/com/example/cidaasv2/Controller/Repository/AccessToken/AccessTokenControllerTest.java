package com.example.cidaasv2.Controller.Repository.AccessToken;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)

public class AccessTokenControllerTest {

    Context context;

    AccessTokenController shared;

    AccessTokenController accessTokenController;

    @Before
    public void setUp() {

        context= RuntimeEnvironment.application;
        DBHelper.setConfig(context);
        accessTokenController=new AccessTokenController(context);
    }

    @Test
    public void testGetShared() throws Exception {
        AccessTokenController result = AccessTokenController.getShared(null);
        Assert.assertTrue( result instanceof AccessTokenController);
    }

    @Test
    public void testGetAccessTokenByCode() throws Exception {
        accessTokenController.getAccessTokenByCode("code", new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testGetAccessToken() throws Exception {
        accessTokenController.getAccessToken("sub", new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testGetAccessTokenByRefreshToken() throws Exception {
        accessTokenController.getAccessTokenByRefreshToken("refreshToken", new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme