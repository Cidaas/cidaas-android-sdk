package de.cidaas.sdk.android.service.entity.userlogininfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginInfoDataEntity implements Serializable {

    @JsonProperty("data")
    private List<de.cidaas.sdk.android.Service.Entity.UserLoginInfo.UserLoginInfoResponseDataEntity> data;
    private int count;


    public List<de.cidaas.sdk.android.Service.Entity.UserLoginInfo.UserLoginInfoResponseDataEntity> getData() {
        return data;
    }

    public void setData(List<de.cidaas.sdk.android.Service.Entity.UserLoginInfo.UserLoginInfoResponseDataEntity> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
