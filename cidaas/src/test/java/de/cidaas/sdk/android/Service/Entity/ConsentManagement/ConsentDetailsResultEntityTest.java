package de.cidaas.sdk.android.Service.Entity.ConsentManagement;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentDetailsResultEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentSettingsReponseDataEntity;

import static junit.framework.TestCase.assertTrue;

public class ConsentDetailsResultEntityTest {

    ConsentSettingsReponseDataEntity data;

    ConsentDetailsResultEntity consentDetailsResultEntity = new ConsentDetailsResultEntity();

    @Before
    public void setUp() {

    }

    @Test
    public void getSuccess() {
        consentDetailsResultEntity.setSuccess(true);
        assertTrue(consentDetailsResultEntity.isSuccess());

    }

    @Test
    public void getStatus() {

        consentDetailsResultEntity.setStatus(417);
        assertTrue(consentDetailsResultEntity.getStatus() == 417);

    }

    @Test
    public void setData() {
        data = new ConsentSettingsReponseDataEntity();

        data.setCollectionMethod("Code");
        consentDetailsResultEntity.setData(data);
        Assert.assertEquals("Code", consentDetailsResultEntity.getData().getCollectionMethod());

    }
}


//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme