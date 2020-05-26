package de.cidaas.sdk.android.cidaasnative.Register.RegisterUser;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegisterUser.RegisterNewUserRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegistrationEntity;

public class RegisterNewUserRequestEntityTest {

    RegistrationEntity registrationEntity;


    RegisterNewUserRequestEntity registerNewUserRequestEntity;

    @Before
    public void setUp() {

        registerNewUserRequestEntity = new RegisterNewUserRequestEntity();
    }

    @Test
    public void setRequestId() {
        registerNewUserRequestEntity.setRequestId("TestData");
        Assert.assertEquals("TestData", registerNewUserRequestEntity.getRequestId());
    }

    @Test
    public void setRegistrationEntity() {
        registrationEntity = new RegistrationEntity();
        registrationEntity.setGender("TestData");
        registerNewUserRequestEntity.setRegistrationEntity(registrationEntity);
        Assert.assertEquals("TestData", registerNewUserRequestEntity.getRegistrationEntity().getGender());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme