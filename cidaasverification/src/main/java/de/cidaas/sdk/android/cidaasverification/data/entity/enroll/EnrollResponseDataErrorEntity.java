package de.cidaas.sdk.android.cidaasverification.data.entity.enroll;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollResponseDataErrorEntity implements Serializable {

    EnrollResponseErrorMetaDataEntity  metadata;
    public EnrollResponseErrorMetaDataEntity getMetaErrordata() {
        return metadata;
    }

    public void setMetaErrordata(EnrollResponseErrorMetaDataEntity metadata) {
        this.metadata = metadata;
    }
}
