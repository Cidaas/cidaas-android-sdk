package com.example.cidaasv2.Models.DBModel;

import java.io.Serializable;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public class AccessTokenModel implements Serializable {
  //Shared instance
  public static AccessTokenModel sharedinstance;
    public static AccessTokenModel getShared()
    {
        if(sharedinstance==null)
        {
            sharedinstance=new AccessTokenModel();
        }
        return sharedinstance;
    }

    //Properties
    private String accessToken;
    private String userState;
    private String refreshToken;
    private String idToken;
    private String scope;
    private int expiresIn=0;
    private String key;
    private String salt;
    private String userId;
    private long seconds=0;
    private String plainToken;
    private boolean isEncrypted=false;
  //Getters and Setters

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public String getPlainToken() {
        return plainToken;
    }

    public void setPlainToken(String plainToken) {
        this.plainToken = plainToken;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public void setEncrypted(boolean encrypted) {
        isEncrypted = encrypted;
    }

}


