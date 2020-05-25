package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseDataEntity;

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