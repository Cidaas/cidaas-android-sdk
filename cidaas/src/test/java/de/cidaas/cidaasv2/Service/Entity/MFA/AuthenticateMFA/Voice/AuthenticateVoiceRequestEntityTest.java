package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;


public class AuthenticateVoiceRequestEntityTest {


    File voiceFile;

    AuthenticateVoiceRequestEntity authenticateVoiceRequestEntity;

    @Before
    public void setUp() {
        authenticateVoiceRequestEntity = new AuthenticateVoiceRequestEntity();
    }

    @Test
    public void getStatusID() {
        authenticateVoiceRequestEntity.setStatusId("Status_ID");
        assertTrue(authenticateVoiceRequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getVerifierPassword() {
        File voiceFile = new File("");
        authenticateVoiceRequestEntity.setVoiceFile(voiceFile);
        assertTrue(authenticateVoiceRequestEntity.getVoiceFile().equals(voiceFile));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        authenticateVoiceRequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(authenticateVoiceRequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(authenticateVoiceRequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(authenticateVoiceRequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(authenticateVoiceRequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(authenticateVoiceRequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        authenticateVoiceRequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(authenticateVoiceRequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme