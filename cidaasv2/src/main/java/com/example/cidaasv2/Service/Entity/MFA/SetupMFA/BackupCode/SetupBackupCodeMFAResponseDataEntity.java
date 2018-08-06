package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown=true)
public class SetupBackupCodeMFAResponseDataEntity implements Serializable {


    String statusId;
    BackupCodeData[] backupCodes;

    public BackupCodeData[] getBackupCodes() {
        return backupCodes;
    }

    public void setBackupCodes(BackupCodeData[] backupCodes) {
        this.backupCodes = backupCodes;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

}
