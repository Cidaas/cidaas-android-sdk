package com.example.cidaasv2.Controller.Repository.Tenant;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoDataEntity;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.eq;

public class TenantControllerTest {
    Context context;

    TenantController tenantController=new TenantController(context);


    @Captor
    private ArgumentCaptor<Result<TenantInfoEntity>> cb;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetShared() throws Exception {
        TenantController result = TenantController.getShared(context);
        Assert.assertThat(new TenantController(context), samePropertyValuesAs(result));
    }

    @Test
    public void testGetSharednull() throws Exception {
        TenantController result = TenantController.getShared(null);
        Assert.assertThat(new TenantController(null), samePropertyValuesAs(result));
    }

    @Test
    public void testGetSharedexception() throws Exception {
        TenantController result = TenantController.getShared(null);

        Assert.assertThat(new TenantController(context), samePropertyValuesAs(result));
       // throw new IllegalAccessException();
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
              Assert.assertTrue("Failed",result.getStatus()==200);
            }
            @Override
            public void failure(WebAuthError error) {
                Assert.assertTrue(error.getErrorMessage().equals("base url must not be empty"));

            }
        });
    }

    @Test
    public void testGetTenantInfoServiceFaliure() throws Exception {
        TenantController tenantService123= Mockito.mock(TenantController.class);

        TenantInfoEntity tenantInfoEntity=new TenantInfoEntity();
        TenantInfoDataEntity tenantInfoDataEntity=new TenantInfoDataEntity();
        tenantInfoDataEntity.setTenant_name("TenantName");
        tenantInfoEntity.setData(tenantInfoDataEntity);
        tenantInfoEntity.setStatus(204);
        tenantInfoEntity.setSuccess(true);





        tenantService123.getTenantInfo("https://nightlybuild.cidaas.de", new Result<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {
                Assert.assertEquals("TenantName",result.getData().getTenant_name());
            }

            @Override
            public void failure(WebAuthError error) {

            }
        });
        Mockito.verify(tenantService123).getTenantInfo(eq("https://nightlybuild.cidaas.de"),cb.capture());


        cb.getValue().success(tenantInfoEntity);

    }


    @Test
    public void testGetTenantInfoService() throws Exception {


        TenantController tenantService123= Mockito.mock(TenantController.class);

        TenantInfoEntity tenantInfoEntity=new TenantInfoEntity();
        TenantInfoDataEntity tenantInfoDataEntity=new TenantInfoDataEntity();
        tenantInfoDataEntity.setTenant_name("TenantName");
        tenantInfoEntity.setData(tenantInfoDataEntity);
        tenantInfoEntity.setStatus(204);
        tenantInfoEntity.setSuccess(true);


        tenantService123.getTenantInfo("https://nightlybuild.cidaas.de", new Result<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {
                Assert.assertEquals("TenantName",result.getData().getTenant_name());
            }

            @Override
            public void failure(WebAuthError error) {
               Assert.assertEquals(500,error.getErrorCode());
            }
        });
        Mockito.verify(tenantService123).getTenantInfo(eq("https://nightlybuild.cidaas.de"),cb.capture());


        WebAuthError error=WebAuthError.getShared(context).customException(500,"InternalError",406);
        cb.getValue().failure(error);
       // cb.getValue().success(tenantInfoEntity);

    }



}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme