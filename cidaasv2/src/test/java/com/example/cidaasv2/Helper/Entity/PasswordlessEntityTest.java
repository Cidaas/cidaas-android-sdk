package com.example.cidaasv2.Helper.Entity;

import org.junit.Assert;
import org.junit.Test;

public class PasswordlessEntityTest {


    PasswordlessEntity passwordlessEntity=new PasswordlessEntity();
    @Test
    public void setEmail()
    {
        passwordlessEntity.setEmail("email");
        Assert.assertEquals("email",passwordlessEntity.getEmail());
    }
    @Test
    public void setMobile()
    {
        passwordlessEntity.setMobile("Mobile");
        Assert.assertEquals("Mobile",passwordlessEntity.getMobile());
    }
    @Test
    public void setSub()
    {
        passwordlessEntity.setSub("Sub");
        Assert.assertEquals("Sub",passwordlessEntity.getSub());
    }

    
    @Test
    public void setRequestId()
    {
        passwordlessEntity.setRequestId("requestId");
        Assert.assertEquals("requestId",passwordlessEntity.getRequestId());
    }
    @Test
    public void setTrackId()
    {
        passwordlessEntity.setTrackId("TrackId");
        Assert.assertEquals("TrackId",passwordlessEntity.getTrackId());
    }
    @Test
    public void setUsageType()
    {
        passwordlessEntity.setUsageType("UsageType");
        Assert.assertEquals("UsageType",passwordlessEntity.getUsageType());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme