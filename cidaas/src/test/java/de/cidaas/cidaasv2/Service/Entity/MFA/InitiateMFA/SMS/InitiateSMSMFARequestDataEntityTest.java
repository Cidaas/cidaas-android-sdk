package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFARequestDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateSMSMFARequestDataEntityTest {
    InitiateSMSMFARequestDataEntity initiateSMSMFARequestDataEntity = new InitiateSMSMFARequestDataEntity();

    @Test
    public void getDeviceId() {
        initiateSMSMFARequestDataEntity.setDeviceId("DeviceID");
        assertTrue(initiateSMSMFARequestDataEntity.getDeviceId() == "DeviceID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme