package de.cidaas.sdk.android.cidaasnative.Register.RegistrationSetup;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.entity.register.registrationsetup.RegistrationSetupErrorData;
import de.cidaas.sdk.android.cidaasnative.data.entity.register.registrationsetup.RegistrationSetupErrorEntity;

public class RegistrationSetupErrorEntityTest {

    RegistrationSetupErrorData error;

    RegistrationSetupErrorEntity registrationSetupErrorEntity;

    @Before
    public void setUp() {
        registrationSetupErrorEntity = new RegistrationSetupErrorEntity();

    }


    @Test
    public void setSuccess() {
        registrationSetupErrorEntity.setSuccess(true);
        Assert.assertTrue(registrationSetupErrorEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        registrationSetupErrorEntity.setStatus(27);
        Assert.assertEquals(27, registrationSetupErrorEntity.getStatus());

    }

    @Test
    public void setData() {
        error = new RegistrationSetupErrorData();

        error.setError("Code");
        registrationSetupErrorEntity.setError(error);
        Assert.assertEquals("Code", registrationSetupErrorEntity.getError().getError());

    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme