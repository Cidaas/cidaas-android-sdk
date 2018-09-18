package com.example.cidaasv2.Controller.Repository.AccessToken;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Models.DBModel.AccessTokenModel;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)

public class AccessTokenControllerTest {

    Context context;

    AccessTokenController shared;

    AccessTokenController accessTokenController;

    @Before
    public void setUp() {
        accessTokenController=new AccessTokenController(context);
        context= RuntimeEnvironment.application;
        DBHelper.setConfig(context);
        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("PushNotificationId");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceId("DeviceId");
        DBHelper.getShared().addDeviceInfo(deviceInfoEntity);
        WebAuthError webAuthError=new WebAuthError(context);

    }

    @Test
    public void testGetShared() throws Exception {
        AccessTokenController result = AccessTokenController.getShared(null);
        Assert.assertTrue( result instanceof AccessTokenController);
    }

    @Test
    public void testGetAccessTokenByCode() throws Exception {
        accessTokenController.getAccessTokenByCode("code", new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testGetAccessToken() throws Exception {
        DBHelper dbHelper=new DBHelper();
        accessTokenController=new AccessTokenController(context);

        AccessTokenModel accessTokenModel=new AccessTokenModel();
        accessTokenModel.setAccessToken("New Access Token");
        accessTokenModel.setUserId("userId");
        accessTokenModel.setEncrypted(true);
        accessTokenModel.setRefreshToken("RefreshToken");
        accessTokenModel.setPlainToken("PlainToken");

        dbHelper.setAccessToken(accessTokenModel);


        accessTokenController.getAccessToken("userId", new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testGetAccessTokenTImes() throws Exception {
        DBHelper dbHelper=new DBHelper();
        accessTokenController=new AccessTokenController(context);

        AccessTokenModel accessTokenModel=new AccessTokenModel();
        accessTokenModel.setAccessToken("New Access Token");
        accessTokenModel.setUserId("userId");
        accessTokenModel.setEncrypted(false);
        accessTokenModel.setRefreshToken("RefreshToken");
        accessTokenModel.setPlainToken("PlainToken");
        accessTokenModel.setExpiresIn(10);
        accessTokenModel.setSeconds((System.currentTimeMillis()/1000)+1000);

        dbHelper.setAccessToken(accessTokenModel);


        accessTokenController.getAccessToken("userId", new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testGetAccessTokenTImesFaliruew() throws Exception {
        DBHelper dbHelper=new DBHelper();
        accessTokenController=new AccessTokenController(context);

        AccessTokenModel accessTokenModel=new AccessTokenModel();
        accessTokenModel.setAccessToken("New Access Token");
        accessTokenModel.setUserId("userId");
        accessTokenModel.setEncrypted(true);
        accessTokenModel.setRefreshToken("RefreshToken");
        accessTokenModel.setPlainToken("PlainToken");
        accessTokenModel.setExpiresIn(10);
        accessTokenModel.setSeconds((System.currentTimeMillis()/1000)+1000);

        dbHelper.setAccessToken(accessTokenModel);


        accessTokenController.getAccessToken("userId", new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testNUllGetAccessToken() throws Exception {



        accessTokenController.getAccessToken("Sub", new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
    @Test
    public void testGetAccessTokenByRefreshToken() throws Exception {
        accessTokenController.getAccessTokenByRefreshToken("refreshToken", new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme