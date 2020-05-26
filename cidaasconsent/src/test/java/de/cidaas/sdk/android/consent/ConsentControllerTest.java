package de.cidaas.sdk.android.consent;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import de.cidaas.sdk.android.consent.Domain.Controller.Consent.ConsentController;
import de.cidaas.sdk.android.entities.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;


@RunWith(RobolectricTestRunner.class)

public class ConsentControllerTest {

    Context context;
    ConsentController shared;
    ConsentController consentController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        consentController = new ConsentController(context);
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
        consentController.getConsentDetails("name", null);
    }

    @Test
    public void testAcceptConsent() throws Exception {
        consentController.acceptConsent(null, null);
    }

    @Test
    public void testAcceptConsentValue() throws Exception {
        ConsentEntity consentManagementAcceptedRequestEntity = new ConsentEntity("", "", "", "", true);
        consentManagementAcceptedRequestEntity.setSub("Sub");
        consentManagementAcceptedRequestEntity.setAccepted(true);
        consentManagementAcceptedRequestEntity.setTrackId("TrackId");
        consentController.acceptConsent(consentManagementAcceptedRequestEntity, new EventResult<LoginCredentialsResponseEntity>() {
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