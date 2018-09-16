package com.example.cidaasv2.Service.Register.RegistrationSetup;

import org.junit.Assert;
import org.junit.Test;

public class RegistrationSetupFieldDefenitionTest {
    RegistrationSetupFieldDefenition registrationSetupFieldDefenition = new RegistrationSetupFieldDefenition();

    @Test
    public void testGetAttributesKeys() throws Exception {

        String[] result ={"Test"};
                registrationSetupFieldDefenition.setAttributesKeys(result);
//        Assert.assertArrayEquals(new String[]{"replaceMeWithExpectedResult"}, result);
        Assert.assertEquals("Test", registrationSetupFieldDefenition.getAttributesKeys()[0]);
    }


    @Test
    public void setReferenceNumber()
    {
        registrationSetupFieldDefenition.setMatchWith("TestData");
        junit.framework.Assert.assertEquals("TestData",registrationSetupFieldDefenition.getMatchWith());
    }

    @Test
    public void setStatus(){
        registrationSetupFieldDefenition.setMaxLength(27);
        junit.framework.Assert.assertEquals(27,registrationSetupFieldDefenition.getMaxLength());

    }


    @Test
    public void code(){
        registrationSetupFieldDefenition.setMinLength(27);
        junit.framework.Assert.assertEquals(27,registrationSetupFieldDefenition.getMinLength());

    }



    @Test
    public void setApplyPasswordPoly(){
        registrationSetupFieldDefenition.setApplyPasswordPoly(true);
        junit.framework.Assert.assertTrue(registrationSetupFieldDefenition.isApplyPasswordPoly());

    }


    @Test
    public void setSuccess(){
        registrationSetupFieldDefenition.setVerificationRequired(true);
        junit.framework.Assert.assertTrue(registrationSetupFieldDefenition.isVerificationRequired());

    }

    @Test
    public void setMoreInfo()
    {
        registrationSetupFieldDefenition.setName("TestData");
        junit.framework.Assert.assertEquals("TestData",registrationSetupFieldDefenition.getName());
    }

    @Test
    public void getName()
    {
        registrationSetupFieldDefenition.setLanguage("TestData");
        junit.framework.Assert.assertEquals("TestData",registrationSetupFieldDefenition.getLanguage());
    }


    @Test
    public void getLanguage()
    {
        registrationSetupFieldDefenition.setLocale("TestData");
        junit.framework.Assert.assertEquals("TestData",registrationSetupFieldDefenition.getLocale());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme