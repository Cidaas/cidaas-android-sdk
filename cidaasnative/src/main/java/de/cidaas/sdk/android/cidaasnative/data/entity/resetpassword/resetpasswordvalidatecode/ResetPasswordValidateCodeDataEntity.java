package de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetpasswordvalidatecode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetPasswordValidateCodeDataEntity implements Serializable {
    String exchangeId;
    String resetRequestId;

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getResetRequestId() {
        return resetRequestId;
    }

    public void setResetRequestId(String resetRequestId) {
        this.resetRequestId = resetRequestId;
    }
}
