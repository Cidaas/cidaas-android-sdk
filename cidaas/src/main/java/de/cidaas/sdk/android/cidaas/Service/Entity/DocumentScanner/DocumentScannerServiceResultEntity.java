package de.cidaas.sdk.android.cidaas.Service.Entity.DocumentScanner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentScannerServiceResultEntity implements Serializable {

    int status;
    boolean success;
    DocumentScannerServiceDataEntity data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DocumentScannerServiceDataEntity getData() {
        return data;
    }

    public void setData(DocumentScannerServiceDataEntity data) {
        this.data = data;
    }
}
