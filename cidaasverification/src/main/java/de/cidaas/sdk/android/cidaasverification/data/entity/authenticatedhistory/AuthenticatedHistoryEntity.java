package de.cidaas.sdk.android.cidaasverification.data.entity.authenticatedhistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticatedHistoryEntity implements Serializable {
    private String device_id = "";
    private String push_id = "";
    private String client_id = "";
    private String sub = "";
    private int skip = 0;
    private int take = 0;
    private String verification_type = "";

    //The startDate and endDate must be in ISO Format
    private String start_time = "";
    private String end_time = "";

    public AuthenticatedHistoryEntity() {
    }

    //mandatory parameters
    public AuthenticatedHistoryEntity(String sub, int skip, int take, String verification_type, String start_time, String end_time) {
        this.sub = sub;
        this.skip = skip;
        this.take = take;
        this.verification_type = verification_type;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public String getVerification_type() {
        return verification_type;
    }

    public void setVerification_type(String verification_type) {
        this.verification_type = verification_type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }


    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
