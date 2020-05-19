package widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Scanned;

import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.ExcangeId.ExchangeIDEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScannedResponseData implements Serializable {
    private ExchangeIDEntity exchange_id;
    private String sub="";
    private String status_id="";
    private String[] push_random_numbers;
    private UserInfo user_info;



    public ExchangeIDEntity getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(ExchangeIDEntity exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String[] getPush_random_numbers() {
        return push_random_numbers;
    }

    public void setPush_random_numbers(String[] push_random_numbers) {
        this.push_random_numbers = push_random_numbers;
    }

    public UserInfo getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfo user_info) {
        this.user_info = user_info;
    }
}
