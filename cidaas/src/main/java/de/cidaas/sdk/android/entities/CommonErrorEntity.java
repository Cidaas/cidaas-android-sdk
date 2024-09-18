package de.cidaas.sdk.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.LinkedHashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonErrorEntity implements Serializable {

    private boolean success;
    private int status;
    private Object error;
    private Object code;



    private String referenceNumber;

    private String error_description;


    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }


    // Method to check if the error is a string or an object
    public String getErrorAsString() {
        if (error instanceof String) {
            return (String) error;
        }
        return null;
    }

    public ErrorEntity getErrorAsObject() {
        if (error instanceof LinkedHashMap) {
            // Deserialize LinkedHashMap into ErrorEntity
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(error, ErrorEntity.class);
        }
        return null;
    }
}
