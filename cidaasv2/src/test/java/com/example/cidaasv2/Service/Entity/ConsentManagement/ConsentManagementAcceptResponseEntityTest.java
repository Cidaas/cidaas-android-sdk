package com.example.cidaasv2.Service.Entity.ConsentManagement;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ConsentManagementAcceptResponseEntityTest {



    ConsentManagementAcceptResponseEntity consentManagementAcceptResponseEntity=new ConsentManagementAcceptResponseEntity();

    @Before
    public void setUp() {

    }

    @Test
    public void getSuccess()
    {
        consentManagementAcceptResponseEntity.setSuccess(true);
        assertTrue(consentManagementAcceptResponseEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        consentManagementAcceptResponseEntity.setStatus(417);
        assertTrue(consentManagementAcceptResponseEntity.getStatus()==417);

    }


    @Test
    public void setData()
    {
        consentManagementAcceptResponseEntity.setData(true);
        assertTrue(consentManagementAcceptResponseEntity.isData());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme