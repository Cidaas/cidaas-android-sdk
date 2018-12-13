package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class EnrollFIDOMFARequestEntityTest {

    EnrollFIDOMFARequestEntity enrollFIDOMFARequestEntity;

    @Before
    public void setUp() {
    enrollFIDOMFARequestEntity=new EnrollFIDOMFARequestEntity();
    }


    @Test
    public void getStatusID()
    {
        enrollFIDOMFARequestEntity.setStatusId("Status_ID");
        assertTrue(enrollFIDOMFARequestEntity.getStatusId().equals("Status_ID"));
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

        enrollFIDOMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(enrollFIDOMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(enrollFIDOMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(enrollFIDOMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(enrollFIDOMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(enrollFIDOMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }



}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme