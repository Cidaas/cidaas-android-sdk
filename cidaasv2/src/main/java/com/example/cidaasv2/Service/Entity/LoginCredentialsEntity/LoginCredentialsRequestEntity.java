package com.example.cidaasv2.Service.Entity.LoginCredentialsEntity;

import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginCredentialsRequestEntity implements Serializable{

    String Username;
    String password;
    String username_type;

    private String requestId;


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername_type() {
        return username_type;
    }

    public void setUsername_type(String username_type) {
        this.username_type = username_type;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
