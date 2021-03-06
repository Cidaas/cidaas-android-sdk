package de.cidaas.sdk.android.cidaasnative.Register;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import de.cidaas.sdk.android.cidaasnative.data.entity.register.registeruser.RegisterNewUserRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.register.registeruser.RegisterNewUserResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.register.registrationsetup.RegistrationSetupRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.register.registrationsetup.RegistrationSetupResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.controller.registration.RegistrationController;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;


@RunWith(RobolectricTestRunner.class)
@Ignore
public class RegistrationControllerTest {
    Context context;
    RegistrationController shared;
    RegistrationSetupResponseEntity validateRegistrationFilelds;
    RegistrationController registrationController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        registrationController = RegistrationController.getShared(context);
    }

    @Test
    public void testGetShared() throws Exception {
        RegistrationController result = RegistrationController.getShared(null);
        Assert.assertTrue(result instanceof RegistrationController);
    }

    @Test
    public void testGetRegisterationFields() throws Exception {
        registrationController.getRegisterationFields("baseurl", new RegistrationSetupRequestEntity(), new EventResult<RegistrationSetupResponseEntity>() {
            @Override
            public void success(RegistrationSetupResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testRegisterNewUser() throws Exception {

        registrationController.registerNewUser("baseurl", new RegisterNewUserRequestEntity(), new EventResult<RegisterNewUserResponseEntity>() {
            @Override
            public void success(RegisterNewUserResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testRegisterWithNewUserService() throws Exception {
        registrationController.registerWithNewUserService("baseurl", new RegisterNewUserRequestEntity(), new EventResult<RegisterNewUserResponseEntity>() {
            @Override
            public void success(RegisterNewUserResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme