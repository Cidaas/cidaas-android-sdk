package de.cidaas.sdk.android.Service.Entity.ConsentManagement;

import junit.framework.Assert;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentManagementAcceptedRequestEntity;

import static junit.framework.TestCase.assertTrue;

public class ConsentManagementAcceptedRequestEntityTest {
    ConsentManagementAcceptedRequestEntity resumeConsentRequestEntity = new ConsentManagementAcceptedRequestEntity();


    @Test
    public void getAccepted() {
        resumeConsentRequestEntity.setAccepted(true);
        assertTrue(resumeConsentRequestEntity.isAccepted());
    }

    @Test
    public void getSub() {
        resumeConsentRequestEntity.setSub("test");
        assertTrue(resumeConsentRequestEntity.getSub() == "test");
    }

    @Test
    public void getVersion() {
        resumeConsentRequestEntity.setVersion("test");
        assertTrue(resumeConsentRequestEntity.getVersion() == "test");
    }

    @Test
    public void getName() {
        resumeConsentRequestEntity.setName("Name");
        assertTrue(resumeConsentRequestEntity.getName() == "Name");
    }

    @Test
    public void getClientId() {
        resumeConsentRequestEntity.setClient_id("test");
        assertTrue(resumeConsentRequestEntity.getClient_id() == "test");
    }

    @Test
    public void setTrack_id() {
        resumeConsentRequestEntity.setTrackId("Test");
        Assert.assertEquals("Test", resumeConsentRequestEntity.getTrackId());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme