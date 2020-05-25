package de.cidaas.cidaasv2.Service.Entity.MFA.MFAList;

import org.junit.Assert;
import org.junit.Test;

public class MFAListResponseEntityTest {
    //Field data of type MFAListResponseDataEntity[] - was not mocked since Mockito doesn't mock arrays
    MFAListResponseEntity mFAListResponseEntity = new MFAListResponseEntity();

    @Test
    public void testGetData() throws Exception {
        MFAListResponseDataEntity Entity = new MFAListResponseDataEntity();
        Entity.set_id("Id");

        MFAListResponseDataEntity[] result = {Entity};
        mFAListResponseEntity.setData(result);
        Assert.assertEquals("Id", mFAListResponseEntity.getData()[0].get_id());
    }


    @Test
    public void setSuccess() {
        mFAListResponseEntity.setSuccess(true);
        junit.framework.Assert.assertTrue(mFAListResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        mFAListResponseEntity.setStatus(27);
        junit.framework.Assert.assertEquals(27, mFAListResponseEntity.getStatus());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme