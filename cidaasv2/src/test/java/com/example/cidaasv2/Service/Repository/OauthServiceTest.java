package com.example.cidaasv2.Service.Repository;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OauthServiceTest {

    Context context;
    OauthService oauthService;

    @Before
    public void setUp() {
      oauthService=new OauthService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        OauthService result = OauthService.getShared(null);
        Assert.assertEquals(new OauthService(null), result);
    }

    @Test
    public void testGetLoginUrl() throws Exception {

        oauthService.getLoginUrl("requestId", null);
    }

    @Test
    public void testGetUserinfo() throws Exception {

        oauthService.getUserinfo("AccessToken", null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme