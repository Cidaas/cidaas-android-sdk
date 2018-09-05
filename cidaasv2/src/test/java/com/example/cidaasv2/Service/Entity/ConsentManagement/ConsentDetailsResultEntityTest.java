package com.example.cidaasv2.Service.Entity.ConsentManagement;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ConsentDetailsResultEntityTest {

    ConsentDetailsResultDataEntity data;

    ConsentDetailsResultEntity consentDetailsResultEntity=new ConsentDetailsResultEntity();

    @Before
    public void setUp() {

    }

    @Test
    public void getSuccess()
    {
        consentDetailsResultEntity.setSuccess(true);
        assertTrue(consentDetailsResultEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        consentDetailsResultEntity.setStatus(417);
        assertTrue(consentDetailsResultEntity.getStatus()==417);

    }
   }



//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme