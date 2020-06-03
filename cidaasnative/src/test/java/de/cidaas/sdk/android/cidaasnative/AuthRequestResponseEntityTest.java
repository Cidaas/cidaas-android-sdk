package de.cidaas.sdk.android.cidaasnative;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.entity.authrequest.AuthRequestResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.requestid.RequestIDEntity;

import static junit.framework.TestCase.assertTrue;

public class AuthRequestResponseEntityTest {

    RequestIDEntity data;

    AuthRequestResponseEntity authRequestResponseEntity;

    @Before
    public void setUp() {

    }

    @Test
    public void getSuccess() {
        AuthRequestResponseEntity authRequestResponseEntity = new AuthRequestResponseEntity();
        authRequestResponseEntity.setSuccess(true);
        assertTrue(authRequestResponseEntity.isSuccess());

    }

    @Test
    public void getStatus() {

        AuthRequestResponseEntity authRequestResponseEntity = new AuthRequestResponseEntity();
        authRequestResponseEntity.setStatus(417);
        assertTrue(authRequestResponseEntity.getStatus() == 417);

    }

    @Test
    public void getData() {

        AuthRequestResponseEntity authRequestResponseEntity = new AuthRequestResponseEntity();
        authRequestResponseEntity.setSuccess(true);
        authRequestResponseEntity.setData(data);
        Assert.assertEquals(authRequestResponseEntity.getData(), data);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme