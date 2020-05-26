package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.FIDOKey;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.FIDOKey.InitiateFIDOMFAResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateFIDOMFAResponseDataEntityTest {
    InitiateFIDOMFAResponseDataEntity initiateFIDOMFAResponseDataEntity = new InitiateFIDOMFAResponseDataEntity();

    @Test
    public void getStatusId() {
        initiateFIDOMFAResponseDataEntity.setStatusId("statusId");
        assertTrue(initiateFIDOMFAResponseDataEntity.getStatusId() == "statusId");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme