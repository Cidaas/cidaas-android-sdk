package de.cidaas.cidaasv2.Service.Entity.ConsentManagement;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.ConsentManagement.ConsentManagementResponseEntity;

import static junit.framework.TestCase.assertTrue;

public class ConsentManagementResponseEntityTest {

    ConsentManagementResponseEntity consentManagementResponseEntity = new ConsentManagementResponseEntity();

    @Test
    public void getSuccess() {
        consentManagementResponseEntity.setSuccess(true);
        assertTrue(consentManagementResponseEntity.isSuccess());

    }

    @Test
    public void getStatus() {

        consentManagementResponseEntity.setStatus(417);
        assertTrue(consentManagementResponseEntity.getStatus() == 417);

    }

    @Test
    public void getData() {

        consentManagementResponseEntity.setSuccess(true);
        consentManagementResponseEntity.setData("data");
        Assert.assertEquals(consentManagementResponseEntity.getData(), "data");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme