package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.Email;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseDataEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseEntity;

public class InitiateEmailMFAResponseEntityTest {
    InitiateEmailMFAResponseDataEntity data;

    InitiateEmailMFAResponseEntity initiateEmailMFAResponseEntity;

    @Before
    public void setUp() {
        initiateEmailMFAResponseEntity = new InitiateEmailMFAResponseEntity();
    }

    @Test
    public void setSuccess() {
        initiateEmailMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(initiateEmailMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        initiateEmailMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, initiateEmailMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new InitiateEmailMFAResponseDataEntity();

        data.setStatusId("Test");
        initiateEmailMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", initiateEmailMFAResponseEntity.getData().getStatusId());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme