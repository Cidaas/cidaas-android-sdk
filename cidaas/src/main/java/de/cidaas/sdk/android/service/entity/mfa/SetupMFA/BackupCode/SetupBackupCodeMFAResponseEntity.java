package de.cidaas.sdk.android.service.entity.mfa.SetupMFA.BackupCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetupBackupCodeMFAResponseEntity implements Serializable {

    boolean success;
    int status;
    SetupBackupCodeMFAResponseDataEntity data;


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

    public SetupBackupCodeMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(SetupBackupCodeMFAResponseDataEntity data) {
        this.data = data;
    }
}
