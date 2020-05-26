package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.SMS;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.SMS.InitiateSMSMFARequestDataEntity;

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