package com.example.cidaasv2.Controller.Repository.Deduplication;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DeduplicationControllerTest {
    Context context;
    DeduplicationController shared;
    DeduplicationController deduplicationController;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        deduplicationController=new DeduplicationController(context);

    }

    @Test
    public void testGetShared() throws Exception {
        DeduplicationController result = DeduplicationController.getShared(null);
        Assert.assertTrue(result instanceof DeduplicationController);
    }

    @Test
    public void testGetDeduplicationList() throws Exception {
        deduplicationController.getDeduplicationList("baseurl", "trackId", null);
    }

    @Test
    public void testRegisterDeduplication() throws Exception {
        deduplicationController.registerDeduplication("baseurl", "trackId", null);
    }

    @Test
    public void testLoginDeduplication() throws Exception {
        deduplicationController.loginDeduplication("baseurl", "requestId", "sub", "password", new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test()
    public void testLoginDeduplicationExc() throws Exception {



       /* deduplicationController.loginDeduplication("baseurl", "requestId", "", "password", new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });*/
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme