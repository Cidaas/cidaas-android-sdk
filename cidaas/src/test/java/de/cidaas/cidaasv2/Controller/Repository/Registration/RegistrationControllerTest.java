package de.cidaas.cidaasv2.Controller.Repository.Registration;

import android.content.Context;

import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupRequestEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


@RunWith(RobolectricTestRunner.class)

public class RegistrationControllerTest {
    Context context;
    RegistrationController shared;
    RegistrationSetupResponseEntity validateRegistrationFilelds;
    RegistrationController registrationController;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        registrationController = new RegistrationController(context);
    }

    @Test
    public void testGetShared() throws Exception {
        RegistrationController result = RegistrationController.getShared(null);
        Assert.assertTrue(result instanceof RegistrationController);
    }

    @Test
    public void testGetRegisterationFields() throws Exception {
        registrationController.getRegisterationFields("baseurl", new RegistrationSetupRequestEntity(), new Result<RegistrationSetupResponseEntity>() {
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

        registrationController.registerNewUser("baseurl", new RegisterNewUserRequestEntity(), new Result<RegisterNewUserResponseEntity>() {
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
        registrationController.registerWithNewUserService("baseurl", new RegisterNewUserRequestEntity(), new Result<RegisterNewUserResponseEntity>() {
            @Override
            public void success(RegisterNewUserResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateAccountVerificationService() throws Exception {
        registrationController.initiateAccountVerificationService("baseurl", new RegisterUserAccountInitiateRequestEntity(), new Result<RegisterUserAccountInitiateResponseEntity>() {
            @Override
            public void success(RegisterUserAccountInitiateResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testVerifyAccountVerificationService() throws Exception {
        registrationController.verifyAccountVerificationService("baseurl", "code", "accvid", null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme