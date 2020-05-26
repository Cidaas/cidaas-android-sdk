package de.cidaas.cidaasv2.Service.Repository.Verification.TOTP;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Dictionary;
import java.util.Hashtable;


import de.cidaas.sdk.android.entities.DeviceInfoEntity;












import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

@RunWith(RobolectricTestRunner.class)
public class TOTPVerificationServiceTest {
    Context context;
    TOTPVerificationService totpVerificationService;

    @Before
    public void setUp() {

        context = RuntimeEnvironment.application;
        totpVerificationService = new TOTPVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        TOTPVerificationService result = TOTPVerificationService.getShared(null);
        Assert.assertTrue(result instanceof TOTPVerificationService);
    }

    @Test
    public void testSetupTOTPMFA() throws Exception {

        totpVerificationService.setupTOTP("baseurl", "accessToken", new SetupTOTPMFARequestEntity(), null, new Result<SetupTOTPMFAResponseEntity>() {
            @Override
            public void success(SetupTOTPMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupTOTPNullMFA() throws Exception {

        totpVerificationService.setupTOTP("", "accessToken", new SetupTOTPMFARequestEntity(), null, new Result<SetupTOTPMFAResponseEntity>() {
            @Override
            public void success(SetupTOTPMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupTOTPFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        totpVerificationService.setupTOTP("localhost:234235", "accessToken", new SetupTOTPMFARequestEntity(), null, new Result<SetupTOTPMFAResponseEntity>() {
            @Override
            public void success(SetupTOTPMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testScannedTOTPMFA() throws Exception {

        ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
        scannedRequestEntity.setClient_id("client");
        scannedRequestEntity.setStatusId("sid");
        scannedRequestEntity.setUsage_pass("upass");
        scannedRequestEntity.setDeviceInfo(new DeviceInfoEntity());

        totpVerificationService.scannedTOTP("baseurl", scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
            @Override
            public void success(ScannedResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testScannedTOTPNUllMFA() throws Exception {

        ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
        scannedRequestEntity.setClient_id("client");
        scannedRequestEntity.setStatusId("sid");
        scannedRequestEntity.setUsage_pass("upass");
        scannedRequestEntity.setDeviceInfo(new DeviceInfoEntity());

        totpVerificationService.scannedTOTP("", scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
            @Override
            public void success(ScannedResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testScannedTOTPFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
        scannedRequestEntity.setClient_id("client");
        scannedRequestEntity.setStatusId("sid");
        scannedRequestEntity.setUsage_pass("upass");
        scannedRequestEntity.setDeviceInfo(new DeviceInfoEntity());


        totpVerificationService.scannedTOTP("localhost:234235", scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
            @Override
            public void success(ScannedResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }


    @Test
    public void testEnrollTOTPMFA() throws Exception {

        totpVerificationService.enrollTOTP("baseurl", "accessToken", new EnrollTOTPMFARequestEntity(), null, new Result<EnrollTOTPMFAResponseEntity>() {
            @Override
            public void success(EnrollTOTPMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollTOTPNULLMFA() throws Exception {

        totpVerificationService.enrollTOTP("", "accessToken", new EnrollTOTPMFARequestEntity(), null, new Result<EnrollTOTPMFAResponseEntity>() {
            @Override
            public void success(EnrollTOTPMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollTOTPFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        totpVerificationService.enrollTOTP("localhost:234235", "accessToken", new EnrollTOTPMFARequestEntity(), null, new Result<EnrollTOTPMFAResponseEntity>() {
            @Override
            public void success(EnrollTOTPMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testInitiateTOTP() throws Exception {

        totpVerificationService.initiateTOTP("baseurl", "sd", new InitiateTOTPMFARequestEntity(), null, new Result<InitiateTOTPMFAResponseEntity>() {
            @Override
            public void success(InitiateTOTPMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateTOTPNULLMFA() throws Exception {

        totpVerificationService.initiateTOTP("", "sd", new InitiateTOTPMFARequestEntity(), null, new Result<InitiateTOTPMFAResponseEntity>() {
            @Override
            public void success(InitiateTOTPMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateTOTPFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        totpVerificationService.initiateTOTP("localhost:234235", "sd", new InitiateTOTPMFARequestEntity(), null, new Result<InitiateTOTPMFAResponseEntity>() {
            @Override
            public void success(InitiateTOTPMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testAuthenticateTOTP() throws Exception {

        totpVerificationService.authenticateTOTP("baseurl", new AuthenticateTOTPRequestEntity(), null, new Result<AuthenticateTOTPResponseEntity>() {
            @Override
            public void success(AuthenticateTOTPResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticateTOTPNULLMFA() throws Exception {

        totpVerificationService.authenticateTOTP("", new AuthenticateTOTPRequestEntity(), null, new Result<AuthenticateTOTPResponseEntity>() {
            @Override
            public void success(AuthenticateTOTPResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testAuthenticateTOTPFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        totpVerificationService.authenticateTOTP("localhost:234235", new AuthenticateTOTPRequestEntity(), null, new Result<AuthenticateTOTPResponseEntity>() {
            @Override
            public void success(AuthenticateTOTPResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
