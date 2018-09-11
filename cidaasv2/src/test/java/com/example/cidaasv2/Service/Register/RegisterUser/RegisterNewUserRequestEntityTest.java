package com.example.cidaasv2.Service.Register.RegisterUser;

import com.example.cidaasv2.Helper.Entity.RegistrationEntity;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class RegisterNewUserRequestEntityTest {
    @Mock
    RegistrationEntity registrationEntity;
    @InjectMocks
    RegisterNewUserRequestEntity registerNewUserRequestEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme