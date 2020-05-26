package de.cidaas.sdk.android.cidaasnative.Register;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Dictionary;
import java.util.Hashtable;

import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegisterUser.RegisterNewUserRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegisterUser.RegisterNewUserResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegistrationSetup.RegistrationSetupRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegistrationSetup.RegistrationSetupResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Service.Registration.RegistrationService;
import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;


@RunWith(RobolectricTestRunner.class)
public class RegistrationServiceTest {

    Context context;
    RegistrationService registrationService;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        registrationService = new RegistrationService(context);
        DBHelper.setConfig(context);

        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();

        deviceInfoEntity.setDeviceId("DeviceID");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setPushNotificationId("PushNotificationId");

        Dictionary<String, String> savedProperties = new Hashtable<>();

        savedProperties.put("Verifier", "codeVerifier");
        savedProperties.put("Challenge", "codeChallenge");
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


        registrationService.getRegistrationSetup("baseurl", new RegistrationSetupRequestEntity(), null, new EventResult<RegistrationSetupResponseEntity>() {
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


        registrationService.getRegistrationSetup("", new RegistrationSetupRequestEntity(), null, new EventResult<RegistrationSetupResponseEntity>() {
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

        registrationService.registerNewUser("baseurl", new RegisterNewUserRequestEntity(), new EventResult<RegisterNewUserResponseEntity>() {
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

        registrationService.registerNewUser("", new RegisterNewUserRequestEntity(), new EventResult<RegisterNewUserResponseEntity>() {
            @Override
            public void success(RegisterNewUserResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

}
