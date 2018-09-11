package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class InitiateTOTPMFAResponseDataEntityTest {
    InitiateTOTPMFAResponseDataEntity initiateTOTPMFAResponseDataEntity=new InitiateTOTPMFAResponseDataEntity();

    @Test
    public void getStatusId()
    {
        initiateTOTPMFAResponseDataEntity.setStatusId("statusId");
        assertTrue(initiateTOTPMFAResponseDataEntity.getStatusId()=="statusId");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme