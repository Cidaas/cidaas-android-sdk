package com.example.cidaasv2.Service.Entity.ConsentManagement;

import org.junit.Assert;
import org.junit.Test;

public class ConsentSettingsReponseDataEntityTest {
    //Field services of type ConsentSettingsResponseServiceEntity[] - was not mocked since Mockito doesn't mock arrays
    //Field piiControllers of type consentSettingsReponseDataEntity[] - was not mocked since Mockito doesn't mock arrays
    ConsentSettingsReponseDataEntity consentSettingsReponseDataEntity = new ConsentSettingsReponseDataEntity();


    @Test
    public void isSensitive()
    {
        consentSettingsReponseDataEntity.setSensitive(true);
        junit.framework.Assert.assertEquals(true,consentSettingsReponseDataEntity.isSensitive());
    }
    @Test
    public void isenabled()
    {
        consentSettingsReponseDataEntity.setEnabled(true);
        junit.framework.Assert.assertEquals(true,consentSettingsReponseDataEntity.isEnabled());
    }

    @Test
    public void setPolicyUrl()
    {
        consentSettingsReponseDataEntity.setPolicyUrl("Test");
        junit.framework.Assert.assertEquals("Test",consentSettingsReponseDataEntity.getPolicyUrl());
    }

    @Test
    public void setUserAgreeText()
    {
        consentSettingsReponseDataEntity.setUserAgreeText("Test");
        junit.framework.Assert.assertEquals("Test",consentSettingsReponseDataEntity.getUserAgreeText());
    }

    @Test
    public void setDescription()
    {
        consentSettingsReponseDataEntity.setDescription("Test");
        junit.framework.Assert.assertEquals("Test",consentSettingsReponseDataEntity.getDescription());
    }

    @Test
    public void setName()
    {
        consentSettingsReponseDataEntity.setName("Test");
        junit.framework.Assert.assertEquals("Test",consentSettingsReponseDataEntity.getName());
    }

    @Test
    public void setLanguage()
    {
        consentSettingsReponseDataEntity.setLanguage("Test");
        junit.framework.Assert.assertEquals("Test",consentSettingsReponseDataEntity.getLanguage());
    }

    @Test
    public void setConsentReceiptID()
    {
        consentSettingsReponseDataEntity.setConsentReceiptID("Test");
        junit.framework.Assert.assertEquals("Test",consentSettingsReponseDataEntity.getConsentReceiptID());
    }

    @Test
    public void setCollectionMethod()
    {
        consentSettingsReponseDataEntity.setCollectionMethod("Test");
        junit.framework.Assert.assertEquals("Test",consentSettingsReponseDataEntity.getCollectionMethod());
    }

    @Test
    public void setJurisdiction()
    {
        consentSettingsReponseDataEntity.setJurisdiction("Test");
        junit.framework.Assert.assertEquals("Test",consentSettingsReponseDataEntity.getJurisdiction());
    }

    @Test
    public void setStatus()
    {
        consentSettingsReponseDataEntity.setStatus("Test");
        junit.framework.Assert.assertEquals("Test",consentSettingsReponseDataEntity.getStatus());
    }

    @Test
    public void setConsent_type()
    {
        consentSettingsReponseDataEntity.setConsent_type("Test");
        junit.framework.Assert.assertEquals("Test",consentSettingsReponseDataEntity.getConsent_type());
    }

    @Test
    public void setVersion()
    {
        consentSettingsReponseDataEntity.setVersion("Test");
        junit.framework.Assert.assertEquals("Test",consentSettingsReponseDataEntity.getVersion());
    }

    @Test
    public void testGetSpiCat() throws Exception {
        String[] result = {"Test"};

        consentSettingsReponseDataEntity.setSpiCat(result);

        Assert.assertEquals("Test", consentSettingsReponseDataEntity.getSpiCat()[0]);
    }

    @Test
    public void testGetServices() throws Exception {
        ConsentSettingsResponseServiceEntity consentSettingsResponseServiceEntity=new ConsentSettingsResponseServiceEntity();
        consentSettingsResponseServiceEntity.setService("Test");
        ConsentSettingsResponseServiceEntity[] result = {consentSettingsResponseServiceEntity};
        consentSettingsReponseDataEntity.setServices(result);
        // Assert.assertArrayEquals(new ConsentSettingsResponseServiceEntity[]{new ConsentSettingsResponseServiceEntity()}, result);
        Assert.assertEquals("Test",  consentSettingsReponseDataEntity.getServices()[0].getService());

    }

    @Test
    public void testGetPiiControllers() throws Exception {

        ConsentSettingsPIIControllersEntity consentSettingsPIIControllersEntity=new ConsentSettingsPIIControllersEntity();
        consentSettingsPIIControllersEntity.setPhone("Test");
        ConsentSettingsPIIControllersEntity[] result = {consentSettingsPIIControllersEntity};
        consentSettingsReponseDataEntity.setPiiControllers(result);

        //     Assert.assertArrayEquals(new consentSettingsReponseDataEntity[]{new consentSettingsReponseDataEntity()}, result);
        Assert.assertEquals("Test", consentSettingsReponseDataEntity.getPiiControllers()[0].getPhone());
    }
}


//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme