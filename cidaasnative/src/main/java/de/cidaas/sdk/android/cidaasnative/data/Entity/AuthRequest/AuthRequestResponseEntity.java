package de.cidaas.sdk.android.cidaasnative.data.Entity.AuthRequest;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.cidaas.sdk.android.cidaasnative.data.Entity.RequestId.RequestIDEntity;

/**
 * Created by widasrnarayanan on 9/5/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequestResponseEntity {
    private boolean success;
    private int status;
    private RequestIDEntity data;

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

    public RequestIDEntity getData() {

        return data;
    }

    public void setData(RequestIDEntity data) {
        this.data = data;
    }
}
