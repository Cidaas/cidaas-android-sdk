package de.cidaas.sdk.android.controller;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.models.dbmodel.AccessTokenModel;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;


@RunWith(RobolectricTestRunner.class)


public class AccessTokenControllerTest {

    Context context;

    AccessTokenController shared;

    AccessTokenController accessTokenController;

    @Before
    public void setUp() {
        accessTokenController = new AccessTokenController(context);
        context = RuntimeEnvironment.application;
        DBHelper.setConfig(context);
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("PushNotificationId");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceId("DeviceId");
        DBHelper.getShared().addDeviceInfo(deviceInfoEntity);
        WebAuthError webAuthError = new WebAuthError(context);

    }

    @Test
    public void testGetShared() throws Exception {
        AccessTokenController result = AccessTokenController.getShared(null);
        Assert.assertTrue(result instanceof AccessTokenController);
    }

    @Test
    public void testGetAccessTokenByCode() throws Exception {
        accessTokenController.getAccessTokenByCode("code", new EventResult<AccessTokenEntity>() {
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
        DBHelper dbHelper = new DBHelper();
        accessTokenController = new AccessTokenController(context);

        AccessTokenModel accessTokenModel = new AccessTokenModel();
        accessTokenModel.setAccess_token("New Access Token");
        accessTokenModel.setUserId("userId");
        accessTokenModel.setEncrypted(true);
        accessTokenModel.setRefresh_token("RefreshToken");
        accessTokenModel.setPlainToken("PlainToken");

        dbHelper.setAccessToken(accessTokenModel);


        accessTokenController.getAccessToken("userId", new EventResult<AccessTokenEntity>() {
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
        DBHelper dbHelper = new DBHelper();
        accessTokenController = new AccessTokenController(context);

        AccessTokenModel accessTokenModel = new AccessTokenModel();
        accessTokenModel.setAccess_token("New Access Token");
        accessTokenModel.setUserId("userId");
        accessTokenModel.setEncrypted(false);
        accessTokenModel.setRefresh_token("RefreshToken");
        accessTokenModel.setPlainToken("PlainToken");
        accessTokenModel.setExpires_in(10);
        accessTokenModel.setSeconds((System.currentTimeMillis() / 1000) + 1000);

        dbHelper.setAccessToken(accessTokenModel);


        accessTokenController.getAccessToken("userId", new EventResult<AccessTokenEntity>() {
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
        DBHelper dbHelper = new DBHelper();
        accessTokenController = new AccessTokenController(context);

        AccessTokenModel accessTokenModel = new AccessTokenModel();
        accessTokenModel.setAccess_token("New Access Token");
        accessTokenModel.setUserId("userId");
        accessTokenModel.setEncrypted(true);
        accessTokenModel.setRefresh_token("RefreshToken");
        accessTokenModel.setPlainToken("PlainToken");
        accessTokenModel.setExpires_in(10);
        accessTokenModel.setSeconds((System.currentTimeMillis() / 1000) + 1000);

        dbHelper.setAccessToken(accessTokenModel);


        accessTokenController.getAccessToken("userId", new EventResult<AccessTokenEntity>() {
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


        accessTokenController.getAccessToken("Sub", new EventResult<AccessTokenEntity>() {
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
        accessTokenController.getAccessTokenByRefreshToken("refreshToken", new EventResult<AccessTokenEntity>() {
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