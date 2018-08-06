package com.example.cidaasv2.Controller;

import android.app.Activity;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Loaders.ICustomLoader;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Dictionary;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class CidaasTest {
    @Mock
    Activity activity;
    @Mock
    ICustomLoader loader;
    @Mock
    WebAuthError webAuthError;
    @Mock
    Result<AccessTokenEntity> logincallback;
    @Mock
    Dictionary<String, String> savedProperties;
    @Mock
    HashMap<String, String> extraParams;
    @Mock
    Cidaas cidaasInstance;
    @Mock
    DeviceInfoEntity deviceInfoEntity;
    @InjectMocks
    Cidaas cidaas;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetInstance() throws Exception {
        Cidaas result = Cidaas.getInstance(null);
        Assert.assertEquals(new Cidaas(null), result);
    }

    @Test
    public void testIsENABLE_PKCE() throws Exception {
        boolean result = cidaas.isENABLE_PKCE();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testSetENABLE_PKCE() throws Exception {
        cidaas.setENABLE_PKCE(true);
    }

    @Test
    public void testLoginWithCredentials3() throws Exception {
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.loginWithCredentials("username", "usernametype", "password", "requestId", null);
    }

    @Test
    public void testGetTenantInfo() throws Exception {
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.getTenantInfo(null);
    }

    @Test
    public void testGetClientInfo() throws Exception {
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.getClientInfo("RequestId", null);
    }

    @Test
    public void testInitiateresetPassword() throws Exception {
        when(webAuthError.propertyMissingException()).thenReturn(new WebAuthError(null));

        cidaas.initiateresetPassword(null, null);
    }

    @Test
    public void testLoginWithBrowser() throws Exception {
        cidaas.loginWithBrowser("color", null);
    }


    @Test
    public void testGetAccessTokenByCode() throws Exception {
        cidaas.getAccessTokenByCode("code", null);
    }

    @Test
    public void testGetAccessToken() throws Exception {
        when(webAuthError.noUserFoundException()).thenReturn(new WebAuthError(null));

        cidaas.getAccessToken("userid", null);
    }

    @Test
    public void testGetAccessTokenByRefreshToken() throws Exception {
        cidaas.getAccessTokenByRefreshToken("refreshToken", null);
    }

    @Test
    public void testGetUserInfo() throws Exception {
        cidaas.getUserInfo("access_token", null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme