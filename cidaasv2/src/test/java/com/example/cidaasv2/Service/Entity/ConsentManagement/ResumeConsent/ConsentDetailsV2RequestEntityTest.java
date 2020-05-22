package com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent;

import org.junit.Before;
import org.junit.Test;

import widas.raja.cidaasconsentv2.data.Entity.v1.ConsentDetailsV2RequestEntity;

import static junit.framework.TestCase.assertTrue;

public class ConsentDetailsV2RequestEntityTest {
    private ConsentDetailsV2RequestEntity underTest;

    @Before
    public void setup() {
        underTest = new ConsentDetailsV2RequestEntity("sub", "requestId", "track_id", "consent_id", "consent_version_id", "client_id");
    }
    
    @Test
    public void getTrackID()
    {
        assertTrue(underTest.getTrack_id() == "track_id");
    }
    @Test
    public void getSub()
    {
        assertTrue(underTest.getSub() == "sub");
    }


    @Test
    public void getVersion()
    {
        assertTrue(underTest.getRequestId() == "requestId");
    }
    @Test
    public void getConsent_id()
    {
        assertTrue(underTest.getConsent_id() == "consent_id");
    }

    @Test
    public void getConsent_version_id() {
        assertTrue(underTest.getConsent_version_id() == "consent_version_id");
    }
    @Test
    public void getClientId()
    {
        assertTrue(underTest.getClient_id() == "client_id");
    }
}
