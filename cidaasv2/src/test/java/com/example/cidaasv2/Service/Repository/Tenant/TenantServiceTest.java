package com.example.cidaasv2.Service.Repository.Tenant;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoDataEntity;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.cidaasv2.util.AuthenticationAPI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import timber.log.Timber;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(RobolectricTestRunner.class)
public class TenantServiceTest {

    CidaassdkService service;
    Context context;
    TenantService tenantService;

    AuthenticationAPI mockAPI;

    @Captor
    private ArgumentCaptor<Result<TenantInfoEntity>> cb;

    @Before
    public void setUp() throws Exception{
        context= RuntimeEnvironment.application;
        MockitoAnnotations.initMocks(this);
        tenantService=new TenantService(context);
        mockAPI=new AuthenticationAPI();

    }

    @Test
    public void testGetShared() throws Exception {
        TenantService result = TenantService.getShared(context);
        Assert.assertThat(new TenantService(context), samePropertyValuesAs(result));
    }


    @Test
    public void testGetTenantInfo() throws Exception {

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
        TenantService tenantService123=Mockito.mock(TenantService.class);

        TenantInfoEntity tenantInfoEntity=new TenantInfoEntity();
        TenantInfoDataEntity tenantInfoDataEntity=new TenantInfoDataEntity();
        tenantInfoDataEntity.setTenant_name("TenantName");
        tenantInfoEntity.setData(tenantInfoDataEntity);
        tenantInfoEntity.setStatus(200);
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
    public void testGetTenantInfoForFailure() throws Exception {
        TenantService tenantService123=Mockito.mock(TenantService.class);

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

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @Test
    public void testWeb() throws Exception{
        try{



            mockAPI.getDomain();
            mockAPI.willReturnTenant();

            tenantService.getTenantInfo(removeLastChar(mockAPI.getDomain()), new Result<TenantInfoEntity>() {
                @Override
                public void success(TenantInfoEntity result) {

                }

                @Override
                public void failure(WebAuthError error) {

                }
            });
        }
        catch (Exception e){

        }
    }

    @Test
    public void testWebClient() throws  Exception{

       try {


           final MockResponse response = new MockResponse().setResponseCode(200)
                   .addHeader("Content-Type", "application/json; charset=utf-8")
                   .setBody("\"{\n" +
                           "    \"success\": true,\n" +
                           "    \"status\": 200,\n" +
                           "    \"data\": {\n" +
                           "        \"tenant_name\": \"Cidaas Developers\",\n" +
                           "        \"allowLoginWith\": [\n" +
                           "            \"EMAIL\",\n" +
                           "            \"MOBILE\",\n" +
                           "            \"USER_NAME\"\n" +
                           "        ]\n" +
                           "    }\n" +
                           "}\"");

           MockWebServer server = new MockWebServer();
           server.shutdown();
           server.start(2717);
           server.url("/public-srv/tenantinfo/basic");


           server.enqueue(response);
           Cidaas.baseurl="https://"+server.getHostName();
           //server.start();


         //  RecordedRequest request=server.takeRequest();
    //       request.getBody();
         //  Assert.assertEquals("/public-srv/tenantinfo/basic",request.getRequestLine());

           tenantService.getTenantInfo("localhost", new Result<TenantInfoEntity>() {
               @Override
               public void success(TenantInfoEntity result) {
                   Assert.assertEquals("Cidaas developer",result.getData().getTenant_name());
               }

               @Override
               public void failure(WebAuthError error) {
                   Assert.assertEquals("Cidaas developer",error.getErrorMessage());
               }
           });




           server.getHostName();

       }
       catch (Exception e)
       {
           Assert.assertEquals(e.getMessage(),true,true);
           Assert.assertFalse(e.getMessage(),true);
       }

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
