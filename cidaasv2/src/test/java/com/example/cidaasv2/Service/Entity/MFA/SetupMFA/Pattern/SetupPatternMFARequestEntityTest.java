package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SetupPatternMFARequestEntityTest {

    SetupPatternMFARequestEntity setupPatternRequestEntity;

    @Before
    public void setUp() {
        setupPatternRequestEntity=new SetupPatternMFARequestEntity();
    }

    @Test
    public void setClientID() throws Exception {

        setupPatternRequestEntity.setClient_id("ClientID");

        Assert.assertEquals("ClientID",setupPatternRequestEntity.getClient_id());
    }


    @Test
    public void setLogoURL() throws Exception {

        setupPatternRequestEntity.setLogoUrl("LogoURL");

        Assert.assertEquals("LogoURL",setupPatternRequestEntity.getLogoUrl());
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

        setupPatternRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(setupPatternRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(setupPatternRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(setupPatternRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(setupPatternRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(setupPatternRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme