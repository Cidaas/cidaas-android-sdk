package com.example.cidaasv2.Service.Register.RegistrationSetup;

import org.junit.Assert;
import org.junit.Test;

public class RegistrationSetupLocaleTextEntityTest {
    //Field attributes of type RegistrationSetupAttributesEntity[] - was not mocked since Mockito doesn't mock arrays
    RegistrationSetupLocaleTextEntity registrationSetupLocaleTextEntity = new RegistrationSetupLocaleTextEntity();

    @Test
    public void testGetAttributes() throws Exception {
        RegistrationSetupAttributesEntity[] result = registrationSetupLocaleTextEntity.getAttributes();
//        Assert.assertArrayEquals(new RegistrationSetupAttributesEntity[]{new RegistrationSetupAttributesEntity()}, result);
        Assert.assertArrayEquals(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme