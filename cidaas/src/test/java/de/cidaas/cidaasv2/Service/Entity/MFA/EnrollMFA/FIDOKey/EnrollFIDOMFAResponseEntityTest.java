package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey;

import junit.framework.Assert;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.FIDOKey.EnrollFIDOMFAResponseEntity;

public class EnrollFIDOMFAResponseEntityTest {

    EnrollFIDOMFAResponseEntity enrollFIDOMFAResponseEntity = new EnrollFIDOMFAResponseEntity();

    @Test
    public void setSuccess() {
        enrollFIDOMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(enrollFIDOMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        enrollFIDOMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, enrollFIDOMFAResponseEntity.getStatus());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme