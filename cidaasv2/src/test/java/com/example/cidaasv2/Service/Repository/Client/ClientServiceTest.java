package com.example.cidaasv2.Service.Repository.Client;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientServiceTest {

    Context context;
    ClientService clientService;

    @Before
    public void setUp() {
       clientService=new ClientService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        ClientService result = ClientService.getShared(null);
        Assert.assertTrue(result instanceof ClientService);
    }

    @Test
    public void testGetClientInfo() throws Exception {

        clientService.getClientInfo("requestId", "baseurl", null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme