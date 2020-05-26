package de.cidaas.cidaasv2.Service.Entity.MFA.SetupMFA.Voice;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class SetupVoiceMFAResponseEntityTest {
    @Mock
    SetupVoiceMFAResponseDataEntity data;
    @InjectMocks
    SetupVoiceMFAResponseEntity setupVoiceMFAResponseEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void setSuccess() {
        setupVoiceMFAResponseEntity.setSuccess(true);
        Assert.assertTrue(setupVoiceMFAResponseEntity.isSuccess());

    }

    @Test
    public void setStatus() {
        setupVoiceMFAResponseEntity.setStatus(27);
        Assert.assertEquals(27, setupVoiceMFAResponseEntity.getStatus());

    }

    @Test
    public void setData() {
        data = new SetupVoiceMFAResponseDataEntity();

        data.setSt("Test");
        setupVoiceMFAResponseEntity.setData(data);
        Assert.assertEquals("Test", setupVoiceMFAResponseEntity.getData().getSt());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme