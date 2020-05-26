package de.cidaas.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFARequestEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static junit.framework.Assert.assertTrue;

public class InitiateSmartPushMFARequestEntityTest {

    @InjectMocks
    InitiateSmartPushMFARequestEntity initiateSmartPushMFARequestEntity;

    @Before
    public void setUp() {

        initiateSmartPushMFARequestEntity = new InitiateSmartPushMFARequestEntity();
    }


    @Test
    public void setClientId() {
        initiateSmartPushMFARequestEntity.setClient_id("ClientID");
        assertTrue(initiateSmartPushMFARequestEntity.getClient_id().equals("ClientID"));
    }

    @Test
    public void setEmail() {
        initiateSmartPushMFARequestEntity.setEmail("email");
        assertTrue(initiateSmartPushMFARequestEntity.getEmail().equals("email"));
    }

    @Test
    public void setMobile() {
        initiateSmartPushMFARequestEntity.setMobile("Mobile");
        assertTrue(initiateSmartPushMFARequestEntity.getMobile().equals("Mobile"));
    }

    @Test
    public void setUsagePass() {
        initiateSmartPushMFARequestEntity.setUsage_pass("UsagePass");
        assertTrue(initiateSmartPushMFARequestEntity.getUsage_pass().equals("UsagePass"));
    }


    @Test
    public void getUsageType() {
        initiateSmartPushMFARequestEntity.setUsageType("UsageType");
        assertTrue(initiateSmartPushMFARequestEntity.getUsageType().equals("UsageType"));
    }


    @Test
    public void setSub() {
        initiateSmartPushMFARequestEntity.setSub("Sub");
        assertTrue(initiateSmartPushMFARequestEntity.getSub().equals("Sub"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        initiateSmartPushMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(initiateSmartPushMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(initiateSmartPushMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(initiateSmartPushMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(initiateSmartPushMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(initiateSmartPushMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        initiateSmartPushMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(initiateSmartPushMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme