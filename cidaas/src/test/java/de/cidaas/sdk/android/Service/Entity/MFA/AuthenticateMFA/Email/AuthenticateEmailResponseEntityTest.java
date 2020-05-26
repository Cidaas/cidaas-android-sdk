package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.Email;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.Email.AuthenticateEmailResponseDataEntity;
import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.Email.AuthenticateEmailResponseEntity;

import static org.junit.Assert.assertTrue;


public class AuthenticateEmailResponseEntityTest {

    AuthenticateEmailResponseDataEntity data;

    AuthenticateEmailResponseEntity authenticateEmailResponseEntity = new AuthenticateEmailResponseEntity();

    @Before
    public void setUp() {

    }


    @Test
    public void getSuccess() {
        authenticateEmailResponseEntity.setSuccess(true);
        assertTrue(authenticateEmailResponseEntity.isSuccess());

    }

    @Test
    public void getStatus() {

        authenticateEmailResponseEntity.setStatus(417);
        assertTrue(authenticateEmailResponseEntity.getStatus() == 417);

    }

    @Test
    public void getData() {

        authenticateEmailResponseEntity.setSuccess(true);
        authenticateEmailResponseEntity.setData(data);
        Assert.assertEquals(authenticateEmailResponseEntity.getData(), data);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme