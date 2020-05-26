package de.cidaas.sdk.android.consent;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Dictionary;
import java.util.Hashtable;

import de.cidaas.sdk.android.consent.Domain.Service.Consent.ConsentService;
import de.cidaas.sdk.android.consent.data.Entity.ResumeConsent.ResumeConsentEntity;
import de.cidaas.sdk.android.consent.data.Entity.ResumeConsent.ResumeConsentResponseEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentDetailsResultEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentManagementAcceptResponseEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentManagementAcceptedRequestEntity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;


@RunWith(RobolectricTestRunner.class)

public class ConsentServiceTest {

    Context context;
    ConsentService consentService;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        consentService = new ConsentService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        ConsentService result = ConsentService.getShared(null);
        Assert.assertTrue(result instanceof ConsentService);
    }

    @Test
    public void testGetConsentDetails() throws Exception {

        consentService.getConsentDetails("", null, new EventResult<ConsentDetailsResultEntity>() {
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

        consentService.getConsentDetails("", null, new EventResult<ConsentDetailsResultEntity>() {
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
        ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity = new ConsentManagementAcceptedRequestEntity();
        consentManagementAcceptedRequestEntity.setTrackId("TrackId");
        consentManagementAcceptedRequestEntity.setVersion("Version");
        consentManagementAcceptedRequestEntity.setName("Name");
        consentManagementAcceptedRequestEntity.setAccepted(true);
        consentManagementAcceptedRequestEntity.setClient_id("ClientId");
        consentManagementAcceptedRequestEntity.setSub("Sub");
        consentService.acceptConsent("baseurl", consentManagementAcceptedRequestEntity, null, new EventResult<ConsentManagementAcceptResponseEntity>() {
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
        ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity = new ConsentManagementAcceptedRequestEntity();
        consentManagementAcceptedRequestEntity.setTrackId("TrackId");
        consentManagementAcceptedRequestEntity.setVersion("Version");
        consentManagementAcceptedRequestEntity.setName("Name");
        consentManagementAcceptedRequestEntity.setAccepted(true);
        consentManagementAcceptedRequestEntity.setClient_id("ClientId");
        consentManagementAcceptedRequestEntity.setSub("Sub");
        consentService.acceptConsent("", consentManagementAcceptedRequestEntity, null, new EventResult<ConsentManagementAcceptResponseEntity>() {
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

        ResumeConsentEntity resumeConsentRequestEntity = new ResumeConsentEntity("", "", "", "", "");
        resumeConsentRequestEntity.setTrack_id("TrackId");
        resumeConsentRequestEntity.setClient_id("ClientId");
        resumeConsentRequestEntity.setSub("Sub");
        consentService.resumeConsent("baseurl", resumeConsentRequestEntity, null, new EventResult<ResumeConsentResponseEntity>() {
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

        ResumeConsentEntity resumeConsentRequestEntity = new ResumeConsentEntity("", "", "", "", "");
        resumeConsentRequestEntity.setTrack_id("TrackId");
        resumeConsentRequestEntity.setClient_id("ClientId");
        resumeConsentRequestEntity.setSub("Sub");
        consentService.resumeConsent("", resumeConsentRequestEntity, null, new EventResult<ResumeConsentResponseEntity>() {
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
        String domainURL = server.url("").toString();
        server.url("/consent-management-srv/settings/public?name=");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        consentService.getConsentDetails(StringUtils.chop(CidaasHelper.baseurl), null, new EventResult<ConsentDetailsResultEntity>() {
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
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        consentService.acceptConsent(StringUtils.chop(CidaasHelper.baseurl), new ConsentManagementAcceptedRequestEntity(), null, new EventResult<ConsentManagementAcceptResponseEntity>() {
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
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        consentService.resumeConsent(StringUtils.chop(CidaasHelper.baseurl), new ResumeConsentEntity("", "", "", "", ""), null, new EventResult<ResumeConsentResponseEntity>() {
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