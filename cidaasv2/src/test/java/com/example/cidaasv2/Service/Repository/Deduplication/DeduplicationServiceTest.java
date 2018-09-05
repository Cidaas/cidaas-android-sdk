package com.example.cidaasv2.Service.Repository.Deduplication;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DeduplicationServiceTest {

    Context context;
    DeduplicationService deduplicationService;

    @Before
    public void setUp()
    {
        context= RuntimeEnvironment.application;
      deduplicationService=new DeduplicationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        DeduplicationService result = DeduplicationService.getShared(null);
        Assert.assertEquals(new DeduplicationService(null), result);
    }

    @Test
    public void testGetDeduplicationList() throws Exception {

        deduplicationService.getDeduplicationList("baseurl", "trackId", null);
    }

    @Test
    public void testRegisterDeduplication() throws Exception {

        deduplicationService.registerDeduplication("baseurl", "trackId", null);
    }

    @Test
    public void testLoginDeduplication() throws Exception {

        deduplicationService.loginDeduplication("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme