package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.Voice;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.Voice.InitiateVoiceMFARequestEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateVoiceMFARequestEntityTest {

    InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntity;

    @Before
    public void setUp() {
        initiateVoiceMFARequestEntity = new InitiateVoiceMFARequestEntity();
    }


    @Test
    public void setClientId() {
        initiateVoiceMFARequestEntity.setClient_id("ClientID");
        assertTrue(initiateVoiceMFARequestEntity.getClient_id().equals("ClientID"));
    }

    @Test
    public void setEmail() {
        initiateVoiceMFARequestEntity.setEmail("email");
        assertTrue(initiateVoiceMFARequestEntity.getEmail().equals("email"));
    }

    @Test
    public void setMobile() {
        initiateVoiceMFARequestEntity.setMobile("Mobile");
        assertTrue(initiateVoiceMFARequestEntity.getMobile().equals("Mobile"));
    }

    @Test
    public void getUsageType() {
        initiateVoiceMFARequestEntity.setUsageType("UsageType");
        assertTrue(initiateVoiceMFARequestEntity.getUsageType().equals("UsageType"));
    }


    @Test
    public void setSub() {
        initiateVoiceMFARequestEntity.setSub("Sub");
        assertTrue(initiateVoiceMFARequestEntity.getSub().equals("Sub"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        initiateVoiceMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(initiateVoiceMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(initiateVoiceMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(initiateVoiceMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(initiateVoiceMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(initiateVoiceMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        initiateVoiceMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(initiateVoiceMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme