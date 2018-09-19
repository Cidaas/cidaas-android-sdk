package com.example.cidaasv2.Service.Repository.ChangePassword;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Dictionary;
import java.util.Hashtable;

import static org.mockito.Mockito.*;

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
        context= RuntimeEnvironment.application;
        DBHelper.setConfig(context);
        changePasswordService= ChangePasswordService.getShared(context);

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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetShared() throws Exception {
        ChangePasswordService result = ChangePasswordService.getShared(context);
        Assert.assertTrue( result instanceof ChangePasswordService);
    }

    @Test
    public void testChangePassword() throws Exception {
    //    when(service.getInstance()).thenReturn(null);

        ChangePasswordRequestEntity changePasswordRequestEntity=new ChangePasswordRequestEntity();
        changePasswordRequestEntity.setAccess_token("Access_Token");
        changePasswordRequestEntity.setOld_password("Old_Password");
        changePasswordRequestEntity.setConfirm_password("Password");
        changePasswordRequestEntity.setSub("Sub");

        changePasswordService.changePassword(changePasswordRequestEntity, "baseurl", new Result<ChangePasswordResponseEntity>() {
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

        changePasswordService.changePassword(new ChangePasswordRequestEntity(), "", new Result<ChangePasswordResponseEntity>() {
            @Override
            public void success(ChangePasswordResponseEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme