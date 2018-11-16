package com.example.cidaasv2.Service.Entity.Deduplication;

import junit.framework.Assert;

import org.junit.Test;

public class DeduplicationListTest {

    String provider;
    String sub;
    String email;
    String emailName;
    String firstname;
    String lastname;
    String displayName;
    String currentLocale;
    String country;
    String region;
    String city;
    String zipcode;
    
    DeduplicationList deduplicationList=new DeduplicationList();

    @Test
    public void setProvider()
    {
        deduplicationList.setProvider("Test");
        Assert.assertEquals("Test",deduplicationList.getProvider());
    }

    @Test
    public void setFirstname()
    {
        deduplicationList.setFirstname("Test");
        Assert.assertEquals("Test",deduplicationList.getFirstname());
    }


    @Test
    public void setSub()
    {
        deduplicationList.setSub("Sub");
        Assert.assertEquals("Sub",deduplicationList.getSub());
    }

    @Test
    public void setEmail()
    {
        deduplicationList.setEmail("Test");
        Assert.assertEquals("Test",deduplicationList.getEmail());
    }

    @Test
    public void setLastname()
    {
        deduplicationList.setLastname("LastName");
        Assert.assertEquals("LastName",deduplicationList.getLastname());
    }

    @Test
    public void setEmailName()
    {
        deduplicationList.setEmailName("Email");
        Assert.assertEquals("Email",deduplicationList.getEmailName());
    }

    @Test
    public void setDisplayName()
    {
        deduplicationList.setDisplayName("Test");
        Assert.assertEquals("Test",deduplicationList.getDisplayName());
    }

    @Test
    public void setCurrentLocale()
    {
        deduplicationList.setCurrentLocale("test");
        Assert.assertEquals("test",deduplicationList.getCurrentLocale());
    }


    @Test
    public void setCountry()
    {
        deduplicationList.setCountry("Test");
        Assert.assertEquals("Test",deduplicationList.getCountry());
    }

    @Test
    public void setRegion()
    {
        deduplicationList.setRegion("Test");
        Assert.assertEquals("Test",deduplicationList.getRegion());
    }

    @Test
    public void setCity()
    {
        deduplicationList.setCity("Test");
        Assert.assertEquals("Test",deduplicationList.getCity());
    }

    @Test
    public void setZipcode()
    {
        deduplicationList.setZipcode("Test");
        Assert.assertEquals("Test",deduplicationList.getZipcode());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme