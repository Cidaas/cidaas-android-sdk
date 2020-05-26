package de.cidaas.cidaasv2.Service.Entity.ValidateDevice;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;


public class ValidateDeviceRequestEntityTest {

    ValidateDeviceRequestEntity validateDeviceRequestEntity;


    @Before
    public void setUp() {

        validateDeviceRequestEntity = new ValidateDeviceRequestEntity();
    }


    @Test
    public void setIntermediate_verifiation_id() {
        validateDeviceRequestEntity.setIntermediate_verifiation_id("Test");
        Assert.assertEquals("Test", validateDeviceRequestEntity.getIntermediate_verifiation_id());

    }

    @Test
    public void setDeviceInfo() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setDeviceMake("Make");
        deviceInfoEntity.setPushNotificationId("push");

        validateDeviceRequestEntity.setDeviceInfo(deviceInfoEntity);
        Assert.assertEquals("Make", validateDeviceRequestEntity.getDeviceInfo().getDeviceMake());
        Assert.assertEquals("push", validateDeviceRequestEntity.getDeviceInfo().getPushNotificationId());

    }

    @Test
    public void setAccess_verifier() {
        validateDeviceRequestEntity.setAccess_verifier("Test");
        Assert.assertEquals("Test", validateDeviceRequestEntity.getAccess_verifier());

    }

    @Test
    public void setStatusId() {
        validateDeviceRequestEntity.setStatusId("Test");
        Assert.assertEquals("Test", validateDeviceRequestEntity.getStatusId());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme