package de.cidaas.sdk.android.cidaasnative.ChangePassword;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.changepassword.ChangePasswordResponseDataEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.changepassword.ChangePasswordResponseEntity;

public class ChangePasswordResponseEntityTest {

    ChangePasswordResponseDataEntity data;

    ChangePasswordResponseEntity changePasswordResponseEntity;

    @Before
    public void setUp() {

        changePasswordResponseEntity = new ChangePasswordResponseEntity();
    }

    @Test
    public void setSuccess() {
        changePasswordResponseEntity.setSuccess(true);
        Assert.assertTrue(changePasswordResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        changePasswordResponseEntity.setStatus(27);
        Assert.assertEquals(27, changePasswordResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new ChangePasswordResponseDataEntity();

        data.setChanged(true);
        changePasswordResponseEntity.setData(data);
        Assert.assertEquals(true, changePasswordResponseEntity.getData().isChanged());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme