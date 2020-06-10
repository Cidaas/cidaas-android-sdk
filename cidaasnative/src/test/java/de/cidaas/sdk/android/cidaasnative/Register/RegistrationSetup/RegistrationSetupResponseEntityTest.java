package de.cidaas.sdk.android.cidaasnative.Register.RegistrationSetup;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.entity.register.registrationsetup.RegistrationSetupResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.register.registrationsetup.RegistrationSetupResultDataEntity;

public class RegistrationSetupResponseEntityTest {
    //Field data of type RegistrationSetupResultDataEntity[] - was not mocked since Mockito doesn't mock arrays
    RegistrationSetupResponseEntity registrationSetupResponseEntity = new RegistrationSetupResponseEntity();


    @Test
    public void setSuccess() {
        registrationSetupResponseEntity.setSuccess(true);
        junit.framework.Assert.assertTrue(registrationSetupResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        registrationSetupResponseEntity.setStatus(27);
        junit.framework.Assert.assertEquals(27, registrationSetupResponseEntity.getStatus());

    }


    @Test
    public void testGetData() throws Exception {
        RegistrationSetupResultDataEntity registrationSetupResultDataEntity = new RegistrationSetupResultDataEntity();
        registrationSetupResultDataEntity.setFieldKey("Code");

        RegistrationSetupResultDataEntity[] result = {registrationSetupResultDataEntity};
        registrationSetupResponseEntity.setData(result);
//        Assert.assertArrayEquals(new RegistrationSetupResultDataEntity[]{new RegistrationSetupResultDataEntity()}, result);
        Assert.assertEquals("Code", registrationSetupResponseEntity.getData()[0].getFieldKey());
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme