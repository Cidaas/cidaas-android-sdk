package widas.cidaassdkv2.cidaasnativev2.data.Entity.Register.RegisterUser;

import widas.cidaassdkv2.cidaasnativev2.data.Entity.Register.RegistrationEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterNewUserRequestEntity implements Serializable{
    String requestId;
    RegistrationEntity registrationEntity;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public RegistrationEntity getRegistrationEntity() {
        return registrationEntity;
    }

    public void setRegistrationEntity(RegistrationEntity registrationEntity) {
        this.registrationEntity = registrationEntity;
    }
}
