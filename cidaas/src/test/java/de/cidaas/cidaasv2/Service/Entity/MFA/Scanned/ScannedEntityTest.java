package de.cidaas.cidaasv2.Service.Entity.MFA.Scanned;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;


import static junit.framework.Assert.assertTrue;

public class ScannedEntityTest {

    ScannedEntity scannedEntity;

    @Before
    public void setUp() {
        scannedEntity = new ScannedEntity();
    }

    @Test
    public void getStatusID() {
        scannedEntity.setStatusId("Status_ID");
        assertTrue(scannedEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        scannedEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(scannedEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(scannedEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(scannedEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(scannedEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(scannedEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        scannedEntity.setUsage_pass("Usage_Pass");
        assertTrue(scannedEntity.getUsage_pass().equals("Usage_Pass"));
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme