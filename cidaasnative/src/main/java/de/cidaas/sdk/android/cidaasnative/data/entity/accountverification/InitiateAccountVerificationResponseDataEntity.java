package de.cidaas.sdk.android.cidaasnative.data.entity.accountverification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateAccountVerificationResponseDataEntity implements Serializable {
    String accvid = "";

    public String getAccvid() {
        return accvid;
    }

    public void setAccvid(String accvid) {
        this.accvid = accvid;
    }
}
