package com.example.cidaasv2.Service.Register.RegistrationSetup;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class RegistrationSetupResultDataEntityTest {
    @Mock
    RegistrationSetupFieldDefenition fieldDefinition;
    @Mock
    RegistrationSetupLocaleTextEntity localeText;
    @InjectMocks
    RegistrationSetupResultDataEntity registrationSetupResultDataEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme