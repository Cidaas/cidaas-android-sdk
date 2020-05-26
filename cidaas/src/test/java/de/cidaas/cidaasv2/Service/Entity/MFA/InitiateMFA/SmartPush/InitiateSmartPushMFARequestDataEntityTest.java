package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush;

import org.junit.Test;


import static junit.framework.Assert.assertTrue;

public class InitiateSmartPushMFARequestDataEntityTest {
    InitiateSmartPushMFARequestDataEntity initiateSmartPushMFARequestDataEntity = new InitiateSmartPushMFARequestDataEntity();

    @Test
    public void getDeviceId() {
        initiateSmartPushMFARequestDataEntity.setDeviceId("DeviceID");
        assertTrue(initiateSmartPushMFARequestDataEntity.getDeviceId() == "DeviceID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme