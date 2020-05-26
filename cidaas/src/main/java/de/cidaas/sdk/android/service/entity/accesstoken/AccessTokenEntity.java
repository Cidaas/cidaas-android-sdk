package de.cidaas.sdk.android.service.entity.accesstoken;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by widasrnarayanan on 16/1/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessTokenEntity implements Serializable {

    //Constructors
    public AccessTokenEntity(String access_token, String refresh_token, int expires_in, String sub) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
        this.sub = sub;
    }

    public AccessTokenEntity() {
    }

    public AccessTokenEntity(String access_token, String userstate, String refresh_token, String id_token, String scope, int expires_in, String sub,
                             String token_type, String session_state, String viewtype, String grant_type, String code) {
        this.access_token = access_token;
        this.userstate = userstate;
        this.refresh_token = refresh_token;
        this.id_token = id_token;
        this.scope = scope;
        this.expires_in = expires_in;
        this.sub = sub;
        this.token_type = token_type;
        this.session_state = session_state;
        this.viewtype = viewtype;
        this.grant_type = grant_type;
        this.code = code;
    }

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
    private String token_type;
    private String session_state;
    private String viewtype;
    private String grant_type;


    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }


    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getSession_state() {
        return session_state;
    }

    public void setSession_state(String session_state) {
        this.session_state = session_state;
    }

    public String getViewtype() {
        return viewtype;
    }

    public void setViewtype(String viewtype) {
        this.viewtype = viewtype;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
