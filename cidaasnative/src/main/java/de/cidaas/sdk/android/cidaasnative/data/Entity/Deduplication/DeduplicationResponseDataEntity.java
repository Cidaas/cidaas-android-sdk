package de.cidaas.sdk.android.cidaasnative.data.Entity.Deduplication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeduplicationResponseDataEntity implements Serializable {
    String email;
    DeduplicationList deduplicationList[];

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DeduplicationList[] getDeduplicationList() {
        return deduplicationList;
    }

    public void setDeduplicationList(DeduplicationList[] deduplicationList) {
        this.deduplicationList = deduplicationList;
    }
}
