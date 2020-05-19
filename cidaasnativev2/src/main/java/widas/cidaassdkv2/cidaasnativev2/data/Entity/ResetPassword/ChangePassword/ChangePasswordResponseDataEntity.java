package widas.cidaassdkv2.cidaasnativev2.data.Entity.ResetPassword.ChangePassword;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePasswordResponseDataEntity implements Serializable{
    boolean changed;

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {

        this.changed = changed;
    }
}
