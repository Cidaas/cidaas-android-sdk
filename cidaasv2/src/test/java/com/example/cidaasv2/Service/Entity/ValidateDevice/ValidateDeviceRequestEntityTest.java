package com.example.cidaasv2.Service.Entity.ValidateDevice;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ValidateDeviceRequestEntityTest {

    ValidateDeviceRequestEntity validateDeviceRequestEntity;


    @Before
    public void setUp() {

        validateDeviceRequestEntity=new ValidateDeviceRequestEntity();
    }


    @Test
    public void setIntermediate_verifiation_id(){
        validateDeviceRequestEntity.setIntermediate_verifiation_id("Test");
        Assert.assertEquals("Test",validateDeviceRequestEntity.getIntermediate_verifiation_id());

    }
    @Test
    public void setDeviceInfo(){
        DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
        deviceInfoEntity.setDeviceMake("Make");
        deviceInfoEntity.setPushNotificationId("push");

        validateDeviceRequestEntity.setDeviceInfo(deviceInfoEntity);
        Assert.assertEquals("Make",validateDeviceRequestEntity.getDeviceInfo().getDeviceMake());
        Assert.assertEquals("push",validateDeviceRequestEntity.getDeviceInfo().getPushNotificationId());

    }
    @Test
    public void setAccess_verifier(){
        validateDeviceRequestEntity.setAccess_verifier("Test");
        Assert.assertEquals("Test",validateDeviceRequestEntity.getAccess_verifier());

    }
    @Test
    public void setStatusId(){
        validateDeviceRequestEntity.setStatusId("Test");
        Assert.assertEquals("Test",validateDeviceRequestEntity.getStatusId());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme