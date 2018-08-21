package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class AuthenticateTOTPRequestEntityTest {
    @Mock
    DeviceInfoEntity deviceInfo;
    @InjectMocks
    AuthenticateTOTPRequestEntity authenticateTOTPRequestEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getStatusID()
    {
        authenticateTOTPRequestEntity.setStatusId("Status_ID");
        assertTrue(authenticateTOTPRequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getVerifierPassword()
    {
        authenticateTOTPRequestEntity.setVerifierPassword("Password");
        assertTrue(authenticateTOTPRequestEntity.getVerifierPassword().equals("Password"));
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

        authenticateTOTPRequestEntity.setDeviceInfo(deviceInfo);

/*        assertTrue(authenticateTOTPRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(authenticateTOTPRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(authenticateTOTPRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(authenticateTOTPRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(authenticateTOTPRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));*/
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme