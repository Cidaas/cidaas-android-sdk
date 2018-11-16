package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class EnrollSMSMFARequestEntityTest {
    @Mock
    DeviceInfoEntity deviceInfo;
    @InjectMocks
    EnrollSMSMFARequestEntity enrollSMSMFARequestEntity;

    @Before
    public void setUp() {
        
        enrollSMSMFARequestEntity=new EnrollSMSMFARequestEntity();
    }


    @Test
    public void getStatusID()
    {
        enrollSMSMFARequestEntity.setStatusId("Status_ID");
        assertTrue(enrollSMSMFARequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getCode()
    {
        enrollSMSMFARequestEntity.setCode("Code");
        assertTrue(enrollSMSMFARequestEntity.getCode().equals("Code"));
    }

    @Test
    public void setSub()
    {
        enrollSMSMFARequestEntity.setSub("Sub");
        assertTrue(enrollSMSMFARequestEntity.getSub().equals("Sub"));
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

        enrollSMSMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(enrollSMSMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(enrollSMSMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(enrollSMSMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(enrollSMSMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(enrollSMSMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme