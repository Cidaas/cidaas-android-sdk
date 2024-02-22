package de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Dictionary;

import de.cidaas.sdk.android.entities.AddressFormatEntity;
import de.cidaas.sdk.android.service.entity.UserInfo.Address;

//Created entity based on https://docs.cidaas.com/docs/cidaas-iam/tnotekth3x1xg-progressive-update-user  (15-feb-2024)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDataEntity implements Serializable {

    String identityId;
    String sub;
    String given_name;
    String family_name;
    String middle_name;
    String nickname;
    String email;
    boolean email_verified;
    String mobile_number;
    boolean mobile_number_verified;
    String phone_number;
    boolean phone_number_verified;
    String profile;

    String picture;
    String website;

    String gender;

    String zoneinfo;
    String locale;
    String birthdate;

    AddressFormatEntity address;
    String provider;
    String  providerUserId;
    String username;

    public Dictionary<String, Object> getIdentityCustomFields() {
        return identityCustomFields;
    }

    public void setIdentityCustomFields(Dictionary<String, Object> identityCustomFields) {
        this.identityCustomFields = identityCustomFields;
    }

    Dictionary<String,Object> identityCustomFields;
    String raw_json;

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public boolean isMobile_number_verified() {
        return mobile_number_verified;
    }

    public void setMobile_number_verified(boolean mobile_number_verified) {
        this.mobile_number_verified = mobile_number_verified;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public boolean isPhone_number_verified() {
        return phone_number_verified;
    }

    public void setPhone_number_verified(boolean phone_number_verified) {
        this.phone_number_verified = phone_number_verified;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getZoneinfo() {
        return zoneinfo;
    }

    public void setZoneinfo(String zoneinfo) {
        this.zoneinfo = zoneinfo;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public AddressFormatEntity getAddress() {
        return address;
    }

    public void setAddress(AddressFormatEntity address) {
        this.address = address;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRaw_json() {
        return raw_json;
    }

    public void setRaw_json(String raw_json) {
        this.raw_json = raw_json;
    }
}
