package widas.cidaassdkv2.cidaasVerificationV2.data.Service;


import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Authenticate.AuthenticateEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Authenticate.AuthenticateResponse;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.AuthenticatedHistory.AuthenticatedHistoryEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.AuthenticatedHistory.AuthenticatedHistoryResponse;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Delete.DeleteEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Delete.DeleteResponse;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Enroll.EnrollEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Enroll.EnrollResponse;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Initiate.InitiateEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Initiate.InitiateResponse;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Push.PushAcknowledge.PushAcknowledgeEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Push.PushAcknowledge.PushAcknowledgeResponse;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Push.PushAllow.PushAllowEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Push.PushAllow.PushAllowResponse;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Push.PushReject.PushRejectEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Push.PushReject.PushRejectResponse;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Scanned.ScannedEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Scanned.ScannedResponse;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Settings.ConfiguredMFAList.ConfiguredMFAList;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Settings.ConfiguredMFAList.GetMFAListEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Settings.Others.UpdateFCMTokenEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Settings.PendingNotification.PendingNotificationEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Settings.PendingNotification.PendingNotificationResponse;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Setup.SetupEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Setup.SetupResponse;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.UpdateFCMToken.UpdateFCMTokenResponseEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.VerificationContinue.VerificationContinue;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.VerificationContinue.VerificationContinueResponseEntity;


public interface ICidaasSDK_V2_Services {

    //Setup
    @POST
    Call<SetupResponse> setup(@Url String url, @HeaderMap Map<String,String> headers, @Body SetupEntity setupEntity);

    //Scanned
    @POST
    Call<ScannedResponse> scanned(@Url String url, @HeaderMap Map<String,String> headers, @Body ScannedEntity scannedEntity);

    //Enroll
    @POST
    Call<EnrollResponse> enroll(@Url String url, @HeaderMap Map<String,String> headers, @Body EnrollEntity enrollEntity);

    //Enroll with multipart
    @Multipart
    @POST
    Call<EnrollResponse> enrollWithMultipart(@Url String url, @HeaderMap Map<String,String> headers, @Part MultipartBody.Part face,
                                             @PartMap() HashMap<String, RequestBody> map);

    //Initiate
    @POST
    Call<InitiateResponse> initiate(@Url String url, @HeaderMap Map<String,String> headers, @Body InitiateEntity initiateEntity);

    //PushAcknowledge
    @POST
    Call<PushAcknowledgeResponse> pushAcknowledge(@Url String url, @HeaderMap Map<String,String> headers, @Body PushAcknowledgeEntity pushAcknowledgeEntity);

    //PushAllow
    @POST
    Call<PushAllowResponse> pushAllow(@Url String url, @HeaderMap Map<String,String> headers, @Body PushAllowEntity pushAllowEntity);

    //PushReject
    @POST
    Call<PushRejectResponse> pushReject(@Url String url, @HeaderMap Map<String,String> headers, @Body PushRejectEntity pushRejectEntity);

    //Authenticate
    @POST
    Call<AuthenticateResponse> authenticate(@Url String url, @HeaderMap Map<String,String> headers, @Body AuthenticateEntity authenticateEntity);


    //Authenticate Face MFA
    @POST
    @Multipart
    Call<AuthenticateResponse> authenticateWithMultipart(@Url String url, @HeaderMap Map<String,String>headers, @Part MultipartBody.Part face,
                                                             @PartMap() HashMap<String, RequestBody> map);

    //Delete MFA
    @HTTP(method = "DELETE", hasBody = true)
    Call<DeleteResponse> delete(@Url String url, @HeaderMap Map<String,String> headers, @Body DeleteEntity deleteEntity);

    //ConfiguredMFAList
    @POST
    Call<ConfiguredMFAList> getConfiguredMFAList(@Url String url, @HeaderMap Map<String,String> headers, @Body GetMFAListEntity getMFAListEntity);

    //PendingNotification List
    @POST
    Call<PendingNotificationResponse> getPendingNotification(@Url String url, @HeaderMap Map<String,String> headers, @Body PendingNotificationEntity pendingNotificationEntity);

    //AuthenticatedHistory List
    @POST
    Call<AuthenticatedHistoryResponse> getAuthenticatedHistory(@Url String url, @HeaderMap Map<String,String> headers, @Body AuthenticatedHistoryEntity authenticatedHistoryEntity);

    //Update FCM List
    @POST
    Call<UpdateFCMTokenResponseEntity> updateFCMToken(@Url String url, @HeaderMap Map<String,String> headers, @Body UpdateFCMTokenEntity updateFCMTokenEntity);

    //Resume Login
    @POST
    Call<VerificationContinueResponseEntity> verificationContinue(@Url String url, @HeaderMap Map<String,String>headers, @Body VerificationContinue verificationContinueEntity);


}
