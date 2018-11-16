package com.example.cidaasv2.Service.Repository.Consent;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.HelperClass;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentDetailsResultEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptResponseEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptedRequestEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentRequestEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentRequestEntityTest;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Dictionary;
import java.util.Hashtable;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ConsentServiceTest {

    Context context;
    ConsentService consentService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
     consentService=new ConsentService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        ConsentService result = ConsentService.getShared(null);
        Assert.assertTrue(result instanceof ConsentService);
    }

    @Test
    public void testGetConsentDetails() throws Exception {

        consentService.getConsentDetails("baseurl", "consentName", new Result<ConsentDetailsResultEntity>() {
            @Override
            public void success(ConsentDetailsResultEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
    @Test
    public void testGetConsentDetailsNUl() throws Exception {

        consentService.getConsentDetails("", "consentName", new Result<ConsentDetailsResultEntity>() {
            @Override
            public void success(ConsentDetailsResultEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testAcceptConsent() throws Exception {
        ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity=new ConsentManagementAcceptedRequestEntity();
        consentManagementAcceptedRequestEntity.setTrackId("TrackId");
        consentManagementAcceptedRequestEntity.setVersion("Version");
        consentManagementAcceptedRequestEntity.setName("Name");
        consentManagementAcceptedRequestEntity.setAccepted(true);
        consentManagementAcceptedRequestEntity.setClient_id("ClientId");
        consentManagementAcceptedRequestEntity.setSub("Sub");
        consentService.acceptConsent("baseurl", consentManagementAcceptedRequestEntity,null, new Result<ConsentManagementAcceptResponseEntity>() {
            @Override
            public void success(ConsentManagementAcceptResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testAcceptConsentnull() throws Exception {
        ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity=new ConsentManagementAcceptedRequestEntity();
        consentManagementAcceptedRequestEntity.setTrackId("TrackId");
        consentManagementAcceptedRequestEntity.setVersion("Version");
        consentManagementAcceptedRequestEntity.setName("Name");
        consentManagementAcceptedRequestEntity.setAccepted(true);
        consentManagementAcceptedRequestEntity.setClient_id("ClientId");
        consentManagementAcceptedRequestEntity.setSub("Sub");
        consentService.acceptConsent("", consentManagementAcceptedRequestEntity,null, new Result<ConsentManagementAcceptResponseEntity>() {
            @Override
            public void success(ConsentManagementAcceptResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testResumeConsent() throws Exception {

        ResumeConsentRequestEntity resumeConsentRequestEntity =new ResumeConsentRequestEntity();
        resumeConsentRequestEntity.setTrack_id("TrackId");
        resumeConsentRequestEntity.setVersion("Version");
        resumeConsentRequestEntity.setName("Name");
        resumeConsentRequestEntity.setClient_id("ClientId");
        resumeConsentRequestEntity.setSub("Sub");
        consentService.resumeConsent("baseurl", resumeConsentRequestEntity, null,new Result<ResumeConsentResponseEntity>() {
            @Override
            public void success(ResumeConsentResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testResumeConsentNull() throws Exception {

        ResumeConsentRequestEntity resumeConsentRequestEntity =new ResumeConsentRequestEntity();
        resumeConsentRequestEntity.setTrack_id("TrackId");
        resumeConsentRequestEntity.setVersion("Version");
        resumeConsentRequestEntity.setName("Name");
        resumeConsentRequestEntity.setClient_id("ClientId");
        resumeConsentRequestEntity.setSub("Sub");
        consentService.resumeConsent("", resumeConsentRequestEntity, null,new Result<ResumeConsentResponseEntity>() {
            @Override
            public void success(ResumeConsentResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }



    @Test
    public void testGetClientInfoFail() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/consent-management-srv/settings/public?name=");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        consentService.getConsentDetails(HelperClass.removeLastChar(Cidaas.baseurl),null, new Result<ConsentDetailsResultEntity>() {
            @Override
            public void success(ConsentDetailsResultEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e("Success");
            }
        });


    }


    @Test
    public void tConsenrt() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        consentService.acceptConsent(HelperClass.removeLastChar(Cidaas.baseurl),new ConsentManagementAcceptedRequestEntity(),null, new Result<ConsentManagementAcceptResponseEntity>() {
            @Override
            public void success(ConsentManagementAcceptResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e("Success");
            }
        });


    }



    @Test
    public void Resume() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL= server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        Cidaas.baseurl=domainURL;


        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","localhost:234235");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");



        consentService.resumeConsent(HelperClass.removeLastChar(Cidaas.baseurl),new ResumeConsentRequestEntity(),null, new Result<ResumeConsentResponseEntity>() {
            @Override
            public void success(ResumeConsentResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e("Success");
            }
        });


    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme