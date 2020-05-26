package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.SMS;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.SMS.InitiateSMSMFAResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateSMSMFAResponseDataEntityTest {
    InitiateSMSMFAResponseDataEntity initiateSMSMFAResponseDataEntity = new InitiateSMSMFAResponseDataEntity();

    @Test
    public void getStatusId() {
        initiateSMSMFAResponseDataEntity.setStatusId("statusId");
        assertTrue(initiateSMSMFAResponseDataEntity.getStatusId() == "statusId");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme