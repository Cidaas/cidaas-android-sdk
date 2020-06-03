package de.cidaas.sdk.android.cidaasnative.Register.RegistrationSetup;

import org.junit.Assert;
import org.junit.Test;

import de.cidaas.sdk.android.cidaasnative.data.entity.register.registrationsetup.RegistrationSetupAttributesEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.register.registrationsetup.RegistrationSetupLocaleTextEntity;

public class RegistrationSetupLocaleTextEntityTest {


    //Field attributes of type RegistrationSetupAttributesEntity[] - was not mocked since Mockito doesn't mock arrays
    RegistrationSetupLocaleTextEntity registrationSetupLocaleTextEntity = new RegistrationSetupLocaleTextEntity();

    @Test
    public void testGetAttributes() throws Exception {
        RegistrationSetupAttributesEntity registrationSetupAttributesEntity = new RegistrationSetupAttributesEntity();
        registrationSetupAttributesEntity.setKey("key");
        RegistrationSetupAttributesEntity[] result = {registrationSetupAttributesEntity};
        registrationSetupLocaleTextEntity.setAttributes(result);
//        Assert.assertArrayEquals(new RegistrationSetupAttributesEntity[]{new RegistrationSetupAttributesEntity()}, result);
        Assert.assertEquals("key", registrationSetupLocaleTextEntity.getAttributes()[0].getKey());
    }


    @Test
    public void setLocale() {
        registrationSetupLocaleTextEntity.setLocale("TestData");
        junit.framework.Assert.assertEquals("TestData", registrationSetupLocaleTextEntity.getLocale());
    }

    @Test
    public void setLanguage() {
        registrationSetupLocaleTextEntity.setLanguage("TestData");
        junit.framework.Assert.assertEquals("TestData", registrationSetupLocaleTextEntity.getLanguage());
    }

    @Test
    public void Name() {
        registrationSetupLocaleTextEntity.setName("TestData");
        junit.framework.Assert.assertEquals("TestData", registrationSetupLocaleTextEntity.getName());
    }

    @Test
    public void setVerificationRequired() {
        registrationSetupLocaleTextEntity.setVerificationRequired("Test");
        junit.framework.Assert.assertEquals("Test", registrationSetupLocaleTextEntity.getVerificationRequired());

    }


    @Test
    public void setRequired() {
        registrationSetupLocaleTextEntity.setRequired("Test");
        junit.framework.Assert.assertEquals("Test", registrationSetupLocaleTextEntity.getRequired());

    }


    @Test
    public void setMaxLength() {
        registrationSetupLocaleTextEntity.setMaxLength("TestData");
        junit.framework.Assert.assertEquals("TestData", registrationSetupLocaleTextEntity.getMaxLength());
    }

    @Test
    public void setMatchWith() {
        registrationSetupLocaleTextEntity.setMatchWith("TestData");
        junit.framework.Assert.assertEquals("TestData", registrationSetupLocaleTextEntity.getMatchWith());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme