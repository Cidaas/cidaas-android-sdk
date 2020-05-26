package de.cidaas.sdk.android.Service.Entity.MFA.SetupMFA.IVR;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.SetupMFA.IVR.SetupIVRMFAResponseDataEntity;


public class SetupIVRMFAResponseDataEntityTest {
    @Test
    public void setStatusId() throws Exception {

        SetupIVRMFAResponseDataEntity setupIVRMFAResponseDataEntity = new SetupIVRMFAResponseDataEntity();

        setupIVRMFAResponseDataEntity.setStatusId("StatusId");

        Assert.assertEquals("StatusId", setupIVRMFAResponseDataEntity.getStatusId());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme