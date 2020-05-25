package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class InitiateVoiceMFARequestEntityTest {

    InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntity;

    @Before
    public void setUp() {
        initiateVoiceMFARequestEntity=new InitiateVoiceMFARequestEntity();
    }



    @Test
    public void setClientId()
    {
        initiateVoiceMFARequestEntity.setClient_id("ClientID");
        assertTrue(initiateVoiceMFARequestEntity.getClient_id().equals("ClientID"));
    }

    @Test
    public void setEmail()
    {
        initiateVoiceMFARequestEntity.setEmail("email");
        assertTrue(initiateVoiceMFARequestEntity.getEmail().equals("email"));
    }
    @Test
    public void setMobile()
    {
        initiateVoiceMFARequestEntity.setMobile("Mobile");
        assertTrue(initiateVoiceMFARequestEntity.getMobile().equals("Mobile"));
    }

    @Test
    public void setUsagePass()
    {
        initiateVoiceMFARequestEntity.setUsage_pass("UsagePass");
        assertTrue(initiateVoiceMFARequestEntity.getUsage_pass().equals("UsagePass"));
    }
    
    
    @Test
    public void getUsageType()
    {
        initiateVoiceMFARequestEntity.setUsageType("UsageType");
        assertTrue(initiateVoiceMFARequestEntity.getUsageType().equals("UsageType"));
    }

   

    @Test
    public void setSub()
    {
        initiateVoiceMFARequestEntity.setSub("Sub");
        assertTrue(initiateVoiceMFARequestEntity.getSub().equals("Sub"));
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

        initiateVoiceMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(initiateVoiceMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(initiateVoiceMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(initiateVoiceMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(initiateVoiceMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(initiateVoiceMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId()
    {
        initiateVoiceMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(initiateVoiceMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme