package de.cidaas.cidaasv2.Helper.Entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegistrationCustomFieldEntityTest {

    RegistrationCustomFieldEntity registrationCustomFieldEntity;

    @Before
    public void setUp() {
        registrationCustomFieldEntity = new RegistrationCustomFieldEntity();
    }


    @Test
    public void setValue() {
        registrationCustomFieldEntity.setValue("Value");
        Assert.assertEquals("Value", registrationCustomFieldEntity.getValue());
    }

    @Test
    public void setId() {
        registrationCustomFieldEntity.setId("Id");
        Assert.assertEquals("Id", registrationCustomFieldEntity.getId());
    }

    @Test
    public void setDataType() {
        registrationCustomFieldEntity.setDataType("DataType");
        Assert.assertEquals("DataType", registrationCustomFieldEntity.getDataType());
    }

    @Test
    public void setInternal() {
        registrationCustomFieldEntity.setInternal(true);
        Assert.assertEquals(true, registrationCustomFieldEntity.isInternal());
    }

    @Test
    public void setReadOnly() {
        registrationCustomFieldEntity.setReadOnly(true);
        Assert.assertEquals(true, registrationCustomFieldEntity.isReadOnly());
    }

    @Test
    public void setLastUpdateFrom() {
        registrationCustomFieldEntity.setLastUpdateFrom("LastValue");
        Assert.assertEquals("LastValue", registrationCustomFieldEntity.getLastUpdateFrom());
    }

    @Test
    public void setKey() {
        registrationCustomFieldEntity.setKey("Key");
        Assert.assertEquals("Key", registrationCustomFieldEntity.getKey());
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme