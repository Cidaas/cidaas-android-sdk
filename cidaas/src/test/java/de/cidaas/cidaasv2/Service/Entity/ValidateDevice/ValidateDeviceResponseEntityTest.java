package de.cidaas.cidaasv2.Service.Entity.ValidateDevice;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class ValidateDeviceResponseEntityTest {

    ValidateDeviceResponseDataEntity data;

    ValidateDeviceResponseEntity validateDeviceResponseEntity;

    @Before
    public void setUp() {
        validateDeviceResponseEntity = new ValidateDeviceResponseEntity();
    }

    @Test
    public void setSuccess() {
        validateDeviceResponseEntity.setSuccess(true);
        Assert.assertTrue(validateDeviceResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        validateDeviceResponseEntity.setStatus(27);
        Assert.assertEquals(27, validateDeviceResponseEntity.getStatus());

    }

    @Test
    public void setValidateDeviceResponseEntity() {
        data = new ValidateDeviceResponseDataEntity();
        data.setUsage_pass("Test");
        validateDeviceResponseEntity.setData(data);
        Assert.assertEquals("Test", validateDeviceResponseEntity.getData().getUsage_pass());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme