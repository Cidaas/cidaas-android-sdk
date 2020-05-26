package de.cidaas.sdk.android.cidaasnative.domain.Service.Login;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import de.cidaas.sdk.android.cidaasnative.data.Entity.Login.LoginCredentialsRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Login.LoginCredentialsResponseErrorEntity;
import de.cidaas.sdk.android.cidaasnative.data.Service.CidaasNativeService;
import de.cidaas.sdk.android.cidaasnative.data.Service.Helper.NativeURLHelper;
import de.cidaas.sdk.android.cidaasnative.data.Service.ICidaasNativeService;
import de.cidaas.sdk.android.entities.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class NativeLoginService {


    private Context context;

    public static NativeLoginService shared;

    CidaasNativeService service;
    public ObjectMapper objectMapper = new ObjectMapper();

    public NativeLoginService(Context contextFromCidaas) {
        if (service == null) {
            service = new CidaasNativeService(contextFromCidaas);
        }

        context = contextFromCidaas;

    }

    public static NativeLoginService getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new NativeLoginService(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    //----------------------------------------------------------Login With Credentials-----------------------------------------------------------------------------
    public void loginWithCredentials(final String baseurl, final LoginCredentialsRequestEntity loginCredentialsRequestEntity,
                                     final EventResult<LoginCredentialsResponseEntity> callback) {
        //Local Variables

        String methodName = "LoginService :serviceForLoginWithCredentials()";
        try {

            if (baseurl != null && !baseurl.equals("")) {
                //Construct URL For RequestId
                String loginUrl = baseurl + NativeURLHelper.getShared().getLoginWithCredentials();

                Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, NativeURLHelper.contentTypeJson);

                //Call Service-getRequestId
                serviceForLoginWithCredentials(loginUrl, loginCredentialsRequestEntity, headers, callback);

            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(com.example.cidaasv2.R.string.EMPTY_BASE_URL_SERVICE), "Error :" + methodName));
                return;
            }
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE, e.getMessage()));
        }
    }

    private void serviceForLoginWithCredentials(String loginUrl, LoginCredentialsRequestEntity loginCredentialsRequestEntity, Map<String, String> headers,
                                                final EventResult<LoginCredentialsResponseEntity> callback) {
        final String methodName = "Error :LoginService :serviceForLoginWithCredentials()";
        try {
            final ICidaasNativeService CidaasNativeService = service.getInstance();

            CidaasNativeService.loginWithCredentials(loginUrl, headers, loginCredentialsRequestEntity).enqueue(new Callback<LoginCredentialsResponseEntity>() {
                @Override
                public void onResponse(Call<LoginCredentialsResponseEntity> call, Response<LoginCredentialsResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE,
                                    response.code(), "Error" + methodName));
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
                                    loginCredentialsResponseErrorEntity.getError(), "Error :" + methodName));

                        } catch (Exception e) {
                            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE,
                                    e.getMessage()));
                            // Timber.e("response"+response.message()+e.getMessage());
                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginCredentialsResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE, t.getMessage(),
                            "Error :" + methodName));
                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE, e.getMessage()));
        }
    }

}