package de.cidaas.sdk.android.Service.Entity.MFA.SetupMFA.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.service.entity.mfa.SetupMFA.Pattern.SetupPatternMFARequestEntity;

import static junit.framework.Assert.assertTrue;

public class SetupPatternMFARequestEntityTest {

    SetupPatternMFARequestEntity setupPatternRequestEntity;

    @Before
    public void setUp() {
        setupPatternRequestEntity = new SetupPatternMFARequestEntity();
    }

    @Test
    public void setClientID() throws Exception {

        setupPatternRequestEntity.setClient_id("ClientID");

        Assert.assertEquals("ClientID", setupPatternRequestEntity.getClient_id());
    }


    @Test
    public void setLogoURL() throws Exception {

        setupPatternRequestEntity.setLogoUrl("LogoURL");

        Assert.assertEquals("LogoURL", setupPatternRequestEntity.getLogoUrl());
    }

    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        setupPatternRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(setupPatternRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(setupPatternRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(setupPatternRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(setupPatternRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(setupPatternRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme