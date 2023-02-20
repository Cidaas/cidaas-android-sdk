package de.cidaas.sdk.android.cidaasnative;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.entity.login.LoginEntity;

public class LoginEntityTest {

    LoginEntity loginEntity = new LoginEntity();

    @Test
    public void setUsername() {
        loginEntity.setUsername("UserName");
        Assert.assertEquals("UserName", loginEntity.getUsername());
    }

    @Test
    public void setUsernameType() {
        loginEntity.setUsername_type("UserNameType");
        Assert.assertEquals("UserNameType", loginEntity.getUsername_type());
    }

    @Test
    public void setPassword() {
        loginEntity.setPassword("");
        Assert.assertEquals("", loginEntity.getPassword());
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme