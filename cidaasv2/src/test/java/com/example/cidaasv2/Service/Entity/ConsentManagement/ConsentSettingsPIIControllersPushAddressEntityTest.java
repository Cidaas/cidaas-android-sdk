package com.example.cidaasv2.Service.Entity.ConsentManagement;

import junit.framework.Assert;

import org.junit.Test;

public class ConsentSettingsPIIControllersPushAddressEntityTest {

    ConsentSettingsPIIControllersAddressEntity consentSettingsPIIControllersAddressEntity=new ConsentSettingsPIIControllersAddressEntity();

    @Test
    public void setCountry()
    {
        consentSettingsPIIControllersAddressEntity.setAddressCountry("Test");
        Assert.assertEquals("Test",consentSettingsPIIControllersAddressEntity.getAddressCountry());
    }

    @Test
    public void setRegion()
    {
        consentSettingsPIIControllersAddressEntity.setStreetAddress("Test");
        Assert.assertEquals("Test",consentSettingsPIIControllersAddressEntity.getStreetAddress());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme