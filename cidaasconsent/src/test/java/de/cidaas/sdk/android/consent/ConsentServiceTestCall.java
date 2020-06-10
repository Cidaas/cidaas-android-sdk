package de.cidaas.sdk.android.consent;


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

import de.cidaas.sdk.android.consent.data.entity.resumeconsent.ResumeConsentEntity;
import de.cidaas.sdk.android.consent.data.entity.resumeconsent.ResumeConsentResponseEntity;
import de.cidaas.sdk.android.consent.domain.service.consent.ConsentService;
import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentDetailsResultEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentManagementAcceptResponseEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentManagementAcceptedRequestEntity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

@Ignore
public class ConsentServiceTestCall {
    Context context = Mockito.mock(Context.class);

    private final CountDownLatch latch = new CountDownLatch(1);


    ConsentService consentService = new ConsentService(context);


    DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
    Dictionary<String, String> savedProperties = new Hashtable<>();

    Dictionary<String, String> loginProperties = new Hashtable<>();

    ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity = new ConsentManagementAcceptedRequestEntity();
    ResumeConsentEntity resumeConsentRequestEntity = new ResumeConsentEntity("", "", "", "", "");


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

        consentManagementAcceptedRequestEntity.setTrackId("TrackId");
        consentManagementAcceptedRequestEntity.setVersion("Version");
        consentManagementAcceptedRequestEntity.setName("Name");
        consentManagementAcceptedRequestEntity.setAccepted(true);
        consentManagementAcceptedRequestEntity.setSub("Sub");
        consentManagementAcceptedRequestEntity.setClient_id("ClientId");

        resumeConsentRequestEntity.setTrack_id("TrackId");
        resumeConsentRequestEntity.setSub("Sub");
        resumeConsentRequestEntity.setClient_id("ClientId");
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
                            "    \"name\": \"Raja_Developers\"\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/token-srv/token");


            server.enqueue(response);
            CidaasHelper.baseurl = domainURL;

            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));


            consentService.getConsentDetails(StringUtils.chop(CidaasHelper.baseurl), null, new EventResult<ConsentDetailsResultEntity>() {
                @Override
                public void success(ConsentDetailsResultEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getName());
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
                            "    \"access_token\": \"Raja_Developers\"\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/token-srv/token");


            server.enqueue(response);
            CidaasHelper.baseurl = domainURL;

            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));


            consentService.getConsentDetails(StringUtils.chop(CidaasHelper.baseurl), null, new EventResult<ConsentDetailsResultEntity>() {
                @Override
                public void success(ConsentDetailsResultEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getName());
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
            CidaasHelper.baseurl = domainURL;

            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));

            consentService.getConsentDetails(StringUtils.chop(CidaasHelper.baseurl), null, new EventResult<ConsentDetailsResultEntity>() {
                @Override
                public void success(ConsentDetailsResultEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getName());
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
            CidaasHelper.baseurl = domainURL;
            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));

            consentService.getConsentDetails(StringUtils.chop(CidaasHelper.baseurl), null, new EventResult<ConsentDetailsResultEntity>() {
                @Override
                public void success(ConsentDetailsResultEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getName());
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
            CidaasHelper.baseurl = domainURL;

            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));

            consentService.getConsentDetails(StringUtils.chop(CidaasHelper.baseurl), null, new EventResult<ConsentDetailsResultEntity>() {
                @Override
                public void success(ConsentDetailsResultEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getName());
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
            CidaasHelper.baseurl = domainURL;


            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));

            consentService.getConsentDetails(StringUtils.chop(CidaasHelper.baseurl), null, new EventResult<ConsentDetailsResultEntity>() {
                @Override
                public void success(ConsentDetailsResultEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getName());
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


    //AcceptConsent
    @Test
    public void testAcceptConsent() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\":  true " +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/token-srv/token");


            server.enqueue(response);
            CidaasHelper.baseurl = domainURL;

            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));


            consentService.acceptConsent(StringUtils.chop(CidaasHelper.baseurl), consentManagementAcceptedRequestEntity, null, new EventResult<ConsentManagementAcceptResponseEntity>() {
                @Override
                public void success(ConsentManagementAcceptResponseEntity result) {

                    Assert.assertEquals(true, result.isData());
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
    public void testAcceptConsentException() throws Exception {

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
            CidaasHelper.baseurl = domainURL;

            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));


            consentService.acceptConsent(StringUtils.chop(CidaasHelper.baseurl), consentManagementAcceptedRequestEntity, null, new EventResult<ConsentManagementAcceptResponseEntity>() {
                @Override
                public void success(ConsentManagementAcceptResponseEntity result) {

                    Assert.assertEquals(true, result.isData());
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
    public void testAcceptConsentFor202() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(202)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 202,\n" +
                            "    \"data\":  true " +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/tenantinfo/basic");


            server.enqueue(response);
            CidaasHelper.baseurl = domainURL;

            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));

            consentService.acceptConsent(StringUtils.chop(CidaasHelper.baseurl), consentManagementAcceptedRequestEntity, null, new EventResult<ConsentManagementAcceptResponseEntity>() {
                @Override
                public void success(ConsentManagementAcceptResponseEntity result) {

                    Assert.assertEquals(true, result.isData());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Service failure but successful response", error.getErrorMessage());
                    latch.countDown();
                }
            });
            //latch.await();
            Thread.sleep(3000);
            Timber.e("Success");

        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), true, true);
            Assert.assertFalse(e.getMessage(), true);
        }

    }

    @Test
    public void testAcceptConsentFor401() throws Exception {

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
            CidaasHelper.baseurl = domainURL;
            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));

            consentService.acceptConsent(StringUtils.chop(CidaasHelper.baseurl), consentManagementAcceptedRequestEntity, null, new EventResult<ConsentManagementAcceptResponseEntity>() {
                @Override
                public void success(ConsentManagementAcceptResponseEntity result) {

                    Assert.assertEquals(true, result.isData());
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
    public void testAcceptConsentFaliureError() throws Exception {

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
            CidaasHelper.baseurl = domainURL;

            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));

            consentService.acceptConsent(StringUtils.chop(CidaasHelper.baseurl), consentManagementAcceptedRequestEntity, null, new EventResult<ConsentManagementAcceptResponseEntity>() {
                @Override
                public void success(ConsentManagementAcceptResponseEntity result) {

                    Assert.assertEquals(true, result.isData());
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
    public void testAcceptConsentFaliureException() throws Exception {

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
            CidaasHelper.baseurl = domainURL;


            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));

            consentService.acceptConsent(StringUtils.chop(CidaasHelper.baseurl), consentManagementAcceptedRequestEntity, null, new EventResult<ConsentManagementAcceptResponseEntity>() {
                @Override
                public void success(ConsentManagementAcceptResponseEntity result) {

                    Assert.assertEquals(true, result.isData());
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
    public void testAcceptConsentFException() throws Exception {

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
            CidaasHelper.baseurl = domainURL;


            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));

            consentService.acceptConsent(StringUtils.chop(CidaasHelper.baseurl), consentManagementAcceptedRequestEntity, null, new EventResult<ConsentManagementAcceptResponseEntity>() {
                @Override
                public void success(ConsentManagementAcceptResponseEntity result) {

                    Assert.assertEquals(true, result.isData());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
//                    Assert.assertEquals("Invalid tenant",error.getErrorMessage());
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


    //resumeConsent

    @Test
    public void testResumeConsent() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"code\": \"Raja_Developers\"\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/token-srv/token");


            server.enqueue(response);
            CidaasHelper.baseurl = domainURL;

            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));


            consentService.resumeConsent(StringUtils.chop(CidaasHelper.baseurl), resumeConsentRequestEntity, null, new EventResult<ResumeConsentResponseEntity>() {
                @Override
                public void success(ResumeConsentResponseEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getCode());
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
    public void testResumeConsentException() throws Exception {

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
            CidaasHelper.baseurl = domainURL;

            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));


            consentService.resumeConsent(StringUtils.chop(CidaasHelper.baseurl), resumeConsentRequestEntity, null, new EventResult<ResumeConsentResponseEntity>() {
                @Override
                public void success(ResumeConsentResponseEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getCode());
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
    public void testResumeConsentFor202() throws Exception {

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
            CidaasHelper.baseurl = domainURL;

            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));

            consentService.resumeConsent(StringUtils.chop(CidaasHelper.baseurl), resumeConsentRequestEntity, null, new EventResult<ResumeConsentResponseEntity>() {
                @Override
                public void success(ResumeConsentResponseEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getCode());
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
    public void testResumeConsentFor401() throws Exception {

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
            CidaasHelper.baseurl = domainURL;
            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));
            consentService.resumeConsent(StringUtils.chop(CidaasHelper.baseurl), resumeConsentRequestEntity, null, new EventResult<ResumeConsentResponseEntity>() {
                @Override
                public void success(ResumeConsentResponseEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getCode());
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
    public void testResumeConsentFaliureError() throws Exception {

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
            CidaasHelper.baseurl = domainURL;

            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));

            consentService.resumeConsent(StringUtils.chop(CidaasHelper.baseurl), resumeConsentRequestEntity, null, new EventResult<ResumeConsentResponseEntity>() {
                @Override
                public void success(ResumeConsentResponseEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getCode());
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
    public void testResumeConsentFaliureException() throws Exception {

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
            CidaasHelper.baseurl = domainURL;


            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));

            consentService.resumeConsent(StringUtils.chop(CidaasHelper.baseurl), resumeConsentRequestEntity, null, new EventResult<ResumeConsentResponseEntity>() {
                @Override
                public void success(ResumeConsentResponseEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getCode());
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
    public void testResumeConseiureException() throws Exception {

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
            CidaasHelper.baseurl = domainURL;


            loginProperties.put("DomainURL", StringUtils.chop(CidaasHelper.baseurl));

            consentService.resumeConsent(StringUtils.chop(CidaasHelper.baseurl), resumeConsentRequestEntity, null, new EventResult<ResumeConsentResponseEntity>() {
                @Override
                public void success(ResumeConsentResponseEntity result) {

                    Assert.assertEquals("Raja_Developers", result.getData().getCode());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    //  Assert.assertEquals("Invalid tenant",error.getErrorMessage());
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
