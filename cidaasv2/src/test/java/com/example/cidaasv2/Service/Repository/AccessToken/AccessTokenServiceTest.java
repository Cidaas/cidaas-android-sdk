package com.example.cidaasv2.Service.Repository.AccessToken;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;




public class AccessTokenServiceTest {
   Context context;
   AccessTokenService accessTokenService;

    @Before
    public void setUp() {
         accessTokenService=new AccessTokenService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        AccessTokenService result = AccessTokenService.getShared(context);
       // Assert.assertEquals(new AccessTokenService(context), result);
        Assert.assertThat(new AccessTokenService(context), samePropertyValuesAs(result));
    }

    @Test
    public void testGetAccessTokenByCode() throws Exception {
      //  PowerMockito.mockStatic(URLHelper.class);
      //  BDDMockito.given(URLHelper.contentType).willReturn("application/x-www-form-urlencoded");

        //when
        //sut.execute();

  //      when(urlHelper.getTokenUrl()).thenReturn("/token-srv/token");
    }

    @Test
    public void testGetAccessTokenByIdAndSecret() throws Exception {
        accessTokenService.getAccessTokenByIdAndSecret("ClientId", "ClientSecret", null);
    }

    @Test
    public void testGetAccessTokenByRefreshToken() throws Exception {

        accessTokenService.getAccessTokenByRefreshToken("refreshToken", null, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme