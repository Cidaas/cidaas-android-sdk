package com.example.cidaasv2.Service.Repository.Deduplication;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.Deduplication.DeduplicationResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;

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
        Assert.assertTrue(result instanceof DeduplicationService);
    }

    @Test
    public void testGetDeduplicationList() throws Exception {

        deduplicationService.getDeduplicationList("baseurl", "trackId", null);
    }

    @Test
    public void testGetDeduplicationListnul() throws Exception {

        deduplicationService.getDeduplicationList("", "trackId", new Result<DeduplicationResponseEntity>() {
            @Override
            public void success(DeduplicationResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testRegisterDeduplication() throws Exception {

        deduplicationService.registerDeduplication("baseurl", "trackId", new Result<RegisterDeduplicationEntity>() {
            @Override
            public void success(RegisterDeduplicationEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testRegisterDeduplicationNull() throws Exception {

        deduplicationService.registerDeduplication("", "trackId", new Result<RegisterDeduplicationEntity>() {
            @Override
            public void success(RegisterDeduplicationEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testLoginDeduplication() throws Exception {

        deduplicationService.registerDeduplication("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme