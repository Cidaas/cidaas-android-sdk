package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.Email;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateEmailMFAResponseDataEntityTest {
    InitiateEmailMFAResponseDataEntity initiateEmailMFAResponseDataEntity = new InitiateEmailMFAResponseDataEntity();

    @Test
    public void getStatusId() {
        initiateEmailMFAResponseDataEntity.setStatusId("statusId");
        assertTrue(initiateEmailMFAResponseDataEntity.getStatusId() == "statusId");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme