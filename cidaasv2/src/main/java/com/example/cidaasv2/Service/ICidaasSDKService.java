package com.example.cidaasv2.Service;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Entity.RegistrationEntity;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.DeduplicationResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.LoginDeduplication.LoginDeduplicationResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.FIDOKey.AuthenticateFIDORequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.FIDOKey.AuthenticateFIDOResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP.AuthenticateTOTPRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP.AuthenticateTOTPResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceResponseEntity;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptResponseEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptedRequestEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementResponseEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentDetailsResultEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentRequestEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.BackupCode.EnrollBackupCodeMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.BackupCode.EnrollBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey.EnrollFIDOMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey.EnrollFIDOMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS.EnrollSMSMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS.EnrollSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.FIDOKey.InitiateFIDOMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.FIDOKey.InitiateFIDOMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Face.InitiateFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Face.InitiateFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern.InitiatePatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern.InitiatePatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP.InitiateTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP.InitiateTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.FIDO.SetupFIDOMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face.SetupFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.FIDO.SetupFIDOMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face.SetupFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS.SetupSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush.SetupSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush.SetupSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP.SetupTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP.SetupTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceRequestEntity;
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyResponseEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.cidaasv2.Service.Entity.UserProfile.UserprofileResponseEntity;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;
import com.example.cidaasv2.Service.Scanned.ScannedRequestEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by widasrnarayanan on 17/1/18.
 */

public interface ICidaasSDKService {
    //-----------------------------------------------------------POST Call--------------------------------------------------------------
    //get Access token by Code
    @FormUrlEncoded
    @POST
    Call<AccessTokenEntity> getAccessTokenByCode(@Url String url,
                                                 @HeaderMap Map<String,String>headers,
                                                 @FieldMap(encoded = true) Map<String, String> params);

//get Access token by refreshtoken
    @FormUrlEncoded
    @POST
    Call<AccessTokenEntity> getAccessTokenByRefreshToken(@Url String url,
                                                       @HeaderMap Map<String,String>headers,
                                                       @FieldMap(encoded = true) Map<String, String> params);

    //Todo Add FieldMap Pending
   // @FormUrlEncoded
    @POST
    Call<AuthRequestResponseEntity> getRequestId(@Url String url, @HeaderMap Map<String,String>headers, @Body AuthRequestEntity authRequestEntity);

    //Todo Add FieldMap Pending
   // @FormUrlEncoded
    @POST
    Call<String> getLoginUrl(@Url String url,@HeaderMap Map<String,String> headers);

    //Login with Credentials
    @POST
    Call<LoginCredentialsResponseEntity> loginWithCredentials(@Url String url,  @HeaderMap Map<String,String>headers, @Body LoginCredentialsRequestEntity loginCredentialsRequestEntity);

    //Resume Login
    @POST
    Call<ResumeLoginResponseEntity> resumeLogin(@Url String url, @HeaderMap Map<String,String>headers, @Body ResumeLoginRequestEntity resumeLoginRequestEntity);

    //Resume Login
    @POST
    Call<ResumeConsentResponseEntity> resumeConsent(@Url String url, @HeaderMap Map<String,String>headers , @Body ResumeConsentRequestEntity resumeConsentRequestEntity);


    //consent Accept
    @POST
    Call<ConsentManagementAcceptResponseEntity> acceptConsent(@Url String url,  @HeaderMap Map<String,String>headers, @Body ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity);

    //Setup Email MFA
    @POST
    Call<SetupEmailMFAResponseEntity> setupEmailMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body DeviceInfoEntity deviceInfoEntity);

    //Enroll Email MFA
    @POST
    Call<EnrollEmailMFAResponseEntity> enrollEmailMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body EnrollEmailMFARequestEntity enrollEmailMFARequestEntity);


    //Inititate Email MFA
    @POST
    Call<InitiateEmailMFAResponseEntity> initiateEmailMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body InitiateEmailMFARequestEntity initiateEmailMFARequestEntity);

    //Authenticate Email MFA
    @POST
    Call<AuthenticateEmailResponseEntity> authenticateEmailMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body AuthenticateEmailRequestEntity authenticateEmailRequestEntity);

    //Setup SMS MFA
    @POST
    Call<SetupSMSMFAResponseEntity> setupSMSMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body DeviceInfoEntity deviceInfoEntity);

    //Enroll SMS MFA
    @POST
    Call<EnrollSMSMFAResponseEntity> enrollSMSMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body EnrollSMSMFARequestEntity enrollSMSMFARequestEntity);


    //Inititate sms MFA
    @POST
    Call<InitiateSMSMFAResponseEntity> initiateSMSMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body InitiateSMSMFARequestEntity initiateSMSMFARequestEntity);

    //Authenticate sms MFA
    @POST
    Call<AuthenticateSMSResponseEntity> authenticateSMSMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body AuthenticateSMSRequestEntity authenticateSMSRequestEntity);

    //Setup IVR MFA
    @POST
    Call<SetupIVRMFAResponseEntity> setupIVRMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body DeviceInfoEntity deviceInfoEntity);

    //Enroll IVR MFA
    @POST
    Call<EnrollIVRMFAResponseEntity> enrollIVRMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body EnrollIVRMFARequestEntity enrollIVRMFARequestEntity);


    //Inititate ivr MFA
    @POST
    Call<InitiateIVRMFAResponseEntity> initiateIVRMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body InitiateIVRMFARequestEntity initiateSMSMFARequestEntity);

    //Authenticate ivr MFA
    @POST
    Call<AuthenticateIVRResponseEntity> authenticateIVRMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body AuthenticateIVRRequestEntity authenticateIVRRequestEntity);


    //Setup BackupCode MFA
    @POST
    Call<SetupBackupCodeMFAResponseEntity> setupBackupCodeMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body SetupBackupCodeRequestEntity setupBackupCodeRequestEntity);

    //Enroll BackupCode MFA
    @POST
    Call<EnrollBackupCodeMFAResponseEntity> enrollBackupCodeMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body EnrollBackupCodeMFARequestEntity enrollBackupCodeMFARequestEntity);


    //Inititate backupcode MFA
    @POST
    Call<InitiateBackupCodeMFAResponseEntity> initiateBackupCodeMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body InitiateBackupCodeMFARequestEntity initiateBackupCodeMFARequestEntity);

    //Authenticate backupcode MFA
    @POST
    Call<AuthenticateBackupCodeResponseEntity> authenticateBackupCodeMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body AuthenticateBackupCodeRequestEntity authenticateBackupCodeRequestEntity);

    //Setup Face MFA
    @POST
    Call<SetupFaceMFAResponseEntity> setupFaceMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body SetupFaceMFARequestEntity setupFaceMFARequestEntity);

    //Enroll Face MFA
    @POST
    @Multipart
    Call<EnrollFaceMFAResponseEntity> enrollFaceMFA(@Url String url, @HeaderMap Map<String,String>headers,
                                                    @Part MultipartBody.Part face,
                                                    @PartMap() HashMap<String, RequestBody> map);


    //Inititate Face MFA
    @POST
    Call<InitiateFaceMFAResponseEntity> initiateFaceMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body InitiateFaceMFARequestEntity initiateFaceMFARequestEntity);

    //Authenticate Face MFA
    @POST
    @Multipart
    Call<AuthenticateFaceResponseEntity> authenticateFaceMFA(@Url String url,
                                                             @HeaderMap Map<String,String>headers,
                                                             @Part MultipartBody.Part face,
                                                             @PartMap() HashMap<String, String> map);

    //Setup FIDO MFA
    @POST
    Call<SetupFIDOMFAResponseEntity> setupFIDOMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body SetupFIDOMFARequestEntity setupFIDOMFARequestEntity);

    //Enroll FIDO MFA
    @POST
    Call<EnrollFIDOMFAResponseEntity> enrollFIDOMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body EnrollFIDOMFARequestEntity enrollFIDOMFARequestEntity);


    //Inititate FIDO MFA
    @POST
    Call<InitiateFIDOMFAResponseEntity> initiateFIDOMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body InitiateFIDOMFARequestEntity initiateFIDOMFARequestEntity);

    //Authenticate FIDO MFA
    @POST
    Call<AuthenticateFIDOResponseEntity> authenticateFIDOMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body AuthenticateFIDORequestEntity authenticateFIDORequestEntity);


    //Setup Fingerprint MFA
    @POST
    Call<SetupFingerprintMFAResponseEntity> setupFingerprintMFA(@Url String url, @HeaderMap Map<String,String>headers,@Body SetupFingerprintMFARequestEntity setupFingerprintMFARequestEntity);

    //Enroll Fingerprint MFA
    @POST
    Call<EnrollFingerprintMFAResponseEntity> enrollFingerprintMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body EnrollFingerprintMFARequestEntity enrollFingerprintMFARequestEntity);


    //Inititate Fingerprint MFA
    @POST
    Call<InitiateFingerprintMFAResponseEntity> initiateFingerprintMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body InitiateFingerprintMFARequestEntity initiateFingerprintMFARequestEntity);

    //Authenticate Fingerprint MFA
    @POST
    Call<AuthenticateFingerprintResponseEntity> authenticateFingerprintMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity);


    //Scanned
    @POST
    Call<ScannedResponseEntity> scanned(@Url String url, @HeaderMap Map<String,String>headers, @Body ScannedRequestEntity scannedRequestEntity);

    //Validate Device
    @POST
    Call<ValidateDeviceResponseEntity> validateDevice(@Url String url, @HeaderMap Map<String,String>headers, @Body ValidateDeviceRequestEntity validateDeviceRequestEntity);


    //Setup Pattern MFA
    @POST
    Call<SetupPatternMFAResponseEntity> setupPatternMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body SetupPatternMFARequestEntity setupPatternMFARequestEntity);

    //Enroll Pattern MFA
    @POST
    Call<EnrollPatternMFAResponseEntity> enrollPatternMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body EnrollPatternMFARequestEntity enrollPatternMFARequestEntity);


    //Inititate Pattern MFA
    @POST
    Call<InitiatePatternMFAResponseEntity> initiatePatternMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body InitiatePatternMFARequestEntity initiatePatternMFARequestEntity);

    //Authenticate Pattern MFA
    @POST
    Call<AuthenticatePatternResponseEntity> authenticatePatternMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body AuthenticatePatternRequestEntity authenticatePatternRequestEntity);

    //Setup SmartPush MFA
    @POST
    Call<SetupSmartPushMFAResponseEntity> setupSmartPushMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body SetupSmartPushMFARequestEntity setupSmartPushMFARequestEntity);

    //Enroll SmartPush MFA
    @POST
    Call<EnrollSmartPushMFAResponseEntity> enrollSmartPushMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body EnrollSmartPushMFARequestEntity enrollSmartPushMFARequestEntity);


    //Inititate SmartPush MFA
    @POST
    Call<InitiateSmartPushMFAResponseEntity> initiateSmartPushMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body InitiateSmartPushMFARequestEntity initiateSmartPushMFARequestEntity);

    //Authenticate SmartPush MFA
    @POST
    Call<AuthenticateSmartPushResponseEntity> authenticateSmartPushMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body AuthenticateSmartPushRequestEntity authenticateSmartPushRequestEntity);

    //Setup TOTP MFA
    @POST
    Call<SetupTOTPMFAResponseEntity> setupTOTPMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body SetupTOTPMFARequestEntity setupTOTPMFARequestEntity);

    //Enroll TOTP MFA
    @POST
    Call<EnrollTOTPMFAResponseEntity> enrollTOTPMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body EnrollTOTPMFARequestEntity enrollTOTPMFARequestEntity);


    //Inititate TOTP MFA
    @POST
    Call<InitiateTOTPMFAResponseEntity> initiateTOTPMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body InitiateTOTPMFARequestEntity initiateTOTPMFARequestEntity);

    //Authenticate TOTP MFA
    @POST
    Call<AuthenticateTOTPResponseEntity> authenticateTOTPMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body AuthenticateTOTPRequestEntity authenticateTOTPRequestEntity);

    //Setup Voice MFA
    @POST
    Call<SetupVoiceMFAResponseEntity> setupVoiceMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body SetupVoiceMFARequestEntity setupVoiceMFARequestEntity);

    //Enroll Voice MFA
    @POST
    Call<EnrollVoiceMFAResponseEntity> enrollVoiceMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body EnrollVoiceMFARequestEntity enrollVoiceMFARequestEntity);


    //Inititate Voice MFA
    @POST
    Call<InitiateVoiceMFAResponseEntity> initiateVoiceMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntity);

    //Authenticate Voice MFA
    @POST
    Call<AuthenticateVoiceResponseEntity> authenticateVoiceMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body AuthenticateVoiceRequestEntity authenticateVoiceRequestEntity);


    //Reset Password
    @POST
    Call<ResetPasswordResponseEntity> initiateresetPassword(@Url String url,  @HeaderMap Map<String,String>headers, @Body ResetPasswordRequestEntity resetPasswordRequestEntity);

    //Reset Password validate code
    @POST
    Call<ResetPasswordValidateCodeResponseEntity> resetPasswordValidateCode(@Url String url, @HeaderMap Map<String,String>headers,
                                                                        @Body ResetPasswordValidateCodeRequestEntity resetPasswordValidateCodeRequestEntity);

    //Reset New Password
    @POST
    Call<ResetNewPasswordResponseEntity> ResetNewPassword(@Url String url, @HeaderMap Map<String,String>headers,
                                                        @Body ResetNewPasswordRequestEntity resetNewPasswordRequestEntity);

    //Change Password
    @POST
    Call<ChangePasswordResponseEntity> changePassword(@Url String url, @HeaderMap Map<String,String>headers,
                                                      @Body ChangePasswordRequestEntity changePasswordRequestEntity);


                                                                            //Register New User
    @POST
    Call<RegisterNewUserResponseEntity> registerNewUser(@Url String url, @HeaderMap Map<String,String>headers, @Body RegistrationEntity registrationEntity);

    @POST
    Call<RegisterUserAccountInitiateResponseEntity> initiateAccountVerification(@Url String url, @HeaderMap Map<String,String>headers,
                                                                                @Body RegisterUserAccountInitiateRequestEntity registerUserAccountInitiateRequestEntity);

    @POST
    Call<RegisterUserAccountVerifyResponseEntity> verifyAccountVerification(@Url String url, @HeaderMap Map<String,String>headers,
                                                                            @Body RegisterUserAccountVerifyRequestEntity registerUserAccountVerifyRequestEntity);


    @POST
    Call<RegisterDeduplicationEntity> registerDeduplication(@Url String url, @HeaderMap Map<String,String> headers);

    //Login with Credentials
    @POST
    Call<LoginDeduplicationResponseEntity> logindeDuplicatopm(@Url String url, @HeaderMap Map<String,String>headers, @Body LoginEntity loginCredentialsRequestEntity);



    //-----------------------------------------------------GetCall-----------------------------------------------------------------
    //Get Registration Setup
    @GET
    Call<RegistrationSetupResponseEntity> getRegistrationSetup(@Url String url);

    //Get Registration Setup
    //get userinfo
    @GET
    Call<UserinfoEntity> getUserInfo(@Url String url, @HeaderMap Map<String,String>headers);

    //Get TenantInfo
    @GET
    Call<TenantInfoEntity> getTenantInfo(@Url String url);

    //Get TenantInfo
    @GET
    Call<DeduplicationResponseEntity> getDeduplicationList(@Url String url);

    //Get TenantInfo
    @GET
    Call<UserprofileResponseEntity> getInternalUserProfileInfo(@Url String url, @HeaderMap Map<String,String>headers);


    //Get Client
    @GET
    Call<ClientInfoEntity> getClientInfo(@Url String url);

    //Get MFA list
    @GET
    Call<MFAListResponseEntity> getmfaList(@Url String url, @Query("sub") String userid, @Query("userDeviceId") String userDeviceId);

    //Get ConsentInfo
    @GET
    Call<ConsentManagementResponseEntity> getConsentInfo(@Url String url);

    //Get Consent String Details
    @GET
    Call<ConsentDetailsResultEntity> getConsentStringDetails(@Url String url);



}
