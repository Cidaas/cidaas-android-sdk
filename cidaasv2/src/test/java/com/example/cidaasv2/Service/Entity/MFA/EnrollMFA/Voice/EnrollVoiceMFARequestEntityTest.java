package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class EnrollVoiceMFARequestEntityTest {
    @Mock
    File audioFile;

    @InjectMocks
    EnrollVoiceMFARequestEntity enrollVoiceMFARequestEntity;

    @Before
    public void setUp() {

        enrollVoiceMFARequestEntity=new EnrollVoiceMFARequestEntity();
    }


    @Test
    public void getStatusID()
    {
        enrollVoiceMFARequestEntity.setStatusId("Status_ID");
        assertTrue(enrollVoiceMFARequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getCode()
    {
        enrollVoiceMFARequestEntity.setAudioFile(audioFile);
        enrollVoiceMFARequestEntity.getAudioFile();
       // assertTrue(enrollVoiceMFARequestEntity.getAudioFile().equals("Code"));
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

        enrollVoiceMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(enrollVoiceMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(enrollVoiceMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(enrollVoiceMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(enrollVoiceMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(enrollVoiceMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId()
    {
        enrollVoiceMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(enrollVoiceMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme