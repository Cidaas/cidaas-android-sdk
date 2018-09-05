package com.example.cidaasv2.Service.Entity.ClientInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ClientInfoEntityTest {

    ClientInfoDataEntity data;

    ClientInfoEntity clientInfoEntity;

    @Before
    public void setUp() {

    }


    @Test
    public void getSuccess()
    {
        ClientInfoEntity clientInfoEntity=new ClientInfoEntity();
        clientInfoEntity.setSuccess(true);
        assertTrue(clientInfoEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        ClientInfoEntity clientInfoEntity=new ClientInfoEntity();
        clientInfoEntity.setStatus(417);
        assertTrue(clientInfoEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        ClientInfoEntity clientInfoEntity=new ClientInfoEntity();
        clientInfoEntity.setSuccess(true);
        clientInfoEntity.setData(data);
        Assert.assertEquals(clientInfoEntity.getData(),data);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme