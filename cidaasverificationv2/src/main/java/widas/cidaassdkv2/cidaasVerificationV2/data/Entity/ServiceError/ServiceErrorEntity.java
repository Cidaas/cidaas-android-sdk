package widas.cidaassdkv2.cidaasVerificationV2.data.Entity.ServiceError;

import java.io.Serializable;

public class ServiceErrorEntity implements Serializable
{
    int code=0;
    int status=0;
    String moreInfo="";
    String type="";
    String referenceNumber="";
    String error="";
    String developerError="";
    boolean logged=false;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDeveloperError() {
        return developerError;
    }

    public void setDeveloperError(String developerError) {
        this.developerError = developerError;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
