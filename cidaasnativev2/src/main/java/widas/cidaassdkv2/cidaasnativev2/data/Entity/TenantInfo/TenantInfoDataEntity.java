package widas.cidaassdkv2.cidaasnativev2.data.Entity.TenantInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by widasrnarayanan on 23/5/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TenantInfoDataEntity implements Serializable {
    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String[] getAllowLoginWith() {
        return allowLoginWith;
    }

    public void setAllowLoginWith(String[] allowLoginWith) {
        this.allowLoginWith = allowLoginWith;
    }

    private String tenant_name;
    private String tenant_key;
    private String versionInfo;
    private String[] allowLoginWith;

    public String getTenant_key() {
        return tenant_key;
    }

    public void setTenant_key(String tenant_key) {
        this.tenant_key = tenant_key;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }
}


