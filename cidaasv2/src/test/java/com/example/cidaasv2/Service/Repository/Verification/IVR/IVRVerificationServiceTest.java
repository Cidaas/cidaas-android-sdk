package com.example.cidaasv2.Service.Repository.Verification.IVR;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.CidaasHelper;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.Pattern.PatternVerificationService;

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
public class IVRVerificationServiceTest {
    Context context;
    IVRVerificationService iVRVerificationService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
       iVRVerificationService=new IVRVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        IVRVerificationService result = IVRVerificationService.getShared(null);

        Assert.assertTrue(result instanceof IVRVerificationService);
    }


    @Test
    public void testSetupIVRMFA() throws Exception {

        iVRVerificationService.setupIVRMFA("baseurl", "AccessToken","phnumber",null, new Result<SetupIVRMFAResponseEntity>() {
            @Override
            public void success(SetupIVRMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupIVRNullMFA() throws Exception {

        iVRVerificationService.setupIVRMFA("", "AccessToken","phnumber",null, new Result<SetupIVRMFAResponseEntity>() {
            @Override
            public void success(SetupIVRMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupIVRFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");


        iVRVerificationService.setupIVRMFA("localhost:234235", "AccessToken","phnumber",null, new Result<SetupIVRMFAResponseEntity>() {
            @Override
            public void success(SetupIVRMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testEnrollIVRMFA() throws Exception {

        iVRVerificationService.enrollIVRMFA("baseurl", "accessToken", new EnrollIVRMFARequestEntity(), null,new Result<EnrollIVRMFAResponseEntity>() {
            @Override
            public void success(EnrollIVRMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
    @Test
    public void testEnrollIVRNULLMFA() throws Exception {

        iVRVerificationService.enrollIVRMFA("", "accessToken", new EnrollIVRMFARequestEntity(), null,new Result<EnrollIVRMFAResponseEntity>() {
            @Override
            public void success(EnrollIVRMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testEnrollIVRFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        iVRVerificationService.enrollIVRMFA("localhost:234235", "accessToken", new EnrollIVRMFARequestEntity(),null, new Result<EnrollIVRMFAResponseEntity>() {
            @Override
            public void success(EnrollIVRMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testInitiateIVRMFA() throws Exception {

        iVRVerificationService.initiateIVRMFA("baseurl", new InitiateIVRMFARequestEntity(),null, new Result<InitiateIVRMFAResponseEntity>() {
            @Override
            public void success(InitiateIVRMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateIVRNULLMFA() throws Exception {

        iVRVerificationService.initiateIVRMFA("", new InitiateIVRMFARequestEntity(),null, new Result<InitiateIVRMFAResponseEntity>() {
            @Override
            public void success(InitiateIVRMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateIVRFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        iVRVerificationService.initiateIVRMFA("localhost:234235", new InitiateIVRMFARequestEntity(),null, new Result<InitiateIVRMFAResponseEntity>() {
            @Override
            public void success(InitiateIVRMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

    @Test
    public void testAuthenticateIVRMFA() throws Exception {

        iVRVerificationService.authenticateIVRMFA("baseurl", new AuthenticateIVRRequestEntity(),null, new Result<AuthenticateIVRResponseEntity>() {
            @Override
            public void success(AuthenticateIVRResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAuthenticateIVRNULLMFA() throws Exception {

        iVRVerificationService.authenticateIVRMFA("", new AuthenticateIVRRequestEntity(),null, new Result<AuthenticateIVRResponseEntity>() {
            @Override
            public void success(AuthenticateIVRResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testAuthenticateIVRFAILMFA() throws Exception {

        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        iVRVerificationService.authenticateIVRMFA("localhost:234235", new AuthenticateIVRRequestEntity(),null, new Result<AuthenticateIVRResponseEntity>() {
            @Override
            public void success(AuthenticateIVRResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
