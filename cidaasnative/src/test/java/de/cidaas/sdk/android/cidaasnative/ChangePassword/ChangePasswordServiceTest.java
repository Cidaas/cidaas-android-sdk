package de.cidaas.sdk.android.cidaasnative.ChangePassword;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Dictionary;
import java.util.Hashtable;

import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Service.ChangePassword.ChangePasswordService;
import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.service.CidaassdkService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

@RunWith(RobolectricTestRunner.class)
public class ChangePasswordServiceTest {
    @Mock
    CidaassdkService service;
    @Mock
    ObjectMapper objectMapper;

    Context context;
    @Mock
    ChangePasswordService shared;

    ChangePasswordService changePasswordService;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;

        changePasswordService = ChangePasswordService.getShared(context);
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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetShared() throws Exception {
        ChangePasswordService result = ChangePasswordService.getShared(context);
        Assert.assertTrue(result instanceof ChangePasswordService);
    }

    @Test
    public void testChangePassword() throws Exception {
        //    when(service.getInstance()).thenReturn(null);

        ChangePasswordRequestEntity changePasswordRequestEntity = new ChangePasswordRequestEntity();
        changePasswordRequestEntity.setAccess_token("Access_Token");
        changePasswordRequestEntity.setOld_password("Old_Password");
        changePasswordRequestEntity.setConfirm_password("Password");
        changePasswordRequestEntity.setSub("Sub");

        changePasswordService.changePassword(changePasswordRequestEntity, "baseurl", null, new EventResult<ChangePasswordResponseEntity>() {
            @Override
            public void success(ChangePasswordResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testChangePasswordnull() throws Exception {
        //    when(service.getInstance()).thenReturn(null);

        changePasswordService.changePassword(new ChangePasswordRequestEntity(), "", null, new EventResult<ChangePasswordResponseEntity>() {
            @Override
            public void success(ChangePasswordResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testGetClientInfoFail() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        ChangePasswordRequestEntity changePasswordRequestEntity = new ChangePasswordRequestEntity();
        changePasswordRequestEntity.setSub("Sub");
        changePasswordRequestEntity.setIdentityId("Id");
        changePasswordRequestEntity.setNew_password("Pass");
        changePasswordRequestEntity.setConfirm_password("Pass");
        changePasswordRequestEntity.setOld_password("Pass");
        changePasswordRequestEntity.setAccess_token("Access");


        changePasswordService.changePassword(changePasswordRequestEntity, "localhost:234235", null, new EventResult<ChangePasswordResponseEntity>() {
            @Override
            public void success(ChangePasswordResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e("Success");
            }
        });


    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme