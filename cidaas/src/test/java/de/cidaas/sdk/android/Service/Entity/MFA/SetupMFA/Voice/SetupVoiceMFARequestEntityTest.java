package de.cidaas.sdk.android.Service.Entity.MFA.SetupMFA.Voice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.service.entity.mfa.SetupMFA.Voice.SetupVoiceMFARequestEntity;

import static junit.framework.Assert.assertTrue;

public class SetupVoiceMFARequestEntityTest {

    SetupVoiceMFARequestEntity setupVoiceRequestEntity;

    @Before
    public void setUp() {
        setupVoiceRequestEntity = new SetupVoiceMFARequestEntity();
    }

    @Test
    public void setClientID() throws Exception {

        setupVoiceRequestEntity.setClient_id("ClientID");

        Assert.assertEquals("ClientID", setupVoiceRequestEntity.getClient_id());
    }


    @Test
    public void setLogoURL() throws Exception {

        setupVoiceRequestEntity.setLogoUrl("LogoURL");

        Assert.assertEquals("LogoURL", setupVoiceRequestEntity.getLogoUrl());
    }

    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        setupVoiceRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(setupVoiceRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(setupVoiceRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(setupVoiceRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(setupVoiceRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(setupVoiceRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme