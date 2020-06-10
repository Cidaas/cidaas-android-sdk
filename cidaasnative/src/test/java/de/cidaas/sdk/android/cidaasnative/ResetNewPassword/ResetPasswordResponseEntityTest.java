package de.cidaas.sdk.android.cidaasnative.ResetNewPassword;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.ResetPasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.ResetPasswordResultDataEntity;

public class ResetPasswordResponseEntityTest {

    ResetPasswordResultDataEntity data;

    ResetPasswordResponseEntity resetPasswordResponseEntity;

    @Before
    public void setUp() {
        resetPasswordResponseEntity = new ResetPasswordResponseEntity();
    }

    @Test
    public void setSuccess() {
        resetPasswordResponseEntity.setSuccess(true);
        Assert.assertTrue(resetPasswordResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        resetPasswordResponseEntity.setStatus(27);
        Assert.assertEquals(27, resetPasswordResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new ResetPasswordResultDataEntity();

        data.setRprq("Test");
        resetPasswordResponseEntity.setData(data);
        Assert.assertEquals("Test", resetPasswordResponseEntity.getData().getRprq());

    }


}


//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme