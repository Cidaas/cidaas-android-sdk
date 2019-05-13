package com.example.cidaasv2.Interface;


import android.content.Context;

import com.example.cidaasv2.Helper.Entity.ConsentEntity;
import com.example.cidaasv2.Helper.Entity.FingerPrintEntity;
import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Entity.RegistrationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentDetailsResultEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.DeduplicationResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS.EnrollSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS.SetupSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetPasswordEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyResponseEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupResponseEntity;

import java.io.File;
import java.util.HashMap;

import androidx.annotation.NonNull;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public interface IOAuthWebLogin {

    //Get RequestId
     void getRequestId( Result<AuthRequestResponseEntity> result,HashMap<String, String>... extraParams);
     void getRequestId(String DomainUrl,String ClientId,String RedirectURL,String ClientSecret,Result<AuthRequestResponseEntity> result);
     //void getRequestId(Dictionary<String,String> loginproperties,Result<AuthRequestResponseEntity> result);

    //Get Tenant Info
    void getTenantInfo(Result<TenantInfoEntity> result);

    //Get ClientInfo
    void getClientInfo(String RequestId,Result<ClientInfoEntity> clientInfoEntityResult);


    //Login methods
    void loginWithCredentials(String requestId, LoginEntity loginEntity, Result<LoginCredentialsResponseEntity> result);

    //Consent Details
    void getConsentDetails(String consentName, Result<ConsentDetailsResultEntity> consentResult);
    void loginAfterConsent(ConsentEntity consentEntity,Result<LoginCredentialsResponseEntity> loginresult);

    //MFA
    void getMFAList(String sub,Result<MFAListResponseEntity> mfaresult);

    //Configuration EMAIL
    void configureEmail(String sub,Result<SetupEmailMFAResponseEntity> result);
    void enrollEmail(String code,String statusId,Result<EnrollEmailMFAResponseEntity> result);
    void loginWithEmail(PasswordlessEntity passwordlessEntity, final Result<InitiateEmailMFAResponseEntity> initiateresult);
    void verifyEmail(String code,String statusId,Result<LoginCredentialsResponseEntity> result);

    //SMS
    void configureSMS(String sub,Result<SetupSMSMFAResponseEntity> result);
    void enrollSMS(String code,String statusId,Result<EnrollSMSMFAResponseEntity> result);
    void loginWithSMS(PasswordlessEntity passwordlessEntity,Result<InitiateSMSMFAResponseEntity> result);
    void verifySMS(String code,String statusId,Result<LoginCredentialsResponseEntity> result);

// IVR
    void configureIVR(String sub,Result<SetupIVRMFAResponseEntity> result);
    void enrollIVR(String code,String statusId,Result<EnrollIVRMFAResponseEntity> result);
    void loginWithIVR(PasswordlessEntity passwordlessEntity,final Result<InitiateIVRMFAResponseEntity> initiateresult);
    void verifyIVR(String code,String statusId,Result<LoginCredentialsResponseEntity> result);

    //Backupcode
    void configureBackupcode(String sub,Result<SetupBackupCodeMFAResponseEntity> result);
    void loginWithBackupcode(String code,PasswordlessEntity passwordlessEntity, Result<LoginCredentialsResponseEntity> result);

    //FACE
    void configureFaceRecognition(File photo, String sub, @NonNull final String logoURL, int attempts, Result<EnrollFaceMFAResponseEntity> result);
    void loginWithFaceRecognition(File photo, PasswordlessEntity passwordlessEntity,
                                   final Result<LoginCredentialsResponseEntity> loginresult);

    //FINGERPRINT
    void configureFingerprint(Context context,String sub, @NonNull final String logoURL, FingerPrintEntity fingerPrintEntity, Result<EnrollFingerprintMFAResponseEntity> result);
    void loginWithFingerprint(Context context,PasswordlessEntity passwordlessEntity, FingerPrintEntity fingerPrintEntity, final Result<LoginCredentialsResponseEntity> loginresult);

    //PATTERN
    void configurePatternRecognition(@NonNull final String pattern,String sub,String logoURL,Result<EnrollPatternMFAResponseEntity> result);
    void loginWithPatternRecognition(@NonNull final String pattern, @NonNull final PasswordlessEntity passwordlessEntity,
                                     final Result<LoginCredentialsResponseEntity> loginresult);
    //SMARTPUSH
    void configureSmartPush(String sub,@NonNull final String logoURL,Result<EnrollSmartPushMFAResponseEntity> result);
    void loginWithSmartPush(PasswordlessEntity passwordlessEntity, final Result<LoginCredentialsResponseEntity> loginresult);

    //TOTP
    void configureTOTP(String sub,@NonNull final String logoURL,Result<EnrollTOTPMFAResponseEntity> result);
    void loginWithTOTP(PasswordlessEntity passwordlessEntity, final Result<LoginCredentialsResponseEntity> loginresult);

    //VOICE
    void configureVoiceRecognition(File voice,@NonNull final String logoURL,String sub,Result<EnrollVoiceMFAResponseEntity> result);
    void loginWithVoiceRecognition(File voice,PasswordlessEntity passwordlessEntity,
                                    final Result<LoginCredentialsResponseEntity> loginresult);

//--------------------------------REGISTRATION-------------------------------------------


    void getRegistrationFields(String requestId, String locale,Result<RegistrationSetupResponseEntity> result);
    void registerUser(String requestId, RegistrationEntity registrationEntity,final Result<RegisterNewUserResponseEntity> registerFieldsresult);

    void initiateEmailVerification(String requestId,String sub, final Result<RegisterUserAccountInitiateResponseEntity> Result);
    void initiateSMSVerification(String requestId,String sub, final Result<RegisterUserAccountInitiateResponseEntity> Result);
    void initiateIVRVerification(String requestId,String sub, final Result<RegisterUserAccountInitiateResponseEntity> Result);
    void verifyAccount(String code,String aacvid, Result<RegisterUserAccountVerifyResponseEntity> result);

//--------------------------------DEDUPLICATION-------------------------------------------
    void getDeduplicationDetails(String trackId, final Result<DeduplicationResponseEntity> deduplicaionResult);
    void registerUser(String trackId,final Result<RegisterDeduplicationEntity> deduplicaionResult);
    void loginWithDeduplication(String requestId,String sub,String password, final Result<LoginCredentialsResponseEntity> deduplicaionResult);

//--------------------------------RESETPASSWORD-------------------------------------------

    //Reset Password
    void initiateResetPasswordByEmail(String requestId,String email, Result<ResetPasswordResponseEntity> resetPasswordResponseEntityResult);
    void initiateResetPasswordBySMS(String requestId,String mobile, Result<ResetPasswordResponseEntity> resetPasswordResponseEntityResult);
    void handleResetPassword(String code,String rprq, final Result<ResetPasswordValidateCodeResponseEntity> resetpasswordResult);
    void resetPassword(ResetPasswordEntity resetPasswordEntity, final Result<ResetNewPasswordResponseEntity> resetpasswordResult);

//---------------------------------Change Password-------------------------------

void changePassword(String sub, ChangePasswordRequestEntity changePasswordRequestEntity,Result<ChangePasswordResponseEntity> result);


//-----------------------------common Methods--------------------------------------
    void getAccessToken(String sub,Result<AccessTokenEntity> result);
    void renewToken(String refershtoken,Result<AccessTokenEntity> result);
    void getUserInfo(String sub, Result<UserinfoEntity> result);


}
