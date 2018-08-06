package com.example.cidaasv2.Service.Entity.MFA.MFAList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MFAListResponseDataEntity implements Serializable{
    String _id;
    String verificationType;
    MFAListDeviceEntity[] devices;

    public MFAListDeviceEntity[] getDevices() {
        return devices;
    }

    public void setDevices(MFAListDeviceEntity[] devices) {
        this.devices = devices;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
    }


}
