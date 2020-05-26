package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;


import static junit.framework.Assert.assertTrue;

public class EnrollTOTPMFARequestEntityTest {
    @Mock
    DeviceInfoEntity deviceInfo;
    @InjectMocks
    EnrollTOTPMFARequestEntity enrollTOTPMFARequestEntity;

    @Before
    public void setUp() {
        enrollTOTPMFARequestEntity = new EnrollTOTPMFARequestEntity();
    }


    @Test
    public void getStatusID() {
        enrollTOTPMFARequestEntity.setStatusId("Status_ID");
        assertTrue(enrollTOTPMFARequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getCode() {
        enrollTOTPMFARequestEntity.setVerifierPassword("Code");
        assertTrue(enrollTOTPMFARequestEntity.getVerifierPassword().equals("Code"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        enrollTOTPMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(enrollTOTPMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(enrollTOTPMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(enrollTOTPMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(enrollTOTPMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(enrollTOTPMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        enrollTOTPMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(enrollTOTPMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme