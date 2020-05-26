package de.cidaas.sdk.android.Service.Repository.UserProfile;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.service.entity.userprofile.UserprofileResponseEntity;
import de.cidaas.sdk.android.service.repository.UserProfile.UserProfileService;


@RunWith(RobolectricTestRunner.class)
public class UserProfileServiceTest {

    Context context;
    UserProfileService userProfileService;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        userProfileService = new UserProfileService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        UserProfileService result = UserProfileService.getShared(null);

        Assert.assertTrue(result instanceof UserProfileService);
    }

    @Test
    public void testGetInternalUserProfileInfo() throws Exception {

        userProfileService.getInternalUserProfileInfo("baseurl", "AccessToken", "sub", new EventResult<UserprofileResponseEntity>() {
            @Override
            public void success(UserprofileResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
