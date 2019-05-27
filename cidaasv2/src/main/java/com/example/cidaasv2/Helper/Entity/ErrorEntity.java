package com.example.cidaasv2.Helper.Entity;

import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.FaceMetaData;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.VoiceMetaData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorEntity implements Serializable
{

    private int code;
    private String moreInfo;
    private String type;
    private int status;
    private String referenceNumber;
    private  String error;
    private FaceMetaData faceMetaData;
    private VoiceMetaData voiceMetaData;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public FaceMetaData getFaceMetaData() {
        return faceMetaData;
    }

    public void setFaceMetaData(FaceMetaData faceMetaData) {
        this.faceMetaData = faceMetaData;
    }

    public VoiceMetaData getVoiceMetaData() {
        return voiceMetaData;
    }

    public void setVoiceMetaData(VoiceMetaData voiceMetaData) {
        this.voiceMetaData = voiceMetaData;
    }
}
