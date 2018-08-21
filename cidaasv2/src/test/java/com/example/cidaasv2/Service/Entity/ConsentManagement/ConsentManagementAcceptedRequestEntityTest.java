package com.example.cidaasv2.Service.Entity.ConsentManagement;

import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentRequestEntity;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ConsentManagementAcceptedRequestEntityTest {
    ConsentManagementAcceptedRequestEntity resumeConsentRequestEntity = new ConsentManagementAcceptedRequestEntity();


    @Test
    public void getAccepted()
    {
        resumeConsentRequestEntity.setAccepted(true);
        assertTrue(resumeConsentRequestEntity.isAccepted());
    }
    @Test
    public void getSub()
    {
        resumeConsentRequestEntity.setSub("test");
        assertTrue(resumeConsentRequestEntity.getSub()=="test");
    }
    @Test
    public void getVersion()
    {
        resumeConsentRequestEntity.setVersion("test");
        assertTrue(resumeConsentRequestEntity.getVersion()=="test");
    }
    @Test
    public void getName()
    {
        resumeConsentRequestEntity.setName("Name");
        assertTrue(resumeConsentRequestEntity.getName()=="Name");
    }
    @Test
    public void getClientId()
    {
        resumeConsentRequestEntity.setClient_id("test");
        assertTrue(resumeConsentRequestEntity.getClient_id()=="test");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme