package com.example.cidaasv2.Service.Register.RegisterUserAccountVerification;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class RegisterUserAccountInitiateResponseEntityTest {
    @Mock
    RegisterUserAccountInitiateResponseDataEntity data;
    @InjectMocks
    RegisterUserAccountInitiateResponseEntity registerUserAccountInitiateResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme