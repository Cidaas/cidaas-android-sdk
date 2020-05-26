package de.cidaas.cidaasv2.Service.Entity.MFA.SetupMFA.SMS;

import org.junit.Assert;
import org.junit.Test;


public class SetupSMSMFAResponseDataEntityTest {
    @Test
    public void setStatusId() throws Exception {

        SetupSMSMFAResponseDataEntity setupSMSMFAResponseDataEntity = new SetupSMSMFAResponseDataEntity();

        setupSMSMFAResponseDataEntity.setStatusId("StatusId");

        Assert.assertEquals("StatusId", setupSMSMFAResponseDataEntity.getStatusId());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme