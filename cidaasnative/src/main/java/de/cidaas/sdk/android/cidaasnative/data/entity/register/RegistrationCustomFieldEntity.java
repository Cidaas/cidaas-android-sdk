package de.cidaas.sdk.android.cidaasnative.data.entity.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationCustomFieldEntity implements Serializable {

    public String key = "";

    public Object value;

    public String id = "";
    public String dataType = "";
    public boolean internal = false;
    public boolean readOnly = false;
    public String lastUpdateFrom = "";

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getLastUpdateFrom() {
        return lastUpdateFrom;
    }

    public void setLastUpdateFrom(String lastUpdateFrom) {
        this.lastUpdateFrom = lastUpdateFrom;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
