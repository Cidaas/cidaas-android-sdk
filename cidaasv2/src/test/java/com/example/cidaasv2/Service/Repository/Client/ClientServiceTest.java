package com.example.cidaasv2.Service.Repository.Client;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;

import org.apache.tools.ant.taskdefs.Length;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)

public class ClientServiceTest {

    Context context;
    ClientService clientService;


    @Before
    public void setUp() {

        context= RuntimeEnvironment.application;
        clientService=new ClientService(context);

    }

    @Test
    public void testGetShared() throws Exception {
        ClientService result = ClientService.getShared(context);
        Assert.assertTrue(result instanceof ClientService);
    }

    @Test
    public void testGetClientInfo() throws Exception {

        clientService.getClientInfo("requestId", "baseurl", null);
    }
    @Test
    public void testGetClientInfoNUllRequestId() throws Exception {

        clientService.getClientInfo("", "baseurl", new Result<ClientInfoEntity>() {
            @Override
            public void success(ClientInfoEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("RequestId or baseurl must not be empty", error.getDetailedErrorMessage());
            }
        });
    }
    @Test
    public void testGetClientInfoNUllBaseurl() throws Exception {


        clientService.getClientInfo("requestId", null, new Result<ClientInfoEntity>() {
            @Override
            public void success(ClientInfoEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals(true, error.getErrorMessage().equals("One of the property is missing."));
            }
        });
    }

    @Test
    public void MockTestServer() throws Exception {
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme