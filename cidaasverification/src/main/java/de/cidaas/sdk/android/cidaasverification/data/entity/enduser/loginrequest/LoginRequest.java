package de.cidaas.sdk.android.cidaasverification.data.entity.enduser.loginrequest;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.io.Serializable;

import de.cidaas.sdk.android.entities.FingerPrintEntity;
import de.cidaas.sdk.android.helper.enums.UsageType;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest implements Serializable {

    private String pass_code = "";
    private String sub = "";

    //For face and voice
    private File fileToSend;
    private int attempt = 0;

    //For Fingerprint
    @JsonIgnore
    private FingerPrintEntity fingerPrintEntity;

    private String usageType = "";

    private String trackId = "";

    private String requestId = "";


    public LoginRequest() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPass_code() {
        return pass_code;
    }

    public void setPass_code(String pass_code) {
        this.pass_code = pass_code;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public File getFileToSend() {
        return fileToSend;
    }

    public void setFileToSend(File fileToSend) {
        this.fileToSend = fileToSend;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public FingerPrintEntity getFingerPrintEntity() {
        return fingerPrintEntity;
    }

    public void setFingerPrintEntity(FingerPrintEntity fingerPrintEntity) {
        this.fingerPrintEntity = fingerPrintEntity;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public static LoginRequest getPasswordlessRequestEntity(@NonNull String sub, @NonNull String requestId) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSub(sub);
        loginRequest.setRequestId(requestId);
        loginRequest.setUsageType(UsageType.PASSWORDLESS);

        return loginRequest;
    }

    //For Passwordless Email
    public static LoginRequest getPasswordlessEmailRequestEntity(@NonNull String sub, @NonNull String requestId) {
        return getPasswordlessRequestEntity(sub, requestId);
    }

    //For Passwordless SMS
    public static LoginRequest getPasswordlessSMSRequestEntity(@NonNull String sub, @NonNull String requestId) {
        return getPasswordlessRequestEntity(sub, requestId);
    }

    //For Passwordless IVR
    public static LoginRequest getPasswordlessIVRRequestEntity(@NonNull String sub, @NonNull String requestId) {
        return getPasswordlessRequestEntity(sub, requestId);
    }

    /**
     * @param pass_code
     * @param sub
     * @param requestId
     * @return
     */

    //For Passwordless Pattern
    public static LoginRequest getPasswordlessPatternLoginRequestEntity(@NonNull String pass_code, @NonNull String sub, @NonNull String requestId) {
        LoginRequest loginRequest = getPasswordlessRequestEntity(sub,requestId);
        loginRequest.setPass_code(pass_code);
        return loginRequest;
    }

    //For Passwordless SmartPush
    public static LoginRequest getPasswordlessSmartPushLoginRequestEntity(@NonNull String sub, @NonNull String requestId) {
        return getPasswordlessRequestEntity(sub, requestId);
    }

    //For Passwordless TOTP
    public static LoginRequest getPasswordlessTOTPRequestEntity(@NonNull String sub, @NonNull String requestId) {
        return getPasswordlessRequestEntity(sub, requestId);
    }

    //For Passwordless Face
    public static LoginRequest getPasswordlessFaceLoginRequestEntity(@NonNull String sub, @NonNull String requestId, @NonNull File fileToSend) {
        LoginRequest loginRequest = getPasswordlessRequestEntity(sub,requestId);
        loginRequest.setFileToSend(fileToSend);
        return loginRequest;
    }

    //For Passwordless Voice
    public static LoginRequest getPasswordlessVoiceLoginRequestEntity(@NonNull String sub, @NonNull String requestId, @NonNull File fileToSend) {
        return getPasswordlessFaceLoginRequestEntity(sub, requestId, fileToSend);
    }

    //For Passwordless Fingerprint
    public static LoginRequest getPasswordlessFingerprintLoginRequestEntity(@NonNull String sub, @NonNull String requestId, @NonNull FingerPrintEntity fingerPrintEntity) {
        LoginRequest loginRequest = getPasswordlessRequestEntity(sub,requestId);
        loginRequest.setFingerPrintEntity(fingerPrintEntity);
        return loginRequest;
    }

    //For Passwordless Email
    public static LoginRequest getMFAEmailRequestEntity(@NonNull String sub, @NonNull String requestId) {
        return getPasswordlessRequestEntity(sub, requestId);
    }

    //For Passwordless SMS
    public static LoginRequest getMFASMSRequestEntity(@NonNull String sub, @NonNull String requestId, @NonNull String trackId) {
        LoginRequest loginRequest = getPasswordlessRequestEntity(sub,requestId);
        loginRequest.setUsageType(UsageType.MFA);
        loginRequest.setTrackId(trackId);
        return loginRequest;
    }

    //For Passwordless IVR
    public static LoginRequest getMFAIVRRequestEntity(@NonNull String sub, @NonNull String requestId, @NonNull String trackId) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSub(sub);
        loginRequest.setRequestId(requestId);
        loginRequest.setUsageType(UsageType.MFA);
        loginRequest.setTrackId(trackId);

        return loginRequest;
    }


    //For MFA Pattern
    public static LoginRequest getMFAPatternLoginRequestEntity(@NonNull String pass_code, @NonNull String sub, @NonNull String requestId, @NonNull String trackId) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPass_code(pass_code);
        loginRequest.setSub(sub);
        loginRequest.setRequestId(requestId);
        loginRequest.setUsageType(UsageType.MFA);
        loginRequest.setTrackId(trackId);

        return loginRequest;

    }

    //For Passwordless SmartPush
    public static LoginRequest getMFASmartPushLoginRequestEntity(@NonNull String sub, @NonNull String requestId, @NonNull String trackId) {
        return getMFAIVRRequestEntity(sub, requestId, trackId);
    }

    //For Passwordless TOTP
    public static LoginRequest getMFATOTPRequestEntity(@NonNull String sub, @NonNull String requestId, @NonNull String trackId) {
        return getMFASmartPushLoginRequestEntity(sub, requestId, trackId);
    }

    //For MFA Face
    public static LoginRequest getMFAFaceLoginRequestEntity(@NonNull String sub, @NonNull String requestId, @NonNull File fileToSend, @NonNull String trackId) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSub(sub);
        loginRequest.setFileToSend(fileToSend);
        loginRequest.setRequestId(requestId);
        loginRequest.setUsageType(UsageType.MFA);
        loginRequest.setTrackId(trackId);

        return loginRequest;
    }

    //For MFA Voice
    public static LoginRequest getMFAVoiceLoginRequestEntity(@NonNull String sub, @NonNull String requestId, @NonNull File fileToSend, @NonNull String trackId) {
        return getMFAFaceLoginRequestEntity(sub, requestId, fileToSend,trackId);
    }

    //For MFA Fingerprint
    public static LoginRequest getMFAFingerprintLoginRequestEntity(@NonNull String sub, @NonNull String requestId, @NonNull FingerPrintEntity fingerPrintEntity, @NonNull String trackId) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSub(sub);
        loginRequest.setRequestId(requestId);
        loginRequest.setFingerPrintEntity(fingerPrintEntity);
        loginRequest.setUsageType(UsageType.MFA);
        loginRequest.setTrackId(trackId);

        return loginRequest;
    }
}
