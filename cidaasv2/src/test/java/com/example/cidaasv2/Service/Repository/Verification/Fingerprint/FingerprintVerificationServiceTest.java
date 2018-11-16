package com.example.cidaasv2.Service.Repository.Verification.Fingerprint;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

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
public class FingerprintVerificationServiceTest {

    Context context;
    FingerprintVerificationService fingerprintVerificationService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        fingerprintVerificationService=new FingerprintVerificationService(context);

        DBHelper.setConfig(context);
        DBHelper.getShared().setFCMToken("FCM");
    }

    @Test
    public void testGetShared() throws Exception {
        FingerprintVerificationService result = FingerprintVerificationService.getShared(null);

        Assert.assertTrue(result instanceof FingerprintVerificationService);
    }



    @Test
    public void testSetupFingerprintMFA() throws Exception {

        fingerprintVerificationService.setupFingerprint("baseurl", "accessToken","code",new SetupFingerprintMFARequestEntity(),null, new Result<SetupFingerprintMFAResponseEntity>() {
            @Override
            public void success(SetupFingerprintMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupFingerprintNullMFA() throws Exception {

        fingerprintVerificationService.setupFingerprint("", "accessToken","code",new SetupFingerprintMFARequestEntity(),null, new Result<SetupFingerprintMFAResponseEntity>() {
            @Override
            public void success(SetupFingerprintMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupFingerprintFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");


        fingerprintVerificationService.setupFingerprint("localhost:234235", "accessToken","code",new SetupFingerprintMFARequestEntity(),null, new Result<SetupFingerprintMFAResponseEntity>() {
            @Override
            public void success(SetupFingerprintMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testScannedFingerprintMFA() throws Exception {

        fingerprintVerificationService.scannedFingerprint("baseurl", "Usa","Status","Access",null,new Result<ScannedResponseEntity>() {
            @Override
            public void success(ScannedResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
    @Test
    public void testScannedFingerprintNUllMFA() throws Exception {

        fingerprintVerificationService.scannedFingerprint("", "Usa","Status","accessToken",null,new Result<ScannedResponseEntity>() {
            @Override
            public void success(ScannedResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testScannedFingerprintFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        fingerprintVerificationService.scannedFingerprint("localhost:234235", "Usa","StatusId","accessToken",null, new Result<ScannedResponseEntity>() {
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
    public void testEnrollFingerprintMFA() throws Exception {

        fingerprintVerificationService.enrollFingerprint("baseurl", "accessToken", new EnrollFingerprintMFARequestEntity(), null,new Result<EnrollFingerprintMFAResponseEntity>() {
            @Override
            public void success(EnrollFingerprintMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
    @Test
    public void testEnrollFingerprintNULLMFA() throws Exception {

        fingerprintVerificationService.enrollFingerprint("", "accessToken", new EnrollFingerprintMFARequestEntity(), null,new Result<EnrollFingerprintMFAResponseEntity>() {
            @Override
            public void success(EnrollFingerprintMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollFingerprintFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        fingerprintVerificationService.enrollFingerprint("localhost:234235", "accessToken", new EnrollFingerprintMFARequestEntity(),null, new Result<EnrollFingerprintMFAResponseEntity>() {
            @Override
            public void success(EnrollFingerprintMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testInitiateFingerprint() throws Exception {

        fingerprintVerificationService.initiateFingerprint("baseurl", "sd",new InitiateFingerprintMFARequestEntity(),null, new Result<InitiateFingerprintMFAResponseEntity>() {
            @Override
            public void success(InitiateFingerprintMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateFingerprintNULLMFA() throws Exception {

        fingerprintVerificationService.initiateFingerprint("", "sd",new InitiateFingerprintMFARequestEntity(),null, new Result<InitiateFingerprintMFAResponseEntity>() {
            @Override
            public void success(InitiateFingerprintMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateFingerprintFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        fingerprintVerificationService.initiateFingerprint("localhost:234235", "sd",new InitiateFingerprintMFARequestEntity(),null, new Result<InitiateFingerprintMFAResponseEntity>() {
            @Override
            public void success(InitiateFingerprintMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testAuthenticateFingerprint() throws Exception {

        fingerprintVerificationService.authenticateFingerprint("baseurl", new AuthenticateFingerprintRequestEntity(),null, new Result<AuthenticateFingerprintResponseEntity>() {
            @Override
            public void success(AuthenticateFingerprintResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticateFingerprintNULLMFA() throws Exception {

        fingerprintVerificationService.authenticateFingerprint("", new AuthenticateFingerprintRequestEntity(),null, new Result<AuthenticateFingerprintResponseEntity>() {
            @Override
            public void success(AuthenticateFingerprintResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testAuthenticateFingerprintFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        fingerprintVerificationService.authenticateFingerprint("localhost:234235", new AuthenticateFingerprintRequestEntity(),null, new Result<AuthenticateFingerprintResponseEntity>() {
            @Override
            public void success(AuthenticateFingerprintResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
