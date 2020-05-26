package de.cidaas.cidaasv2.Service.Scanned;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Scanned.ScannedResponseDataEntity;
import de.cidaas.sdk.android.cidaas.Service.Scanned.ScannedResponseEntity;

public class ScannedResponseTest {
    ScannedResponseDataEntity data;
    ScannedResponseEntity scannedResponseEntity;

    @Before
    public void setUp() {
        scannedResponseEntity = new ScannedResponseEntity();
    }


    @Test
    public void setSuccess() {
        scannedResponseEntity.setSuccess(true);
        Assert.assertTrue(scannedResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        scannedResponseEntity.setStatus(27);
        Assert.assertEquals(27, scannedResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new ScannedResponseDataEntity();

        data.setUserDeviceId("Code");
        scannedResponseEntity.setData(data);
        Assert.assertEquals("Code", scannedResponseEntity.getData().getUserDeviceId());

    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme