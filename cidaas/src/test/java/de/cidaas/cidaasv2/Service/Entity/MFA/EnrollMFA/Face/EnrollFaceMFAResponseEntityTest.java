package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.Face;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFAResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceResponseDataEntity;

public class EnrollFaceMFAResponseEntityTest {

    EnrollFaceResponseDataEntity data;
    EnrollFaceMFAResponseEntity enrollFaceMFAResponseEntity;

    @Before
    public void setUp() {

        enrollFaceMFAResponseEntity = new EnrollFaceMFAResponseEntity();
    }

    @Test
    public void setSuccess() {
        enrollFaceMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(enrollFaceMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        enrollFaceMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, enrollFaceMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new EnrollFaceResponseDataEntity();

        data.setSub("Test");
        enrollFaceMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", enrollFaceMFAResponseEntity.getData().getSub());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme