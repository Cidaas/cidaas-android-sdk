package com.example.cidaasv2.Helper.Entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommonErrorEntityTest {
    private CommonErrorEntity commonErrorEntity;

    @Before
    public void setUp() {
        commonErrorEntity = new CommonErrorEntity();
        commonErrorEntity.setError_description("Error");
    }

    @Test
    public void Success(){

    commonErrorEntity.setSuccess(true);
        Assert.assertTrue(commonErrorEntity.isSuccess());

    }

    @Test
    public void Status(){

        commonErrorEntity.setStatus(123);
        Assert.assertEquals(123,commonErrorEntity.getStatus());
    }

    @Test
    public void setRefnumber(){

        commonErrorEntity.setRefnumber("refNumber");
        Assert.assertEquals("refNumber",commonErrorEntity.getRefnumber());
    }

    @Test
    public void setError_description(){
        commonErrorEntity.setError_description("ErrorDesc");
        Assert.assertEquals("ErrorDesc",commonErrorEntity.getError_description());
    }
}