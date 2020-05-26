package de.cidaas.cidaasv2.Service.Repository;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Service.Entity.UserinfoEntity;
import de.cidaas.sdk.android.cidaas.Service.Repository.OauthService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OauthServiceTest {

    Context context;
    OauthService oauthService;

    @Before
    public void setUp() {

        oauthService = new OauthService(context);

    }

    @Test
    public void testGetShared() throws Exception {
        OauthService result = OauthService.getShared(null);
        Assert.assertTrue(result instanceof OauthService);
    }

    @Test
    public void testGetLoginUrl() throws Exception {

        oauthService.getLoginUrl("requestId", "", new Result<String>() {
            @Override
            public void success(String result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testGetUserinfo() throws Exception {

        oauthService.getUserinfo("AccessToken", "", new Result<UserinfoEntity>() {
            @Override
            public void success(UserinfoEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
