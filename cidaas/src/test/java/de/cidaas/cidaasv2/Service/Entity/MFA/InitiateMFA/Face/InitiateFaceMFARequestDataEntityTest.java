package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.Face;

import org.junit.Test;


import static junit.framework.Assert.assertTrue;

public class InitiateFaceMFARequestDataEntityTest {
    InitiateFaceMFARequestDataEntity initiateFaceMFARequestDataEntity = new InitiateFaceMFARequestDataEntity();

    @Test
    public void getDeviceId() {
        initiateFaceMFARequestDataEntity.setDeviceId("DeviceID");
        assertTrue(initiateFaceMFARequestDataEntity.getDeviceId() == "DeviceID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme