package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.Email;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFARequestDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateEmailMFARequestDataEntityTest {
    InitiateEmailMFARequestDataEntity initiateEmailMFARequestDataEntity = new InitiateEmailMFARequestDataEntity();

    @Test
    public void getDeviceId() {
        initiateEmailMFARequestDataEntity.setDeviceId("DeviceID");
        assertTrue(initiateEmailMFARequestDataEntity.getDeviceId() == "DeviceID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme