package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.FIDOKey;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class InitiateFIDOMFAResponseEntityTest {

    InitiateFIDOMFAResponseDataEntity data;

    InitiateFIDOMFAResponseEntity initiateFIDOMFAResponseEntity;

    @Before
    public void setUp() {
        initiateFIDOMFAResponseEntity = new InitiateFIDOMFAResponseEntity();
    }

    @Test
    public void setSuccess() {
        initiateFIDOMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(initiateFIDOMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        initiateFIDOMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, initiateFIDOMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new InitiateFIDOMFAResponseDataEntity();

        data.setStatusId("Test");
        initiateFIDOMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", initiateFIDOMFAResponseEntity.getData().getStatusId());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme