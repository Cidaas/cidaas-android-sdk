package de.cidaas.sdk.android.Service.Entity.MFA.TOTPEntity;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.TOTPEntity.TOTPEntity;


public class TOTPEntityTest {


    TOTPEntity totpEntity = new TOTPEntity();

    @Test
    public void setName() {
        totpEntity.setName("Name");
        Assert.assertEquals("Name", totpEntity.getName());

    }

    @Test
    public void setIssuer() {
        totpEntity.setIssuer("Issuer");
        Assert.assertEquals("Issuer", totpEntity.getIssuer());
    }

    @Test
    public void setTimer_count() {
        totpEntity.setTimer_count("Timer_count");
        Assert.assertEquals("Timer_count", totpEntity.getTimer_count());
    }

    @Test
    public void setTotp_string() {
        totpEntity.setTotp_string("TOTP");
        Assert.assertEquals("TOTP", totpEntity.getTotp_string());
    }
}


//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme