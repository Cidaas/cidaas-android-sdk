package de.cidaas.cidaasv2.Helper.Entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

public class RegistrationEntityTest {

    Date birthdate = new Date();


    Dictionary<String, RegistrationCustomFieldEntity> customFields;

    RegistrationEntity registrationEntity;

    @Before
    public void setUp() {
        registrationEntity = new RegistrationEntity();


    }


    @Test
    public void setFamily_name() {
        registrationEntity.setFamily_name("Value");
        Assert.assertEquals("Value", registrationEntity.getFamily_name());
    }

    @Test
    public void setBirthdate() {

        birthdate.setDate(27 / 12 / 1994);
        registrationEntity.setBirthdate(birthdate);
        Assert.assertEquals(birthdate, registrationEntity.getBirthdate());
    }

    @Test
    public void setUsername() {
        registrationEntity.setUsername("Username");
        Assert.assertEquals("Username", registrationEntity.getUsername());
    }

    @Test
    public void setEmail() {
        registrationEntity.setEmail("email");
        Assert.assertEquals("email", registrationEntity.getEmail());
    }

    @Test
    public void setGiven_name() {
        registrationEntity.setGiven_name("Given Name");
        Assert.assertEquals("Given Name", registrationEntity.getGiven_name());
    }

    @Test
    public void setPassword() {
        registrationEntity.setPassword("Password");
        Assert.assertEquals("Password", registrationEntity.getPassword());
    }

    @Test
    public void setPassword_echo() {
        registrationEntity.setPassword_echo("Password echo");
        Assert.assertEquals("Password echo", registrationEntity.getPassword_echo());
    }

    @Test
    public void setMobile_number() {
        registrationEntity.setMobile_number("MobileNUmber");
        Assert.assertEquals("MobileNUmber", registrationEntity.getMobile_number());
    }

    @Test
    public void setGender() {
        registrationEntity.setGender("Gender");
        Assert.assertEquals("Gender", registrationEntity.getGender());
    }

    @Test
    public void setWebsite() {
        registrationEntity.setWebsite("Website");
        Assert.assertEquals("Website", registrationEntity.getWebsite());
    }

    @Test
    public void setProvider() {
        registrationEntity.setProvider("Provider");
        Assert.assertEquals("Provider", registrationEntity.getProvider());
    }

    @Test
    public void setCustomFields() {
        RegistrationCustomFieldEntity registrationCustomFieldEntity = new RegistrationCustomFieldEntity();
        registrationCustomFieldEntity.setValue("Raja");

        Dictionary<String, RegistrationCustomFieldEntity> customFields = new Hashtable<>();
        customFields.put("Sample", registrationCustomFieldEntity);


        registrationEntity.setCustomFields(customFields);
        Assert.assertEquals("Raja", registrationEntity.getCustomFields().get("Sample").getValue());
    }


}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme