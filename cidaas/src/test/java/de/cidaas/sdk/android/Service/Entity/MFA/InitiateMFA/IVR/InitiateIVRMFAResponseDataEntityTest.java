package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.IVR;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.IVR.InitiateIVRMFAResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateIVRMFAResponseDataEntityTest {
    InitiateIVRMFAResponseDataEntity initiateIVRMFAResponseDataEntity = new InitiateIVRMFAResponseDataEntity();

    @Test
    public void getStatusId() {
        initiateIVRMFAResponseDataEntity.setStatusId("statusId");
        assertTrue(initiateIVRMFAResponseDataEntity.getStatusId() == "statusId");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme