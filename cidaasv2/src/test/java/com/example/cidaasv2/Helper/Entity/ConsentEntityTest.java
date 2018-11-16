package com.example.cidaasv2.Helper.Entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConsentEntityTest {
    String consentName;
    String consentVersion;
    String sub;
    String trackId;
    boolean isAccepted;

    ConsentEntity consentEntity=new ConsentEntity();

    @Before
    public void setUp() {


    }

    @Test
    public void Accepted(){

        consentEntity.setAccepted(true);
        Assert.assertTrue(consentEntity.isAccepted());

    }

    @Test
    public void ConsentName(){

        consentEntity.setConsentName("ConsentName");
        Assert.assertEquals("ConsentName",consentEntity.getConsentName());
    }

    @Test
    public void Error(){
        consentEntity.setConsentVersion("Version");
        Assert.assertEquals("Version",consentEntity.getConsentVersion());

    }


    @Test
    public void setRefnumber(){

        consentEntity.setSub("sub");
        Assert.assertEquals("sub",consentEntity.getSub());
    }

    @Test
    public void setError_description(){
        consentEntity.setTrackId("TrackId");
        Assert.assertEquals("TrackId",consentEntity.getTrackId());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme