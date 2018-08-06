package com.example.cidaasv2.Service.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by widasrnarayanan on 16/1/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessTokenEntity implements Serializable {
    //Getters and Setters
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUserstate() {
        return userstate;
    }

    public void setUserstate(String userstate) {
        this.userstate = userstate;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }


    //Properties
    private String access_token;
    private String userstate;
    private String refresh_token;
    private String id_token;
    private String scope;
    private int expires_in;
    private String sub;
    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }






}
