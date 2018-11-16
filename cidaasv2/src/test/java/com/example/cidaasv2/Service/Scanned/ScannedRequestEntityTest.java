package com.example.cidaasv2.Service.Scanned;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ScannedRequestEntityTest {



    ScannedRequestEntity scannedRequestEntity;

    @Before
    public void setUp() {

        scannedRequestEntity=new ScannedRequestEntity();
    }


    @Test
    public void setUsage_pass()
    {
        scannedRequestEntity.setUsage_pass("TestData");
        Assert.assertEquals("TestData",scannedRequestEntity.getUsage_pass());
    }

    @Test
    public void setStatusId()
    {
        scannedRequestEntity.setStatusId("TestData");
        Assert.assertEquals("TestData",scannedRequestEntity.getStatusId());
    }

    @Test
    public void setDeviceInfo()
    {

        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("TestData");
        scannedRequestEntity.setDeviceInfo(deviceInfoEntity);
        Assert.assertEquals("TestData",scannedRequestEntity.getDeviceInfo().getPushNotificationId());
    }



}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme