package de.cidaas.cidaasv2.Helper.Entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegisterNewUserCustomValueTest {

    RegisterNewUserCustomValue registerNewUserCustomValue;


    @Before
    public void setUp() {
        registerNewUserCustomValue = new RegisterNewUserCustomValue();

    }

    @Test
    public void setValue() {
        registerNewUserCustomValue.setValue("Value");
        Assert.assertEquals("Value", registerNewUserCustomValue.getValue());
    }

    @Test
    public void setId() {
        registerNewUserCustomValue.setId("Id");
        Assert.assertEquals("Id", registerNewUserCustomValue.getId());
    }

    @Test
    public void setDataType() {
        registerNewUserCustomValue.setDataType("DataType");
        Assert.assertEquals("DataType", registerNewUserCustomValue.getDataType());
    }

    @Test
    public void setInternal() {
        registerNewUserCustomValue.setInternal(true);
        Assert.assertEquals(true, registerNewUserCustomValue.isInternal());
    }

    @Test
    public void setReadOnly() {
        registerNewUserCustomValue.setReadOnly(true);
        Assert.assertEquals(true, registerNewUserCustomValue.isReadOnly());
    }

    @Test
    public void setLastUpdateFrom() {
        registerNewUserCustomValue.setLastUpdateFrom("LastValue");
        Assert.assertEquals("LastValue", registerNewUserCustomValue.getLastUpdateFrom());
    }

    @Test
    public void setKey() {
        registerNewUserCustomValue.setKey("Key");
        Assert.assertEquals("Key", registerNewUserCustomValue.getKey());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme