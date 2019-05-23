package com.example.cidaasv2.VerificationV2.data.Entity.Settings.ConfiguredMFAList;

import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetMFAListResponseData implements Serializable {

    private String tenant_name = "";
    private String tenant_key = "";

    @JsonProperty("configured_list")
    private List<MFAListData> mfaListData;

    private UserInfo user_info;

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getTenant_key() {
        return tenant_key;
    }

    public void setTenant_key(String tenant_key) {
        this.tenant_key = tenant_key;
    }

    public List<MFAListData> getMfaListData() {
        return mfaListData;
    }

    public void setMfaListData(List<MFAListData> mfaListData) {
        this.mfaListData = mfaListData;
    }

    public UserInfo getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfo user_info) {
        this.user_info = user_info;
    }
}
