package de.cidaas.sdk.android.cidaasVerification.data.entity.settings.configuredmfalist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import de.cidaas.sdk.android.cidaasVerification.data.entity.scanned.UserInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MFAListResponseData implements Serializable {

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
