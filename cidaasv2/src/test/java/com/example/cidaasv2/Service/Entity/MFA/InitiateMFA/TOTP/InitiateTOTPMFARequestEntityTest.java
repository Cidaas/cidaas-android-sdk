package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class InitiateTOTPMFARequestEntityTest {

    InitiateTOTPMFARequestEntity initiateTOTPMFARequestEntity;

    @Before
    public void setUp() {

        initiateTOTPMFARequestEntity=new InitiateTOTPMFARequestEntity();
    }


    @Test
    public void setClientId()
    {
        initiateTOTPMFARequestEntity.setClient_id("ClientID");
        assertTrue(initiateTOTPMFARequestEntity.getClient_id().equals("ClientID"));
    }

    @Test
    public void setEmail()
    {
        initiateTOTPMFARequestEntity.setEmail("email");
        assertTrue(initiateTOTPMFARequestEntity.getEmail().equals("email"));
    }
    @Test
    public void setMobile()
    {
        initiateTOTPMFARequestEntity.setMobile("Mobile");
        assertTrue(initiateTOTPMFARequestEntity.getMobile().equals("Mobile"));
    }

    @Test
    public void setUsagePass()
    {
        initiateTOTPMFARequestEntity.setUsagePass("UsagePass");
        assertTrue(initiateTOTPMFARequestEntity.getUsagePass().equals("UsagePass"));
    }


    @Test
    public void getUsageType()
    {
        initiateTOTPMFARequestEntity.setUsageType("UsageType");
        assertTrue(initiateTOTPMFARequestEntity.getUsageType().equals("UsageType"));
    }


    @Test
    public void setSub()
    {
        initiateTOTPMFARequestEntity.setSub("Sub");
        assertTrue(initiateTOTPMFARequestEntity.getSub().equals("Sub"));
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

        initiateTOTPMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(initiateTOTPMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(initiateTOTPMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(initiateTOTPMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(initiateTOTPMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(initiateTOTPMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId()
    {
        initiateTOTPMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(initiateTOTPMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme