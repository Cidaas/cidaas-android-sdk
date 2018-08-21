package com.example.cidaasv2.Service.Entity.ConsentManagement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class ConsentManagementAcceptResponseEntityTest {
    @Mock
    ConsentManagementResponseDataEntity data;
    @InjectMocks
    ConsentManagementAcceptResponseEntity consentManagementAcceptResponseEntity=new ConsentManagementAcceptResponseEntity();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
    public void getData()
    {

        consentManagementAcceptResponseEntity.setSuccess(true);
        consentManagementAcceptResponseEntity.setData(data);
        Assert.assertEquals(consentManagementAcceptResponseEntity.getData(),data);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme