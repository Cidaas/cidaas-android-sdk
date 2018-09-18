package com.example.cidaasv2.Service.Repository.Consent;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
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
        consentService.acceptConsent("baseurl", consentManagementAcceptedRequestEntity, new Result<ConsentManagementAcceptResponseEntity>() {
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
        consentService.acceptConsent("", consentManagementAcceptedRequestEntity, new Result<ConsentManagementAcceptResponseEntity>() {
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
        consentService.resumeConsent("baseurl", resumeConsentRequestEntity, new Result<ResumeConsentResponseEntity>() {
            @Override
            public void success(ResumeConsentResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme