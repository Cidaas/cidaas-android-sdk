package com.example.cidaasv2.Service.Repository.RequestId;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RequestIdServiceTest {

    Context context;
    RequestIdService requestIdService;

    @Before
    public void setUp() {
       requestIdService=new RequestIdService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        RequestIdService result = RequestIdService.getShared(null);
        Assert.assertEquals(new RequestIdService(null), result);
    }

    @Test
    public void testGetRequestID() throws Exception {

        requestIdService.getRequestID(null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme