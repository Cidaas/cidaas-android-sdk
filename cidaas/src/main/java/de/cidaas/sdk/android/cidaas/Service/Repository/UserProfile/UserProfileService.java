package de.cidaas.sdk.android.cidaas.Service.Repository.UserProfile;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.CommonError.CommonError;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Enums.WebAuthErrorCode;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Service.HelperForService.Headers.Headers;
import de.cidaas.sdk.android.cidaas.Helper.URLHelper.URLHelper;

import com.example.cidaasv2.R;

import de.cidaas.sdk.android.cidaas.Service.CidaassdkService;
import de.cidaas.sdk.android.cidaas.Service.Entity.UserProfile.UserprofileResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.ICidaasSDKService;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileService {

    CidaassdkService service;
    private ObjectMapper objectMapper = new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static UserProfileService shared;

    public UserProfileService(Context contextFromCidaas) {
        sub = "";
        statusId = "";
        verificationType = "";
        context = contextFromCidaas;
        authenticationType = "";
        if (service == null) {
            service = new CidaassdkService(context);

        }

    }


    public static UserProfileService getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new UserProfileService(contextFromCidaas);
            }
        } catch (Exception e) {
            // Timber.i(e.getMessage());
        }
        return shared;
    }

    //-----------------------------------------------------------------Get Internal UserProfile Info------------------------------------------------------
    public void getInternalUserProfileInfo(String baseurl, String AccessToken, String sub, final Result<UserprofileResponseEntity> callback) {
        //Local Variables
        String methodName = "UserProfileService :getInternalUserProfileInfo()";
        try {

            if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {
                //Construct URL For RequestId

                // Change URL Global wise
                String InternalUserProfileUrl = baseurl + URLHelper.getShared().getInternaluserProfileURL() + sub;

                //Headers Generation
                Map<String, String> headers = Headers.getShared(context).getHeaders(AccessToken, false, URLHelper.contentType);

                //Service Call
                serviceForGetInternalUserProfileInfo(InternalUserProfileUrl, headers, callback);
            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        "Error :" + methodName));
                return;
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.INTERNAL_USER_PROFILE_FAILURE,
                    e.getMessage()));
        }
    }

    public void serviceForGetInternalUserProfileInfo(String internalUserProfileUrl, Map<String, String> headers,
                                                     final Result<UserprofileResponseEntity> callback) {
        final String methodName = "UserProfileService :getInternalUserProfileInfo()";
        try {
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getInternalUserProfileInfo(internalUserProfileUrl, headers).enqueue(new Callback<UserprofileResponseEntity>() {
                @Override
                public void onResponse(Call<UserprofileResponseEntity> call, Response<UserprofileResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.INTERNAL_USER_PROFILE_FAILURE,
                                    response.code(), "Error :" + methodName));
                        }
                    } else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INTERNAL_USER_PROFILE_FAILURE, response
                                , "Error :" + methodName));
                    }
                }

                @Override
                public void onFailure(Call<UserprofileResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INTERNAL_USER_PROFILE_FAILURE,
                            t.getMessage(), "Error :" + methodName));

                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception:" + methodName, WebAuthErrorCode.INTERNAL_USER_PROFILE_FAILURE,
                    e.getMessage()));
        }
    }

    //get user info
}
