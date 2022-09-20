package de.cidaas.sdk.android.cidaasverification.data.entity.authenticatedhistory;

import java.io.Serializable;


public class UserAuthenticatedHistoryDataEntity implements Serializable {



    private String push_id = "";
    private String client_id = "";
    private String status_id = "";

    public UserAuthenticatedHistoryDataEntity() {


    }
    public UserAuthenticatedHistoryDataEntity(String status_id) {
        this.push_id = push_id;
        this.client_id = client_id;
        this.status_id = status_id;

    }
    public UserAuthenticatedHistoryDataEntity(String push_id,String client_id,String status_id) {
        this.push_id = push_id;
        this.client_id = client_id;
        this.status_id = status_id;

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

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

}
