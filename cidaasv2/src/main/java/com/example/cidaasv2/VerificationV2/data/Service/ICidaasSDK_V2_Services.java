package com.example.cidaasv2.VerificationV2.data.Service;



import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceResponseEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.AuthenticatedHistory.AuthenticatedHistoryDataEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.AuthenticatedHistory.AuthenticatedHistoryEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.AuthenticatedHistory.AuthenticatedHistoryResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Delete.DeleteEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Delete.DeleteResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Initiate.InitiateEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Initiate.InitiateResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAcknowledge.PushAcknowledgeEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAcknowledge.PushAcknowledgeResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAllow.PushAllowEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAllow.PushAllowResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushReject.PushRejectEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushReject.PushRejectResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.ConfiguredMFAList.ConfiguredMFAList;
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.ConfiguredMFAList.GetMFAListEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.PendingNotification.PendingNotificationEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.PendingNotification.PendingNotificationResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupResponse;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;



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
    @DELETE
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


}
