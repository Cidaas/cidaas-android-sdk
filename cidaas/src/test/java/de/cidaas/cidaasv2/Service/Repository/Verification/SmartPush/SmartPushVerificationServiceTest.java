package de.cidaas.cidaasv2.Service.Repository.Verification.SmartPush;

import android.content.Context;

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
public class SmartPushVerificationServiceTest {

    SmartPushVerificationService smartPushVerificationService;
    ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
    Context context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        smartPushVerificationService = new SmartPushVerificationService(context);

        scannedRequestEntity.setClient_id("client");
        scannedRequestEntity.setStatusId("sid");
        scannedRequestEntity.setUsage_pass("upass");
        scannedRequestEntity.setDeviceInfo(new DeviceInfoEntity());
    }


    @Test
    public void testSetupSmartPushMFA() throws Exception {

        smartPushVerificationService.setupSmartPush("baseurl", "accessToken", new SetupSmartPushMFARequestEntity(), null, new Result<SetupSmartPushMFAResponseEntity>() {
            @Override
            public void success(SetupSmartPushMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupSmartPushNullMFA() throws Exception {

        smartPushVerificationService.setupSmartPush("", "accessToken", new SetupSmartPushMFARequestEntity(), null, new Result<SetupSmartPushMFAResponseEntity>() {
            @Override
            public void success(SetupSmartPushMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupSmartPushFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        smartPushVerificationService.setupSmartPush("localhost:234235", "accessToken", new SetupSmartPushMFARequestEntity(), null, new Result<SetupSmartPushMFAResponseEntity>() {
            @Override
            public void success(SetupSmartPushMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testScannedSmartPushMFA() throws Exception {

        smartPushVerificationService.scannedSmartPush("baseurl", scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
            @Override
            public void success(ScannedResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testScannedSmartPushNUllMFA() throws Exception {

        smartPushVerificationService.scannedSmartPush("", scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
            @Override
            public void success(ScannedResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testScannedSmartPushFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        smartPushVerificationService.scannedSmartPush("localhost:234235", scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
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
    public void testEnrollSmartPushMFA() throws Exception {

        smartPushVerificationService.enrollSmartPush("baseurl", "accessToken", new EnrollSmartPushMFARequestEntity(), null, new Result<EnrollSmartPushMFAResponseEntity>() {
            @Override
            public void success(EnrollSmartPushMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollSmartPushNULLMFA() throws Exception {

        smartPushVerificationService.enrollSmartPush("", "accessToken", new EnrollSmartPushMFARequestEntity(), null, new Result<EnrollSmartPushMFAResponseEntity>() {
            @Override
            public void success(EnrollSmartPushMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollSmartPushFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        smartPushVerificationService.enrollSmartPush("localhost:234235", "accessToken", new EnrollSmartPushMFARequestEntity(), null, new Result<EnrollSmartPushMFAResponseEntity>() {
            @Override
            public void success(EnrollSmartPushMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testInitiateSmartPush() throws Exception {

        smartPushVerificationService.initiateSmartPush("baseurl", new InitiateSmartPushMFARequestEntity(), null, new Result<InitiateSmartPushMFAResponseEntity>() {
            @Override
            public void success(InitiateSmartPushMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateSmartPushNULLMFA() throws Exception {

        smartPushVerificationService.initiateSmartPush("", new InitiateSmartPushMFARequestEntity(), null, new Result<InitiateSmartPushMFAResponseEntity>() {
            @Override
            public void success(InitiateSmartPushMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateSmartPushFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        smartPushVerificationService.initiateSmartPush("localhost:234235", new InitiateSmartPushMFARequestEntity(), null, new Result<InitiateSmartPushMFAResponseEntity>() {
            @Override
            public void success(InitiateSmartPushMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testAuthenticateSmartPush() throws Exception {

        smartPushVerificationService.authenticateSmartPush("baseurl", new AuthenticateSmartPushRequestEntity(), null, new Result<AuthenticateSmartPushResponseEntity>() {
            @Override
            public void success(AuthenticateSmartPushResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticateSmartPushNULLMFA() throws Exception {

        smartPushVerificationService.authenticateSmartPush("", new AuthenticateSmartPushRequestEntity(), null, new Result<AuthenticateSmartPushResponseEntity>() {
            @Override
            public void success(AuthenticateSmartPushResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testAuthenticateSmartPushFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        smartPushVerificationService.authenticateSmartPush("localhost:234235", new AuthenticateSmartPushRequestEntity(), null, new Result<AuthenticateSmartPushResponseEntity>() {
            @Override
            public void success(AuthenticateSmartPushResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
