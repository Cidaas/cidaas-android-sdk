package de.cidaas.sdk.android.cidaasverification.data.entity.scanned;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceListResponse implements Serializable {

    private String success = "";
    private int status ;


    public List<DevicesListData> getData() {
        return data;
    }

    public void setData(List<DevicesListData> data) {
        this.data = data;
    }

    List<DevicesListData> data;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }




}
