package com.example.cidaasv2.Service.Repository.AccessToken;

import android.content.Context;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.util.AuthenticationAPI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Dictionary;
import java.util.Hashtable;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;



@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class AccessTokenServiceTest {
   Context context;
   AccessTokenService accessTokenService;
    AuthenticationAPI mockAPI;

    @Before
    public void setUp() throws Exception{

        context= RuntimeEnvironment.application;
        DBHelper.setConfig(context);

        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("PushNotificationId");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceId("DeviceId");
        DBHelper.getShared().addDeviceInfo(deviceInfoEntity);


        Dictionary<String,String> savedProperties=new Hashtable<>();
        savedProperties.put("Verifier", "codeVerifier");
        savedProperties.put("Challenge", "codeChallenge");
        savedProperties.put("Method", "codeChallengeMethod");
        DBHelper.getShared().addChallengeProperties(savedProperties);

        accessTokenService=new AccessTokenService(context);


        mockAPI=new AuthenticationAPI();
    }

    @Test
    public void testGetShared() throws Exception {
        AccessTokenService result = AccessTokenService.getShared(context);
       // Assert.assertEquals(new AccessTokenService(context), result);
        Assert.assertThat(new AccessTokenService(context), samePropertyValuesAs(result));
    }

    @Test
    public void testGetAccessTokenByCode() throws Exception {

        accessTokenService.getAccessTokenByCode("", "", new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testGetAccessTokenByCodeSucess() throws Exception {

        accessTokenService.getAccessTokenByCode("baseURL", "Code", new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }


    @Test
    public void testGetAccessTokenByIdAndSecret() throws Exception {
        accessTokenService.getAccessTokenByIdAndSecret("ClientId", "ClientSecret", new Result<AccessTokenEntity>() {
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

        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","DomainURL");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");




        accessTokenService.getAccessTokenByRefreshToken("refreshToken", loginproperties, new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    @Test
    public void testGetAccessTokenByRefreshTokenNUll() throws Exception {

        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL","DomainURL");
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");

        accessTokenService.getAccessTokenByRefreshToken("refreshToken", null, new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }


    @Test
    public void testGetAccessTokenByRefreshTokenSamp() throws Exception {

        Dictionary<String,String> loginproperties=new Hashtable<>();
        loginproperties.put("DomainURL",removeLastChar(mockAPI.getDomain()));
        loginproperties.put("ClientId","ClientId");
        loginproperties.put("RedirectURL","RedirectURL");

        try{



            mockAPI.getDomain();
            mockAPI.willReturnAccessToken();




            accessTokenService.getAccessTokenByRefreshToken("RefreshToken",loginproperties, new Result<AccessTokenEntity>() {
                @Override
                public void success(AccessTokenEntity result) {
                    Assert.assertTrue(true);
                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertTrue(false);
                }
            });
        }
        catch (Exception e){

        }
    }




}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme