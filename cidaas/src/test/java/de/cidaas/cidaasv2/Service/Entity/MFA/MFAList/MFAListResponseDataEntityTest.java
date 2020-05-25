package de.cidaas.cidaasv2.Service.Entity.MFA.MFAList;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class MFAListResponseDataEntityTest {
    @Test
    public void getDeviceInfoEntity() {
        MFAListResponseDataEntity deviceInfoEntity = new MFAListResponseDataEntity();
        deviceInfoEntity.set_id("Id");
        deviceInfoEntity.setVerificationType("VerificationType");


        assertTrue(deviceInfoEntity.getVerificationType().equals("VerificationType"));
        assertTrue(deviceInfoEntity.get_id().equals("Id"));

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme