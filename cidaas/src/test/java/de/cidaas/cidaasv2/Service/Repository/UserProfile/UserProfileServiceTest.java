package de.cidaas.cidaasv2.Service.Repository.UserProfile;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


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

        userProfileService.getInternalUserProfileInfo("baseurl", "AccessToken", "sub", null, new Result<UserprofileResponseEntity>() {
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
