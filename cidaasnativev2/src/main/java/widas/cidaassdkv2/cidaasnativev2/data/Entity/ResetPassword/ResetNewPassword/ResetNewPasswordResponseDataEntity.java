package widas.cidaassdkv2.cidaasnativev2.data.Entity.ResetPassword.ResetNewPassword;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetNewPasswordResponseDataEntity implements Serializable{
    boolean reseted;

    public boolean isReseted() {
        return reseted;
    }

    public void setReseted(boolean reseted) {
        this.reseted = reseted;
    }
}
