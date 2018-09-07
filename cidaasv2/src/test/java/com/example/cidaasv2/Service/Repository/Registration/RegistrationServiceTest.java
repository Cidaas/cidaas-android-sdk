package com.example.cidaasv2.Service.Repository.Registration;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyResponseEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupRequestEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class RegistrationServiceTest {

    Context context;
    RegistrationService registrationService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        registrationService=new RegistrationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        RegistrationService result = RegistrationService.getShared(null);

        Assert.assertTrue(result instanceof RegistrationService);
    }

    @Test
    public void testGetRegistrationSetup() throws Exception {


        registrationService.getRegistrationSetup("baseurl", new RegistrationSetupRequestEntity(), new Result<RegistrationSetupResponseEntity>() {
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

        registrationService.registerNewUser("baseurl", new RegisterNewUserRequestEntity(), new Result<RegisterNewUserResponseEntity>() {
            @Override
            public void success(RegisterNewUserResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateAccountVerification() throws Exception {

        registrationService.initiateAccountVerification("baseurl", null, new Result<RegisterUserAccountInitiateResponseEntity>() {
            @Override
            public void success(RegisterUserAccountInitiateResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testVerifyAccountVerification() throws Exception {

        registrationService.verifyAccountVerification("baseurl", null, new Result<RegisterUserAccountVerifyResponseEntity>() {
            @Override
            public void success(RegisterUserAccountVerifyResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
