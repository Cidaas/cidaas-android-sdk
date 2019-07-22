package com.example.cidaasv2.Controller.Repository.Consent;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptedRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)

public class ConsentControllerTest {

    Context context;
    ConsentController shared;
    ConsentController consentController;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        consentController=new ConsentController(context);
    }

    @Test
    public void testGetShared() throws Exception {
        ConsentController result = ConsentController.getShared(null);
        Assert.assertTrue(result instanceof ConsentController);
    }

    @Test
    public void testGetConsentURL() throws Exception {
        consentController.getConsentURL("consentName", "consentVersion", null);
    }

    @Test
    public void testGetConsentDetails() throws Exception {
        consentController.getConsentDetails("baseurl", "consentName", null);
    }

    @Test
    public void testAcceptConsent() throws Exception {
        consentController.acceptConsent("baseurl", null, null);
    }

    @Test
    public void testAcceptConsentValue() throws Exception {
        ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity=new ConsentManagementAcceptedRequestEntity();
        consentManagementAcceptedRequestEntity.setSub("Sub");
        consentManagementAcceptedRequestEntity.setClient_id("ClientId");
        consentManagementAcceptedRequestEntity.setAccepted(true);
        consentManagementAcceptedRequestEntity.setName("Name");
        consentManagementAcceptedRequestEntity.setVersion("Version");
        consentManagementAcceptedRequestEntity.setTrackId("TrackId");
        consentController.acceptConsent("baseurl", consentManagementAcceptedRequestEntity, new Result<LoginCredentialsResponseEntity>() {
            @Override
            public void success(LoginCredentialsResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme