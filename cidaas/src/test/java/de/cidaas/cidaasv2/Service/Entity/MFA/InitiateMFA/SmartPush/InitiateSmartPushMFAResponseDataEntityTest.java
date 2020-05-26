package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFAResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateSmartPushMFAResponseDataEntityTest {
    InitiateSmartPushMFAResponseDataEntity initiateSmartPushMFAResponseDataEntity = new InitiateSmartPushMFAResponseDataEntity();

    @Test
    public void getStatusId() {
        initiateSmartPushMFAResponseDataEntity.setStatusId("statusId");
        assertTrue(initiateSmartPushMFAResponseDataEntity.getStatusId() == "statusId");

    }

    @Test
    public void setRandomnumber() {
        initiateSmartPushMFAResponseDataEntity.setRandomNumber("RandomNumber");
        assertTrue(initiateSmartPushMFAResponseDataEntity.getRandomNumber() == "RandomNumber");

    }

    @Test
    public void setCurrentSatatus() {
        initiateSmartPushMFAResponseDataEntity.setCurrent_status("Status");
        assertTrue(initiateSmartPushMFAResponseDataEntity.getCurrent_status() == "Status");

    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme