package de.cidaas.sdk.android.cidaasnative;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;

import de.cidaas.sdk.android.cidaasnative.data.entity.authrequest.AuthRequestResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.service.RequestId.RequestIdService;
import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.service.CidaassdkService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

@Ignore
public class RequestIdServiceCall {
    private final CountDownLatch latch = new CountDownLatch(1);

    CidaassdkService service;
    RequestIdService requestIdService;
    DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
    Dictionary<String, String> savedProperties = new Hashtable<>();


    Context context;

    @Before
    public void setUp() throws Exception {
        context = Mockito.mock(Context.class);
        service = new CidaassdkService(context);
        MockitoAnnotations.initMocks(this);
        requestIdService = new RequestIdService(context);

        deviceInfoEntity.setDeviceId("DeviceID");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setPushNotificationId("PushNotificationId");


        savedProperties.put("Verifier", "codeVerifier");
        savedProperties.put("Challenge", "codeChallenge");
        savedProperties.put("Method", "ChallengeMethod");

    }


    @Test
    public void testWebClient() throws Exception {

        //  Whitebox.set
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


            requestIdService.getRequestID(loginProperties, new EventResult<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getRequestId());
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

        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), true, true);
            Assert.assertFalse(e.getMessage(), true);
        }

    }


    @Test
    public void testWebClientnull() throws Exception {

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


            requestIdService.getRequestID(loginProperties, new EventResult<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getRequestId());
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

        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), true, true);
            Assert.assertFalse(e.getMessage(), true);
        }

    }


    @Test
    public void testWebClientFor202() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(202)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "        \"requestId_name\": \"Raja Developers\",\n" +
                            "        \"allowLoginWith\": [\n" +
                            "            \"EMAIL\",\n" +
                            "            \"MOBILE\",\n" +
                            "            \"USER_NAME\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");


            server.enqueue(response);
            CidaasHelper.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));
            loginProperties.put("ClientId", "ClientId");
            loginProperties.put("RedirectURL", "RedirectURL");


            requestIdService.getRequestID(loginProperties, new EventResult<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {

                    Assert.assertEquals("Service failure but successful response", result.getData().getRequestId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Service failure but successful response", error.getErrorMessage());
                    latch.countDown();
                }
            });
            //latch.await();
            //Thread.sleep(3000);
            Timber.e("Success");

        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), true, true);
            Assert.assertFalse(e.getMessage(), true);
        }

    }


    @Test
    public void testWebClientFor401() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "   \"error\": {\n" +
                            "                \"code\": 24008,\n" +
                            "                        \"moreInfo\": \"\",\n" +
                            "                        \"type\": \"LoginException\",\n" +
                            "                        \"status\": 400,\n" +
                            "                        \"referenceNumber\": \"1537337364806\",\n" +
                            "                        \"error\": \"Invalid requestId\"\n" +
                            "            }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");


            server.enqueue(response);
            CidaasHelper.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));
            loginProperties.put("ClientId", "ClientId");
            loginProperties.put("RedirectURL", "RedirectURL");


            requestIdService.getRequestID(loginProperties, new EventResult<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {

                    Assert.assertEquals("Service failure but successful response", result.getData().getRequestId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId", error.getErrorMessage());
                    latch.countDown();
                }
            });
            //latch.await();
            //Thread.sleep(3000);
            Timber.e("Success");

        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), true, true);
            Assert.assertFalse(e.getMessage(), true);
        }

    }

    @Test
    public void testWebClientFaliureError() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "     \"error\": \"Invalid requestId\" \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");


            server.enqueue(response);
            CidaasHelper.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));
            loginProperties.put("ClientId", "ClientId");
            loginProperties.put("RedirectURL", "RedirectURL");

            requestIdService.getRequestID(loginProperties, new EventResult<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {

                    Assert.assertEquals("Service failure but successful response", result.getData().getRequestId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId", error.getErrorMessage());
                    latch.countDown();
                }
            });
            //latch.await();
            //Thread.sleep(3000);
            Timber.e("Success");

        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), true, true);
            Assert.assertFalse(e.getMessage(), true);
        }

    }


    @Test
    public void testWebClientFaliureException() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "     \"error\": \"Invalid requestId \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/requestIdinfo/basic");


            server.enqueue(response);
            CidaasHelper.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));
            loginProperties.put("ClientId", "ClientId");
            loginProperties.put("RedirectURL", "RedirectURL");

            requestIdService.getRequestID(loginProperties, new EventResult<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {

                    Assert.assertEquals("Service failure but successful response", result.getData().getRequestId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid requestId", error.getErrorMessage());
                    latch.countDown();
                }
            });
            //latch.await();
            //Thread.sleep(3000);
            Timber.e("Success");

        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), true, true);
            Assert.assertFalse(e.getMessage(), true);
        }

    }

}
