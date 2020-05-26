package de.cidaas.cidaasv2.Service.Entity.MFA.SetupMFA.Face;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.Face.SetupFaceMFAResponseDataEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.Face.SetupFaceMFAResponseEntity;

public class SetupFaceMFAResponseEntityTest {

    SetupFaceMFAResponseDataEntity data;

    SetupFaceMFAResponseEntity setupFaceMFAResponseEntity;

    @Before
    public void setUp() {
        setupFaceMFAResponseEntity = new SetupFaceMFAResponseEntity();
    }


    @Test
    public void setSuccess() {
        setupFaceMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(setupFaceMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        setupFaceMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, setupFaceMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new SetupFaceMFAResponseDataEntity();

        data.setSt("Test");
        setupFaceMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", setupFaceMFAResponseEntity.getData().getSt());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme