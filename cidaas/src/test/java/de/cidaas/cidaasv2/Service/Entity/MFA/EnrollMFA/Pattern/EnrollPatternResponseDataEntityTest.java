package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternResponseDataEntity;

import static junit.framework.Assert.assertTrue;

public class EnrollPatternResponseDataEntityTest {

    EnrollPatternResponseDataEntity enrollPatternResponseDataEntity = new EnrollPatternResponseDataEntity();

    @Test
    public void getSub() {
        enrollPatternResponseDataEntity.setSub("test");
        assertTrue(enrollPatternResponseDataEntity.getSub() == "test");
    }

    @Test
    public void geDescription() {
        enrollPatternResponseDataEntity.setTrackingCode("test");
        assertTrue(enrollPatternResponseDataEntity.getTrackingCode() == "test");

    }

    @Test
    public void getUsageType() {
        enrollPatternResponseDataEntity.setUsageType("UsageType");
        assertTrue(enrollPatternResponseDataEntity.getUsageType() == "UsageType");

    }

    @Test
    public void getVerificationType() {
        enrollPatternResponseDataEntity.setVerificationType("verificationType");
        assertTrue(enrollPatternResponseDataEntity.getVerificationType() == "verificationType");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme