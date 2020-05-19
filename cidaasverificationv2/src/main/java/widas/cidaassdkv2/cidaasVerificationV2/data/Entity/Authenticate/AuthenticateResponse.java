package widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Authenticate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateResponse implements Serializable {

    boolean success;
    int status;
    AuthenticateResponseDataEntity data;

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

    public AuthenticateResponseDataEntity getData() {
        return data;
    }

    public void setData(AuthenticateResponseDataEntity data) {
        this.data = data;
    }
}
