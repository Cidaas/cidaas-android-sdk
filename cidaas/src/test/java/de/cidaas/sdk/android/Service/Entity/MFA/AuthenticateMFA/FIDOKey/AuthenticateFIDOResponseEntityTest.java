package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.FIDOKey;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.FIDOKey.AuthenticateFIDOResponseDataEntity;
import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.FIDOKey.AuthenticateFIDOResponseEntity;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateFIDOResponseEntityTest {

    AuthenticateFIDOResponseDataEntity data;

    AuthenticateFIDOResponseEntity authenticateFIDOResponseEntity;

    @Before
    public void setUp() {
        authenticateFIDOResponseEntity = new AuthenticateFIDOResponseEntity();
    }


    @Test
    public void getSuccess() {
        authenticateFIDOResponseEntity.setSuccess(true);
        assertTrue(authenticateFIDOResponseEntity.isSuccess());

    }

    @Test
    public void getStatus() {

        authenticateFIDOResponseEntity.setStatus(417);
        assertTrue(authenticateFIDOResponseEntity.getStatus() == 417);

    }

    @Test
    public void getData() {

        authenticateFIDOResponseEntity.setSuccess(true);
        authenticateFIDOResponseEntity.setData(data);
        Assert.assertEquals(authenticateFIDOResponseEntity.getData(), data);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme