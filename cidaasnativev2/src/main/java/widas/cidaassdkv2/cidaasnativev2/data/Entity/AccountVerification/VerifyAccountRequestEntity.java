package widas.cidaassdkv2.cidaasnativev2.data.Entity.AccountVerification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyAccountRequestEntity implements Serializable{
    String accvid;
    String code;

    public String getAccvid() {
        return accvid;
    }

    public void setAccvid(String accvid) {
        this.accvid = accvid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
