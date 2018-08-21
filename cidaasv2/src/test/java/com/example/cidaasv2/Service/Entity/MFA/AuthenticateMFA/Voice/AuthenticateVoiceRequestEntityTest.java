package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class AuthenticateVoiceRequestEntityTest {
    @Mock
    DeviceInfoEntity deviceInfo;
    @Mock
    File voiceFile;
    @InjectMocks
    AuthenticateVoiceRequestEntity authenticateVoiceRequestEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getStatusID()
    {
        authenticateVoiceRequestEntity.setStatusId("Status_ID");
        assertTrue(authenticateVoiceRequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getVerifierPassword()
    {
        File voiceFile=new File("");
        authenticateVoiceRequestEntity.setVoiceFile(voiceFile);
        assertTrue(authenticateVoiceRequestEntity.getVoiceFile().equals(voiceFile));
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

        authenticateVoiceRequestEntity.setDeviceInfo(deviceInfo);

        //assertTrue(authenticateVoiceRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));4
        /*
        assertTrue(authenticateVoiceRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(authenticateVoiceRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(authenticateVoiceRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(authenticateVoiceRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));*/
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme