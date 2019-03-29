package com.example.cidaasv2.Helper.Genral;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Models.DBModel.AccessTokenModel;
import com.example.cidaasv2.Models.DBModel.UserInfoModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Dictionary;
import java.util.Hashtable;


@RunWith(RobolectricTestRunner.class)
public class DBHelperRunnnerTest {



    private Context context;
    private DBHelper dBHelper=new DBHelper();
    @Before
    public void setUp() {

        context= RuntimeEnvironment.application;
        DBHelper.setConfig(context);

  }

    @Test
    public void testGetShared() throws Exception {
        DBHelper result = DBHelper.getShared();
        Assert.assertTrue(result instanceof DBHelper);
//        Assert.assertEquals(new DBHelper(), result);
    }

    @Test
    public void testSetEnablePKCE() throws Exception {
        dBHelper.setEnablePKCE(true);
    }

    @Test
    public void testGetEnablePKCE() throws Exception {
        boolean result = dBHelper.getEnablePKCE();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testSetEnableLog() throws Exception {
        dBHelper.setEnableLog(true);
    }

    @Test
    public void testGetEnableLog() throws Exception {
        boolean result = dBHelper.getEnableLog();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testSetConfig() throws Exception {
        DBHelper.setConfig(null);
    }

    @Test
    public void testAddChallengeProperties() throws Exception {
        dBHelper.addChallengeProperties(null);
    }

    @Test
    public void testAddSecret() throws Exception {
        dBHelper.addSecret("secret", "sub");
    }

    @Test
    public void testGetSecret() throws Exception {
        String result = dBHelper.getSecret("sub");
        Assert.assertEquals("secret", result);
    }

    @Test
    public void testGetChallengeProperties() throws Exception {
        Dictionary<String, String> savedProperties=new Hashtable<>();

        savedProperties.put("Verifier", "codeVerifier");
        savedProperties.put("Challenge","codeChallenge");
        savedProperties.put("Method", "ChallengeMethod");

        dBHelper.addChallengeProperties(savedProperties);

        Dictionary<String, String> result = dBHelper.getChallengeProperties();
        Assert.assertEquals("codeVerifier", result.get("Verifier"));
        Assert.assertEquals("codeChallenge", result.get("Challenge"));
        Assert.assertEquals("ChallengeMethod", result.get("Method"));
    }

    @Test
    public void testAddLoginProperties() throws Exception {
        //context= RuntimeEnvironment.application;
         DBHelper.preferences=null;

        dBHelper.addLoginProperties(null);
    }

    @Test
    public void testGetLoginProperties() throws Exception {
        Dictionary<String, String> loginProperties=new Hashtable<>();
        loginProperties.put("DomainURL","DomainURL");
        dBHelper.addLoginProperties(loginProperties);
        Dictionary<String, String> result = dBHelper.getLoginProperties("DomainURL");
        Assert.assertEquals("DomainURL", result.get("DomainURL"));
    }

    @Test
    public void testAddDeviceInfo() throws Exception {
        dBHelper.addDeviceInfo(new DeviceInfoEntity());
    }

    @Test
    public void testGetDeviceInfo() throws Exception {
        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();

        deviceInfoEntity.setDeviceId("DeviceID");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setPushNotificationId("PushNotificationId");

        dBHelper.addDeviceInfo(deviceInfoEntity);

        DeviceInfoEntity result = dBHelper.getDeviceInfo();
        Assert.assertEquals("DeviceID", result.getDeviceId());
        Assert.assertEquals("DeviceVersion", result.getDeviceVersion());
        Assert.assertEquals("DeviceModel", result.getDeviceModel());
        Assert.assertEquals("DeviceMake", result.getDeviceMake());
        Assert.assertEquals("PushNotificationId", result.getPushNotificationId());
    }

    @Test
    public void testGetDeviceInfoNUll() throws Exception {
        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();

        deviceInfoEntity.setDeviceId("DeviceID");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setPushNotificationId("PushNotificationId");
      //  DBHelper.preferences=null;

        dBHelper.addDeviceInfo(deviceInfoEntity);

        DeviceInfoEntity result = dBHelper.getDeviceInfo();
        Assert.assertEquals("DeviceID", result.getDeviceId());
        Assert.assertEquals("DeviceVersion", result.getDeviceVersion());
        Assert.assertEquals("DeviceModel", result.getDeviceModel());
        Assert.assertEquals("DeviceMake", result.getDeviceMake());
        Assert.assertEquals("PushNotificationId", result.getPushNotificationId());
    }

    @Test
    public void testSetFCMToken() throws Exception {
        dBHelper.setFCMToken("fcmToken");
    }

    @Test
    public void testGetFCMToken() throws Exception {
        String result = dBHelper.getFCMToken();
        Assert.assertEquals("fcmToken", result);
    }

    @Test
    public void testSetUserDeviceId() throws Exception {
        dBHelper.setUserDeviceId("userDeviceId", "DomainURL");
    }

    @Test
    public void testGetUserDeviceId() throws Exception {
        dBHelper.setUserDeviceId("userDeviceId", "DomainURL");
        String result = dBHelper.getUserDeviceId("DomainURL");

        Assert.assertEquals("userDeviceId", result);
    }

    @Test
    public void testSetAccessToken() throws Exception {
        dBHelper.setAccessToken(new AccessTokenModel());
    }

    @Test
    public void testGetAccessToken() throws Exception {
        AccessTokenModel accessTokenModel=new AccessTokenModel();
        accessTokenModel.setAccess_token("New Access Token");
        accessTokenModel.setUserId("userId");
        accessTokenModel.setEncrypted(true);
        accessTokenModel.setRefresh_token("RefreshToken");
        accessTokenModel.setPlainToken("PlainToken");

        dBHelper.setAccessToken(accessTokenModel);

        AccessTokenModel result = dBHelper.getAccessToken("userId");
        Assert.assertEquals("New Access Token", result.getAccess_token());
        Assert.assertEquals("userId", result.getUserId());
        Assert.assertEquals("RefreshToken", result.getRefresh_token());
        Assert.assertEquals("PlainToken", result.getPlainToken());
        Assert.assertEquals(true, result.isEncrypted());
    }

    @Test
    public void testSetUserInfo() throws Exception {
        dBHelper.setUserInfo(new UserInfoModel());
    }


    @Test
    public void testSetUserInfoeex() throws Exception {

        dBHelper.getAccessToken("");
        UserInfoModel userInfoModel=new UserInfoModel();
        userInfoModel.setDisplayname("");
        userInfoModel.setEmail("");

        dBHelper.setUserInfo(userInfoModel);
    }

    @Test
    public void testGetUserInfo() throws Exception {
        UserInfoModel userInfoModel=new UserInfoModel();
        userInfoModel.setDisplayname("Raja");
        userInfoModel.setUserid("27");
        userInfoModel.setEmail("rajanarayanan27@gmail.com");

        dBHelper.setUserInfo(userInfoModel);
        UserInfoModel result = dBHelper.getUserInfo("27");
        Assert.assertEquals("Raja", result.getDisplayname());
        Assert.assertEquals("rajanarayanan27@gmail.com", result.getEmail());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme