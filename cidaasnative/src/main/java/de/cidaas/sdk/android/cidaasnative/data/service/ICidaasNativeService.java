package de.cidaas.sdk.android.cidaasnative.data.service;

import java.util.Map;

import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.AccountVerificationListResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.InitiateAccountVerificationRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.InitiateAccountVerificationResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.VerifyAccountRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.VerifyAccountResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.authrequest.AuthRequestResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.clientinfo.ClientInfoEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.deduplication.DeduplicationResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.deduplication.registerdeduplication.RegisterDeduplicationEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.login.LoginCredentialsRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.login.LoginEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.login.LogoutResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.register.RegistrationEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.register.UpdateUserResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.register.registeruser.RegisterNewUserResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.register.registrationsetup.RegistrationSetupResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.ResetPasswordRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.ResetPasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.changepassword.ChangePasswordRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.changepassword.ChangePasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetnewpassword.ResetNewPasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetnewpassword.ResetPasswordEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetpasswordvalidatecode.ResetPasswordValidateCodeRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetpasswordvalidatecode.ResetPasswordValidateCodeResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.tenantinfo.TenantInfoEntity;
import de.cidaas.sdk.android.entities.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ICidaasNativeService {

    //RequestId
    @POST
    @FormUrlEncoded
    Call<AuthRequestResponseEntity> getRequestId(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap(encoded = true) Map<String, String> params);

    //Get Client
    @GET
    Call<ClientInfoEntity> getClientInfo(@Url String url, @HeaderMap Map<String, String> headers);

    //Get TenantInfo
    @GET
    Call<TenantInfoEntity> getTenantInfo(@Url String url, @HeaderMap Map<String, String> headers);


    //Login with Credentials
    @POST
    Call<LoginCredentialsResponseEntity> loginWithCredentials(@Url String url, @HeaderMap Map<String, String> headers, @Body LoginCredentialsRequestEntity loginCredentialsRequestEntity);

    //Logout
    @GET
    Call<ResponseBody> logout(@Url String url, @HeaderMap Map<String, String> headers);

    //Logout for embedded Browser
    Call<LogoutResponseEntity> logoutFromEmbeddedBrowser(@Url String url, @HeaderMap Map<String, String> headers, @Query("access_token_hint") String access_token_hint, @Query("post_logout_redirect_uri") String postlogoutRedirectURL);

    //Get TenantInfo
    @GET
    Call<DeduplicationResponseEntity> getDeduplicationList(@Url String url, @HeaderMap Map<String, String> headers);

    @POST
    Call<RegisterDeduplicationEntity> registerDeduplication(@Url String url, @HeaderMap Map<String, String> headers);

    //Reset Password
    @POST
    Call<ResetPasswordResponseEntity> initiateresetPassword(@Url String url, @HeaderMap Map<String, String> headers, @Body ResetPasswordRequestEntity resetPasswordRequestEntity);

    //Reset Password validate code
    @POST
    Call<ResetPasswordValidateCodeResponseEntity> resetPasswordValidateCode(@Url String url, @HeaderMap Map<String, String> headers,
                                                                            @Body ResetPasswordValidateCodeRequestEntity resetPasswordValidateCodeRequestEntity);

    //Reset New Password
    @POST
    Call<ResetNewPasswordResponseEntity> ResetNewPassword(@Url String url, @HeaderMap Map<String, String> headers,
                                                          @Body ResetPasswordEntity resetPasswordEntity);

    //Change Password
    @POST
    Call<ChangePasswordResponseEntity> changePassword(@Url String url, @HeaderMap Map<String, String> headers,
                                                      @Body ChangePasswordRequestEntity changePasswordRequestEntity);


    //Register New User
    @POST
    Call<RegisterNewUserResponseEntity> registerNewUser(@Url String url, @HeaderMap Map<String, String> headers, @Body RegistrationEntity registrationEntity);

    //update user Profile
    @PUT
    Call<UpdateUserResponseEntity> updateUserProfile(@Url String url, @HeaderMap Map<String, String> headers, @Body RegistrationEntity registrationEntity);


    @POST
    Call<InitiateAccountVerificationResponseEntity> initiateAccountVerification(@Url String url, @HeaderMap Map<String, String> headers,
                                                                                @Body InitiateAccountVerificationRequestEntity initiateAccountVerificationRequestEntity);

    @POST
    Call<VerifyAccountResponseEntity> verifyAccountVerification(@Url String url, @HeaderMap Map<String, String> headers,
                                                                @Body VerifyAccountRequestEntity verifyAccountRequestEntity);

    @GET
    Call<AccountVerificationListResponseEntity> getAccountVerificationList(@Url String url, @HeaderMap Map<String, String> headers);


    //Login with Credentials
    @POST
    Call<LoginCredentialsResponseEntity> logindeDuplicatopm(@Url String url, @HeaderMap Map<String, String> headers, @Body LoginEntity loginCredentialsRequestEntity);


    //Get Registration Setup
    @GET
    Call<RegistrationSetupResponseEntity> getRegistrationSetup(@Url String url, @HeaderMap Map<String, String> headers);

}
