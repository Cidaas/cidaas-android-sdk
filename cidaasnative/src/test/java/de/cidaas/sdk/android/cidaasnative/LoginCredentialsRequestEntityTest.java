package de.cidaas.sdk.android.cidaasnative;

import junit.framework.Assert;

import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.Entity.Login.LoginCredentialsRequestEntity;

public class LoginCredentialsRequestEntityTest {


    LoginCredentialsRequestEntity loginCredentialsRequestEntity = new LoginCredentialsRequestEntity();

    @Test
    public void setUsername() {
        loginCredentialsRequestEntity.setUsername("Username");
        Assert.assertEquals("Username", loginCredentialsRequestEntity.getUsername());
    }

    @Test
    public void setPassword() {
        loginCredentialsRequestEntity.setPassword("Password");
        Assert.assertEquals("Password", loginCredentialsRequestEntity.getPassword());
    }

    @Test
    public void setUsername_type() {
        loginCredentialsRequestEntity.setUsername_type("Usernametype");
        Assert.assertEquals("Usernametype", loginCredentialsRequestEntity.getUsername_type());
    }

    @Test
    public void setRequestId() {
        loginCredentialsRequestEntity.setRequestId("RequestId");
        Assert.assertEquals("RequestId", loginCredentialsRequestEntity.getRequestId());
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme