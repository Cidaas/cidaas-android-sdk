package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.IVR;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.IVR.InitiateIVRMFARequestDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateIVRMFARequestDataEntityTest {

    InitiateIVRMFARequestDataEntity initiateIVRMFARequestDataEntity = new InitiateIVRMFARequestDataEntity();

    @Test
    public void getDeviceId() {
        initiateIVRMFARequestDataEntity.setDeviceId("DeviceID");
        assertTrue(initiateIVRMFARequestDataEntity.getDeviceId() == "DeviceID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme