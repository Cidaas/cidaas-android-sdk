package com.example.cidaasv2.Service.Register.RegistrationSetup;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class RegistrationSetupResultDataEntityTest {
    String dataType;
    String fieldGroup;
    boolean isGroupTitle;
    String fieldKey;
    String fieldType;
    int order;
    boolean readOnly;
    boolean required;
 
    RegistrationSetupFieldDefenition fieldDefinition;

    RegistrationSetupLocaleTextEntity localeText;
   
    RegistrationSetupResultDataEntity registrationSetupResultDataEntity;

    @Before
    public void setUp()
    {
       registrationSetupResultDataEntity=new RegistrationSetupResultDataEntity();
    }



    @Test
    public void setDataType()
    {
        registrationSetupResultDataEntity.setDataType("TestData");
        junit.framework.Assert.assertEquals("TestData",registrationSetupResultDataEntity.getDataType());
    }

    @Test
    public void setFieldGroup()
    {
        registrationSetupResultDataEntity.setFieldGroup("TestData");
        junit.framework.Assert.assertEquals("TestData",registrationSetupResultDataEntity.getFieldGroup());
    }
    @Test
    public void setFieldType()
    {
        registrationSetupResultDataEntity.setFieldType("TestData");
        junit.framework.Assert.assertEquals("TestData",registrationSetupResultDataEntity.getFieldType());
    }

    @Test
    public void setFieldKey(){
        registrationSetupResultDataEntity.setFieldKey("Test");
        junit.framework.Assert.assertEquals("Test",registrationSetupResultDataEntity.getFieldKey());

    }


    @Test
    public void setIsGroupTitle(){
        registrationSetupResultDataEntity.setGroupTitle(true);
        junit.framework.Assert.assertEquals(true,registrationSetupResultDataEntity.isGroupTitle());

    }



    @Test
    public void setReadOnly()
    {
        registrationSetupResultDataEntity.setReadOnly(true);
        junit.framework.Assert.assertEquals(true,registrationSetupResultDataEntity.isReadOnly());
    }

    @Test
    public void setRequired()
    {
        registrationSetupResultDataEntity.setRequired(true);
        junit.framework.Assert.assertEquals(true,registrationSetupResultDataEntity.isRequired());
    }


    @Test
    public void setFieldDefinition(){
        fieldDefinition=new RegistrationSetupFieldDefenition();

        fieldDefinition.setLanguage("Code");

        registrationSetupResultDataEntity.setFieldDefinition(fieldDefinition);
        Assert.assertEquals("Code",registrationSetupResultDataEntity.getFieldDefinition().getLanguage());

    }

    @Test
    public void setLocaleText(){
        localeText=new RegistrationSetupLocaleTextEntity();

        localeText.setLanguage("Code");

        registrationSetupResultDataEntity.setLocaleText(localeText);
        Assert.assertEquals("Code",registrationSetupResultDataEntity.getLocaleText().getLanguage());

    }
    @Test
    public void setOrder(){
        localeText=new RegistrationSetupLocaleTextEntity();

        localeText.setLanguage("Code");

        registrationSetupResultDataEntity.setOrder(17);
        Assert.assertEquals(17,registrationSetupResultDataEntity.getOrder());

    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme