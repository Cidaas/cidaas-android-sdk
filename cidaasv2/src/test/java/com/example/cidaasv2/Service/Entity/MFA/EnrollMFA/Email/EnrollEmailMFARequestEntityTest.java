package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class EnrollEmailMFARequestEntityTest {
 

   
    EnrollEmailMFARequestEntity enrollEmailMFARequestEntity;

    @Before
    public void setUp() {
        enrollEmailMFARequestEntity=new EnrollEmailMFARequestEntity();
    }


    @Test
    public void getStatusID()
    {
        enrollEmailMFARequestEntity.setStatusId("Status_ID");
        assertTrue(enrollEmailMFARequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getCode()
    {
        enrollEmailMFARequestEntity.setCode("Code");
        assertTrue(enrollEmailMFARequestEntity.getCode().equals("Code"));
    }

    @Test
    public void setSub()
    {
        enrollEmailMFARequestEntity.setSub("Sub");
        assertTrue(enrollEmailMFARequestEntity.getSub().equals("Sub"));
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

        enrollEmailMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(enrollEmailMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(enrollEmailMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(enrollEmailMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(enrollEmailMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(enrollEmailMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme