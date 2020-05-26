package de.cidaas.cidaasv2.Helper.Entity;

import org.junit.Assert;
import org.junit.Test;


public class PhysicalVerificationEntityTest {

    private String sub;
    private String physicalVerificationId;
    private String userDeviceId;
    private String usageType;

    PhysicalVerificationEntity physicalVerificationEntity = new PhysicalVerificationEntity();

    @Test
    public void Sub() {
        physicalVerificationEntity.setSub("Sub");
        Assert.assertEquals("Sub", physicalVerificationEntity.getSub());
    }

    @Test
    public void setPhysicalVerificationId() {
        physicalVerificationEntity.setPhysicalVerificationId("PhysicalVerificationId");
        Assert.assertEquals("PhysicalVerificationId", physicalVerificationEntity.getPhysicalVerificationId());
    }

    @Test
    public void setUserDeviceId() {
        physicalVerificationEntity.setUserDeviceId("UserDeviceId");
        Assert.assertEquals("UserDeviceId", physicalVerificationEntity.getUserDeviceId());
    }

    @Test
    public void setUsageType() {
        physicalVerificationEntity.setUsageType("UsageType");
        Assert.assertEquals("UsageType", physicalVerificationEntity.getUsageType());
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme