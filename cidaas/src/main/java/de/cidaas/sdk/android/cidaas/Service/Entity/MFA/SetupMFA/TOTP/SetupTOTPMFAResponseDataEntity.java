package de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.TOTP;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetupTOTPMFAResponseDataEntity implements Serializable {

    String secret = "";
    String t = "";
    String d = "";
    String issuer = "";
    String l = "";
    String sub = "";
    String rns = "";

    String cid = "";
    String rurl = "";
    String st = "";
    String udi = "";
    String current_status;


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getRns() {
        return rns;
    }

    public void setRns(String rns) {
        this.rns = rns;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getRurl() {
        return rurl;
    }

    public void setRurl(String rurl) {
        this.rurl = rurl;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getUdi() {
        return udi;
    }

    public void setUdi(String udi) {
        this.udi = udi;
    }

    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

}
