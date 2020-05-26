package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.Voice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.Voice.AuthenticateVoiceResponseDataEntity;
import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.Voice.AuthenticateVoiceResponseEntity;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateVoiceResponseEntityTest {

    AuthenticateVoiceResponseDataEntity data;

    AuthenticateVoiceResponseEntity authenticateVoiceResponseEntity;

    @Before
    public void setUp() {
        authenticateVoiceResponseEntity = new AuthenticateVoiceResponseEntity();
    }


    @Test
    public void getSuccess() {
        authenticateVoiceResponseEntity.setSuccess(true);
        assertTrue(authenticateVoiceResponseEntity.isSuccess());

    }

    @Test
    public void getStatus() {

        authenticateVoiceResponseEntity.setStatus(417);
        assertTrue(authenticateVoiceResponseEntity.getStatus() == 417);

    }

    @Test
    public void getData() {

        authenticateVoiceResponseEntity.setSuccess(true);
        authenticateVoiceResponseEntity.setData(data);
        Assert.assertEquals(authenticateVoiceResponseEntity.getData(), data);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme