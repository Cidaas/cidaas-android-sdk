package de.cidaas.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFAResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPResponseDataEntity;

public class EnrollTOTPMFAResponseEntityTest {
    @Mock
    EnrollTOTPResponseDataEntity data;
    @InjectMocks
    EnrollTOTPMFAResponseEntity enrollTOTPMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setSuccess() {
        enrollTOTPMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(enrollTOTPMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        enrollTOTPMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, enrollTOTPMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new EnrollTOTPResponseDataEntity();

        data.setSub("Test");
        enrollTOTPMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", enrollTOTPMFAResponseEntity.getData().getSub());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme