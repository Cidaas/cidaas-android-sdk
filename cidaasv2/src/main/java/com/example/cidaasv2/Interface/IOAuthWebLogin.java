package com.example.cidaasv2.Interface;


import android.support.annotation.NonNull;

import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey.EnrollFIDOMFAResponseEntity;
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
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS.SetupSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;

import java.io.File;
import java.util.Dictionary;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public interface IOAuthWebLogin {
    //login Methods
     void getRequestId(Result<AuthRequestResponseEntity> result);
     void getRequestId(String DomainUrl,String ClientId,String RedirectURL,String ClientSecret,Result<AuthRequestResponseEntity> result);
     //void getRequestId(Dictionary<String,String> loginproperties,Result<AuthRequestResponseEntity> result);

     void getLoginURL(Result<String> result);
     void getLoginURL(String RequestId,Result<String> result);
     void getLoginURL(String DomainUrl,String ClientId,String RedirectURL,String ClientSecret,Result<String> result);
     void getLoginURL(Dictionary<String,String> loginproperties,Result<String> result);


    //Login methods
     void loginWithCredentials(String requestId, LoginEntity loginEntity, Result<LoginCredentialsResponseEntity> result);
     void loginWithEmail(final String email, final String mobile,@NonNull final String sub, @NonNull final String usageType, @NonNull final String trackId,
                         @NonNull final String requestId, final Result<InitiateEmailMFAResponseEntity> initiateresult);

     void loginWithSMS(final String email, final String mobile,@NonNull final String sub, @NonNull final String usageType, @NonNull final String trackId,
                       @NonNull final String requestId, Result<InitiateSMSMFAResponseEntity> result);

    void verifyEmail(String code,Result<LoginCredentialsResponseEntity> result);

    void verifySMS(String code,Result<LoginCredentialsResponseEntity> result);

    void verifyIVR(String code,Result<LoginCredentialsResponseEntity> result);

    void verifyBackupCode(String code,Result<LoginCredentialsResponseEntity> result);

     void loginWithIVR(final String email, final String mobile, final String sub,@NonNull final String usageType,@NonNull final String trackId,
                       @NonNull final String requestId,final Result<InitiateIVRMFAResponseEntity> initiateresult);

     void loginWithBackupCode(final String code,final String email, final String mobile,@NonNull final String sub, @NonNull final String usageType,
                              @NonNull final String trackId, @NonNull final String requestId, Result<LoginCredentialsResponseEntity> result);

     void loginWithFaceRecognition(File faceimageFile, final String email, final String mobile, final String sub,
                        String requestId,String trackId,@NonNull final String usageType, final Result<LoginCredentialsResponseEntity> loginresult);

     void loginWithFIDO(String usageType,String email, String sub,String trackId,Result<LoginCredentialsResponseEntity> result);
     void loginWithFingerprint(final String email, final String mobile, final String sub,
                               String requestId,String trackId,@NonNull final String usageType, final Result<LoginCredentialsResponseEntity> loginresult);

     void loginWithPatternRecognition(@NonNull final String patternCode, final String email, final String mobile, final String sub,
                           String requestId,String trackId,@NonNull final String usageType, final Result<LoginCredentialsResponseEntity> loginresult);

     void loginWithSmartPush(final String email, final String mobile, final String sub,
                             String requestId,String trackId,@NonNull final String usageType, final Result<LoginCredentialsResponseEntity> loginresult);

     void loginWithTOTP( final String email, final String mobile, final String sub,
                        String requestId,String trackId,@NonNull final String usageType, final Result<LoginCredentialsResponseEntity> loginresult);

     void loginWithVoiceRecognition(File VoiceaudioFile,final String email, final String mobile, final String sub,
                         String requestId,String trackId,@NonNull final String usageType, final Result<LoginCredentialsResponseEntity> loginresult);


     //Configure methods
    void configureEmail(String sub,Result<SetupEmailMFAResponseEntity> result);
    void configureSMS(String sub,Result<SetupSMSMFAResponseEntity> result);
    void configureIVR(String sub,Result<SetupIVRMFAResponseEntity> result);
    void configureBackupCode(String sub,Result<SetupBackupCodeMFAResponseEntity> result);


    void configureFaceRecognition(File faceimageFile,String sub, Result<EnrollFaceMFAResponseEntity> result);
    void configureFIDO(String sub,Result<EnrollFIDOMFAResponseEntity> result);
    void configureFingerprint(String sub,Result<EnrollFingerprintMFAResponseEntity> result);
    void configurePatternRecognition(String patternString,String sub,Result<EnrollPatternMFAResponseEntity> result);
    void configureSmartPush(String sub,Result<EnrollSmartPushMFAResponseEntity> result);
    void configureTOTP(String sub,Result<EnrollTOTPMFAResponseEntity> result);
    void configureVoiceRecognition(File VoiceaudioFile,String sub,Result<EnrollVoiceMFAResponseEntity> result);


    //Browser opening methods
    void loginWithBrowser(String color,Result<AccessTokenEntity> result);


    void getRegisterationFields(String requestId, String acceptLanguage,Result<RegistrationSetupResponseEntity> result);

    //Get Tenant Info
     void getTenantInfo(Result<TenantInfoEntity> result);

    //Get ClientInfo
     void getClientInfo(String RequestId,Result<ClientInfoEntity> clientInfoEntityResult);

     //Reset Password
    void initiateResetPassword(String requestId,ResetPasswordRequestEntity resetPasswordRequestEntity, Result<ResetPasswordResponseEntity> resetPasswordResponseEntityResult);

    //Authorization Methods
     void getLoginCode(String url,Result<AccessTokenEntity> callback);

     void getAccessTokenByCode(String code, Result<AccessTokenEntity> result);
     void getAccessToken(String userid,Result<AccessTokenEntity> result);
     void getAccessTokenByRefreshToken(String refershtoken,Result<AccessTokenEntity> result);

     void getUserInfo(String access_token, Result<UserinfoEntity> result);

}
