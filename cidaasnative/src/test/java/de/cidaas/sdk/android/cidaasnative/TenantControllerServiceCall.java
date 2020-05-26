package de.cidaas.sdk.android.cidaasnative;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.CountDownLatch;

import de.cidaas.sdk.android.cidaasnative.data.Entity.TenantInfo.TenantInfoEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Controller.Tenant.TenantController;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

public class TenantControllerServiceCall {


    Context context = Mockito.mock(Context.class);

    TenantController tenantController = new TenantController(context);


    @Test
    public void testWebClient() throws Exception {

        try {
            Timber.e("Success");

            final MockResponse response = new MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .setBody("{\n" +
                            "    \"success\": true,\n" +
                            "    \"status\": 200,\n" +
                            "    \"data\": {\n" +
                            "        \"tenant_name\": \"Raja Developers\",\n" +
                            "        \"allowLoginWith\": [\n" +
                            "            \"EMAIL\",\n" +
                            "            \"MOBILE\",\n" +
                            "            \"USER_NAME\"\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}");

            MockWebServer server = new MockWebServer();
            String domainURL = server.url("").toString();
            server.url("/public-srv/tenantinfo/basic");


            server.enqueue(response);
            CidaasHelper.baseurl = domainURL;

            final CountDownLatch latch = new CountDownLatch(1);
            tenantController.getTenantInfo(StringUtils.chop(CidaasHelper.baseurl), new EventResult<TenantInfoEntity>() {
                @Override
                public void success(TenantInfoEntity result) {

                    Assert.assertEquals("Raja Developers", result.getData().getTenant_name());
                    latch.countDown();

                }

                @Override
                public void failure(WebAuthError error) {
                    Assert.assertEquals("Cidaas developer", error.getErrorMessage());
                    latch.countDown();
                }
            });
            latch.await();
            //Thread.sleep(3000);
            Timber.e("Success");

        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), true, true);
            Assert.assertFalse(e.getMessage(), true);
        }
    }
}
