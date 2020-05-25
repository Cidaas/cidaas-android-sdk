package com.example.cidaasv2.Service.Repository.Verification.Email;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.CidaasHelper;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailMFAResponseEntity;

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
public class EmailVerificationServiceTest {
    Context context;
    EmailVerificationService emailVerificationService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
      emailVerificationService=new EmailVerificationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        EmailVerificationService result = EmailVerificationService.getShared(null);

        Assert.assertTrue(result instanceof EmailVerificationService);
    }

    @Test
    public void testSetupEmailMFA() throws Exception {

        emailVerificationService.setupEmailMFA("baseurl", "accessToken",null, new Result<SetupEmailMFAResponseEntity>() {
            @Override
            public void success(SetupEmailMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupEMailMFANUll() throws Exception {

        emailVerificationService.setupEmailMFA("", "accessToken",null, new Result<SetupEmailMFAResponseEntity>() {
            @Override
            public void success(SetupEmailMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testsetupFail() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");


        emailVerificationService.setupEmailMFA("localhost:234235", "accessToken",null, new Result<SetupEmailMFAResponseEntity>() {
            @Override
            public void success(SetupEmailMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });


    }
    @Test
    public void testEnrollEmailMFA() throws Exception {

        emailVerificationService.enrollEmailMFA("baseurl", "accessToken", new EnrollEmailMFARequestEntity(),null, new Result<EnrollEmailMFAResponseEntity>() {
            @Override
            public void success(EnrollEmailMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testSetupEmailMFANUll() throws Exception {

        emailVerificationService.enrollEmailMFA("", "accessToken", new EnrollEmailMFARequestEntity(),null, new Result<EnrollEmailMFAResponseEntity>() {
            @Override
            public void success(EnrollEmailMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testenrollFail() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        emailVerificationService.enrollEmailMFA("localhost:234235", "accessToken", new EnrollEmailMFARequestEntity(),null, new Result<EnrollEmailMFAResponseEntity>() {
            @Override
            public void success(EnrollEmailMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });


    }
    @Test
    public void testInitiateEmailMFA() throws Exception {

        emailVerificationService.initiateEmailMFA("baseurl", new InitiateEmailMFARequestEntity(),null, new Result<InitiateEmailMFAResponseEntity>() {
            @Override
            public void success(InitiateEmailMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testinitiateEmailMFANUll() throws Exception {

        emailVerificationService.initiateEmailMFA("", new InitiateEmailMFARequestEntity(),null, new Result<InitiateEmailMFAResponseEntity>() {
            @Override
            public void success(InitiateEmailMFAResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testinitiateFail() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        emailVerificationService.initiateEmailMFA("localhost:234235", new InitiateEmailMFARequestEntity(),null, new Result<InitiateEmailMFAResponseEntity>() {
            @Override
            public void success(InitiateEmailMFAResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });


    }

    @Test
    public void testAuthenticateEmailMFA() throws Exception {

        emailVerificationService.authenticateEmailMFA("baseurl", new AuthenticateEmailRequestEntity(),null, new Result<AuthenticateEmailResponseEntity>() {
            @Override
            public void success(AuthenticateEmailResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testauthenticateCodeMFANUll() throws Exception {

        emailVerificationService.authenticateEmailMFA("", new AuthenticateEmailRequestEntity(),null, new Result<AuthenticateEmailResponseEntity>() {
            @Override
            public void success(AuthenticateEmailResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testAuthenticateFail() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        emailVerificationService.authenticateEmailMFA("localhost:234235", new AuthenticateEmailRequestEntity(),null, new Result<AuthenticateEmailResponseEntity>() {
            @Override
            public void success(AuthenticateEmailResponseEntity result) {


            }

            @Override
            public void failure(WebAuthError error) {

                Timber.e("Success");
            }
        });


    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
