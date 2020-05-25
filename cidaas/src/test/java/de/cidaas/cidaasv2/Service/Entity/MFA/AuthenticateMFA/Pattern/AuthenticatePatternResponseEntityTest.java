package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternResponseDataEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternResponseEntity;

import static junit.framework.TestCase.assertTrue;

public class AuthenticatePatternResponseEntityTest {

    AuthenticatePatternResponseDataEntity data;

    AuthenticatePatternResponseEntity authenticatePatternResponseEntity;

    @Before
    public void setUp() {
        authenticatePatternResponseEntity = new AuthenticatePatternResponseEntity();
    }


    @Test
    public void getSuccess() {
        authenticatePatternResponseEntity.setSuccess(true);
        assertTrue(authenticatePatternResponseEntity.isSuccess());

    }

    @Test
    public void getStatus() {

        authenticatePatternResponseEntity.setStatus(417);
        assertTrue(authenticatePatternResponseEntity.getStatus() == 417);

    }

    @Test
    public void getData() {

        authenticatePatternResponseEntity.setSuccess(true);
        authenticatePatternResponseEntity.setData(data);
        Assert.assertEquals(authenticatePatternResponseEntity.getData(), data);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme