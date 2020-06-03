package de.cidaas.sdk.android.cidaasVerification.data.service;


import java.util.HashMap;
import java.util.Map;

import de.cidaas.sdk.android.cidaasVerification.data.entity.authenticate.AuthenticateEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.authenticate.AuthenticateResponse;
import de.cidaas.sdk.android.cidaasVerification.data.entity.authenticatedhistory.AuthenticatedHistoryEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.authenticatedhistory.AuthenticatedHistoryResponse;
import de.cidaas.sdk.android.cidaasVerification.data.entity.delete.DeleteEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.delete.DeleteResponse;
import de.cidaas.sdk.android.cidaasVerification.data.entity.enroll.EnrollEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.enroll.EnrollResponse;
import de.cidaas.sdk.android.cidaasVerification.data.entity.initiate.InitiateEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.initiate.InitiateResponse;
import de.cidaas.sdk.android.cidaasVerification.data.entity.push.pushacknowledge.PushAcknowledgeEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.push.pushacknowledge.PushAcknowledgeResponse;
import de.cidaas.sdk.android.cidaasVerification.data.entity.push.pushallow.PushAllowEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.push.pushallow.PushAllowResponse;
import de.cidaas.sdk.android.cidaasVerification.data.entity.push.pushreject.PushRejectEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.push.pushreject.PushRejectResponse;
import de.cidaas.sdk.android.cidaasVerification.data.entity.scanned.ScannedEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.scanned.ScannedResponse;
import de.cidaas.sdk.android.cidaasVerification.data.entity.settings.configuredmfalist.ConfiguredMFAList;
import de.cidaas.sdk.android.cidaasVerification.data.entity.settings.configuredmfalist.GetMFAListEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.settings.others.UpdateFCMTokenEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.settings.pendingnotification.PendingNotificationEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.settings.pendingnotification.PendingNotificationResponse;
import de.cidaas.sdk.android.cidaasVerification.data.entity.setup.SetupEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.setup.SetupResponse;
import de.cidaas.sdk.android.cidaasVerification.data.entity.updatefcmtoken.UpdateFCMTokenResponseEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.verificationcontinue.VerificationContinue;
import de.cidaas.sdk.android.cidaasVerification.data.entity.verificationcontinue.VerificationContinueResponseEntity;
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


public interface ICidaasSDK_V2_Services {

    //Setup
    @POST
    Call<SetupResponse> setup(@Url String url, @HeaderMap Map<String, String> headers, @Body SetupEntity setupEntity);

    //Scanned
    @POST
    Call<ScannedResponse> scanned(@Url String url, @HeaderMap Map<String, String> headers, @Body ScannedEntity scannedEntity);

    //Enroll
    @POST
    Call<EnrollResponse> enroll(@Url String url, @HeaderMap Map<String, String> headers, @Body EnrollEntity enrollEntity);

    //Enroll with multipart
    @Multipart
    @POST
    Call<EnrollResponse> enrollWithMultipart(@Url String url, @HeaderMap Map<String, String> headers, @Part MultipartBody.Part face,
                                             @PartMap() HashMap<String, RequestBody> map);

    //Initiate
    @POST
    Call<InitiateResponse> initiate(@Url String url, @HeaderMap Map<String, String> headers, @Body InitiateEntity initiateEntity);

    //PushAcknowledge
    @POST
    Call<PushAcknowledgeResponse> pushAcknowledge(@Url String url, @HeaderMap Map<String, String> headers, @Body PushAcknowledgeEntity pushAcknowledgeEntity);

    //PushAllow
    @POST
    Call<PushAllowResponse> pushAllow(@Url String url, @HeaderMap Map<String, String> headers, @Body PushAllowEntity pushAllowEntity);

    //PushReject
    @POST
    Call<PushRejectResponse> pushReject(@Url String url, @HeaderMap Map<String, String> headers, @Body PushRejectEntity pushRejectEntity);

    //Authenticate
    @POST
    Call<AuthenticateResponse> authenticate(@Url String url, @HeaderMap Map<String, String> headers, @Body AuthenticateEntity authenticateEntity);


    //Authenticate Face MFA
    @POST
    @Multipart
    Call<AuthenticateResponse> authenticateWithMultipart(@Url String url, @HeaderMap Map<String, String> headers, @Part MultipartBody.Part face,
                                                         @PartMap() HashMap<String, RequestBody> map);

    //Delete MFA
    @HTTP(method = "DELETE", hasBody = true)
    Call<DeleteResponse> delete(@Url String url, @HeaderMap Map<String, String> headers, @Body DeleteEntity deleteEntity);

    //ConfiguredMFAList
    @POST
    Call<ConfiguredMFAList> getConfiguredMFAList(@Url String url, @HeaderMap Map<String, String> headers, @Body GetMFAListEntity getMFAListEntity);

    //PendingNotification List
    @POST
    Call<PendingNotificationResponse> getPendingNotification(@Url String url, @HeaderMap Map<String, String> headers, @Body PendingNotificationEntity pendingNotificationEntity);

    //AuthenticatedHistory List
    @POST
    Call<AuthenticatedHistoryResponse> getAuthenticatedHistory(@Url String url, @HeaderMap Map<String, String> headers, @Body AuthenticatedHistoryEntity authenticatedHistoryEntity);

    //Update FCM List
    @POST
    Call<UpdateFCMTokenResponseEntity> updateFCMToken(@Url String url, @HeaderMap Map<String, String> headers, @Body UpdateFCMTokenEntity updateFCMTokenEntity);

    //Resume Login
    @POST
    Call<VerificationContinueResponseEntity> verificationContinue(@Url String url, @HeaderMap Map<String, String> headers, @Body VerificationContinue verificationContinueEntity);


}
