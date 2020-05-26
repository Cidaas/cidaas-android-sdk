package de.cidaas.sdk.android.cidaasnative.ResetNewPassword;

import junit.framework.Assert;

import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ResetNewPassword.ResetPasswordEntity;

public class ResetPasswordEntityTest {

    ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity();

    @Test
    public void setExchageID() {
        resetPasswordEntity.setExchangeId("ExchangeID");
        Assert.assertEquals("ExchangeID", resetPasswordEntity.getExchangeId());
    }

    @Test
    public void setResetId() {
        resetPasswordEntity.setResetRequestId("ResetId");
        Assert.assertEquals("ResetId", resetPasswordEntity.getResetRequestId());
    }

    @Test
    public void setPassord() {
        resetPasswordEntity.setPassword("Password");
        Assert.assertEquals("Password", resetPasswordEntity.getPassword());
    }

    @Test
    public void conformpassword() {
        resetPasswordEntity.setConfirmPassword("Passwordcnfrm");
        Assert.assertEquals("Passwordcnfrm", resetPasswordEntity.getConfirmPassword());
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme