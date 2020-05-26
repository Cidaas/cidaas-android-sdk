package de.cidaas.cidaasv2.Service.Entity.ResetPassword;

import junit.framework.Assert;

import org.junit.Test;

public class ResetPasswordResultDataEntityTest {

    ResetPasswordResultDataEntity resetPasswordResultDataEntity = new ResetPasswordResultDataEntity();


    @Test
    public void TestResult() {
        resetPasswordResultDataEntity.setRprq("RPRQ");
        Assert.assertEquals("RPRQ", resetPasswordResultDataEntity.getRprq());
    }

    @Test
    public void settest() {
        resetPasswordResultDataEntity.setReset_initiated(true);
        Assert.assertTrue(resetPasswordResultDataEntity.isReset_initiated());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme