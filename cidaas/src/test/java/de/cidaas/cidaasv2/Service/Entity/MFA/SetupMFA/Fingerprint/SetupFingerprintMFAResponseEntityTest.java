package de.cidaas.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFAResponseDataEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFAResponseEntity;

public class SetupFingerprintMFAResponseEntityTest {
    @Mock
    SetupFingerprintMFAResponseDataEntity data;
    @InjectMocks
    SetupFingerprintMFAResponseEntity setupFingerprintMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void setSuccess() {
        setupFingerprintMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(setupFingerprintMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        setupFingerprintMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, setupFingerprintMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new SetupFingerprintMFAResponseDataEntity();

        data.setSt("Test");
        setupFingerprintMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", setupFingerprintMFAResponseEntity.getData().getSt());


    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme