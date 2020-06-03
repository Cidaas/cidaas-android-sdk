package de.cidaas.sdk.android.cidaasnative.Deduplication;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.entity.deduplication.DeduplicationResponseDataEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.deduplication.DeduplicationResponseEntity;

public class DeduplicationResponseEntityTest {

    DeduplicationResponseDataEntity data;

    DeduplicationResponseEntity deduplicationResponseEntity;

    @Before
    public void setUp() {

        deduplicationResponseEntity = new DeduplicationResponseEntity();
    }

    @Test
    public void setSuccess() {
        deduplicationResponseEntity.setSuccess(true);
        Assert.assertTrue(deduplicationResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        deduplicationResponseEntity.setStatus(27);
        Assert.assertEquals(27, deduplicationResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new DeduplicationResponseDataEntity();

        data.setEmail("Code");
        deduplicationResponseEntity.setData(data);
        Assert.assertEquals("Code", deduplicationResponseEntity.getData().getEmail());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme