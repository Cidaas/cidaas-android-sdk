package de.cidaas.cidaasv2.Service.Entity.ConsentManagement;

import org.junit.Assert;
import org.junit.Test;


public class ConsentSettingsResponseServiceEntityTest {
    //Field purposes of type ConsentSettingsServicePurposeEntity[] - was not mocked since Mockito doesn't mock arrays
    ConsentSettingsResponseServiceEntity consentSettingsResponseServiceEntity = new ConsentSettingsResponseServiceEntity();

    @Test
    public void testGetPurposes() throws Exception {
        ConsentSettingsServicePurposeEntity consentSettingsServicePurposeEntity = new ConsentSettingsServicePurposeEntity();
        consentSettingsServicePurposeEntity.setThirdPartyName("Test");
        ConsentSettingsServicePurposeEntity[] result = {consentSettingsServicePurposeEntity};
        consentSettingsResponseServiceEntity.setPurposes(result);
        Assert.assertEquals("Test", consentSettingsResponseServiceEntity.getPurposes()[0].getThirdPartyName());
    }

    @Test
    public void setCountry() {
        consentSettingsResponseServiceEntity.setService("Test");
        junit.framework.Assert.assertEquals("Test", consentSettingsResponseServiceEntity.getService());
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme