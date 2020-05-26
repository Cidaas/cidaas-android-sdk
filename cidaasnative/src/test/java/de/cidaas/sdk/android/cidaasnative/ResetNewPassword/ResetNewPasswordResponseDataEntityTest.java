package de.cidaas.sdk.android.cidaasnative.ResetNewPassword;

import junit.framework.Assert;

import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordResponseDataEntity;

public class ResetNewPasswordResponseDataEntityTest {

    ResetNewPasswordResponseDataEntity resetNewPasswordResponseDataEntity = new ResetNewPasswordResponseDataEntity();


    @Test
    public void setRest() {
        resetNewPasswordResponseDataEntity.setReseted(true);
        Assert.assertTrue(resetNewPasswordResponseDataEntity.isReseted());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme