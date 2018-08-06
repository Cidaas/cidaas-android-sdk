package com.example.cidaasv2.Helper.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public class DeviceInfoEntity implements Parcelable {

  private String deviceId;
  private String deviceMake;
  private String deviceModel;
  private String deviceVersion;
  private String pushNotificationId;

  public String getPushNotificationId() {
    return pushNotificationId;
  }

  public void setPushNotificationId(String pushNotificationId) {
    this.pushNotificationId = pushNotificationId;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getDeviceMake() {
    return deviceMake;
  }

  public void setDeviceMake(String deviceMake) {
    this.deviceMake = deviceMake;
  }

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public String getDeviceVersion() {
    return deviceVersion;
  }

  public void setDeviceVersion(String deviceVersion) {
    this.deviceVersion = deviceVersion;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.deviceId);
    dest.writeString(this.deviceMake);
    dest.writeString(this.deviceModel);
    dest.writeString(this.deviceVersion);
  }

  public DeviceInfoEntity() {
  }

  protected DeviceInfoEntity(Parcel in) {
    this.deviceId = in.readString();
    this.deviceMake = in.readString();
    this.deviceModel = in.readString();
    this.deviceVersion = in.readString();
    this.pushNotificationId=in.readString();
  }

  public static final Parcelable.Creator<DeviceInfoEntity> CREATOR = new Parcelable.Creator<DeviceInfoEntity>() {
    @Override
    public DeviceInfoEntity createFromParcel(Parcel source) {
      return new DeviceInfoEntity(source);
    }

    @Override
    public DeviceInfoEntity[] newArray(int size) {
      return new DeviceInfoEntity[size];
    }
  };
}
