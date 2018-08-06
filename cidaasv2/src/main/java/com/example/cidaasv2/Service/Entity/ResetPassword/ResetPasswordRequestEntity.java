package com.example.cidaasv2.Service.Entity.ResetPassword;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by widasrnarayanan on 23/5/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetPasswordRequestEntity implements Serializable{
    private String email;
    private String processingType;
    private String requestId;
    private String resetMedium;
    private String PhoneNumber;

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProcessingType() {
        return processingType;
    }

    public void setProcessingType(String processingType) {
        this.processingType = processingType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResetMedium() {
        return resetMedium;
    }

    public void setResetMedium(String resetMedium) {
        this.resetMedium = resetMedium;
    }
}
