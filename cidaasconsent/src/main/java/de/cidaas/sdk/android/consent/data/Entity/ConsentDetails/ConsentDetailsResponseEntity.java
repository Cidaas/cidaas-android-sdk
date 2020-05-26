package de.cidaas.sdk.android.consent.data.Entity.ConsentDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsentDetailsResponseEntity implements Serializable {
    private boolean success;
    private int status;
    ConsentDetailsResponseDataEntity data;

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

    public ConsentDetailsResponseDataEntity getData() {
        return data;
    }

    public void setData(ConsentDetailsResponseDataEntity data) {
        this.data = data;
    }
}
