package de.cidaas.sdk.android.cidaasnative.TenantInfo;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.entity.tenantinfo.TenantInfoDataEntity;

public class TenantInfoDataEntityTest {
    TenantInfoDataEntity tenantInfoDataEntity = new TenantInfoDataEntity();

    @Test
    public void testGetAllowLoginWith() throws Exception {

        String[] result = {"Test"};
        tenantInfoDataEntity.setAllowLoginWith(result);
        Assert.assertEquals("Test", tenantInfoDataEntity.getAllowLoginWith()[0]);
    }

    @Test
    public void setTenantName() throws Exception {

        tenantInfoDataEntity.setTenant_name("Test");
        Assert.assertEquals("Test", tenantInfoDataEntity.getTenant_name());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme