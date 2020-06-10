package de.cidaas.sdk.android.service.repository.LocationHistory;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import de.cidaas.sdk.android.R;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.urlhelper.URLHelper;
import de.cidaas.sdk.android.service.CidaassdkService;
import de.cidaas.sdk.android.service.ICidaasSDKService;
import de.cidaas.sdk.android.service.entity.userlogininfo.UserLoginInfoEntity;
import de.cidaas.sdk.android.service.entity.userlogininfo.UserLoginInfoResponseEntity;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class UserLoginInfoService {
    CidaassdkService service;
    private ObjectMapper objectMapper = new ObjectMapper();
    //Local variables

    private Context context;

    public static UserLoginInfoService shared;

    public UserLoginInfoService(Context contextFromCidaas) {

        context = contextFromCidaas;


        if (service == null) {
            service = new CidaassdkService(context);
        }


    }


    public static UserLoginInfoService getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new UserLoginInfoService(contextFromCidaas);
            }

        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    public void getUserLoginInfoService(String baseurl, String accessToken, UserLoginInfoEntity userLoginInfoEntity, final EventResult<UserLoginInfoResponseEntity> callback) {
        String methodName = "UserLoginInfoService :getUserLoginInfoService()";
        try {
            if (baseurl != null && !baseurl.equals("")) {

                //Construct URL For RequestId
                String UserLoginInfoURL = baseurl + URLHelper.getShared().getUserLoginInfoURL();

                //Header Generation
                Map<String, String> headers = Headers.getShared(context).getHeaders(accessToken, false, URLHelper.contentTypeJson);

                //Service call
                serviceForUserLoginInfo(UserLoginInfoURL, userLoginInfoEntity, headers, callback);

            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), "Error :" + methodName));
                return;
            }
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.USER_LOGIN_INFO_SERVICE_FAILURE,
                    e.getMessage()));
        }
    }

    private void serviceForUserLoginInfo(String userLoginInfoURL, UserLoginInfoEntity userLoginInfoEntity, Map<String, String> headers,
                                         final EventResult<UserLoginInfoResponseEntity> callback) {
        final String methodName = "UserLoginInfoService :serviceForUserLoginInfo()";
        try {
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getUserLoginInfoService(userLoginInfoURL, headers, userLoginInfoEntity).enqueue(new Callback<UserLoginInfoResponseEntity>() {
                @Override
                public void onResponse(Call<UserLoginInfoResponseEntity> call, Response<UserLoginInfoResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else if (response.code() == 204) {
                            UserLoginInfoResponseEntity userLoginInfoResponseEntity = new UserLoginInfoResponseEntity();
                            userLoginInfoResponseEntity.setStatus(response.code());
                            userLoginInfoResponseEntity.setSuccess(response.isSuccessful());
                            callback.success(userLoginInfoResponseEntity);
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.USER_LOGIN_INFO_SERVICE_FAILURE,
                                    response.code(), "Error :" + methodName));
                        }
                    } else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.USER_LOGIN_INFO_SERVICE_FAILURE,
                                response, "Error :UserLoginInfoService :getUserLoginInfoService()"));
                    }
                }

                @Override
                public void onFailure(Call<UserLoginInfoResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.USER_LOGIN_INFO_SERVICE_FAILURE,
                            t.getMessage(), "Error :" + methodName));

                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.USER_LOGIN_INFO_SERVICE_FAILURE,
                    e.getMessage()));
        }
    }

}
