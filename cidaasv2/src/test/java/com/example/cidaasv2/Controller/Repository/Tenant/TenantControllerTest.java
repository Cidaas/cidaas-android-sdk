package com.example.cidaasv2.Controller.Repository.Tenant;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

public class TenantControllerTest {
    Context context;

    TenantController tenantController=new TenantController(context);


    @Before
    public void setUp() {

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
    public void testGetTenantInfoBaseurlcallback() throws Exception {


       Result<TenantInfoEntity> result=new Result<TenantInfoEntity>() {
           @Override
           public void success(TenantInfoEntity result) {
  //             TenantInfoEntity tenantInfoEntity =new TenantInfoEntity();
//               Assert.assertThat(tenantInfoEntity, samePropertyValuesAs(result));
           }

           @Override
           public void failure(WebAuthError error) {

           }
       };




         //Result<TenantInfoEntity> result= Mockito.mock(Result.class);

        final TenantInfoEntity entity=new TenantInfoEntity();



        tenantController.getTenantInfo("https://nightlybuild.cidaas.de", result);
   //     doReturn(new RuntimeException()).when(tenanetController).getTenantInfo("https://nightlybuild.cidaas.de",result);

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



}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme