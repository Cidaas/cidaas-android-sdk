package com.example.cidaasv2.Service.Register.RegisterUser;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class RegisterNewUserResponseEntityTest {
    @Mock
    RegisterNewUserDataEntity data;
    @InjectMocks
    RegisterNewUserResponseEntity registerNewUserResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme