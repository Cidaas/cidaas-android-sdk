package com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ResumeConsentDetailsV2RequestEntityTest {
    @Test
    public void gettrackID()
    {
        ResumeConsentRequestEntity resumeConsentRequestEntity = new ResumeConsentRequestEntity();
        resumeConsentRequestEntity.setTrack_id("trackID");
        assertTrue(resumeConsentRequestEntity.getTrack_id()=="trackID");
    }
    @Test
    public void getSub()
    {
        ResumeConsentRequestEntity resumeConsentRequestEntity = new ResumeConsentRequestEntity();
        resumeConsentRequestEntity.setSub("test");
        assertTrue(resumeConsentRequestEntity.getSub()=="test");
    }
    @Test
    public void getVersion()
    {
        ResumeConsentRequestEntity resumeConsentRequestEntity = new ResumeConsentRequestEntity();
        resumeConsentRequestEntity.setVersion("test");
        assertTrue(resumeConsentRequestEntity.getVersion()=="test");
    }
    @Test
    public void getName()
    {
        ResumeConsentRequestEntity resumeConsentRequestEntity = new ResumeConsentRequestEntity();
        resumeConsentRequestEntity.setName("Name");
        assertTrue(resumeConsentRequestEntity.getName()=="Name");
    }
    @Test
    public void getClientId()
    {
        ResumeConsentRequestEntity resumeConsentRequestEntity = new ResumeConsentRequestEntity();
        resumeConsentRequestEntity.setClient_id("test");
        assertTrue(resumeConsentRequestEntity.getClient_id()=="test");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme