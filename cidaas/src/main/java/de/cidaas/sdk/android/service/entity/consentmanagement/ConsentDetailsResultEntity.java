package de.cidaas.sdk.android.service.entity.consentmanagement;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsentDetailsResultEntity implements Serializable {
    boolean success;
    int status;

    // Object data;
    ConsentSettingsReponseDataEntity data;


    public ConsentSettingsReponseDataEntity getData() {
        return data;
    }

    public void setData(ConsentSettingsReponseDataEntity data) {
        this.data = data;
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

    /*public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
*/
}
