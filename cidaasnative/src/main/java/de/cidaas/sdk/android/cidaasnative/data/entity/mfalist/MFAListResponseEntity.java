package de.cidaas.sdk.android.cidaasnative.data.entity.mfalist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MFAListResponseEntity implements Serializable {
    boolean success;
    int status;
    MFAListResponseDataEntity[] data;

    public MFAListResponseDataEntity[] getData() {
        return data;
    }

    public void setData(MFAListResponseDataEntity[] data) {
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


}
