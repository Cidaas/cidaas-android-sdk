package de.cidaas.sdk.android.Service.Entity.MFA.SetupMFA.SMS;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.SetupMFA.SMS.SetupSMSMFAResponseDataEntity;


public class SetupSMSMFAResponseDataEntityTest {
    @Test
    public void setStatusId() throws Exception {

        SetupSMSMFAResponseDataEntity setupSMSMFAResponseDataEntity = new SetupSMSMFAResponseDataEntity();

        setupSMSMFAResponseDataEntity.setStatusId("StatusId");

        Assert.assertEquals("StatusId", setupSMSMFAResponseDataEntity.getStatusId());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme