package de.cidaas.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword;

import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeDataEntity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ResetNewPasswordResponseEntityTest {

    ResetNewPasswordResponseDataEntity data;

    ResetNewPasswordResponseEntity resetNewPasswordResponseEntity;

    @Before
    public void setUp() {
        resetNewPasswordResponseEntity = new ResetNewPasswordResponseEntity();
    }

    @Test
    public void setSuccess() {
        resetNewPasswordResponseEntity.setSuccess(true);
        Assert.assertTrue(resetNewPasswordResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        resetNewPasswordResponseEntity.setStatus(27);
        Assert.assertEquals(27, resetNewPasswordResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new ResetNewPasswordResponseDataEntity();

        data.setReseted(true);
        resetNewPasswordResponseEntity.setData(data);
        Assert.assertTrue(resetNewPasswordResponseEntity.getData().isReseted());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme