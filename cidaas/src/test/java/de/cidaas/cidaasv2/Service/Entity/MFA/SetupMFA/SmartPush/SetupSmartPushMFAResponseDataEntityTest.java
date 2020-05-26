package de.cidaas.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush;

import org.junit.Assert;
import org.junit.Test;


public class SetupSmartPushMFAResponseDataEntityTest {
    SetupSmartPushMFAResponseDataEntity setupResponseDataEntity = new SetupSmartPushMFAResponseDataEntity();


    @Test
    public void setSecret() throws Exception {

        setupResponseDataEntity.setSecret("Test");

        Assert.assertEquals("Test", setupResponseDataEntity.getSecret());
    }

    @Test
    public void setOtpauth() throws Exception {

        setupResponseDataEntity.setT("Test");

        Assert.assertEquals("Test", setupResponseDataEntity.getT());
    }

    @Test
    public void setQueryString() throws Exception {

        setupResponseDataEntity.setD("Test");

        Assert.assertEquals("Test", setupResponseDataEntity.getD());
    }

    @Test
    public void setissuer() throws Exception {

        setupResponseDataEntity.setIssuer("test");

        Assert.assertEquals("test", setupResponseDataEntity.getIssuer());
    }

    @Test
    public void setl() throws Exception {

        setupResponseDataEntity.setL("test");

        Assert.assertEquals("test", setupResponseDataEntity.getL());
    }

    @Test
    public void setRandomNumber() throws Exception {

        setupResponseDataEntity.setRns("Test");

        Assert.assertEquals("Test", setupResponseDataEntity.getRns());
    }

    @Test
    public void setCurrent_status() throws Exception {

        setupResponseDataEntity.setCurrent_status("Test");

        Assert.assertEquals("Test", setupResponseDataEntity.getCurrent_status());
    }


    @Test
    public void setSt() throws Exception {

        setupResponseDataEntity.setSt("StatusId");

        Assert.assertEquals("StatusId", setupResponseDataEntity.getSt());
    }

    @Test
    public void setUdi() throws Exception {

        setupResponseDataEntity.setUdi("udi");

        Assert.assertEquals("udi", setupResponseDataEntity.getUdi());
    }

    @Test
    public void setRurl() throws Exception {

        setupResponseDataEntity.setRurl("Test");

        Assert.assertEquals("Test", setupResponseDataEntity.getRurl());
    }

    @Test
    public void setCid() throws Exception {

        setupResponseDataEntity.setCid("Test");

        Assert.assertEquals("Test", setupResponseDataEntity.getCid());
    }

    @Test
    public void setSub() throws Exception {

        setupResponseDataEntity.setSub("Test");

        Assert.assertEquals("Test", setupResponseDataEntity.getSub());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme