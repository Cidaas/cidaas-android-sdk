package de.cidaas.cidaasv2.Service.Entity.UserProfile;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.UserProfile.UserAccountEntity;

public class UserAccountEntityTest {

    UserAccountEntity userAccountEntity;

    @Before
    public void setUp() {
        userAccountEntity = new UserAccountEntity();
    }

    @Test
    public void set_id() {
        userAccountEntity.set_id("Test");
        Assert.assertEquals("Test", userAccountEntity.get_id());

    }

    @Test
    public void setUpdatedTime() {
        userAccountEntity.setUpdatedTime("Test");
        Assert.assertEquals("Test", userAccountEntity.getUpdatedTime());

    }

    @Test
    public void setCreatedTime() {
        userAccountEntity.setCreatedTime("Test");
        Assert.assertEquals("Test", userAccountEntity.getCreatedTime());

    }

    @Test
    public void setClassName() {
        userAccountEntity.setClassName("Test");
        Assert.assertEquals("Test", userAccountEntity.getClassName());

    }

    @Test
    public void setUserStatus() {
        userAccountEntity.setUserStatus("Test");
        Assert.assertEquals("Test", userAccountEntity.getUserStatus());

    }

    @Test
    public void setSub() {
        userAccountEntity.setSub("Test");
        Assert.assertEquals("Test", userAccountEntity.getSub());

    }

    @Test
    public void setLastLoggedInTime() {
        userAccountEntity.setLastLoggedInTime("Test");
        Assert.assertEquals("Test", userAccountEntity.getLastLoggedInTime());

    }

    @Test
    public void setLastUsedIdentity() {
        userAccountEntity.setLastUsedIdentity("Test");
        Assert.assertEquals("Test", userAccountEntity.getLastUsedIdentity());
    }

    @Test
    public void set__v() {
        userAccountEntity.set__v(27);
        Assert.assertEquals(27, userAccountEntity.get__v());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme