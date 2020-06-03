package de.cidaas.sdk.android.cidaasnative.Deduplication.RegisterDeduplication;

import junit.framework.Assert;

import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.entity.deduplication.registerdeduplication.RegisterDeduplicationDataEntity;

public class RegisterDeduplicationDataEntityTest {


    RegisterDeduplicationDataEntity registerDeduplicationDataEntity = new RegisterDeduplicationDataEntity();

    @Test
    public void setSub() {
        registerDeduplicationDataEntity.setSub("Sub");
        Assert.assertEquals("Sub", registerDeduplicationDataEntity.getSub());
    }

    @Test
    public void setUserStatus() {
        registerDeduplicationDataEntity.setUserStatus("Test");
        Assert.assertEquals("Test", registerDeduplicationDataEntity.getUserStatus());
    }

    @Test
    public void setEmail_verified() {
        registerDeduplicationDataEntity.setEmail_verified(true);
        Assert.assertEquals(true, registerDeduplicationDataEntity.isEmail_verified());
    }

    @Test
    public void setSuggested_action() {
        registerDeduplicationDataEntity.setSuggested_action("Test");
        Assert.assertEquals("Test", registerDeduplicationDataEntity.getSuggested_action());
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme