package de.cidaas.sdk.android.Helper.Entity;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.entities.PhysicalVerificationDeviceInfo;


public class PhysicalVerificationDeviceInfoTest {

    PhysicalVerificationDeviceInfo physicalVerificationDeviceInfo = new PhysicalVerificationDeviceInfo();

    @Test
    public void deviceInfo() {
        physicalVerificationDeviceInfo.setDeviceId("123");
        Assert.assertEquals("123", physicalVerificationDeviceInfo.getDeviceId());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme