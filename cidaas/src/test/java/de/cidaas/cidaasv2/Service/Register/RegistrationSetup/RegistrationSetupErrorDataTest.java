package de.cidaas.cidaasv2.Service.Register.RegistrationSetup;

import junit.framework.Assert;

import org.junit.Test;

public class RegistrationSetupErrorDataTest {


    RegistrationSetupErrorData registrationSetupErrorData = new RegistrationSetupErrorData();

    @Test
    public void Error() {
        registrationSetupErrorData.setError("TestData");
        Assert.assertEquals("TestData", registrationSetupErrorData.getError());
    }

    @Test
    public void Type() {
        registrationSetupErrorData.setType("TestData");
        Assert.assertEquals("TestData", registrationSetupErrorData.getType());
    }

    @Test
    public void setReferenceNumber() {
        registrationSetupErrorData.setReferenceNumber("TestData");
        Assert.assertEquals("TestData", registrationSetupErrorData.getReferenceNumber());
    }

    @Test
    public void setStatus() {
        registrationSetupErrorData.setStatus(27);
        Assert.assertEquals(27, registrationSetupErrorData.getStatus());

    }


    @Test
    public void code() {
        registrationSetupErrorData.setCode(27);
        Assert.assertEquals(27, registrationSetupErrorData.getCode());

    }


    @Test
    public void setMoreInfo() {
        registrationSetupErrorData.setMoreInfo("TestData");
        Assert.assertEquals("TestData", registrationSetupErrorData.getMoreInfo());
    }

}


//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme