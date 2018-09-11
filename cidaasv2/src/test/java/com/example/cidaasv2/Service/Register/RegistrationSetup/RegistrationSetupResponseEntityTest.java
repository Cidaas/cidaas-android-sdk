package com.example.cidaasv2.Service.Register.RegistrationSetup;

import org.junit.Assert;
import org.junit.Test;

public class RegistrationSetupResponseEntityTest {
    //Field data of type RegistrationSetupResultDataEntity[] - was not mocked since Mockito doesn't mock arrays
    RegistrationSetupResponseEntity registrationSetupResponseEntity = new RegistrationSetupResponseEntity();

    @Test
    public void testGetData() throws Exception {
        RegistrationSetupResultDataEntity[] result = registrationSetupResponseEntity.getData();
//        Assert.assertArrayEquals(new RegistrationSetupResultDataEntity[]{new RegistrationSetupResultDataEntity()}, result);
        Assert.assertArrayEquals(null, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme