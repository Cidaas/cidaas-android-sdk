package de.cidaas.cidaasv2.Service.Repository.Deduplication;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Controller.Cidaas;
import de.cidaas.cidaasv2.Controller.HelperClass;
import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;

import com.example.cidaasv2.Service.Entity.Deduplication.DeduplicationResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

public class DeduplicationServiceCall {
    Context context = Mockito.mock(Context.class);

    private final CountDownLatch latch = new CountDownLatch(1);


    DeduplicationService deduplicationService = new DeduplicationService(context);


    DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
    Dictionary<String, String> savedProperties = new Hashtable<>();

    Dictionary<String, String> loginProperties = new Hashtable<>();


    @Before
    public void setUp() throws Exception {
        context = Mockito.mock(Context.class);
        MockitoAnnotations.initMocks(this);

        deviceInfoEntity.setDeviceId("DeviceID");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setPushNotificationId("PushNotificationId");


        savedProperties.put("Verifier", "codeVerifier");
        savedProperties.put("Challenge", "codeChallenge");
        savedProperties.put("Method", "ChallengeMethod");


        loginProperties.put("ClientId", "ClientId");
        loginProperties.put("RedirectURL", "RedirectURL");

    }


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
                            "        \"email\": \"Raja_Developers\"\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/token-srv/token");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;

            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));


            deduplicationService.getDeduplicationList("RefreshToken", "track", new Result<DeduplicationResponseEntity>() {
                @Override
                public void success(DeduplicationResponseEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getEmail());
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
    public void testWebClientException() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "        \"email\": \"Raja_Developers\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/token-srv/token");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;

            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));


            deduplicationService.getDeduplicationList(Cidaas.baseurl, "track", new Result<DeduplicationResponseEntity>() {
                @Override
                public void success(DeduplicationResponseEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getEmail());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Cidaas developer", error.getErrorMessage());
                    latch.countDown();
                }
            });
            // latch.await();
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
                            "        \"tenant_name\": \"Raja Developers\",\n" +
                            "        \"allowLoginWith\": [\n" +
                            "            \"EMAIL\",\n" +
                            "            \"MOBILE\",\n" +
                            "            \"USER_NAME\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/tenantinfo/basic");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;

            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));

            deduplicationService.getDeduplicationList(Cidaas.baseurl, "track", new Result<DeduplicationResponseEntity>() {
                @Override
                public void success(DeduplicationResponseEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getEmail());
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
                            "                        \"error\": \"Invalid tenant\"\n" +
                            "            }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/tenantinfo/basic");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));


            deduplicationService.getDeduplicationList(Cidaas.baseurl, "track", new Result<DeduplicationResponseEntity>() {
                @Override
                public void success(DeduplicationResponseEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getEmail());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid tenant", error.getErrorMessage());
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
                            "     \"error\": \"Invalid tenant\" \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/tenantinfo/basic");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;

            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));

            deduplicationService.getDeduplicationList(Cidaas.baseurl, "track", new Result<DeduplicationResponseEntity>() {
                @Override
                public void success(DeduplicationResponseEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getEmail());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid tenant", error.getErrorMessage());
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
                            "     \"error\": \"Invalid tenant \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/tenantinfo/basic");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;


            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));

            deduplicationService.getDeduplicationList(Cidaas.baseurl, null, new Result<DeduplicationResponseEntity>() {
                @Override
                public void success(DeduplicationResponseEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getEmail());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid tenant", error.getErrorMessage());
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
    public void testRegisterDeduplication() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "        \"sub\": \"Raja_Developers\"\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/token-srv/token");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;

            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));


            deduplicationService.registerDeduplication(HelperClass.removeLastChar(Cidaas.baseurl), "track", new Result<RegisterDeduplicationEntity>() {
                @Override
                public void success(RegisterDeduplicationEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getSub());
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
    public void testRegisterDeduplicationException() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"access_token\": \"Raja_Developers\"\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/token-srv/token");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;

            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));


            deduplicationService.registerDeduplication(HelperClass.removeLastChar(Cidaas.baseurl), "track", new Result<RegisterDeduplicationEntity>() {
                @Override
                public void success(RegisterDeduplicationEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Cidaas developer", error.getErrorMessage());
                    latch.countDown();
                }
            });
            // latch.await();
            //Thread.sleep(3000);
            Timber.e("Success");

        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), true, true);
            Assert.assertFalse(e.getMessage(), true);
        }

    }

    @Test
    public void testRegisterDeduplicationFor202() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(202)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "        \"tenant_name\": \"Raja Developers\",\n" +
                            "        \"allowLoginWith\": [\n" +
                            "            \"EMAIL\",\n" +
                            "            \"MOBILE\",\n" +
                            "            \"USER_NAME\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/tenantinfo/basic");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;

            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));

            deduplicationService.registerDeduplication(HelperClass.removeLastChar(Cidaas.baseurl), "track", new Result<RegisterDeduplicationEntity>() {
                @Override
                public void success(RegisterDeduplicationEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getSub());
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
    public void testRegisterDeduplicationFor401() throws Exception {

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
                            "                        \"error\": \"Invalid tenant\"\n" +
                            "            }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/tenantinfo/basic");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));


            deduplicationService.registerDeduplication(HelperClass.removeLastChar(Cidaas.baseurl), "track", new Result<RegisterDeduplicationEntity>() {
                @Override
                public void success(RegisterDeduplicationEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid tenant", error.getErrorMessage());
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
    public void testRegisterDeduplicationFaliureError() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "     \"error\": \"Invalid tenant\" \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/tenantinfo/basic");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;

            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));

            deduplicationService.registerDeduplication(HelperClass.removeLastChar(Cidaas.baseurl), "track", new Result<RegisterDeduplicationEntity>() {
                @Override
                public void success(RegisterDeduplicationEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid tenant", error.getErrorMessage());
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
    public void testRegisterDeduplicationFaliureException() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(401)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": false,\n" +
                            "    \"status\": 401,\n" +
                            "     \"error\": \"Invalid tenant \n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/tenantinfo/basic");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;


            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));

            deduplicationService.registerDeduplication(HelperClass.removeLastChar(Cidaas.baseurl), "track", new Result<RegisterDeduplicationEntity>() {
                @Override
                public void success(RegisterDeduplicationEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Invalid tenant", error.getErrorMessage());
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
