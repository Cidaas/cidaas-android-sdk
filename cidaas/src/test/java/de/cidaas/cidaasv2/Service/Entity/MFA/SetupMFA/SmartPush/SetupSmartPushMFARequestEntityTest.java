package de.cidaas.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;


import static junit.framework.Assert.assertTrue;

public class SetupSmartPushMFARequestEntityTest {

    SetupSmartPushMFARequestEntity setupSmartPushRequestEntity;

    @Before
    public void setUp() {

        setupSmartPushRequestEntity = new SetupSmartPushMFARequestEntity();
    }

    @Test
    public void setClientID() throws Exception {

        setupSmartPushRequestEntity.setClient_id("ClientID");

        Assert.assertEquals("ClientID", setupSmartPushRequestEntity.getClient_id());
    }


    @Test
    public void setLogoURL() throws Exception {

        setupSmartPushRequestEntity.setLogoUrl("LogoURL");

        Assert.assertEquals("LogoURL", setupSmartPushRequestEntity.getLogoUrl());
    }

    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        setupSmartPushRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(setupSmartPushRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(setupSmartPushRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(setupSmartPushRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(setupSmartPushRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(setupSmartPushRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme