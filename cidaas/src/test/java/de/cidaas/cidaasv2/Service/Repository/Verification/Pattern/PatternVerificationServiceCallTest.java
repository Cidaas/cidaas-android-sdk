package de.cidaas.cidaasv2.Service.Repository.Verification.Pattern;

import android.content.Context;

import com.example.cidaasv2.Service.Repository.Verification.Pattern.PatternVerificationService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;

import de.cidaas.cidaasv2.Controller.HelperClass;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;













import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

public class PatternVerificationServiceCallTest {
    CidaassdkService service;
    PatternVerificationService patternVerificationService;
    DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
    Dictionary<String, String> savedProperties = new Hashtable<>();
    ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();

    CountDownLatch latch = new CountDownLatch(1);


    Context context;

    @Before
    public void setUp() throws Exception {
        context = Mockito.mock(Context.class);
        service = new CidaassdkService();
        MockitoAnnotations.initMocks(this);
        patternVerificationService = new PatternVerificationService(context);

        deviceInfoEntity.setDeviceId("DeviceID");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setPushNotificationId("PushNotificationId");


        savedProperties.put("Verifier", "codeVerifier");
        savedProperties.put("Challenge", "codeChallenge");
        savedProperties.put("Method", "ChallengeMethod");

        scannedRequestEntity.setClient_id("client");
        scannedRequestEntity.setStatusId("sid");
        scannedRequestEntity.setUsage_pass("upass");
        scannedRequestEntity.setDeviceInfo(new DeviceInfoEntity());

    }

//INITITATE

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
                            "    \"statusId\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\"" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/verification-srv/backupcode/callSetup");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;


            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.initiatePattern(loginProperties.get("DomainURL"), new InitiatePatternMFARequestEntity(), deviceInfoEntity, new Result<InitiatePatternMFAResponseEntity>() {
                @Override
                public void success(InitiatePatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getStatusId());

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
    public void testWebClinetDevicenull() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"statusId\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\"" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/authz-srv/authrequest/authz/generate");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;


            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.initiatePattern(loginProperties.get("DomainURL"), new InitiatePatternMFARequestEntity(), null, new Result<InitiatePatternMFAResponseEntity>() {
                @Override
                public void success(InitiatePatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getStatusId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    // Assert.assertEquals("Cidaas developer",error.getErrorMessage());
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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.initiatePattern(loginProperties.get("DomainURL"), new InitiatePatternMFARequestEntity(), deviceInfoEntity, new Result<InitiatePatternMFAResponseEntity>() {
                @Override
                public void success(InitiatePatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getStatusId());
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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.initiatePattern(loginProperties.get("DomainURL"), new InitiatePatternMFARequestEntity(), deviceInfoEntity, new Result<InitiatePatternMFAResponseEntity>() {
                @Override
                public void success(InitiatePatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getStatusId());
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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");

            patternVerificationService.initiatePattern(loginProperties.get("DomainURL"), new InitiatePatternMFARequestEntity(), deviceInfoEntity, new Result<InitiatePatternMFAResponseEntity>() {
                @Override
                public void success(InitiatePatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getStatusId());
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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");

            patternVerificationService.initiatePattern(loginProperties.get("DomainURL"), new InitiatePatternMFARequestEntity(), deviceInfoEntity, new Result<InitiatePatternMFAResponseEntity>() {
                @Override
                public void success(InitiatePatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getStatusId());
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

    ///SETUP

    @Test
    public void testSetup() throws Exception {

        //  Whitebox.set
        try {
            Timber.e("Success");


            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"st\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\"" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/verification-srv/backupcode/callSetup");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;


            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.setupPattern(loginProperties.get("DomainURL"), "AccessToken", new SetupPatternMFARequestEntity(), deviceInfoEntity, new Result<SetupPatternMFAResponseEntity>() {
                @Override
                public void success(SetupPatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSt());

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
    public void testSetupDevicenull() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"statusId\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\"" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/authz-srv/authrequest/authz/generate");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;


            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.setupPattern(loginProperties.get("DomainURL"), "AccessToken", new SetupPatternMFARequestEntity(), null, new Result<SetupPatternMFAResponseEntity>() {
                @Override
                public void success(SetupPatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSt());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    // Assert.assertEquals("Cidaas developer",error.getErrorMessage());
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
    public void testSetupFor202() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.setupPattern(loginProperties.get("DomainURL"), "AccessToken", new SetupPatternMFARequestEntity(), deviceInfoEntity, new Result<SetupPatternMFAResponseEntity>() {
                @Override
                public void success(SetupPatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSt());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Service failure but successful response", error.getErrorMessage());
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
    public void testSetupFor401() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.setupPattern(loginProperties.get("DomainURL"), "AccessToken", new SetupPatternMFARequestEntity(), deviceInfoEntity, new Result<SetupPatternMFAResponseEntity>() {
                @Override
                public void success(SetupPatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSt());
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
    public void testSetupFaliureError() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.setupPattern(loginProperties.get("DomainURL"), "AccessToken", new SetupPatternMFARequestEntity(), deviceInfoEntity, new Result<SetupPatternMFAResponseEntity>() {
                @Override
                public void success(SetupPatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSt());
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
    public void testSetupFaliureException() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.setupPattern(loginProperties.get("DomainURL"), "AccessToken", new SetupPatternMFARequestEntity(), deviceInfoEntity, new Result<SetupPatternMFAResponseEntity>() {
                @Override
                public void success(SetupPatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSt());
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


    //SCANNED
    @Test
    public void testScanned() throws Exception {

        //  Whitebox.set
        try {
            Timber.e("Success");


            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"userDeviceId\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\"" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/verification-srv/backupcode/callSetup");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;


            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.scannedPattern(loginProperties.get("DomainURL"), scannedRequestEntity, deviceInfoEntity, new Result<ScannedResponseEntity>() {
                @Override
                public void success(ScannedResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getUserDeviceId());

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
    public void testScannedDevicenull() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"statusId\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\"" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/authz-srv/authrequest/authz/generate");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;


            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.scannedPattern(loginProperties.get("DomainURL"), scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
                @Override
                public void success(ScannedResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getUserDeviceId());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    // Assert.assertEquals("Cidaas developer",error.getErrorMessage());
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
    public void testScannedFor202() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.scannedPattern(loginProperties.get("DomainURL"), scannedRequestEntity, deviceInfoEntity, new Result<ScannedResponseEntity>() {
                @Override
                public void success(ScannedResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getUserDeviceId());
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
    public void testScannedFor401() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.scannedPattern(loginProperties.get("DomainURL"), scannedRequestEntity, deviceInfoEntity, new Result<ScannedResponseEntity>() {
                @Override
                public void success(ScannedResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getUserDeviceId());
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
    public void testScannedFaliureError() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.scannedPattern(loginProperties.get("DomainURL"), scannedRequestEntity, deviceInfoEntity, new Result<ScannedResponseEntity>() {
                @Override
                public void success(ScannedResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getUserDeviceId());
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
    public void testScannedFaliureException() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.scannedPattern(loginProperties.get("DomainURL"), scannedRequestEntity, deviceInfoEntity, new Result<ScannedResponseEntity>() {
                @Override
                public void success(ScannedResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getUserDeviceId());
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


    //ENROLL
    @Test
    public void testEnroll() throws Exception {

        //  Whitebox.set
        try {
            Timber.e("Success");


            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"sub\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\"" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/verification-srv/backupcode/callSetup");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;


            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.enrollPattern(loginProperties.get("DomainURL"), "accs", new EnrollPatternMFARequestEntity(), deviceInfoEntity, new Result<EnrollPatternMFAResponseEntity>() {
                @Override
                public void success(EnrollPatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSub());

                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Cidaas developer", error.getErrorMessage());
                    latch.countDown();
                }
            });
   /* @Test
    public void testSetupPattern() throws Exception {

        iVRVerificationService.setupPattern("baseurl", "accessToken", null,new Result<SetupPatternMFAResponseEntity>() {
            @Override
            public void success(SetupPatternMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollPattern() throws Exception {

        iVRVerificationService.enrollPattern("baseurl", "accessToken", new EnrollPatternMFARequestEntity(), null,new Result<EnrollPatternMFAResponseEntity>() {
            @Override
            public void success(EnrollPatternMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiatePattern() throws Exception {

        iVRVerificationService.initiatePattern("baseurl", new InitiatePatternMFARequestEntity(), null,new Result<InitiatePatternMFAResponseEntity>() {
            @Override
            public void success(InitiatePatternMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticatePattern() throws Exception {

        iVRVerificationService.authenticatePattern("baseurl", new AuthenticatePatternRequestEntity(), null,new Result<AuthenticatePatternResponseEntity>() {
            @Override
            public void success(AuthenticatePatternResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }*/
            latch.await();
            //Thread.sleep(3000);
            Timber.e("Success");

        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), true, true);
            Assert.assertFalse(e.getMessage(), true);
        }

    }


    @Test
    public void testEnrollDevicenull() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"statusId\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\"" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/authz-srv/authrequest/authz/generate");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;


            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.enrollPattern(loginProperties.get("DomainURL"), "accs", new EnrollPatternMFARequestEntity(), null, new Result<EnrollPatternMFAResponseEntity>() {
                @Override
                public void success(EnrollPatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    // Assert.assertEquals("Cidaas developer",error.getErrorMessage());
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
    public void testEnrollFor202() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.enrollPattern(loginProperties.get("DomainURL"), "accs", new EnrollPatternMFARequestEntity(), deviceInfoEntity, new Result<EnrollPatternMFAResponseEntity>() {
                @Override
                public void success(EnrollPatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSub());
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
    public void testEnrollFor401() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.enrollPattern(loginProperties.get("DomainURL"), "accs", new EnrollPatternMFARequestEntity(), deviceInfoEntity, new Result<EnrollPatternMFAResponseEntity>() {
                @Override
                public void success(EnrollPatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSub());
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
    public void testEnrollFaliureError() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.enrollPattern(loginProperties.get("DomainURL"), "accs", new EnrollPatternMFARequestEntity(), deviceInfoEntity, new Result<EnrollPatternMFAResponseEntity>() {
                @Override
                public void success(EnrollPatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSub());
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
    public void testEnrollFaliureException() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.enrollPattern(loginProperties.get("DomainURL"), "accs", new EnrollPatternMFARequestEntity(), deviceInfoEntity, new Result<EnrollPatternMFAResponseEntity>() {
                @Override
                public void success(EnrollPatternMFAResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSub());
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

    //AUTHENTICATE
    @Test
    public void testAuthenticate() throws Exception {

        //  Whitebox.set
        try {
            Timber.e("Success");


            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"sub\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\"" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/verification-srv/backupcode/callSetup");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;


            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.authenticatePattern(loginProperties.get("DomainURL"), new AuthenticatePatternRequestEntity(), deviceInfoEntity, new Result<AuthenticatePatternResponseEntity>() {
                @Override
                public void success(AuthenticatePatternResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSub());

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
    public void testAuthenticateDevicenull() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "    \"sub\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\"" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/authz-srv/authrequest/authz/generate");


            server.enqueue(response);
            Cidaas.baseurl = domainURL;


            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.authenticatePattern(loginProperties.get("DomainURL"), new AuthenticatePatternRequestEntity(), null, new Result<AuthenticatePatternResponseEntity>() {
                @Override
                public void success(AuthenticatePatternResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSub());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    // Assert.assertEquals("Cidaas developer",error.getErrorMessage());
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
    public void testAuthenticateFor202() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.authenticatePattern(loginProperties.get("DomainURL"), new AuthenticatePatternRequestEntity(), deviceInfoEntity, new Result<AuthenticatePatternResponseEntity>() {
                @Override
                public void success(AuthenticatePatternResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSub());
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
    public void testAuthenticateFor401() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");


            patternVerificationService.authenticatePattern(loginProperties.get("DomainURL"), new AuthenticatePatternRequestEntity(), deviceInfoEntity, new Result<AuthenticatePatternResponseEntity>() {
                @Override
                public void success(AuthenticatePatternResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSub());
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
    public void testAuthenticateFaliureError() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");

            patternVerificationService.authenticatePattern(loginProperties.get("DomainURL"), new AuthenticatePatternRequestEntity(), deviceInfoEntity, new Result<AuthenticatePatternResponseEntity>() {
                @Override
                public void success(AuthenticatePatternResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSub());
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
    public void testAuthenticateFaliureException() throws Exception {

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
            Cidaas.baseurl = domainURL;

            Dictionary<String, String> loginProperties = new Hashtable<>();
            loginProperties.put("DomainURL", HelperClass.removeLastChar(Cidaas.baseurl));
            loginProperties.put("ClientID", "ClientID");
            loginProperties.put("RedirectURL", "RedirectURL");

            patternVerificationService.authenticatePattern(loginProperties.get("DomainURL"), new AuthenticatePatternRequestEntity(), deviceInfoEntity, new Result<AuthenticatePatternResponseEntity>() {
                @Override
                public void success(AuthenticatePatternResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData().getSub());
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
