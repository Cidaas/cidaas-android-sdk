package de.cidaas.cidaasv2.Service.Entity.UserProfile;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class IdentityEntityTest {

    IdentityEntity identityEntity;

    @Before
    public void setUp() {
        identityEntity = new IdentityEntity();
    }

    @Test
    public void set_id() {
        identityEntity.set_id("Test");
        Assert.assertEquals("Test", identityEntity.get_id());

    }

    @Test
    public void setUpdatedTime() {
        identityEntity.setUpdatedTime("Test");
        Assert.assertEquals("Test", identityEntity.getUpdatedTime());

    }

    @Test
    public void setCreatedTime() {
        identityEntity.setCreatedTime("Test");
        Assert.assertEquals("Test", identityEntity.getCreatedTime());

    }

    @Test
    public void setClassName() {
        identityEntity.setClassName("Test");
        Assert.assertEquals("Test", identityEntity.getClassName());

    }

    @Test
    public void setUsername() {
        identityEntity.setUsername("Test");
        Assert.assertEquals("Test", identityEntity.getUsername());

    }

    @Test
    public void setSub() {
        identityEntity.setSub("Test");
        Assert.assertEquals("Test", identityEntity.getSub());

    }

    @Test
    public void setMobile_number() {
        identityEntity.setMobile_number("Test");
        Assert.assertEquals("Test", identityEntity.getMobile_number());

    }

    @Test
    public void setLocale() {
        identityEntity.setLocale("Test");
        Assert.assertEquals("Test", identityEntity.getLocale());

    }

    @Test
    public void setGiven_name() {
        identityEntity.setGiven_name("Test");
        Assert.assertEquals("Test", identityEntity.getGiven_name());

    }

    @Test
    public void setFamily_name() {
        identityEntity.setFamily_name("Test");
        Assert.assertEquals("Test", identityEntity.getFamily_name());

    }

    @Test
    public void setEmail() {
        identityEntity.setEmail("Test");
        Assert.assertEquals("Test", identityEntity.getEmail());

    }

    @Test
    public void setProvider() {
        identityEntity.setProvider("Test");
        Assert.assertEquals("Test", identityEntity.getProvider());

    }

    @Test
    public void setWebsite() {
        identityEntity.setWebsite("Test");
        Assert.assertEquals("Test", identityEntity.getWebsite());

    }

    @Test
    public void set__v() {
        identityEntity.set__v(27);
        Assert.assertEquals(27, identityEntity.get__v());

    }

    @Test
    public void setEmail_verified() {
        identityEntity.setEmail_verified(true);
        Assert.assertTrue(identityEntity.isEmail_verified());
    }

    @Test
    public void setMobile_number_verified() {
        identityEntity.setMobile_number_verified(true);
        Assert.assertTrue(identityEntity.isMobile_number_verified());


    }

    @Test
    public void setMobile_number_obj() {

        MobileNumberObject mobileNumberObject = new MobileNumberObject();
        mobileNumberObject.set_id("Test");
        identityEntity.setMobile_number_obj(mobileNumberObject);

        Assert.assertEquals("Test", identityEntity.getMobile_number_obj().get_id());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme