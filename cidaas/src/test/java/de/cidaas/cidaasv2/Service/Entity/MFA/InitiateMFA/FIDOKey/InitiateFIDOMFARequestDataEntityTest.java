package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.FIDOKey;

import org.junit.Test;


import static junit.framework.Assert.assertTrue;

public class InitiateFIDOMFARequestDataEntityTest {
    InitiateFIDOMFARequestDataEntity initiateFIDOMFARequestDataEntity = new InitiateFIDOMFARequestDataEntity();

    @Test
    public void getDeviceId() {
        initiateFIDOMFARequestDataEntity.setDeviceId("DeviceID");
        assertTrue(initiateFIDOMFARequestDataEntity.getDeviceId() == "DeviceID");

    }
}


//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme