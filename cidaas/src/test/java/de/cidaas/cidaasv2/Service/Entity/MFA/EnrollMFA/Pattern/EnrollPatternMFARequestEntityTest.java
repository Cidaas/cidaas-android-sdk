package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;


import static junit.framework.Assert.assertTrue;

public class EnrollPatternMFARequestEntityTest {
    @Mock
    DeviceInfoEntity deviceInfo;
    @InjectMocks
    EnrollPatternMFARequestEntity enrollPatternMFARequestEntity;

    @Before
    public void setUp() {
        enrollPatternMFARequestEntity = new EnrollPatternMFARequestEntity();
    }

    @Test
    public void getStatusID() {
        enrollPatternMFARequestEntity.setStatusId("Status_ID");
        assertTrue(enrollPatternMFARequestEntity.getStatusId().equals("Status_ID"));
    }

    @Test
    public void getCode() {
        enrollPatternMFARequestEntity.setVerifierPassword("Code");
        assertTrue(enrollPatternMFARequestEntity.getVerifierPassword().equals("Code"));
    }


    @Test
    public void getDeviceInfoEntity() {
        DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setPushNotificationId("push");
        deviceInfoEntity.setDeviceId("deviceID");
        deviceInfoEntity.setDeviceMake("deviceMake");
        deviceInfoEntity.setDeviceModel("deviceModel");
        deviceInfoEntity.setDeviceVersion("deviceVersion");

        enrollPatternMFARequestEntity.setDeviceInfo(deviceInfoEntity);

        assertTrue(enrollPatternMFARequestEntity.getDeviceInfo().getDeviceId().equals("deviceID"));
        assertTrue(enrollPatternMFARequestEntity.getDeviceInfo().getDeviceMake().equals("deviceMake"));
        assertTrue(enrollPatternMFARequestEntity.getDeviceInfo().getDeviceModel().equals("deviceModel"));
        assertTrue(enrollPatternMFARequestEntity.getDeviceInfo().getDeviceVersion().equals("deviceVersion"));
        assertTrue(enrollPatternMFARequestEntity.getDeviceInfo().getPushNotificationId().equals("push"));
    }

    @Test
    public void getUserDeviceId() {
        enrollPatternMFARequestEntity.setUserDeviceId("UserDeveiceId");
        assertTrue(enrollPatternMFARequestEntity.getUserDeviceId().equals("UserDeveiceId"));
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme