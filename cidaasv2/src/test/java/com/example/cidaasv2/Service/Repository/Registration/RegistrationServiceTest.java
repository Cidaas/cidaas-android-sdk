/*
package com.example.cidaasv2.Service.Repository.Registration;

import android.content.Context;

import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserRequestEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupRequestEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegistrationServiceTest {

    Context context;
    RegistrationService registrationService;

    @Before
    public void setUp() {
        registrationService=new RegistrationService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        RegistrationService result = RegistrationService.getShared(null);
        Assert.assertEquals(new RegistrationService(null), result);
    }

    @Test
    public void testGetRegistrationSetup() throws Exception {


        registrationService.getRegistrationSetup("baseurl", new RegistrationSetupRequestEntity(), null);
    }

    @Test
    public void testRegisterNewUser() throws Exception {

        registrationService.registerNewUser("baseurl", new RegisterNewUserRequestEntity(), null);
    }

    @Test
    public void testInitiateAccountVerification() throws Exception {

        registrationService.initiateAccountVerification("baseurl", null, null);
    }

    @Test
    public void testVerifyAccountVerification() throws Exception {

        registrationService.verifyAccountVerification("baseurl", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme*/
