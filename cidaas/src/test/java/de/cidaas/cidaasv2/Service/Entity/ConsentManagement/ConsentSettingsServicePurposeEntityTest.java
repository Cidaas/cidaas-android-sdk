package de.cidaas.cidaasv2.Service.Entity.ConsentManagement;

import junit.framework.Assert;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.ConsentManagement.ConsentSettingsServicePurposeEntity;

public class ConsentSettingsServicePurposeEntityTest {
    String purpose;
    String purposeCategory;
    String consentType;
    String piiCategory;
    boolean primaryPurpose;
    String termination;
    boolean thirdPartyDisclosure;
    String thirdPartyName;

    ConsentSettingsServicePurposeEntity consentSettingsServicePurposeEntity = new ConsentSettingsServicePurposeEntity();

    @Test
    public void setPurpose() {
        consentSettingsServicePurposeEntity.setPurpose("Test");
        Assert.assertEquals("Test", consentSettingsServicePurposeEntity.getPurpose());
    }

    @Test
    public void setPrimaryPurpose() {
        consentSettingsServicePurposeEntity.setPrimaryPurpose(true);
        Assert.assertEquals(true, consentSettingsServicePurposeEntity.isPrimaryPurpose());
    }

    @Test
    public void setirdpartydisclosureThPurpose() {
        consentSettingsServicePurposeEntity.setThirdPartyDisclosure(true);
        Assert.assertEquals(true, consentSettingsServicePurposeEntity.isThirdPartyDisclosure());
    }

    @Test
    public void setPurposeCategory() {
        consentSettingsServicePurposeEntity.setPurposeCategory("Test");
        Assert.assertEquals("Test", consentSettingsServicePurposeEntity.getPurposeCategory());
    }


    @Test
    public void setConsentType() {
        consentSettingsServicePurposeEntity.setConsentType("Test");
        Assert.assertEquals("Test", consentSettingsServicePurposeEntity.getConsentType());
    }

    @Test
    public void setPiiCategory() {
        consentSettingsServicePurposeEntity.setPiiCategory("Test");
        Assert.assertEquals("Test", consentSettingsServicePurposeEntity.getPiiCategory());
    }

    @Test
    public void setTermination() {
        consentSettingsServicePurposeEntity.setTermination("Test");
        Assert.assertEquals("Test", consentSettingsServicePurposeEntity.getTermination());
    }

    @Test
    public void setThirdPartyName() {
        consentSettingsServicePurposeEntity.setThirdPartyName("Test");
        Assert.assertEquals("Test", consentSettingsServicePurposeEntity.getThirdPartyName());
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme