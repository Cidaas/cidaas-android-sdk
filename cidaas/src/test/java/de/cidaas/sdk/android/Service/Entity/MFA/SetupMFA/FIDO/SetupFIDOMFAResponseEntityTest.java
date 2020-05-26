package de.cidaas.sdk.android.Service.Entity.MFA.SetupMFA.FIDO;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.SetupMFA.FIDO.SetupFIDOMFAResponseDataEntity;
import de.cidaas.sdk.android.service.entity.mfa.SetupMFA.FIDO.SetupFIDOMFAResponseEntity;


public class SetupFIDOMFAResponseEntityTest {

    SetupFIDOMFAResponseDataEntity data;

    SetupFIDOMFAResponseEntity setupFIDOMFAResponseEntity;

    @Before
    public void setUp() {

        setupFIDOMFAResponseEntity = new SetupFIDOMFAResponseEntity();
    }


    @Test
    public void setSuccess() {
        setupFIDOMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(setupFIDOMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        setupFIDOMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, setupFIDOMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new SetupFIDOMFAResponseDataEntity();

        data.setSt("Test");
        setupFIDOMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", setupFIDOMFAResponseEntity.getData().getSt());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme