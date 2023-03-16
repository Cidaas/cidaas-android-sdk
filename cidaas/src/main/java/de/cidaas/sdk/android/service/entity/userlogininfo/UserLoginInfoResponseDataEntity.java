package de.cidaas.sdk.android.service.entity.userlogininfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import de.cidaas.sdk.android.service.entity.notificationentity.getpendingnotification.LocationEntity;
import de.cidaas.sdk.android.service.entity.userlogininfo.DeviceInfoEntityWithDetailsEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginInfoResponseDataEntity implements Serializable {
    private String _id;
    private String currentState;
    private String verificationType;
    private DeviceInfoEntityWithDetailsEntity deviceInfo;
    private LocationEntity address;
    private String time;
    private int totalCount = 0;
    private String sub;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public DeviceInfoEntityWithDetailsEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntityWithDetailsEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
    }


    public LocationEntity getAddress() {
        return address;
    }

    public void setAddress(LocationEntity address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
