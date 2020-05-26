package de.cidaas.cidaasv2.Service.Entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class AccessTokenEntityTest {


    AccessTokenEntity accessTokenEntity;


    @Before
    public void setUp() {
        accessTokenEntity = new AccessTokenEntity();

    }

    @Test
    public void setAccess_token() {
        accessTokenEntity.setAccess_token("Test");
        Assert.assertEquals("Test", accessTokenEntity.getAccess_token());

    }

    @Test
    public void setUserstate() {
        accessTokenEntity.setUserstate("Test");
        Assert.assertEquals("Test", accessTokenEntity.getUserstate());
    }

    @Test
    public void setRefresh_token() {
        accessTokenEntity.setRefresh_token("Test");
        Assert.assertEquals("Test", accessTokenEntity.getRefresh_token());
    }

    @Test
    public void setId_token() {
        accessTokenEntity.setId_token("Test");
        Assert.assertEquals("Test", accessTokenEntity.getId_token());
    }

    @Test
    public void setScope() {
        accessTokenEntity.setScope("Test");
        Assert.assertEquals("Test", accessTokenEntity.getScope());
    }

    @Test
    public void setExpires_in() {
        accessTokenEntity.setExpires_in(27);
        Assert.assertEquals(27, accessTokenEntity.getExpires_in());
    }

    @Test
    public void setSub() {
        accessTokenEntity.setSub("Test");
        Assert.assertEquals("Test", accessTokenEntity.getSub());
    }

    @Test
    public void setToken_type() {
        accessTokenEntity.setToken_type("Test");
        Assert.assertEquals("Test", accessTokenEntity.getToken_type());
    }

    @Test
    public void setSession_state() {
        accessTokenEntity.setSession_state("Test");
        Assert.assertEquals("Test", accessTokenEntity.getSession_state());
    }

    @Test
    public void setViewtype() {
        accessTokenEntity.setViewtype("Test");
        Assert.assertEquals("Test", accessTokenEntity.getViewtype());
    }

    @Test
    public void setGrant_type() {
        accessTokenEntity.setGrant_type("Test");
        Assert.assertEquals("Test", accessTokenEntity.getGrant_type());
    }

    @Test
    public void setCode() {
        accessTokenEntity.setCode("Test");
        Assert.assertEquals("Test", accessTokenEntity.getCode());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme