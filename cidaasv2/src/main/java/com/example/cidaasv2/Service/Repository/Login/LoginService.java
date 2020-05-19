package com.example.cidaasv2.Service.Repository.Login;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
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
    public ObjectMapper objectMapper = new ObjectMapper();

    public LoginService(Context contextFromCidaas) {
        if (service == null) {
            service = new CidaassdkService(contextFromCidaas);
        }

        context = contextFromCidaas;
        //Todo setValue for authenticationType

    }

    public static LoginService getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new LoginService(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //---------------------------------------------------------------Get all URL from the domain----------------------------------------------------------
    public void getURLList(final String baseurl, final Result<Object> callback) {
        String methodName = "LoginService :getURLList()";
        try {

            if (baseurl != null && !baseurl.equals("")) {
                //Construct URL For RequestId
                String openIdurl = baseurl + URLHelper.getShared().getOpenIdURL();

                Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, null);

                serviceForGetURLList(openIdurl, headers, callback);
            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        "Error :" + methodName));
                return;
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.RESUME_LOGIN_FAILURE, e.getMessage()));
        }

    }

    private void serviceForGetURLList(String openIdurl, Map<String, String> headers, final Result<Object> callback) {
        final String methodName = "LoginService :serviceForGetURLList()";
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
                                    response.code(), "Error :" + methodName));
                        }
                    } else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.RESUME_LOGIN_FAILURE, response
                                , "Error :" + methodName));
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE, t.getMessage(),
                            "Error :" + methodName));
                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.RESUME_LOGIN_FAILURE, e.getMessage()));
        }
    }

}
