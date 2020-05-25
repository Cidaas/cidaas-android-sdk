package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.FIDOKey;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.FIDOKey.InitiateFIDOMFAResponseDataEntity;

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