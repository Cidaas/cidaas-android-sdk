package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.FIDO;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SetupFIDOMFARequestEntityTest {
   
    DeviceInfoEntity deviceInfo;

    SetupFIDOMFARequestEntity setupFIDORequestEntity;

    @Before
    public void setUp() {
        
        setupFIDORequestEntity=new SetupFIDOMFARequestEntity();
    }

    @Test
    public void setClientID() throws Exception {

        setupFIDORequestEntity.setClient_id("ClientID");

        Assert.assertEquals("ClientID",setupFIDORequestEntity.getClient_id());
    }


    @Test
    public void setLogoURL() throws Exception {

        setupFIDORequestEntity.setLogoUrl("LogoURL");

        Assert.assertEquals("LogoURL",setupFIDORequestEntity.getLogoUrl());
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

        setupFIDORequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(setupFIDORequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(setupFIDORequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(setupFIDORequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(setupFIDORequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(setupFIDORequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme