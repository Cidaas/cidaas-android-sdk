package widas.raja.cidaasconsentv2.Domain.Service.Consent;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.ConsentDetailsV2RequestEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentDetailsResultEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptResponseEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptedRequestEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentRequestEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentResponseEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.v2.ConsentDetailsV2ResponseEntity;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.example.cidaasv2.VerificationV2.data.Service.Helper.VerificationURLHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ConsentService {


    private Context context;

    public static ConsentService shared;

    public ConsentService(Context contextFromCidaas) {

        context=contextFromCidaas;
        if(service==null) {
            service=new CidaassdkService();
        }

        //Todo setValue for authenticationType

    }

    CidaassdkService service;
    public ObjectMapper objectMapper=new ObjectMapper();

    public static ConsentService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new ConsentService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    //---------------------------------------------------------------get Consent String Details--------------------------------------------------------
    public void getConsentDetails(String consentstringDetailsUrl, Map<String, String> headers, final Result<ConsentDetailsResultEntity> callback)
    {
       final String methodName="Consent Service  :getConsentDetails()";
        try {
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getConsentStringDetails(consentstringDetailsUrl, headers).enqueue(new Callback<ConsentDetailsResultEntity>() {
                @Override
                public void onResponse(Call<ConsentDetailsResultEntity> call, Response<ConsentDetailsResultEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.CONSENT_STRING_FAILURE, response.code(),
                                    "Error :"+methodName));
                        }
                    }
                    else {
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.CONSENT_STRING_FAILURE, response,
                                "Error :"+methodName));
                    }
                }

                @Override
                public void onFailure(Call<ConsentDetailsResultEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.CONSENT_STRING_FAILURE, t.getMessage(),
                            "Error :"+methodName));

                }
            });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.CONSENT_STRING_FAILURE, e.getMessage()));
        }
    }


    //---------------------------------------------------------------get ConsentDetailsV2--------------------------------------------------------
    public void getConsentDetailsV2(String consentDetailsUrl,ConsentDetailsV2RequestEntity consentDetailsV2RequestEntity, Map<String, String> headers,
                                    final Result<ConsentDetailsV2ResponseEntity> callback)
    {
        final String methodName="ConsentService:getConsentDetailsV2()";
        try {

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getConsentDetailsV2(consentDetailsUrl,headers,consentDetailsV2RequestEntity).enqueue(new Callback<ConsentDetailsV2ResponseEntity>() {
                @Override
                public void onResponse(Call<ConsentDetailsV2ResponseEntity> call, Response<ConsentDetailsV2ResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.CONSENT_STRING_FAILURE, response.code(),
                                    "Error :"+methodName));
                        }
                    }
                    else {
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.CONSENT_STRING_FAILURE, response,
                                "Error :"+methodName));
                    }
                }

                @Override
                public void onFailure(Call<ConsentDetailsV2ResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.CONSENT_STRING_FAILURE, t.getMessage(),
                            "Error :"+methodName));
                }
            });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.CONSENT_STRING_FAILURE, e.getMessage()));
        }
    }

    //---------------------------------------------------------------------AcceptConsent----------------------------------------------------------------
 /*   public void acceptConsent(String baseurl, ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity,
                              final Result<ConsentManagementAcceptResponseEntity> callback)
    {
        String methodName="Consent Service :acceptConsent()";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){

                //Construct URL For RequestId
                String consentAcceptUrl=baseurl+ VerificationURLHelper.getShared().getAcceptConsent();

                //Header Generation
                Map<String, String> headers = Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

                //Service call to accept consent
                serviceCallForAcceptConsent(consentAcceptUrl, consentManagementAcceptedRequestEntity, headers, callback);

            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE)
                        ,"Error :"+methodName));
                return;
            }

        }
        catch (Exception e)
        {
           callback.failure( WebAuthError.getShared(context).methodException("Exception :"+methodName,
                   WebAuthErrorCode.ACCEPT_CONSENT_FAILURE,e.getMessage()));
        }
    }*/


    public void acceptConsent(String consentAcceptUrl, ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity,
                                             Map<String, String> headers, final Result<ConsentManagementAcceptResponseEntity> callback)
    {
    final String methodName="Consent Service :serviceCallForAcceptConsent()";
      try {
          //Call Service-getRequestId
          final ICidaasSDKService cidaasSDKService = service.getInstance();

          cidaasSDKService.acceptConsent(consentAcceptUrl, headers, consentManagementAcceptedRequestEntity).enqueue(
                  new Callback<ConsentManagementAcceptResponseEntity>() {
              @Override
              public void onResponse(Call<ConsentManagementAcceptResponseEntity> call, Response<ConsentManagementAcceptResponseEntity> response)
              {
                  if (response.isSuccessful()) {
                      if (response.code() == 200) {
                          callback.success(response.body());
                      } else {
                          callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE, response.code(),
                                  "Error :"+methodName));
                      }
                  }
                  else {
                      callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE, response,
                              "Error :"+methodName));
                  }
              }

              @Override
              public void onFailure(Call<ConsentManagementAcceptResponseEntity> call, Throwable t) {
                  callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE, t.getMessage(),
                          "Error :"+methodName));
              }
          });
      }
      catch (Exception e)
      {
          callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.ACCEPT_CONSENT_FAILURE, e.getMessage()));
      }
    }

    //------------------------------------------------------------------------Resume Consent-------------------------------------------------------------
    public void resumeConsent(final String baseurl, final ResumeConsentRequestEntity resumeConsentRequestEntity, final Result<ResumeConsentResponseEntity> callback)
    {
        //Local Variables
        String methodName = "Consent Service :resumeConsent()";
        try{

            if(baseurl!=null && !baseurl.equals("")){

                //Construct URL For RequestId
                String resumeConsentUrl=baseurl+URLHelper.getShared().getResumeConsentURL()+resumeConsentRequestEntity.getTrack_id();

                //Header generation
                Map<String, String> headers = Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

                //service call resume consent
                serviceCallForResumeConsent(resumeConsentRequestEntity,  resumeConsentUrl, headers,callback);
            }
            else
            {
              callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), "Error :"+methodName));
              return;
            }

        }
        catch (Exception e)
        {
            callback.failure( WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.RESUME_CONSENT_FAILURE,e.getMessage()));
        }
    }

    //----------------------------------------------------------------ServiceCallForResumeConsent--------------------------------------------------------
    private void serviceCallForResumeConsent(ResumeConsentRequestEntity resumeConsentRequestEntity,  String resumeConsentUrl, Map<String, String> headers,
                                             final Result<ResumeConsentResponseEntity> callback)
    {
        final String methodName="Consent Service :serviceCallForResumeConsent()";
        try {
            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.resumeConsent(resumeConsentUrl, headers, resumeConsentRequestEntity).enqueue(new Callback<ResumeConsentResponseEntity>() {
                @Override
                public void onResponse(Call<ResumeConsentResponseEntity> call, Response<ResumeConsentResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.RESUME_CONSENT_FAILURE,
                                    response.code(),  "Error :"+methodName));
                        }
                    } else {
                       callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.RESUME_CONSENT_FAILURE, response,
                                "Error :"+methodName));
                    }
                }

                @Override
                public void onFailure(Call<ResumeConsentResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.RESUME_CONSENT_FAILURE, t.getMessage(),
                            "Error :"+methodName));
                }
            });
        }
        catch (Exception e)
        {
            callback.failure( WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.RESUME_CONSENT_FAILURE,e.getMessage()));
        }
    }
}




