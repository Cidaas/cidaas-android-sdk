package de.cidaas.sdk.android.Service.Entity.MFA.SetupMFA.Face;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.service.entity.mfa.SetupMFA.Face.SetupFaceMFARequestEntity;

import static junit.framework.Assert.assertTrue;

public class SetupFaceMFARequestEntityTest {


    SetupFaceMFARequestEntity setupFaceRequestEntity;

    @Before
    public void setUp() {

        setupFaceRequestEntity = new SetupFaceMFARequestEntity();
    }


    @Test
    public void setClientID() throws Exception {

        setupFaceRequestEntity.setClient_id("ClientID");

        Assert.assertEquals("ClientID", setupFaceRequestEntity.getClient_id());
    }


    @Test
    public void setLogoURL() throws Exception {

        setupFaceRequestEntity.setLogoUrl("LogoURL");

        Assert.assertEquals("LogoURL", setupFaceRequestEntity.getLogoUrl());
    }

    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        setupFaceRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(setupFaceRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(setupFaceRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(setupFaceRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(setupFaceRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(setupFaceRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme