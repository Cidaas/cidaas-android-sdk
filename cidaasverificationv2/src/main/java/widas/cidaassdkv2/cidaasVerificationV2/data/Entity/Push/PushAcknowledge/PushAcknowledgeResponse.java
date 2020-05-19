package widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Push.PushAcknowledge;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PushAcknowledgeResponse implements Serializable {

    boolean success;
    int status;
    PushAcknowledgeResponseDataEntity data;

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

    public PushAcknowledgeResponseDataEntity getData() {
        return data;
    }

    public void setData(PushAcknowledgeResponseDataEntity data) {
        this.data = data;
    }
}


