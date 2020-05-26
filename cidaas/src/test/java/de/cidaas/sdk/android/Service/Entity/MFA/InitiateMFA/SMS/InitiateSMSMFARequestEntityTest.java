package de.cidaas.sdk.android.Service.Entity.MFA.InitiateMFA.SMS;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.SMS.InitiateSMSMFARequestEntity;

import static junit.framework.Assert.assertTrue;

public class InitiateSMSMFARequestEntityTest {

    InitiateSMSMFARequestEntity initiateSMSMFARequestEntity;

    @Before
    public void setUp() {
        initiateSMSMFARequestEntity = new InitiateSMSMFARequestEntity();
    }


    @Test
    public void setPhysicalVerification() {
        initiateSMSMFARequestEntity.setPhysicalVerificationId("physicalVerification");
        assertTrue(initiateSMSMFARequestEntity.getPhysicalVerificationId().equals("physicalVerification"));
    }

    @Test
    public void getUsageType() {
        initiateSMSMFARequestEntity.setUsageType("UsageType");
        assertTrue(initiateSMSMFARequestEntity.getUsageType().equals("UsageType"));
    }

    @Test
    public void getVerificationType() {
        initiateSMSMFARequestEntity.setVerificationType("VerificationType");
        assertTrue(initiateSMSMFARequestEntity.getVerificationType().equals("VerificationType"));
    }

    @Test
    public void setSub() {
        initiateSMSMFARequestEntity.setSub("Sub");
        assertTrue(initiateSMSMFARequestEntity.getSub().equals("Sub"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        initiateSMSMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(initiateSMSMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(initiateSMSMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(initiateSMSMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(initiateSMSMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(initiateSMSMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        initiateSMSMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(initiateSMSMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme