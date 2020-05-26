package de.cidaas.cidaasv2.Service.Repository.Verification.Settings;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Controller.Cidaas;
import de.cidaas.cidaasv2.Controller.HelperClass;
import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Service.CidaassdkService;

import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;

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

public class VerificationSettingsServiceCall {
    CidaassdkService service;
    VerificationSettingsService verificationSettingsService;
    DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
    Dictionary<String, String> savedProperties = new Hashtable<>();

    CountDownLatch latch = new CountDownLatch(1);


    Context context;

    @Before
    public void setUp() throws Exception {
        context = Mockito.mock(Context.class);
        service = new CidaassdkService();
        MockitoAnnotations.initMocks(this);
        verificationSettingsService = new VerificationSettingsService(context);

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
                            "    \"data\": [\n" +
                            "    \"_id\": \"556b95f8-9326-4250-a4ee-0e0d887f7a7d\"" +
                            "    ]\n" +
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


            verificationSettingsService.getmfaList(loginProperties.get("DomainURL"), "inter", "Statis", deviceInfoEntity, new Result<MFAListResponseEntity>() {
                @Override
                public void success(MFAListResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData()[0].get_id());

                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Cidaas developer", error.getErrorMessage());
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
    public void testWebClientnull() throws Exception {

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

            verificationSettingsService.getmfaList(loginProperties.get("DomainURL"), "inter", "Statis", deviceInfoEntity, new Result<MFAListResponseEntity>() {
                @Override
                public void success(MFAListResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData()[0].get_id());
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


            verificationSettingsService.getmfaList(loginProperties.get("DomainURL"), "inter", "Statis", deviceInfoEntity, new Result<MFAListResponseEntity>() {
                @Override
                public void success(MFAListResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData()[0].get_id());
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


            verificationSettingsService.getmfaList(loginProperties.get("DomainURL"), "inter", "Statis", deviceInfoEntity, new Result<MFAListResponseEntity>() {
                @Override
                public void success(MFAListResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData()[0].get_id());
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

            verificationSettingsService.getmfaList(loginProperties.get("DomainURL"), "inter", "Statis", deviceInfoEntity, new Result<MFAListResponseEntity>() {
                @Override
                public void success(MFAListResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData()[0].get_id());
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

            verificationSettingsService.getmfaList(loginProperties.get("DomainURL"), "inter", "Statis", deviceInfoEntity, new Result<MFAListResponseEntity>() {
                @Override
                public void success(MFAListResponseEntity result) {

                    Assert.assertEquals("556b95f8-9326-4250-a4ee-0e0d887f7a7d", result.getData()[0].get_id());
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
