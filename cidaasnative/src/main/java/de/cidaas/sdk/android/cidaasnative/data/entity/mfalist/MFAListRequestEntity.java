package de.cidaas.sdk.android.cidaasnative.data.entity.mfalist;

import java.io.Serializable;

public class MFAListRequestEntity implements Serializable {
    String email;
    String requestId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
