package de.cidaas.sdk.android.cidaasnative.ResetNewPassword;

import junit.framework.Assert;

import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ResetPasswordRequestEntity;

public class ResetPasswordRequestEntityTest {

    ResetPasswordRequestEntity resetPasswordRequestEntity = new ResetPasswordRequestEntity();


    @Test
    public void setEmail() {
        resetPasswordRequestEntity.setEmail("TestData");
        Assert.assertEquals("TestData", resetPasswordRequestEntity.getEmail());
    }

    @Test
    public void setPhoneNumber() {
        resetPasswordRequestEntity.setPhoneNumber("TestData");
        Assert.assertEquals("TestData", resetPasswordRequestEntity.getPhoneNumber());
    }

    @Test
    public void setProcessingType() {
        resetPasswordRequestEntity.setProcessingType("TestData");
        Assert.assertEquals("TestData", resetPasswordRequestEntity.getProcessingType());
    }

    @Test
    public void setRequestId() {
        resetPasswordRequestEntity.setRequestId("TestData");
        Assert.assertEquals("TestData", resetPasswordRequestEntity.getRequestId());
    }

    @Test
    public void setResetMedium() {
        resetPasswordRequestEntity.setResetMedium("TestData");
        Assert.assertEquals("TestData", resetPasswordRequestEntity.getResetMedium());
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme