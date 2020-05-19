package widas.cidaassdkv2.cidaasnativev2.data.Service;

import com.example.cidaasv2.Helper.Entity.LoginCredentialsResponseEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.AccountVerification.AccountVerificationListResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.AuthRequest.AuthRequestResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.ClientInfo.ClientInfoEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Deduplication.DeduplicationResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Login.LoginCredentialsRequestEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Login.LoginEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Login.LogoutResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Register.RegisterUser.RegisterNewUserResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.AccountVerification.InitiateAccountVerificationRequestEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.AccountVerification.InitiateAccountVerificationResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.AccountVerification.VerifyAccountRequestEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.AccountVerification.VerifyAccountResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Register.RegistrationEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Register.RegistrationSetup.RegistrationSetupResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.ResetPassword.ResetNewPassword.ResetPasswordEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.ResetPassword.ResetPasswordRequestEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.ResetPassword.ResetPasswordResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeRequestEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.TenantInfo.TenantInfoEntity;

public interface ICidaasNativeService {

    //RequestId
    @POST
    @FormUrlEncoded
    Call<AuthRequestResponseEntity> getRequestId(@Url String url, @HeaderMap Map<String,String>headers, @FieldMap(encoded = true) Map<String, String> params);

    //Get Client
    @GET
    Call<ClientInfoEntity> getClientInfo(@Url String url, @HeaderMap Map<String,String> headers);

    //Get TenantInfo
    @GET
    Call<TenantInfoEntity> getTenantInfo(@Url String url, @HeaderMap Map<String,String>headers);


    //Login with Credentials
    @POST
    Call<LoginCredentialsResponseEntity> loginWithCredentials(@Url String url, @HeaderMap Map<String,String>headers, @Body LoginCredentialsRequestEntity loginCredentialsRequestEntity);

    //Logout for embedded Browser
    Call<LogoutResponseEntity> logoutFromEmbeddedBrowser (@Url String url, @HeaderMap Map<String,String>headers, @Query("access_token_hint") String access_token_hint, @Query("post_logout_redirect_uri") String postlogoutRedirectURL);

    //Get TenantInfo
    @GET
    Call<DeduplicationResponseEntity> getDeduplicationList(@Url String url, @HeaderMap Map<String,String>headers);

    @POST
    Call<RegisterDeduplicationEntity> registerDeduplication(@Url String url, @HeaderMap Map<String,String> headers);

    //Reset Password
    @POST
    Call<ResetPasswordResponseEntity> initiateresetPassword(@Url String url, @HeaderMap Map<String,String>headers, @Body ResetPasswordRequestEntity resetPasswordRequestEntity);

    //Reset Password validate code
    @POST
    Call<ResetPasswordValidateCodeResponseEntity> resetPasswordValidateCode(@Url String url, @HeaderMap Map<String,String>headers,
                                                                            @Body ResetPasswordValidateCodeRequestEntity resetPasswordValidateCodeRequestEntity);
    //Reset New Password
    @POST
    Call<ResetNewPasswordResponseEntity> ResetNewPassword(@Url String url, @HeaderMap Map<String,String>headers,
                                                          @Body ResetPasswordEntity resetPasswordEntity);

    //Change Password
    @POST
    Call<ChangePasswordResponseEntity> changePassword(@Url String url, @HeaderMap Map<String,String>headers,
                                                      @Body ChangePasswordRequestEntity changePasswordRequestEntity);


    //Register New User
    @POST
    Call<RegisterNewUserResponseEntity> registerNewUser(@Url String url, @HeaderMap Map<String,String>headers, @Body RegistrationEntity registrationEntity);

    @POST
    Call<InitiateAccountVerificationResponseEntity> initiateAccountVerification(@Url String url, @HeaderMap Map<String,String>headers,
                                                                                @Body InitiateAccountVerificationRequestEntity initiateAccountVerificationRequestEntity);

    @POST
    Call<VerifyAccountResponseEntity> verifyAccountVerification(@Url String url, @HeaderMap Map<String,String>headers,
                                                                @Body VerifyAccountRequestEntity verifyAccountRequestEntity);

    @GET
    Call<AccountVerificationListResponseEntity> getAccountVerificationList(@Url String url, @HeaderMap Map<String,String>headers);


    //Login with Credentials
    @POST
    Call<LoginCredentialsResponseEntity> logindeDuplicatopm(@Url String url, @HeaderMap Map<String,String>headers, @Body LoginEntity loginCredentialsRequestEntity);


    //Get Registration Setup
    @GET
    Call<RegistrationSetupResponseEntity> getRegistrationSetup(@Url String url, @HeaderMap Map<String,String>headers);

    /*//Resume Login
    @POST
    Call<ResumeLoginResponseEntity> resumeLogin(@Url String url, @HeaderMap Map<String,String>headers, @Body ResumeLoginRequestEntity resumeLoginRequestEntity);
*/


}
