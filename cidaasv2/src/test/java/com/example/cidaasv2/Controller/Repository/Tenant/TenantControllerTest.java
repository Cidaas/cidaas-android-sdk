package com.example.cidaasv2.Controller.Repository.Tenant;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
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
        TenantController result = TenantController.getShared(context);
        Assert.assertThat(new TenantController(context), samePropertyValuesAs(result));
    }

    @Test
    public void testGetTenantInfoBaseurlNull() throws Exception {

        tenantController.getTenantInfo("", new Result<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
              Assert.assertTrue(error.getErrorMessage().equals("base url must not be empty"));

            }
        });
    }

    @Test
    public void testGetTenantInfo() throws Exception {
         tenantController.getTenantInfo("https://nightlybuild.cidaas.de", new Result<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {
              Assert.assertTrue(result.getStatus()==00);
            }
            @Override
            public void failure(WebAuthError error) {
                Assert.assertTrue(error.getErrorMessage().equals("base url must not be empty"));

            }
        });
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme