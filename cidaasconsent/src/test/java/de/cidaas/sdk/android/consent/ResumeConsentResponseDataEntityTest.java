package de.cidaas.sdk.android.consent;

import org.junit.Test;

import de.cidaas.sdk.android.consent.data.Entity.ResumeConsent.ResumeConsentResponseDataEntity;

import static junit.framework.TestCase.assertTrue;

public class ResumeConsentResponseDataEntityTest {
    ResumeConsentResponseDataEntity resumeConsentResponseDataEntity = new ResumeConsentResponseDataEntity();

    @Test
    public void getCode() {
        resumeConsentResponseDataEntity.setCode("code");
        assertTrue(resumeConsentResponseDataEntity.getCode() == "code");
    }

    @Test
    public void getGrantType() {
        resumeConsentResponseDataEntity.setGrant_type("test");
        assertTrue(resumeConsentResponseDataEntity.getGrant_type() == "test");
    }

    @Test
    public void getTokenType() {
        resumeConsentResponseDataEntity.setToken_type("test");
        assertTrue(resumeConsentResponseDataEntity.getToken_type() == "test");

    }

    @Test
    public void getViewType() {
        resumeConsentResponseDataEntity.setViewtype("test");
        assertTrue(resumeConsentResponseDataEntity.getViewtype() == "test");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme