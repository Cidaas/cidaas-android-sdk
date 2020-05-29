package de.cidaas.sdk.android.cidaasnative.Client;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import de.cidaas.sdk.android.cidaasnative.data.Entity.ClientInfo.ClientInfoEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Service.Client.ClientService;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;


@RunWith(RobolectricTestRunner.class)
@Ignore
public class ClientServiceTest {

    Context context;
    ClientService clientService;


    @Before
    public void setUp() {

        context = RuntimeEnvironment.application;
        clientService = new ClientService(context);

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

        clientService.getClientInfo("", "baseurl", new EventResult<ClientInfoEntity>() {
            @Override
            public void success(ClientInfoEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("Request Id is missing.", error.getErrorMessage());
            }
        });
    }

    @Test
    public void testGetClientInfoNUllBaseurl() throws Exception {


        clientService.getClientInfo("requestId", null, new EventResult<ClientInfoEntity>() {
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