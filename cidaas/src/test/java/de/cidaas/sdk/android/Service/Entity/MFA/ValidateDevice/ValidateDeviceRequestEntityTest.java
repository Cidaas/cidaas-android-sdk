package de.cidaas.sdk.android.Service.Entity.MFA.ValidateDevice;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.service.entity.mfa.validatedevice.ValidateDeviceRequestEntity;

import static junit.framework.Assert.assertTrue;

public class ValidateDeviceRequestEntityTest {

    ValidateDeviceRequestEntity validateDeviceRequestEntity;


    @Before
    public void setUp() {
        validateDeviceRequestEntity = new ValidateDeviceRequestEntity();
    }

    @Test
    public void getStatusID() {
        validateDeviceRequestEntity.setStatusId("Status_ID");
        assertTrue(validateDeviceRequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        validateDeviceRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(validateDeviceRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(validateDeviceRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(validateDeviceRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(validateDeviceRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(validateDeviceRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void setAccessVerifier() {
        validateDeviceRequestEntity.setAccess_verifier("AccessVerifier");
        assertTrue(validateDeviceRequestEntity.getAccess_verifier().equals("AccessVerifier"));
    }

    @Test
    public void setinermediateID() {
        validateDeviceRequestEntity.setIntermediate_verifiation_id("IntermediateId");
        assertTrue(validateDeviceRequestEntity.getIntermediate_verifiation_id().equals("IntermediateId"));
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme