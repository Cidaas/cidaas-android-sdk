package com.example.cidaasv2.Service.Entity.ConsentManagement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ConsentResponseEntityTest {

    ConsentResponseDataEntity data;

    ConsentResponseEntity consentResponseEntity;

    @Before
    public void setUp() {

    }

    @Test
    public void getSuccess()
    {
        consentResponseEntity=new ConsentResponseEntity();
        consentResponseEntity.setSuccess(true);
        assertTrue(consentResponseEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        consentResponseEntity=new ConsentResponseEntity();
        consentResponseEntity.setStatus(417);
        assertTrue(consentResponseEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        consentResponseEntity= new ConsentResponseEntity();
        consentResponseEntity.setSuccess(true);
        consentResponseEntity.setData(data);
        Assert.assertEquals(consentResponseEntity.getData(),data);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme