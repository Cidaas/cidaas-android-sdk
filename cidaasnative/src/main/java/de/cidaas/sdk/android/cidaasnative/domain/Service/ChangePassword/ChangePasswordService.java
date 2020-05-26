package de.cidaas.sdk.android.cidaasnative.domain.Service.ChangePassword;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.CommonError.CommonError;
import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Enums.WebAuthErrorCode;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;

import com.example.cidaasv2.R;

import de.cidaas.sdk.android.cidaas.Service.HelperForService.Headers.Headers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Service.CidaasNativeService;
import de.cidaas.sdk.android.cidaasnative.data.Service.Helper.NativeURLHelper;
import de.cidaas.sdk.android.cidaasnative.data.Service.ICidaasNativeService;

public class ChangePasswordService {
    CidaasNativeService service;
    private ObjectMapper objectMapper = new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static ChangePasswordService shared;

    public ChangePasswordService(Context contextFromCidaas) {
        sub = "";
        statusId = "";
        verificationType = "";
        context = contextFromCidaas;
        authenticationType = "";

        if (service == null) {
            service = new CidaasNativeService(context);
        }

    }


    public static ChangePasswordService getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new ChangePasswordService(contextFromCidaas);
            }
        } catch (Exception e) {
            //Timber.i(e.getMessage());
        }
        return shared;
    }

    //------------------------------------------------------------------------------Reset Password Validate Code
    public void changePassword(ChangePasswordRequestEntity changePasswordRequestEntity,
                               String baseurl, DeviceInfoEntity deviceInfoEntityFromParam, final Result<ChangePasswordResponseEntity> callback) {
        //Local Variables
        final String methodName = "ChangePassword Service :changePassword()";
        try {

            if (baseurl != null && !baseurl.equals("")) {
                //Construct URL For Change Password
                String changePasswordUrl = baseurl + NativeURLHelper.getShared().getChangePasswordURl();


                //Construct header
                Map<String, String> headers = Headers.getShared(context).getHeaders(changePasswordRequestEntity.getAccess_token(), false,
                        NativeURLHelper.contentTypeJson);
                serviceForChangePassword(changePasswordRequestEntity, changePasswordUrl, headers, callback);


            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), "Error :" + methodName));
                return;
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.CHANGE_PASSWORD_FAILURE, e.getMessage()));
        }
    }

    private void serviceForChangePassword(ChangePasswordRequestEntity changePasswordRequestEntity, String changePasswordUrl,
                                          Map<String, String> headers, final Result<ChangePasswordResponseEntity> callback) {
        final String methodName = "ChangePassword Service :serviceForChangePassword()";
        try {
            //Call Service-getRequestId
            ICidaasNativeService cidaasNativeService = service.getInstance();
            cidaasNativeService.changePassword(changePasswordUrl, headers, changePasswordRequestEntity)
                    .enqueue(new Callback<ChangePasswordResponseEntity>() {
                        @Override
                        public void onResponse(Call<ChangePasswordResponseEntity> call, Response<ChangePasswordResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.CHANGE_PASSWORD_FAILURE,
                                            response.code(), "Error :" + methodName));
                                }
                            } else {
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.CHANGE_PASSWORD_FAILURE, response,
                                        "Error :" + methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangePasswordResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.CHANGE_PASSWORD_FAILURE, t.getMessage(),
                                    "Error :" + methodName));

                        }
                    });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.CHANGE_PASSWORD_FAILURE, e.getMessage()));
        }
    }
}
