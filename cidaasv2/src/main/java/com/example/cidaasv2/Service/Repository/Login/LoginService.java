package com.example.cidaasv2.Service.Repository.Login;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseErrorEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginResponseEntity;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LoginService {


    private Context context;

    public static LoginService shared;

    CidaassdkService service;
    public ObjectMapper objectMapper=new ObjectMapper();

    public LoginService(Context contextFromCidaas) {
        if(service==null) {
            service=new CidaassdkService(contextFromCidaas);
        }

        context=contextFromCidaas;
        //Todo setValue for authenticationType

    }

    public static LoginService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new LoginService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }




    //----------------------------------------------------------Login With Credentials-----------------------------------------------------------------------------
    public void loginWithCredentials(final String baseurl, final LoginCredentialsRequestEntity loginCredentialsRequestEntity,
                                     final Result<LoginCredentialsResponseEntity> callback)
    {
        //Local Variables

        String methodName = "LoginService :serviceForLoginWithCredentials()";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
              String  loginUrl=baseurl+ URLHelper.getShared().getLoginWithCredentials();

              Map<String, String> headers = Headers.getShared(context).getHeaders(null,false,URLHelper.contentTypeJson);

              //Call Service-getRequestId
              serviceForLoginWithCredentials(loginUrl, loginCredentialsRequestEntity, headers, callback);

            }
            else
            {
              callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), "Error :"+methodName));
                return;
            }
        }
        catch (Exception e)
        {
          callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName,WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE,e.getMessage()));
        }
    }

    private void serviceForLoginWithCredentials(String loginUrl,LoginCredentialsRequestEntity loginCredentialsRequestEntity,  Map<String, String> headers ,
                                               final Result<LoginCredentialsResponseEntity> callback)
    {
        final String methodName="Error :LoginService :serviceForLoginWithCredentials()";
     try {
         final ICidaasSDKService cidaasSDKService = service.getInstance();

         cidaasSDKService.loginWithCredentials(loginUrl, headers, loginCredentialsRequestEntity).enqueue(new Callback<LoginCredentialsResponseEntity>() {
             @Override
             public void onResponse(Call<LoginCredentialsResponseEntity> call, Response<LoginCredentialsResponseEntity> response) {
                 if (response.isSuccessful()) {
                     if (response.code() == 200) {
                         callback.success(response.body());
                     } else {
                         callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE,
                                 response.code(),"Error"+methodName));
                     }
                 } else {
                     assert response.errorBody() != null;
                     try {

                         // Handle proper error message
                         String errorResponse = response.errorBody().source().readByteString().utf8();
                         final LoginCredentialsResponseErrorEntity loginCredentialsResponseErrorEntity;
                         loginCredentialsResponseErrorEntity = objectMapper.readValue(errorResponse, LoginCredentialsResponseErrorEntity.class);



                         callback.failure(WebAuthError.getShared(context).loginFailureException(WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE,
                                 loginCredentialsResponseErrorEntity.getError().getError(), loginCredentialsResponseErrorEntity.getStatus(),
                                 loginCredentialsResponseErrorEntity.getError(),"Error :"+methodName));

                     } catch (Exception e) {
                         callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName,WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE,
                                 e.getMessage()));
                         // Timber.e("response"+response.message()+e.getMessage());
                     }

                 }
             }

             @Override
             public void onFailure(Call<LoginCredentialsResponseEntity> call, Throwable t) {
                 callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE, t.getMessage(),
                     "Error :"+methodName));
             }
         });
     }
     catch (Exception e)
     {
        callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE,e.getMessage()));
     }
    }

    //--------------------------------------------------------- Resume Login After MFA------------------------------------------------------------------------
    public void continueMFA(final String baseurl, final ResumeLoginRequestEntity resumeLoginRequestEntity,final Result<ResumeLoginResponseEntity> callback)
    {
        //Local Variables
        String methodName = "LoginService :continueMFA()";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                String resumeloginUrl=baseurl+URLHelper.getShared().getResumeLoginURL()+resumeLoginRequestEntity.getTrack_id();

                //Header Generator
                Map<String, String> headers = Headers.getShared(context).getHeaders(null,false,URLHelper.contentTypeJson);

                //serviceCall
                serviceCallForContinueMFA(resumeloginUrl, resumeLoginRequestEntity, headers, callback);
            }
            else
            {
              callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),"Error :"+methodName));
                return;
            }
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.RESUME_LOGIN_FAILURE,e.getMessage()));
        }
    }

    private void serviceCallForContinueMFA(String resumeloginUrl, ResumeLoginRequestEntity resumeLoginRequestEntity, Map<String, String> headers,
                                           final Result<ResumeLoginResponseEntity> callback) {
        final String methodName = "LoginService :serviceCallForContinueMFA()";

        try {
            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.resumeLogin(resumeloginUrl, headers, resumeLoginRequestEntity).enqueue(new Callback<ResumeLoginResponseEntity>() {
                @Override
                public void onResponse(Call<ResumeLoginResponseEntity> call, Response<ResumeLoginResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.RESUME_LOGIN_FAILURE, response.code(),
                                    "Error :" + methodName));
                        }
                    } else {
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.RESUME_LOGIN_FAILURE, response,
                                "Error :" + methodName));
                    }
                }

                @Override
                public void onFailure(Call<ResumeLoginResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE, t.getMessage(),
                            "Error :" + methodName));
                }
            });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.RESUME_LOGIN_FAILURE,e.getMessage()));
        }
    }

    //-------------------------------------------------------------------Resume Login-------------------------------------------------------------------------
    public void continuePasswordless(final String baseurl, final ResumeLoginRequestEntity resumeLoginRequestEntity,
                                     final Result<ResumeLoginResponseEntity> callback)
    {
        //Local Variables
        String methodName = "LoginService :continuePasswordless()";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                String continuePasswordlessUrl=baseurl+URLHelper.getShared().getPasswordlessContinueUrl()+resumeLoginRequestEntity.getTrack_id();

                //Header Generation
                Map<String, String> headers =Headers.getShared(context).getHeaders(null,false,URLHelper.contentTypeJson);

                //Service call
                serviceForContinuePasswordless(continuePasswordlessUrl, resumeLoginRequestEntity, headers, callback);

            }
            else
            {
              callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),"Error :"+methodName));
                return;
            }
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.RESUME_LOGIN_FAILURE,e.getMessage()));
        }
    }

    private void serviceForContinuePasswordless(String continuePasswordlessUrl, ResumeLoginRequestEntity resumeLoginRequestEntity, Map<String, String> headers,
                                                final Result<ResumeLoginResponseEntity> callback)
    {
        final String methodName="LoginService :serviceForContinuePasswordless()";
        try {
            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.resumeLogin(continuePasswordlessUrl, headers, resumeLoginRequestEntity).enqueue(new Callback<ResumeLoginResponseEntity>() {
                @Override
                public void onResponse(Call<ResumeLoginResponseEntity> call, Response<ResumeLoginResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                                   response.code(), "Error :"+methodName));
                        }
                    } else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                                response, "Error :"+methodName));
                    }
                }

                @Override
                public void onFailure(Call<ResumeLoginResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE, t.getMessage(),
                           "Error :"+methodName));
                }
            });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.RESUME_LOGIN_FAILURE,e.getMessage()));
        }
    }


    //---------------------------------------------------------------Get all URL from the domain----------------------------------------------------------
    public void getURLList(final String baseurl, final Result<Object> callback)
    {
        String methodName="LoginService :getURLList()";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                String  openIdurl=baseurl +URLHelper.getShared().getOpenIdURL();

                Map<String, String> headers = Headers.getShared(context).getHeaders(null,false,null);

                serviceForGetURLList(openIdurl, headers, callback);
                }
                else
                {
                 callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                         "Error :"+methodName));
                 return;
                }

            }
            catch (Exception e)
            {
                callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.RESUME_LOGIN_FAILURE,e.getMessage()));
            }

    }

    private void serviceForGetURLList(String openIdurl, Map<String, String> headers, final Result<Object> callback)
    {
       final String methodName="LoginService :serviceForGetURLList()";
        try {
            //Call Service-getURLList
            final ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getUrlList(openIdurl, headers).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                                   response.code(), "Error :"+methodName));
                        }
                    } else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.RESUME_LOGIN_FAILURE, response
                                , "Error :"+methodName));
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE, t.getMessage(),
                            "Error :"+methodName));
                }
            });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.RESUME_LOGIN_FAILURE,e.getMessage()));
        }
    }

}
