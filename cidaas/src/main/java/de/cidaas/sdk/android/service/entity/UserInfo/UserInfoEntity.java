package de.cidaas.sdk.android.service.entity.UserInfo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sub",
    "groups",
    "name",
    "family_name",
    "given_name",
    "nickname",
    "preferred_username",
    "birthdate",
    "locale",
    "updated_at",
    "user_status",
    "last_accessed_at",
    "last_used_identity_id",
    "provider",
    "email",
    "email_verified",
    "username",
    "address",
    "mobile_number",
    "customFields"
})
public class UserInfoEntity implements Serializable {

    @JsonProperty("sub")
    private String sub;
    @JsonProperty("groups")
    private List<Group> groups = null;
    @JsonProperty("name")
    private String name;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("preferred_username")
    private String preferredUsername;
    @JsonProperty("birthdate")
    private String birthdate;
    @JsonProperty("locale")
    private String locale;
    @JsonProperty("updated_at")
    private Integer updatedAt;
    @JsonProperty("user_status")
    private String userStatus;
    @JsonProperty("last_accessed_at")
    private Integer lastAccessedAt;
    @JsonProperty("last_used_identity_id")
    private String lastUsedIdentityId;
    @JsonProperty("provider")
    private String provider;
    @JsonProperty("email")
    private String email;
    @JsonProperty("email_verified")
    private Boolean emailVerified;
    @JsonProperty("username")
    private String username;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("mobile_number")
    private String mobileNumber;
    @JsonProperty("customFields")
    private CustomFields customFields;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sub")
    public String getSub() {
        return sub;
    }

    @JsonProperty("sub")
    public void setSub(String sub) {
        this.sub = sub;
    }

    @JsonProperty("groups")
    public List<Group> getGroups() {
        return groups;
    }

    @JsonProperty("groups")
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("family_name")
    public String getFamilyName() {
        return familyName;
    }

    @JsonProperty("family_name")
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @JsonProperty("given_name")
    public String getGivenName() {
        return givenName;
    }

    @JsonProperty("given_name")
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    @JsonProperty("nickname")
    public String getNickname() {
        return nickname;
    }

    @JsonProperty("nickname")
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @JsonProperty("preferred_username")
    public String getPreferredUsername() {
        return preferredUsername;
    }

    @JsonProperty("preferred_username")
    public void setPreferredUsername(String preferredUsername) {
        this.preferredUsername = preferredUsername;
    }

    @JsonProperty("birthdate")
    public String getBirthdate() {
        return birthdate;
    }

    @JsonProperty("birthdate")
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @JsonProperty("locale")
    public String getLocale() {
        return locale;
    }

    @JsonProperty("locale")
    public void setLocale(String locale) {
        this.locale = locale;
    }

    @JsonProperty("updated_at")
    public Integer getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("user_status")
    public String getUserStatus() {
        return userStatus;
    }

    @JsonProperty("user_status")
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    @JsonProperty("last_accessed_at")
    public Integer getLastAccessedAt() {
        return lastAccessedAt;
    }

    @JsonProperty("last_accessed_at")
    public void setLastAccessedAt(Integer lastAccessedAt) {
        this.lastAccessedAt = lastAccessedAt;
    }

    @JsonProperty("last_used_identity_id")
    public String getLastUsedIdentityId() {
        return lastUsedIdentityId;
    }

    @JsonProperty("last_used_identity_id")
    public void setLastUsedIdentityId(String lastUsedIdentityId) {
        this.lastUsedIdentityId = lastUsedIdentityId;
    }

    @JsonProperty("provider")
    public String getProvider() {
        return provider;
    }

    @JsonProperty("provider")
    public void setProvider(String provider) {
        this.provider = provider;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("email_verified")
    public Boolean getEmailVerified() {
        return emailVerified;
    }

    @JsonProperty("email_verified")
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonProperty("mobile_number")
    public String getMobileNumber() {
        return mobileNumber;
    }

    @JsonProperty("mobile_number")
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @JsonProperty("customFields")
    public CustomFields getCustomFields() {
        return customFields;
    }

    @JsonProperty("customFields")
    public void setCustomFields(CustomFields customFields) {
        this.customFields = customFields;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
