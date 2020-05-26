package de.cidaas.sdk.android.Service.Entity.MFA.SetupMFA.Fingerprint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;
import de.cidaas.sdk.android.service.entity.mfa.SetupMFA.Fingerprint.SetupFingerprintMFARequestEntity;

import static junit.framework.Assert.assertTrue;

public class SetupFingerprintMFARequestEntityTest {
    @Mock
    DeviceInfoEntity deviceInfo;
    @InjectMocks
    SetupFingerprintMFARequestEntity setupFingerprintMFARequestEntity;

    @Before
    public void setUp() {
        setupFingerprintMFARequestEntity = new SetupFingerprintMFARequestEntity();
    }

    @Test
    public void setClientID() throws Exception {

        setupFingerprintMFARequestEntity.setClient_id("ClientID");

        Assert.assertEquals("ClientID", setupFingerprintMFARequestEntity.getClient_id());
    }


    @Test
    public void setLogoURL() throws Exception {

        setupFingerprintMFARequestEntity.setLogoUrl("LogoURL");

        Assert.assertEquals("LogoURL", setupFingerprintMFARequestEntity.getLogoUrl());
    }

    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        setupFingerprintMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(setupFingerprintMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(setupFingerprintMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(setupFingerprintMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(setupFingerprintMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(setupFingerprintMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }
}


//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme