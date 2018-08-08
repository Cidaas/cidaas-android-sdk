package com.example.cidaasv2.Service.Entity.AuthRequest;

import com.example.cidaasv2.Service.Entity.RequestId.RequestIDEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

public class AuthRequestResponseEntityTest {
    @Mock
    RequestIDEntity data;
    @InjectMocks
    AuthRequestResponseEntity authRequestResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getSuccess()
    {
        AuthRequestResponseEntity authRequestResponseEntity=new AuthRequestResponseEntity();
        authRequestResponseEntity.setSuccess(true);
        assertTrue(authRequestResponseEntity.isSuccess());

    }
    @Test
    public void getStatus()
    {

        AuthRequestResponseEntity authRequestResponseEntity=new AuthRequestResponseEntity();
        authRequestResponseEntity.setStatus(417);
        assertTrue(authRequestResponseEntity.getStatus()==417);

    }
    @Test
    public void getData()
    {

        AuthRequestResponseEntity authRequestResponseEntity=new AuthRequestResponseEntity();
        authRequestResponseEntity.setSuccess(true);
        authRequestResponseEntity.setData(data);
        Assert.assertEquals(authRequestResponseEntity.getData(),data);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme