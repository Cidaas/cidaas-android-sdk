package com.example.cidaasv2.Service.Repository.Tenant;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import timber.log.Timber;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.Mockito.when;

public class TenantServiceTest {
    @Mock
    CidaassdkService service;

     @Mock
    Context context;

     @Mock
    TenantService shared;

    @InjectMocks
    TenantService tenantService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetShared() throws Exception {
        TenantService result = TenantService.getShared(context);
        Assert.assertThat(new TenantService(context), samePropertyValuesAs(result));
    }


    @Test
    public void testGetTenantInfo() throws Exception {
//        when(new TenantService(context).service).thenReturn(service);

        Assert.assertTrue(service.getInstance()==null);


        tenantService.getTenantInfo("baseurl", new Result<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {
                Timber.e(result.getData().getTenant_name());
            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e(error.ErrorMessage);
            }
        });
    }

    @Test
    public void testGetTenantInfoForEmptyBaseurl() throws Exception {
        when(service.getInstance()).thenReturn(null);
        TenantService sam=new TenantService(context);

        tenantService.getTenantInfo("", new Result<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {
                Timber.e(result.getData().getTenant_name());
            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e(error.ErrorMessage);
            }
        });
    }


    @Test
    public void testTenantService() throws Exception{
       service.getInstance().getTenantInfo("");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme