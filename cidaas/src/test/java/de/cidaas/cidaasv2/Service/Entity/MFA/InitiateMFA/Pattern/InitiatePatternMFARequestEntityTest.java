package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;


import static junit.framework.Assert.assertTrue;

public class InitiatePatternMFARequestEntityTest {

    InitiatePatternMFARequestEntity initiatePatternMFARequestEntity;

    @Before
    public void setUp() {
        initiatePatternMFARequestEntity = new InitiatePatternMFARequestEntity();
    }


    @Test
    public void setClientId() {
        initiatePatternMFARequestEntity.setClient_id("ClientID");
        assertTrue(initiatePatternMFARequestEntity.getClient_id().equals("ClientID"));
    }

    @Test
    public void setEmail() {
        initiatePatternMFARequestEntity.setEmail("email");
        assertTrue(initiatePatternMFARequestEntity.getEmail().equals("email"));
    }

    @Test
    public void setMobile() {
        initiatePatternMFARequestEntity.setMobile("Mobile");
        assertTrue(initiatePatternMFARequestEntity.getMobile().equals("Mobile"));
    }

    @Test
    public void setUsagePass() {
        initiatePatternMFARequestEntity.setUsagePass("UsagePass");
        assertTrue(initiatePatternMFARequestEntity.getUsagePass().equals("UsagePass"));
    }


    @Test
    public void getUsageType() {
        initiatePatternMFARequestEntity.setUsageType("UsageType");
        assertTrue(initiatePatternMFARequestEntity.getUsageType().equals("UsageType"));
    }


    @Test
    public void setSub() {
        initiatePatternMFARequestEntity.setSub("Sub");
        assertTrue(initiatePatternMFARequestEntity.getSub().equals("Sub"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        initiatePatternMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(initiatePatternMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(initiatePatternMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(initiatePatternMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(initiatePatternMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(initiatePatternMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        initiatePatternMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(initiatePatternMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme