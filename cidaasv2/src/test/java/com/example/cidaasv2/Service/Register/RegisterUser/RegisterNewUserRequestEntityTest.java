package com.example.cidaasv2.Service.Register.RegisterUser;

import com.example.cidaasv2.Helper.Entity.RegistrationEntity;
import com.example.cidaasv2.R;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class RegisterNewUserRequestEntityTest {

    RegistrationEntity registrationEntity;
    
    
    RegisterNewUserRequestEntity registerNewUserRequestEntity;

    @Before
    public void setUp() {
        
        registerNewUserRequestEntity=new RegisterNewUserRequestEntity();
    }

    @Test
    public void setRequestId()
    {
        registerNewUserRequestEntity.setRequestId("TestData");
        Assert.assertEquals("TestData",registerNewUserRequestEntity.getRequestId());
    }

    @Test
    public void setRegistrationEntity()
    {
        registrationEntity=new RegistrationEntity();
        registrationEntity.setGender("TestData");
        registerNewUserRequestEntity.setRegistrationEntity(registrationEntity);
        Assert.assertEquals("TestData",registerNewUserRequestEntity.getRegistrationEntity().getGender());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme