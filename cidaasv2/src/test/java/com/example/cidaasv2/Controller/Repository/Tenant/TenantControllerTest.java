package com.example.cidaasv2.Controller.Repository.Tenant;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class TenantControllerTest {
    @Mock
    Context context;
    @Mock
    TenantController shared;
    @InjectMocks
    TenantController tenantController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetShared() throws Exception {
       // TenantController result = TenantController.getShared(null);
     //   Assert.assertEquals(new TenantController(null), result);
    }

    @Test
    public void testGetTenantInfo() throws Exception {
        tenantController.getTenantInfo("baseurl", null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme