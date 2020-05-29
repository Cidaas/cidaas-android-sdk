package de.cidaas.sdk.android.cidaasnative.Client;

import android.content.Context;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.CountDownLatch;

import de.cidaas.sdk.android.cidaasnative.data.Entity.ClientInfo.ClientInfoEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Controller.Client.ClientController;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

@Ignore
public class ClientControllerCallTest {
    @Test
    public void testGetClientInfo() throws Exception {

        Context context = Mockito.mock(Context.class);
        final CountDownLatch latch = new CountDownLatch(1);


        ClientController clientController = new ClientController(context);

        final MockResponse response = new MockResponse().setResponseCode(200)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("{\n" +
                        "\"success\": true,\n" +
                        "  \"status\": 200,\n" +
                        "  \"data\": {\n" +
                        "    \"passwordless_enabled\": true,\n" +
                        "    \"logo_uri\": \"https://www.cidaas.com/wp-content/uploads/2018/02/logo-black.png\",\n" +
                        "    \"login_providers\": [\n" +
                        "      \"facebook\",\n" +
                        "      \"google\",\n" +
                        "      \"linkedin\"\n" +
                        "    ],\n" +
                        "    \"policy_uri\": \"\",\n" +
                        "    \"tos_uri\": \"\",\n" +
                        "    \"client_name\": \"Single Page WebApp (Don't Edit)\"\n" +
                        "    }\n" +
                        "  }");


        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(response);


        CidaasHelper.baseurl = domainURL;


        clientController.getClientInfo("", new EventResult<ClientInfoEntity>() {
            @Override
            public void success(ClientInfoEntity result) {

                Assert.assertEquals("Single Page WebApp (Don't Edit)", result.getData().getClient_name());
                latch.countDown();

            }

            @Override
            public void failure(WebAuthError error) {
                Assert.assertEquals("Cidaas developer", error.getErrorMessage());
                latch.countDown();
            }
        });
        latch.await();
        //Thread.sleep(3000);
        Timber.e("Success");
    }
}
