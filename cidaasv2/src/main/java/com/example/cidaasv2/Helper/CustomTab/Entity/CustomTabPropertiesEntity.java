package com.example.cidaasv2.Helper.CustomTab.Entity;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by widasrnarayanan on 5/3/18.
 */

public class CustomTabPropertiesEntity implements Parcelable{
    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String primaryColor;
    public String secondaryColor;


    protected CustomTabPropertiesEntity(Parcel in) {
        secondaryColor = in.readString();
    }

    public static final Creator<CustomTabPropertiesEntity> CREATOR = new Creator<CustomTabPropertiesEntity>() {
        @Override
        public CustomTabPropertiesEntity createFromParcel(Parcel in) {
            return new CustomTabPropertiesEntity(in);
        }

        @Override
        public CustomTabPropertiesEntity[] newArray(int size) {
            return new CustomTabPropertiesEntity[size];
        }
    };

    public CustomTabPropertiesEntity(String primaryColor, String secondaryColor) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(secondaryColor);
    }
}
