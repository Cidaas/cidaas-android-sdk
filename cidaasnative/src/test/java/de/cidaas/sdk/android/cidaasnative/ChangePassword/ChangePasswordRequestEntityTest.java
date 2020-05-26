package de.cidaas.sdk.android.cidaasnative.ChangePassword;

import junit.framework.Assert;

import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;

public class ChangePasswordRequestEntityTest {


    ChangePasswordRequestEntity changePasswordRequestEntity = new ChangePasswordRequestEntity();

    @Test
    public void setSub() {
        changePasswordRequestEntity.setSub("Sub");
        Assert.assertEquals("Sub", changePasswordRequestEntity.getSub());
    }

    @Test
    public void setResetRequestId() {
        changePasswordRequestEntity.setIdentityId("IdentityId");
        Assert.assertEquals("IdentityId", changePasswordRequestEntity.getIdentityId());
    }

    @Test
    public void setNew_password() {
        changePasswordRequestEntity.setNew_password("Password");
        Assert.assertEquals("Password", changePasswordRequestEntity.getNew_password());
    }

    @Test
    public void setConfirmPassword() {
        changePasswordRequestEntity.setConfirm_password("Confirm Password");
        Assert.assertEquals("Confirm Password", changePasswordRequestEntity.getConfirm_password());
    }

    @Test
    public void setOld_password() {
        changePasswordRequestEntity.setOld_password("Old Password");
        Assert.assertEquals("Old Password", changePasswordRequestEntity.getOld_password());
    }

    @Test
    public void setAccess_token() {
        changePasswordRequestEntity.setAccess_token("Access_token");
        Assert.assertEquals("Access_token", changePasswordRequestEntity.getAccess_token());
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme