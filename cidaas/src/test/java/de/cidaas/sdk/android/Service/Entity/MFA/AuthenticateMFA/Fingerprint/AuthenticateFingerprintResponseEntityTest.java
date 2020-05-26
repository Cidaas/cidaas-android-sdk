package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.Fingerprint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.Fingerprint.AuthenticateFingerprintResponseDataEntity;
import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.Fingerprint.AuthenticateFingerprintResponseEntity;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateFingerprintResponseEntityTest {

    AuthenticateFingerprintResponseDataEntity data;

    AuthenticateFingerprintResponseEntity authenticateFingerprintResponseEntity;

    @Before
    public void setUp() {
        authenticateFingerprintResponseEntity = new AuthenticateFingerprintResponseEntity();
    }


    @Test
    public void getSuccess() {
        authenticateFingerprintResponseEntity.setSuccess(true);
        assertTrue(authenticateFingerprintResponseEntity.isSuccess());

    }

    @Test
    public void getStatus() {

        authenticateFingerprintResponseEntity.setStatus(417);
        assertTrue(authenticateFingerprintResponseEntity.getStatus() == 417);

    }

    @Test
    public void getData() {

        authenticateFingerprintResponseEntity.setSuccess(true);
        authenticateFingerprintResponseEntity.setData(data);
        Assert.assertEquals(authenticateFingerprintResponseEntity.getData(), data);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme