package de.cidaas.sdk.android.cidaasverification.data.entity.enroll;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollResponseErrorMetaDataEntity implements Serializable {


    EnrollResponseErrorMetaDatametaEntity  meta;
    public EnrollResponseErrorMetaDatametaEntity getMetaErrormetadata() {
        return meta;
    }

    public void setMetaErrormetadata(EnrollResponseErrorMetaDatametaEntity meta) {
        this.meta = meta;
    }

}
