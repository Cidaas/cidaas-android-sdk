package com.example.cidaasv2.Service.Entity.ConsentManagement;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ConsentDetailsResultDataEntityTest {

    ConsentDetailsResultDataEntity consentDetailsResultDataEntity=new ConsentDetailsResultDataEntity();
    @Test
    public void getConsentURL()
    {
        consentDetailsResultDataEntity.setConsentURL("code");
        assertTrue(consentDetailsResultDataEntity.getConsentURL()=="code");
    }
    @Test
    public void getId()
    {
        consentDetailsResultDataEntity.set_id("test");
        assertTrue(consentDetailsResultDataEntity.get_id()=="test");
    }
    @Test
    public void geDescription()
    {
        consentDetailsResultDataEntity.setDescription("test");
        assertTrue(consentDetailsResultDataEntity.getDescription()=="test");

    }
    @Test
    public void getTitle()
    {
        consentDetailsResultDataEntity.setTitle("title");
        assertTrue(consentDetailsResultDataEntity.getTitle()=="title");

    }
    @Test
    public void getUserAgreeText()
    {
        consentDetailsResultDataEntity.setUserAgreeText("title");
        assertTrue(consentDetailsResultDataEntity.getUserAgreeText()=="title");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme