package de.cidaas.cidaasv2.Controller.Repository.Client;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Controller.Cidaas;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;

import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.CountDownLatch;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

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


        Cidaas.baseurl = domainURL;


        clientController.getClientInfo(new Result<ClientInfoEntity>() {
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
