package de.cidaas.sdk.android.Service.Entity.MFA.SetupMFA.BackupCode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.SetupMFA.BackupCode.BackupCodeData;
import de.cidaas.sdk.android.service.entity.mfa.SetupMFA.BackupCode.UserDeviceInfo;


public class BackupCodeDataTest {

    BackupCodeData backupCodeData;


    @Before
    public void setUp() {
        backupCodeData = new BackupCodeData();
    }

    @Test
    public void setUsedDeviceInfo() {
        UserDeviceInfo userDeviceInfo = new UserDeviceInfo();
        userDeviceInfo.setUsedTime("UsedTime");
        backupCodeData.setUsedDeviceInfo(userDeviceInfo);
        Assert.assertEquals("UsedTime", backupCodeData.getUsedDeviceInfo().getUsedTime());
    }

    @Test
    public void getCode() {
        backupCodeData.setCode("Code");
        Assert.assertEquals("Code", backupCodeData.getCode());
    }

    @Test
    public void getStatusId() {
        backupCodeData.setStatusId("StatusId");
        Assert.assertEquals("StatusId", backupCodeData.getStatusId());
    }

    @Test
    public void setUsedTime() {
        backupCodeData.setUsedTime("Used_Time");
        Assert.assertEquals("Used_Time", backupCodeData.getUsedTime());
    }

    @Test
    public void getUsed() {
        backupCodeData.setUsed(true);
        Assert.assertEquals(true, backupCodeData.isUsed());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme