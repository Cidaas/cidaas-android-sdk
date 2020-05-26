package de.cidaas.sdk.android.Service.Repository;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.service.entity.UserinfoEntity;
import de.cidaas.sdk.android.service.repository.OauthService;


public class OauthServiceTest {

    Context context;
    OauthService oauthService;

    @Before
    public void setUp() {

        oauthService = OauthService.getShared(context);

    }

    @Test
    public void testGetShared() throws Exception {
        OauthService result = OauthService.getShared(null);
        Assert.assertTrue(result instanceof OauthService);
    }

    @Test
    public void testGetLoginUrl() throws Exception {

        oauthService.getLoginUrl("requestId", "", new EventResult<String>() {
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

        oauthService.getUserinfo("AccessToken", "", new EventResult<UserinfoEntity>() {
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
