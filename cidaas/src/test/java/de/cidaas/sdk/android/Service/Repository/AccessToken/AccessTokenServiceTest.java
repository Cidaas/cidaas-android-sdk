package de.cidaas.sdk.android.Service.Repository.AccessToken;

import android.content.Context;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Matchers;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Dictionary;
import java.util.Hashtable;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;
import de.cidaas.sdk.android.service.repository.AccessToken.AccessTokenService;
import de.cidaas.sdk.android.util.AuthenticationAPI;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;


//@RunWith(RobolectricTestRunner.class)
@Ignore
public class AccessTokenServiceTest {
    Context context;
    AccessTokenService accessTokenService;
    AuthenticationAPI mockAPI;

    @Before
    public void setUp() throws Exception {

        context = RuntimeEnvironment.application;
        DBHelper.setConfig(context);

        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("PushNotificationId");
        deviceInfoEntity.setDeviceMake("DeviceMake");
        deviceInfoEntity.setDeviceModel("DeviceModel");
        deviceInfoEntity.setDeviceVersion("DeviceVersion");
        deviceInfoEntity.setDeviceId("DeviceId");
        DBHelper.getShared().addDeviceInfo(deviceInfoEntity);


        Dictionary<String, String> savedProperties = new Hashtable<>();
        savedProperties.put("Verifier", "codeVerifier");
        savedProperties.put("Challenge", "codeChallenge");
        savedProperties.put("Method", "codeChallengeMethod");
        DBHelper.getShared().addChallengeProperties(savedProperties);

        accessTokenService = new AccessTokenService(context);


        mockAPI = new AuthenticationAPI();
    }

    @Test
    public void testGetShared() throws Exception {
        AccessTokenService result = AccessTokenService.getShared(context);
        // Assert.assertEquals(new AccessTokenService(context), result);
        //MatcherAssert.assertThat(new AccessTokenService(context), );
        Assert.assertThat(new AccessTokenService(context), samePropertyValuesAs(result));
    }

    @Test
    public void testGetAccessTokenByCode() throws Exception {

        accessTokenService.getAccessTokenByCode("", "", new EventResult<AccessTokenEntity>() {
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

        accessTokenService.getAccessTokenByCode("baseURL", "Code", new EventResult<AccessTokenEntity>() {
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
        accessTokenService.getAccessTokenByIdAndSecret("ClientId", "ClientSecret", new EventResult<AccessTokenEntity>() {
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

        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "DomainURL");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        accessTokenService.getAccessTokenByRefreshToken("refreshToken", loginproperties, new EventResult<AccessTokenEntity>() {
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

        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "DomainURL");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");

        accessTokenService.getAccessTokenByRefreshToken("refreshToken", null, new EventResult<AccessTokenEntity>() {
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

        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", removeLastChar(mockAPI.getDomain()));
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");

        try {


            mockAPI.getDomain();
            mockAPI.willReturnAccessToken();


            accessTokenService.getAccessTokenByRefreshToken("RefreshToken", loginproperties, new EventResult<AccessTokenEntity>() {
                @Override
                public void success(AccessTokenEntity result) {
                    Assert.assertTrue(true);
                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertTrue(false);
                }
            });
        } catch (Exception e) {

        }
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


        accessTokenService.getAccessTokenByRefreshToken("localhost:234235", loginproperties, new EventResult<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e("Success");
            }
        });


    }

    @Test
    public void testGetClientInfoF() throws Exception {


        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;

        Dictionary<String, String> loginproperties = new Hashtable<>();
        loginproperties.put("DomainURL", "localhost:234235");
        loginproperties.put("ClientId", "ClientId");
        loginproperties.put("RedirectURL", "RedirectURL");


        accessTokenService.getAccessTokenByCode("localhost:234235", "code", new EventResult<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e("Success");
            }
        });


    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme