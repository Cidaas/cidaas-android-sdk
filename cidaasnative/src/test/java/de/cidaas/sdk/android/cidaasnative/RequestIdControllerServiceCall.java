package de.cidaas.sdk.android.cidaasnative;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;

import de.cidaas.sdk.android.cidaasnative.data.Entity.AuthRequest.AuthRequestResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Controller.RequestId.RequestIdController;
import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;
@Ignore
public class RequestIdControllerServiceCall {

    Context context = Mockito.mock(Context.class);
    RequestIdController requestIdController = new RequestIdController(context);

    DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
    Dictionary<String, String> savedProperties = new Hashtable<>();

    CountDownLatch latch = new CountDownLatch(1);


    @Test
    public void testWebClient() throws Exception {

        try {
            Timber.e("Success");


            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"requestId\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\"" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/authz-srv/authrequest/authz/generate");


            server.enqueue(response);
            CidaasHelper.baseurl = domainURL;


            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));
            loginProperties.put("ClientId", "ClientId");
            loginProperties.put("RedirectURL", "RedirectURL");

            deviceInfoEntity.setDeviceId("DeviceID");
            deviceInfoEntity.setDeviceVersion("DeviceVersion");
            deviceInfoEntity.setDeviceModel("DeviceModel");
            deviceInfoEntity.setDeviceMake("DeviceMake");
            deviceInfoEntity.setPushNotificationId("PushNotificationId");


            savedProperties.put("Verifier", "codeVerifier");
            savedProperties.put("Challenge", "codeChallenge");
            savedProperties.put("Method", "ChallengeMethod");

            requestIdController.getRequestId(loginProperties, new EventResult<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getRequestId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals(null, error.getErrorMessage());
                    latch.countDown();
                }
            });
            latch.await();
            //Thread.sleep(3000);
            Timber.e("Success");

        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), true, true);
            Assert.assertFalse(e.getMessage(), true);
        }

    }

}
