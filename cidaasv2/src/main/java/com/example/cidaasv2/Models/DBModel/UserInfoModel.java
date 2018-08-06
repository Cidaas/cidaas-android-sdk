package com.example.cidaasv2.Models.DBModel;

import java.io.Serializable;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public class UserInfoModel implements Serializable{


 //Shared Instances
 public static UserInfoModel sharedinstance;
 public static UserInfoModel getShared()
 {
  if(sharedinstance==null)
  {
   sharedinstance=new UserInfoModel();
  }
  return sharedinstance;
 }


   //Properties
 private String provider;
    private String userid;
    private String username;
    private String email;
    private String mobile;
    private String firstname;
    private String lastname;
    private String displayname;
    private int active;
    private int emailVerified;
    private int mobileNoVerified;
    private int twofactorenabled;
    //Getters and Setters

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(int emailVerified) {
        this.emailVerified = emailVerified;
    }

    public int getMobileNoVerified() {
        return mobileNoVerified;
    }

    public void setMobileNoVerified(int mobileNoVerified) {
        this.mobileNoVerified = mobileNoVerified;
    }

    public int getTwofactorenabled() {
        return twofactorenabled;
    }

    public void setTwofactorenabled(int twofactorenabled) {
        this.twofactorenabled = twofactorenabled;
    }
}


