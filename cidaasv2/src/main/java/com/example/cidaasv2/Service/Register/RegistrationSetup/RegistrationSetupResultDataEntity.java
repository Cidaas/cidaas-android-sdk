package com.example.cidaasv2.Service.Register.RegistrationSetup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)

public class RegistrationSetupResultDataEntity implements Serializable{
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


    public RegistrationSetupFieldDefenition getFieldDefinition() {
        return fieldDefinition;
    }

    public void setFieldDefinition(RegistrationSetupFieldDefenition fieldDefinition) {
        this.fieldDefinition = fieldDefinition;
    }


    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getFieldGroup() {
        return fieldGroup;
    }

    public void setFieldGroup(String fieldGroup) {
        this.fieldGroup = fieldGroup;
    }

    public boolean isGroupTitle() {
        return isGroupTitle;
    }

    public void setGroupTitle(boolean groupTitle) {
        isGroupTitle = groupTitle;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }



    public RegistrationSetupLocaleTextEntity getLocaleText() {
        return localeText;
    }

    public void setLocaleText(RegistrationSetupLocaleTextEntity localeText) {
        this.localeText = localeText;
    }
}
