package de.cidaas.cidaasv2.Service.Entity.UserProfile;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.UserProfile.MobileNumberObject;

public class MobileNumberObjectTest {

    String carrier_name;
    String carrier_type;
    String country;

    String dail_code;
    String E164_format;
    String given_phone;

    String international_format;
    String national_format;
    String phone;


    MobileNumberObject mobileNumberObject;

    @Before
    public void setUp() {
        mobileNumberObject = new MobileNumberObject();
    }

    @Test
    public void set_id() {
        mobileNumberObject.set_id("Test");
        Assert.assertEquals("Test", mobileNumberObject.get_id());

    }

    @Test
    public void setUpdatedTime() {
        mobileNumberObject.setUpdatedTime("Test");
        Assert.assertEquals("Test", mobileNumberObject.getUpdatedTime());

    }

    @Test
    public void setCreatedTime() {
        mobileNumberObject.setCreatedTime("Test");
        Assert.assertEquals("Test", mobileNumberObject.getCreatedTime());

    }

    @Test
    public void setClassName() {
        mobileNumberObject.setClassName("Test");
        Assert.assertEquals("Test", mobileNumberObject.getClassName());

    }

    @Test
    public void setCarrier_name() {
        mobileNumberObject.setCarrier_name("Test");
        Assert.assertEquals("Test", mobileNumberObject.getCarrier_name());

    }

    @Test
    public void setCarrier_type() {
        mobileNumberObject.setCarrier_type("Test");
        Assert.assertEquals("Test", mobileNumberObject.getCarrier_type());

    }

    @Test
    public void setCountry() {
        mobileNumberObject.setCountry("Test");
        Assert.assertEquals("Test", mobileNumberObject.getCountry());

    }

    @Test
    public void setDail_code() {
        mobileNumberObject.setDail_code("Test");
        Assert.assertEquals("Test", mobileNumberObject.getDail_code());

    }

    @Test
    public void setE164_format() {
        mobileNumberObject.setE164_format("Test");
        Assert.assertEquals("Test", mobileNumberObject.getE164_format());

    }

    @Test
    public void setGiven_phone() {
        mobileNumberObject.setGiven_phone("Test");
        Assert.assertEquals("Test", mobileNumberObject.getGiven_phone());


    }

    @Test
    public void setInternational_format() {
        mobileNumberObject.setInternational_format("Test");
        Assert.assertEquals("Test", mobileNumberObject.getInternational_format());

    }

    @Test
    public void setNational_format() {
        mobileNumberObject.setNational_format("Test");
        Assert.assertEquals("Test", mobileNumberObject.getNational_format());

    }

    @Test
    public void setPhone() {
        mobileNumberObject.setPhone("Test");
        Assert.assertEquals("Test", mobileNumberObject.getPhone());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme