package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.Face;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Face.InitiateFaceMFAResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateFaceMFAResponseDataEntityTest {

    InitiateFaceMFAResponseDataEntity initiateFaceMFAResponseDataEntity = new InitiateFaceMFAResponseDataEntity();

    @Test
    public void getStatusId() {
        initiateFaceMFAResponseDataEntity.setStatusId("statusId");
        assertTrue(initiateFaceMFAResponseDataEntity.getStatusId() == "statusId");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme