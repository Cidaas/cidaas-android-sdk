package com.example.cidaasv2.Service.Repository.Tenant;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import timber.log.Timber;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

@RunWith(RobolectricTestRunner.class)
public class TenantServiceTest {
    CidaassdkService service;
    Context context;
    TenantService tenantService;

    @Before
    public void setUp() {
        context= RuntimeEnvironment.application;
       tenantService=new TenantService(context);
    }

    @Test
    public void testGetShared() throws Exception {
        TenantService result = TenantService.getShared(context);
        Assert.assertThat(new TenantService(context), samePropertyValuesAs(result));
    }


    @Test
    public void testGetTenantInfo() throws Exception {
//        when(new TenantService(context).service).thenReturn(service);

//        Assert.assertTrue(service.getInstance()==null);


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
    public void testGetTenantInfoForSuccess() throws Exception {



        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nighltybuild.cidaas.de")
                .client(client)
                //TODO Add your Retrofit parameters here
                .build();

        TenantInfoEntity tenantInfoEntity=new TenantInfoEntity();
       // Call<TenantInfoEntity> call=Mockito.mockingDetails(Call<TenantInfoEntity> tenantInfoEntity);

       // doReturn(call).when(sdkservice).getTenantInfo(anyString());




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
    public void testWebClient() throws  Exception{
        
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
