package de.cidaas.sdk.android.Service.Entity.UserProfile;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.userprofile.UseprofileResponseDataEntity;
import de.cidaas.sdk.android.service.entity.userprofile.UserprofileResponseEntity;


public class UserprofileResponseEntityTest {


    UseprofileResponseDataEntity data;


    UserprofileResponseEntity userprofileResponseEntity;

    @Before
    public void setUp() {
        userprofileResponseEntity = new UserprofileResponseEntity();
    }

    @Test
    public void setSuccess() {
        userprofileResponseEntity.setSuccess(true);
        Assert.assertTrue(userprofileResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        userprofileResponseEntity.setStatus(27);
        Assert.assertEquals(27, userprofileResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new UseprofileResponseDataEntity();
        String[] groups = {"Test", "Test"};
        data.setGroups(groups);
        userprofileResponseEntity.setData(data);
        Assert.assertEquals("Test", userprofileResponseEntity.getData().getGroups()[0]);

    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme