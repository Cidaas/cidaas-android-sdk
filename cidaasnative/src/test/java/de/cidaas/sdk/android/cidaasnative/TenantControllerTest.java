package de.cidaas.sdk.android.cidaasnative;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import java.util.concurrent.CountDownLatch;

import de.cidaas.sdk.android.cidaasnative.data.entity.tenantinfo.TenantInfoEntity;
import de.cidaas.sdk.android.cidaasnative.domain.controller.tenant.TenantController;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

@Ignore
public class TenantControllerTest {
    Context context;

    TenantController tenantController = new TenantController(context);
    final CountDownLatch latch = new CountDownLatch(1);


    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
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
        tenantController.getTenantInfo("", new EventResult<TenantInfoEntity>() {
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
        tenantController.getTenantInfo("base", new EventResult<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {
                Assert.assertTrue("Failed", result.getStatus() == 200);
            }

            @Override
            public void failure(WebAuthError error) {
                //   Assert.assertTrue(error.getErrorMessage().equals("base url must not be empty"));

            }
        });
    }


    @Test
    public void testGetClientInfoFail() throws Exception {

        Context context = Mockito.mock(Context.class);


        MockWebServer server = new MockWebServer();
        String domainURL = server.url("").toString();
        server.url("/public-srv/Clientinfo/basic");
        server.enqueue(new MockResponse());


        CidaasHelper.baseurl = domainURL;


        tenantController.getTenantInfo("localhost:234235", new EventResult<TenantInfoEntity>() {
            @Override
            public void success(TenantInfoEntity result) {

            }

            @Override
            public void failure(WebAuthError error) {
                Timber.e("Success");
            }
        });


    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme