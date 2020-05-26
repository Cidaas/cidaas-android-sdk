package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestEntity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class InitiateFingerprintMFARequestEntityTest {

    InitiateFingerprintMFARequestEntity initiateFingerprintMFARequestEntity;

    @Before
    public void setUp() {

        initiateFingerprintMFARequestEntity = new InitiateFingerprintMFARequestEntity();
    }


    @Test
    public void setClientId() {
        initiateFingerprintMFARequestEntity.setClient_id("ClientID");
        assertTrue(initiateFingerprintMFARequestEntity.getClient_id().equals("ClientID"));
    }

    @Test
    public void setEmail() {
        initiateFingerprintMFARequestEntity.setEmail("email");
        assertTrue(initiateFingerprintMFARequestEntity.getEmail().equals("email"));
    }

    @Test
    public void setMobile() {
        initiateFingerprintMFARequestEntity.setMobile("Mobile");
        assertTrue(initiateFingerprintMFARequestEntity.getMobile().equals("Mobile"));
    }

    @Test
    public void setUsagePass() {
        initiateFingerprintMFARequestEntity.setUsagePass("UsagePass");
        assertTrue(initiateFingerprintMFARequestEntity.getUsagePass().equals("UsagePass"));
    }


    @Test
    public void getUsageType() {
        initiateFingerprintMFARequestEntity.setUsageType("UsageType");
        assertTrue(initiateFingerprintMFARequestEntity.getUsageType().equals("UsageType"));
    }


    @Test
    public void setSub() {
        initiateFingerprintMFARequestEntity.setSub("Sub");
        assertTrue(initiateFingerprintMFARequestEntity.getSub().equals("Sub"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        initiateFingerprintMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(initiateFingerprintMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(initiateFingerprintMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(initiateFingerprintMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(initiateFingerprintMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(initiateFingerprintMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        initiateFingerprintMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(initiateFingerprintMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme