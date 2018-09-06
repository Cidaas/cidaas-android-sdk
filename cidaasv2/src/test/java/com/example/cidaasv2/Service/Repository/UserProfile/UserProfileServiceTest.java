/*
package com.example.cidaasv2.Service.Repository.UserProfile;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.UserProfile.UserprofileResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserProfileServiceTest {

    Context context;
    UserProfileService userProfileService;

    @Before
    public void setUp() {
     userProfileService=new UserProfileService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        UserProfileService result = UserProfileService.getShared(null);
        Assert.assertEquals(new UserProfileService(null), result);
    }

    @Test
    public void testGetInternalUserProfileInfo() throws Exception {

        userProfileService.getInternalUserProfileInfo("baseurl", "AccessToken", "sub", new Result<UserprofileResponseEntity>() {
            @Override
            public void success(UserprofileResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme*/
