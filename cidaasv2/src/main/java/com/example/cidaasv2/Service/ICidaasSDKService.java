package com.example.cidaasv2.Service;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.DocumentScanner.DocumentScannerServiceResultEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.DenyNotification.DenyNotificationRequestEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.DenyNotification.DenyNotificationResponseEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.NotificationEntity;
import com.example.cidaasv2.Service.Entity.SocialProvider.SocialProviderEntity;
import com.example.cidaasv2.Service.Entity.UserList.ConfiguredMFAListEntity;
import com.example.cidaasv2.Service.Entity.UserLoginInfo.UserLoginInfoEntity;
import com.example.cidaasv2.Service.Entity.UserLoginInfo.UserLoginInfoResponseEntity;
import com.example.cidaasv2.Service.Entity.UserProfile.UserprofileResponseEntity;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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


    @GET
    Call<SocialProviderEntity> getAccessTokenBySocial(@Url String url,@HeaderMap Map<String,String>headers);


    //Todo Add FieldMap Pending


    //Todo Add FieldMap Pending
   // @FormUrlEncoded
    @POST
    Call<String> getLoginUrl(@Url String url,@HeaderMap Map<String,String> headers);

  /*  //Setup Email MFA
    @POST
    Call<SetupEmailMFAResponseEntity> setupEmailMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body SetupEmailMFARequestEntity setupEmailMFARequestEntity);

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
    Call<SetupSMSMFAResponseEntity> setupSMSMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body SetupSMSMFARequestEntity setupSMSMFARequestEntity);

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
    Call<SetupIVRMFAResponseEntity> setupIVRMFA(@Url String url, @HeaderMap Map<String,String>headers, @Body SetupIVRMFARequestEntity setupIVRMFARequestEntity);

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
    Call<SetupFaceMFAResponseEntity> setupFaceMFA(@Url String url, @HeaderMap Map<String,String>headers,
                                                  @Body SetupFaceMFARequestEntity setupFaceMFARequestEntity);

    //Enroll Face MFA

    @Multipart
    @POST
    Call<EnrollFaceMFAResponseEntity> enrollFaceMFA(@Url String url, @HeaderMap Map<String,String>headers,
                                                    @Part MultipartBody.Part face,
                                                    @PartMap() HashMap<String, RequestBody> map);

    //Enroll Face with out photo MFA

    @Multipart
    @POST
    Call<EnrollFaceMFAResponseEntity> enrollFaceWithoutPhotoMFA(@Url String url, @HeaderMap Map<String,String>headers,
                                                    @PartMap() HashMap<String, RequestBody> map);


    //Inititate Face MFA
    @POST
    Call<InitiateFaceMFAResponseEntity> initiateFaceMFA(@Url String url, @HeaderMap Map<String,String>headers,
                                                        @Body InitiateFaceMFARequestEntity initiateFaceMFARequestEntity);

    //Authenticate Face MFA
    @POST
    @Multipart
    Call<AuthenticateFaceResponseEntity> authenticateFaceMFA(@Url String url,
                                                             @HeaderMap Map<String,String>headers,
                                                             @Part MultipartBody.Part face,
                                                             @PartMap() HashMap<String, RequestBody> map);
    //Authenticate Face MFA
    @POST
    @Multipart
    Call<AuthenticateFaceResponseEntity> authenticateFaceWithoutPhotoMFA(@Url String url,
                                                             @HeaderMap Map<String,String>headers,
                                                             @PartMap() HashMap<String, RequestBody> map);



    //Setup Voice MFA
    @POST
    Call<SetupVoiceMFAResponseEntity> setupVoiceMFA(@Url String url, @HeaderMap Map<String,String>headers,
                                                    @Body SetupVoiceMFARequestEntity setupVoiceMFARequestEntity);

    //Enroll Voice MFA
    @POST
    @Multipart
    Call<EnrollVoiceMFAResponseEntity> enrollVoiceMFA(@Url String url, @HeaderMap Map<String,String>headers,
                                                      @Part MultipartBody.Part audio,
                                                      @PartMap() HashMap<String, RequestBody> map);

    //Enroll Voice MFA
    @POST
    @Multipart
    Call<EnrollVoiceMFAResponseEntity> enrollVoiceWithoutAudioMFA(@Url String url, @HeaderMap Map<String,String>headers,
                                                      @PartMap() HashMap<String, RequestBody> map);


    //Inititate Voice MFA
    @POST
    Call<InitiateVoiceMFAResponseEntity> initiateVoiceMFA(@Url String url, @HeaderMap Map<String,String>headers,
                                                          @Body InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntity);

    //Authenticate Voice MFA
    @Multipart
    @POST
    Call<AuthenticateVoiceResponseEntity> authenticateVoiceMFA(@Url String url, @HeaderMap Map<String,String>headers,
                                                               @Part MultipartBody.Part voice,
                                                               @PartMap() HashMap<String, RequestBody> map);

    //Authenticate Voice MFA
    @Multipart
    @POST
    Call<AuthenticateVoiceResponseEntity> authenticateVoiceWithoutAudioMFA(@Url String url, @HeaderMap Map<String,String>headers,
                                                               @PartMap() HashMap<String, RequestBody> map);


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

    //Inititate SmartPush MFA
    @POST
    Call<ResponseBody> initiateSmartPushMFARAW(@Url String url, @HeaderMap Map<String,String>headers, @Body InitiateSmartPushMFARequestEntity initiateSmartPushMFARequestEntity);

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
*/

    //Enroll Face MFA
    @POST
    @Multipart
    Call<DocumentScannerServiceResultEntity> enrollDocument(@Url String url,@HeaderMap Map<String,String>headers, @Part MultipartBody.Part face);


    // Deny Notification
    @POST
    Call<DenyNotificationResponseEntity> denyNotificationService(@Url String url, @Header("Content-Type") String content_type,
                                      @Header("access_token") String access_token,@HeaderMap Map<String,String>headers, @Body DenyNotificationRequestEntity denyRequest);



    //Pending Notification
    @POST
    Call<NotificationEntity> getPendingNotification(@Url String url, @Header("Content-Type") String content_type,
                                                    @Header("access_token") String access_token,@HeaderMap Map<String,String>headers);


    @POST
    Call<Object> updateFCMToken(@Url String url,
                                @Header("access_token") String access_token,
                                @HeaderMap Map<String,String>headers,
                                @Body DeviceInfoEntity deviceInfoEntity);

    //Location History Service
    @POST
    Call<UserLoginInfoResponseEntity> getUserLoginInfoService(@Url String url, @HeaderMap Map<String,String>headers, @Body UserLoginInfoEntity userLoginInfoEntity);



    //-----------------------------------------------------GetCall-----------------------------------------------------------------

    //Get Registration Setup
    //get userinfo
    @GET
    Call<UserinfoEntity> getUserInfo(@Url String url, @HeaderMap Map<String,String>headers);



    //Get TenantInfo
    @GET
    Call<UserprofileResponseEntity> getInternalUserProfileInfo(@Url String url, @HeaderMap Map<String,String>headers);


    /*//Get MFA list
    @GET
    Call<MFAListResponseEntity> getmfaList(@Url String url,@HeaderMap Map<String,String>headers, @Query("sub") String userid, @Query("userDeviceId") String userDeviceId, @Query("common_configs") boolean common_configs);

    //Get MFA list
    @GET
    Call<MFAListResponseEntity> getmfaList(@Url String url,@HeaderMap Map<String,String>headers, @Body String email);
*/
    @GET
    Call<ConfiguredMFAListEntity> getConfiguredMFAList(@Url String url,@HeaderMap Map<String,String>headers, @Query("sub") String sub, @Query("userDeviceId") String userDeviceId);

    //Construct URL
    @GET
    Call<Object> getUrlList(@Url String url,@HeaderMap Map<String,String>headers);

    //-----------------------------------------------------DELETE Call-----------------------------------------------------------------
/*
    //Delete
    @DELETE
    Call<DeleteMFAResponseEntity> delete(@Url String url,@HeaderMap Map<String,String>headers, @Header("access_token") String accessToken);//Delete



    //DeleteAll
    @DELETE
    Call<DeleteMFAResponseEntity> deleteAll(@Url String url, @HeaderMap Map<String,String>headers,@Header("access_token") String accessToken);*/




/*




    @POST
    Call<FidoEnrollServiceResultEntity> enrollFido(@Url String url, @Header("Content-Type") String content_type,
                                                   @Header("access_token") String access_token, @Body FidoEnrollServiceEntity fidoRequest
    );

    @POST
    Call<FidoAuthenticateServiceResultEntity> authenticateFido(@Url String url,
                                                               @Header("Content-Type") String content_type,
                                                               @Header("access_token") String access_token,
                                                               @Body FidoAuthenticateServiceEntity fidoAuthenticateServiceEntity);*/


}
