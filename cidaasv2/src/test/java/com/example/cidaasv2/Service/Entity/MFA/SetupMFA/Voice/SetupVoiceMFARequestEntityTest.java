package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SetupVoiceMFARequestEntityTest {

    SetupVoiceMFARequestEntity setupVoiceRequestEntity;

    @Before
    public void setUp() {
        setupVoiceRequestEntity=new SetupVoiceMFARequestEntity();
    }

    @Test
    public void setClientID() throws Exception {

        setupVoiceRequestEntity.setClient_id("ClientID");

        Assert.assertEquals("ClientID",setupVoiceRequestEntity.getClient_id());
    }


    @Test
    public void setLogoURL() throws Exception {

        setupVoiceRequestEntity.setLogoUrl("LogoURL");

        Assert.assertEquals("LogoURL",setupVoiceRequestEntity.getLogoUrl());
    }

    @Test
    public void getDeviceInfoEntity()
    {
        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
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