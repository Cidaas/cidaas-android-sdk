package de.cidaas.sdk.android.cidaasVerification.data.entity.enroll;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import de.cidaas.sdk.android.cidaasVerification.data.entity.excangeid.ExchangeIDEntity;
import de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.Face.FaceMetadataEntity;


@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollResponseDataEntity implements Serializable {
    private ExchangeIDEntity exchange_id;
    private String sub = "";
    private String status_id = "";

    //For Face
    Boolean enrolled = null;
    FaceMetadataEntity meta;

    public ExchangeIDEntity getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(ExchangeIDEntity exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public Boolean getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Boolean enrolled) {
        this.enrolled = enrolled;
    }

    public FaceMetadataEntity getMeta() {
        return meta;
    }

    public void setMeta(FaceMetadataEntity meta) {
        this.meta = meta;
    }
}
