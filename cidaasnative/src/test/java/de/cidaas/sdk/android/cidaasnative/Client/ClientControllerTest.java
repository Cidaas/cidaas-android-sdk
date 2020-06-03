package de.cidaas.sdk.android.cidaasnative.Client;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import de.cidaas.sdk.android.cidaasnative.data.entity.clientinfo.ClientInfoEntity;
import de.cidaas.sdk.android.cidaasnative.domain.controller.client.ClientController;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

@RunWith(RobolectricTestRunner.class)
@Ignore
public class ClientControllerTest {

    Context context;

    ClientController shared;

    ClientController clientController;


    @Before
    public void setUp() {

        context = RuntimeEnvironment.application;
        clientController = new ClientController(context);
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
        clientController.getClientInfo("", new EventResult<ClientInfoEntity>() {
            @Override
            public void success(ClientInfoEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testGetClientInfoFail() throws Exception {

        Context context = Mockito.mock(Context.class);
        ClientController clientController = new ClientController(context);

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        clientController.getClientInfo("", new EventResult<ClientInfoEntity>() {
            @Override
            public void success(ClientInfoEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e("Success");
            }
        });


    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme