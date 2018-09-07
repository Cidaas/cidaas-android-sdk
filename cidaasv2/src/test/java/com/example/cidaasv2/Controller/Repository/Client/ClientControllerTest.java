package com.example.cidaasv2.Controller.Repository.Client;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ClientControllerTest {

    Context context;

    ClientController shared;

    ClientController clientController;

    @Before
    public void setUp() {

        context= RuntimeEnvironment.application;
        clientController=new ClientController(context);
    }

    @Test
    public void testGenerateChallenge() throws Exception {
        clientController.generateChallenge();
    }

    @Test
    public void testGetShared() throws Exception {
        ClientController result = ClientController.getShared(context);
        Assert.assertTrue(result instanceof ClientController);
    }

    @Test
    public void testGetClientInfo() throws Exception {
        clientController.getClientInfo("baseurl", "RequestId", new Result<ClientInfoEntity>() {
            @Override
            public void success(ClientInfoEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme