package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SetupFaceMFARequestEntityTest {



    SetupFaceMFARequestEntity setupFaceRequestEntity;

    @Before
    public void setUp() {

        setupFaceRequestEntity=new SetupFaceMFARequestEntity();
    }


    @Test
    public void setClientID() throws Exception {

        setupFaceRequestEntity.setClient_id("ClientID");

        Assert.assertEquals("ClientID",setupFaceRequestEntity.getClient_id());
    }


    @Test
    public void setLogoURL() throws Exception {

        setupFaceRequestEntity.setLogoUrl("LogoURL");

        Assert.assertEquals("LogoURL",setupFaceRequestEntity.getLogoUrl());
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

        setupFaceRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(setupFaceRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(setupFaceRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(setupFaceRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(setupFaceRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(setupFaceRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme