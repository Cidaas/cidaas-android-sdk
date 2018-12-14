package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class EnrollFaceMFARequestEntityTest {
    @Mock
    File imagetoSend;
    
    
    EnrollFaceMFARequestEntity enrollFaceMFARequestEntity;

    @Before
    public void setUp() {
        enrollFaceMFARequestEntity=new EnrollFaceMFARequestEntity();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getStatusID()
    {
        enrollFaceMFARequestEntity.setStatusId("Status_ID");
        assertTrue(enrollFaceMFARequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void setImagetoSend()
    {
        enrollFaceMFARequestEntity.setImagetoSend(imagetoSend);
        enrollFaceMFARequestEntity.getImagetoSend();
//        assertTrue(enrollFaceMFARequestEntity.getImagetoSend().equals("Code"));
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

        enrollFaceMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(enrollFaceMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(enrollFaceMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(enrollFaceMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(enrollFaceMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(enrollFaceMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
    @Test
    public void getUserDeviceId()
    {
        enrollFaceMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(enrollFaceMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme