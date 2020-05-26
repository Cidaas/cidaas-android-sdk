package de.cidaas.cidaasv2.Service.Repository.Verification.SMS;

import android.content.Context;

import com.example.cidaasv2.Service.Repository.Verification.SMS.SMSVerificationService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Dictionary;
import java.util.Hashtable;


import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

@RunWith(RobolectricTestRunner.class)
public class SMSVerificationServiceTest {

    CidaassdkService service;

    Context context;

    SMSVerificationService sMSVerificationService;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        sMSVerificationService = new SMSVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        SMSVerificationService result = SMSVerificationService.getShared(null);
        Assert.assertTrue(result instanceof SMSVerificationService);
    }

    @Test
    public void testSetupSMSMFA() throws Exception {

        sMSVerificationService.setupSMSMFA("baseurl", "accessToken", null, new Result<SetupSMSMFAResponseEntity>() {
            @Override
            public void success(SetupSMSMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupSMSNullMFA() throws Exception {

        sMSVerificationService.setupSMSMFA("", "accessToken", null, new Result<SetupSMSMFAResponseEntity>() {
            @Override
            public void success(SetupSMSMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupSMSFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        sMSVerificationService.setupSMSMFA("localhost:234235", "accessToken", null, new Result<SetupSMSMFAResponseEntity>() {
            @Override
            public void success(SetupSMSMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testEnrollSMSMFA() throws Exception {

        sMSVerificationService.enrollSMSMFA("baseurl", "accessToken", new EnrollSMSMFARequestEntity(), null, new Result<EnrollSMSMFAResponseEntity>() {
            @Override
            public void success(EnrollSMSMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollSMSNULLMFA() throws Exception {

        sMSVerificationService.enrollSMSMFA("", "accessToken", new EnrollSMSMFARequestEntity(), null, new Result<EnrollSMSMFAResponseEntity>() {
            @Override
            public void success(EnrollSMSMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollSMSFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        sMSVerificationService.enrollSMSMFA("localhost:234235", "accessToken", new EnrollSMSMFARequestEntity(), null, new Result<EnrollSMSMFAResponseEntity>() {
            @Override
            public void success(EnrollSMSMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testInitiateSMSMFA() throws Exception {

        sMSVerificationService.initiateSMSMFA("baseurl", new InitiateSMSMFARequestEntity(), null, new Result<InitiateSMSMFAResponseEntity>() {
            @Override
            public void success(InitiateSMSMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateSMSNULLMFA() throws Exception {

        sMSVerificationService.initiateSMSMFA("", new InitiateSMSMFARequestEntity(), null, new Result<InitiateSMSMFAResponseEntity>() {
            @Override
            public void success(InitiateSMSMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateSMSFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        sMSVerificationService.initiateSMSMFA("localhost:234235", new InitiateSMSMFARequestEntity(), null, new Result<InitiateSMSMFAResponseEntity>() {
            @Override
            public void success(InitiateSMSMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testAuthenticateSMSMFA() throws Exception {

        sMSVerificationService.authenticateSMSMFA("baseurl", new AuthenticateSMSRequestEntity(), null, new Result<AuthenticateSMSResponseEntity>() {
            @Override
            public void success(AuthenticateSMSResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticateSMSNULLMFA() throws Exception {

        sMSVerificationService.authenticateSMSMFA("", new AuthenticateSMSRequestEntity(), null, new Result<AuthenticateSMSResponseEntity>() {
            @Override
            public void success(AuthenticateSMSResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testAuthenticateSMSFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        sMSVerificationService.authenticateSMSMFA("localhost:234235", new AuthenticateSMSRequestEntity(), null, new Result<AuthenticateSMSResponseEntity>() {
            @Override
            public void success(AuthenticateSMSResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
