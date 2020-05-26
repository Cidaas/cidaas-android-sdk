package de.cidaas.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode;

import junit.framework.Assert;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.BackupCode.UserDeviceInfo;

public class UserDeviceInfoTest {

    UserDeviceInfo userDeviceInfo = new UserDeviceInfo();

    @Test
    public void AddTestFunction() {
        userDeviceInfo.setUsedTime("Used Time");
        Assert.assertEquals("Used Time", userDeviceInfo.getUsedTime());
    }

    @Test
    public void TestFunction() {
        userDeviceInfo.setUserAgent("User Agent");
        Assert.assertEquals("User Agent", userDeviceInfo.getUserAgent());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme