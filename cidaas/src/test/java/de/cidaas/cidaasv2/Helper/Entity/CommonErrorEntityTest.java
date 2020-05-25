package de.cidaas.cidaasv2.Helper.Entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Helper.Entity.CommonErrorEntity;

public class CommonErrorEntityTest {
    CommonErrorEntity commonErrorEntity = new CommonErrorEntity();

    @Before
    public void setUp() {


    }

    @Test
    public void Success() {

        commonErrorEntity.setSuccess(true);
        Assert.assertTrue(commonErrorEntity.isSuccess());

    }

    @Test
    public void Status() {

        commonErrorEntity.setStatus(123);
        Assert.assertEquals(123, commonErrorEntity.getStatus());
    }

    @Test
    public void Error() {
        commonErrorEntity.setError("Error");
        Assert.assertEquals("Error", commonErrorEntity.getError());

    }


    @Test
    public void setRefnumber() {

        commonErrorEntity.setRefnumber("refNumber");
        Assert.assertEquals("refNumber", commonErrorEntity.getRefnumber());
    }

    @Test
    public void setError_description() {
        commonErrorEntity.setError_description("ErrorDesc");
        Assert.assertEquals("ErrorDesc", commonErrorEntity.getError_description());
    }
}


//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme