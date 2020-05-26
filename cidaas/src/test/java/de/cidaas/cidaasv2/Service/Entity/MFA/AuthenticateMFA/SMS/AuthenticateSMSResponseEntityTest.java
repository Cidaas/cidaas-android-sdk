package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static junit.framework.TestCase.assertTrue;

public class AuthenticateSMSResponseEntityTest {

    AuthenticateSMSResponseDataEntity data;

    AuthenticateSMSResponseEntity authenticateSMSResponseEntity;

    @Before
    public void setUp() {
        authenticateSMSResponseEntity = new AuthenticateSMSResponseEntity();
    }


    @Test
    public void getSuccess() {
        authenticateSMSResponseEntity.setSuccess(true);
        assertTrue(authenticateSMSResponseEntity.isSuccess());

    }

    @Test
    public void getStatus() {

        authenticateSMSResponseEntity.setStatus(417);
        assertTrue(authenticateSMSResponseEntity.getStatus() == 417);

    }

    @Test
    public void getData() {

        authenticateSMSResponseEntity.setSuccess(true);
        authenticateSMSResponseEntity.setData(data);
        Assert.assertEquals(authenticateSMSResponseEntity.getData(), data);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme