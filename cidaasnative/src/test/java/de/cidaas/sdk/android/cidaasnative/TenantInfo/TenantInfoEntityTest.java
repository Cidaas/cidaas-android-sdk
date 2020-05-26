package de.cidaas.sdk.android.cidaasnative.TenantInfo;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.Entity.TenantInfo.TenantInfoDataEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.TenantInfo.TenantInfoEntity;

public class TenantInfoEntityTest {

    TenantInfoDataEntity data;


    TenantInfoEntity tenantInfoEntity;

    @Before
    public void setUp() {
        tenantInfoEntity = new TenantInfoEntity();
    }

    @Test
    public void setSuccess() {
        tenantInfoEntity.setSuccess(true);
        Assert.assertTrue(tenantInfoEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        tenantInfoEntity.setStatus(27);
        Assert.assertEquals(27, tenantInfoEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new TenantInfoDataEntity();

        data.setTenant_name("Test");
        tenantInfoEntity.setData(data);
        Assert.assertEquals("Test", tenantInfoEntity.getData().getTenant_name());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme