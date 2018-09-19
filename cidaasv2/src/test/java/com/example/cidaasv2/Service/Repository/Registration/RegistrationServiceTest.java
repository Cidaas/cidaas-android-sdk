package com.example.cidaasv2.Service.Repository.Registration;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyResponseEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupRequestEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupResponseEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Dictionary;
import java.util.Hashtable;

@RunWith(RobolectricTestRunner.class)
public class RegistrationServiceTest {

    Context context;
    RegistrationService registrationService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        registrationService=new RegistrationService(context);
         DBHelper.setConfig(context);

        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();

        deviceInfoEntity.setDeviceId("DeviceID");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setPushNotificationId("PushNotificationId");

        Dictionary<String, String> savedProperties=new Hashtable<>();

        savedProperties.put("Verifier", "codeVerifier");
        savedProperties.put("Challenge","codeChallenge");
        savedProperties.put("Method", "ChallengeMethod");

        DBHelper.getShared().addChallengeProperties(savedProperties);

        DBHelper.getShared().addDeviceInfo(deviceInfoEntity);
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
    public void testGetRegistrationSetupnull() throws Exception {


        registrationService.getRegistrationSetup("", new RegistrationSetupRequestEntity(), new Result<RegistrationSetupResponseEntity>() {
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
    public void testRegisterNewUsernull() throws Exception {

        registrationService.registerNewUser("", new RegisterNewUserRequestEntity(), new Result<RegisterNewUserResponseEntity>() {
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

        registrationService.initiateAccountVerification("baseurl", new RegisterUserAccountInitiateRequestEntity(), new Result<RegisterUserAccountInitiateResponseEntity>() {
            @Override
            public void success(RegisterUserAccountInitiateResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testInitiateAccountVerificationnull() throws Exception {

        registrationService.initiateAccountVerification("", new RegisterUserAccountInitiateRequestEntity(), new Result<RegisterUserAccountInitiateResponseEntity>() {
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

        registrationService.verifyAccountVerification("baseurl", new RegisterUserAccountVerifyRequestEntity(), new Result<RegisterUserAccountVerifyResponseEntity>() {
            @Override
            public void success(RegisterUserAccountVerifyResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testVerifyAccountVerificationnull() throws Exception {

        registrationService.verifyAccountVerification("", new RegisterUserAccountVerifyRequestEntity(), new Result<RegisterUserAccountVerifyResponseEntity>() {
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
