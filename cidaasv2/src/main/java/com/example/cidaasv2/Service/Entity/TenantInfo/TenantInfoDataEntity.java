package com.example.cidaasv2.Service.Entity.TenantInfo;

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
    private String[] allowLoginWith;
}
