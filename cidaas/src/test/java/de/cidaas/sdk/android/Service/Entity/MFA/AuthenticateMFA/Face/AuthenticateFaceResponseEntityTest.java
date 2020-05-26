package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.Face;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.Face.AuthenticateFaceResponseDataEntity;
import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.Face.AuthenticateFaceResponseEntity;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateFaceResponseEntityTest {

    AuthenticateFaceResponseDataEntity data;

    AuthenticateFaceResponseEntity authenticateFaceResponseEntity;

    @Before
    public void setUp() {
        authenticateFaceResponseEntity = new AuthenticateFaceResponseEntity();
    }


    @Test
    public void getSuccess() {
        authenticateFaceResponseEntity.setSuccess(true);
        assertTrue(authenticateFaceResponseEntity.isSuccess());

    }

    @Test
    public void getStatus() {

        authenticateFaceResponseEntity.setStatus(417);
        assertTrue(authenticateFaceResponseEntity.getStatus() == 417);

    }

    @Test
    public void getData() {

        authenticateFaceResponseEntity.setSuccess(true);
        authenticateFaceResponseEntity.setData(data);
        Assert.assertEquals(authenticateFaceResponseEntity.getData(), data);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme