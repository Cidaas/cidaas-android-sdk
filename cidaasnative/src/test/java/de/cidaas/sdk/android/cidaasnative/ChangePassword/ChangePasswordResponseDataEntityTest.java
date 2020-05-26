package de.cidaas.sdk.android.cidaasnative.ChangePassword;

import junit.framework.Assert;

import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ChangePassword.ChangePasswordResponseDataEntity;

public class ChangePasswordResponseDataEntityTest {

    @Test
    public void setSuccess() {

        ChangePasswordResponseDataEntity changePasswordResponseDataEntity = new ChangePasswordResponseDataEntity();
        changePasswordResponseDataEntity.setChanged(true);
        Assert.assertTrue(changePasswordResponseDataEntity.isChanged());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme