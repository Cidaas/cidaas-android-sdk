package de.cidaas.sdk.android.cidaasnative.domain.Service.AccountVerification;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification.AccountVerificationListResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification.InitiateAccountVerificationRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification.InitiateAccountVerificationResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification.VerifyAccountRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification.VerifyAccountResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Service.CidaasNativeService;
import de.cidaas.sdk.android.cidaasnative.data.Service.Helper.NativeURLHelper;
import de.cidaas.sdk.android.cidaasnative.data.Service.ICidaasNativeService;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.Result;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class AccountVerificationService {
    CidaasNativeService service;
    private ObjectMapper objectMapper = new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static AccountVerificationService shared;

    public AccountVerificationService(Context contextFromCidaas) {
        sub = "";
        statusId = "";
        verificationType = "";
        context = contextFromCidaas;
        authenticationType = "";


        if (service == null) {
            service = new CidaasNativeService(context);
        }

    }

    public static AccountVerificationService getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new AccountVerificationService(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //----------------------------------------------------Register New User initiate  Account Verification--------------------------------------------------
    public void initiateAccountVerification(String baseurl, final InitiateAccountVerificationRequestEntity initiateAccountVerificationRequestEntity,
                                            final Result<InitiateAccountVerificationResponseEntity> callback) {
        //Local Variables

        String methodName = "AccountVerificationService :initiateAccountVerification()";
        try {

            if (baseurl != null || !baseurl.equals("")) {
                //Construct URL For RequestId
                String initiateAccountVerificationUrl = baseurl + NativeURLHelper.getShared().getRegisterUserAccountInitiate();

                Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, NativeURLHelper.contentTypeJson);

                serviceForInitiateAccountVerification(initiateAccountVerificationUrl, initiateAccountVerificationRequestEntity, headers, callback);
            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(com.example.cidaasv2.R.string.EMPTY_BASE_URL_SERVICE), "Error :" + methodName));
                return;
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    private void serviceForInitiateAccountVerification(String initiateAccountVerificationUrl,
                                                       InitiateAccountVerificationRequestEntity initiateAccountVerificationRequestEntity,
                                                       Map<String, String> headers, final Result<InitiateAccountVerificationResponseEntity> callback) {
        final String methodName = "AccountVerificationService :verifyAccountVerification()";
        try {
            //Call Service-getRequestId
            ICidaasNativeService cidaasNativeService = service.getInstance();

            cidaasNativeService.initiateAccountVerification(initiateAccountVerificationUrl, headers, initiateAccountVerificationRequestEntity)
                    .enqueue(new Callback<InitiateAccountVerificationResponseEntity>() {
                        @Override
                        public void onResponse(Call<InitiateAccountVerificationResponseEntity> call, Response<InitiateAccountVerificationResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                                            response.code(), "Error :" + methodName));
                                }
                            } else {
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                                        response, "Error :" + methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<InitiateAccountVerificationResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                                    t.getMessage(), "Error :" + methodName));
                        }
                    });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-----------------------------------------------------Register New User verify  Account Verification----------------------------------------------------
    public void verifyAccountVerification(String baseurl, final VerifyAccountRequestEntity verifyAccountRequestEntity,
                                          final Result<VerifyAccountResponseEntity> callback) {
        //Local Variables

        String methodName = "AccountVerificationService :verifyAccountVerification()";
        try {

            if (baseurl != null && !baseurl.equals("")) {
                //Construct URL For RequestId
                String verifyAccountVerificationUrl = baseurl + NativeURLHelper.getShared().getRegisterUserAccountVerify();

                //Header
                Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, NativeURLHelper.contentTypeJson);

                //Service
                serviceForVerifyAccountVerification(verifyAccountVerificationUrl, verifyAccountRequestEntity, headers, callback);
            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(com.example.cidaasv2.R.string.EMPTY_BASE_URL_SERVICE), "Error :" + methodName));
                return;
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    private void serviceForVerifyAccountVerification(String verifyAccountVerificationUrl, VerifyAccountRequestEntity
            verifyAccountRequestEntity, Map<String, String> headers, final Result<VerifyAccountResponseEntity> callback) {
        final String methodName = "AccountVerificationService :verifyAccountVerification()";
        try {
            //Call Service-getRequestId
            ICidaasNativeService cidaasNativeService = service.getInstance();

            cidaasNativeService.verifyAccountVerification(verifyAccountVerificationUrl, headers, verifyAccountRequestEntity)
                    .enqueue(new Callback<VerifyAccountResponseEntity>() {
                        @Override
                        public void onResponse(Call<VerifyAccountResponseEntity> call, Response<VerifyAccountResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                            response.code(), "Error :" + methodName));
                                }
                            } else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                        response, "Error :" + methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<VerifyAccountResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                    t.getMessage(), "Error :" + methodName));
                        }
                    });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-----------------------------------------------------Register New User  Account Verification List ----------------------------------------------------
    public void getAccountVerificationList(String baseurl, final String sub,
                                           final Result<AccountVerificationListResponseEntity> callback) {
        //Local Variables

        String methodName = "AccountVerificationService :getAccountVerificationList()";
        try {

            if (baseurl != null && !baseurl.equals("")) {
                //Construct URL For RequestId
                String verifyAccountListUrl = baseurl + NativeURLHelper.getShared().getAccountVerificationList(sub);

                //Header
                Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, NativeURLHelper.contentTypeJson);

                //Service
                serviceForGetAccountVerificationList(verifyAccountListUrl, headers, callback);
            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(com.example.cidaasv2.R.string.EMPTY_BASE_URL_SERVICE), "Error :" + methodName));
                return;
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    private void serviceForGetAccountVerificationList(String verifyAccountVerificationUrl, Map<String, String> headers, final Result<AccountVerificationListResponseEntity> callback) {
        final String methodName = "AccountVerificationService :verifyAccountVerification()";
        try {
            //Call Service-getRequestId
            ICidaasNativeService cidaasNativeService = service.getInstance();

            cidaasNativeService.getAccountVerificationList(verifyAccountVerificationUrl, headers)
                    .enqueue(new Callback<AccountVerificationListResponseEntity>() {
                        @Override
                        public void onResponse(Call<AccountVerificationListResponseEntity> call, Response<AccountVerificationListResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                            response.code(), "Error :" + methodName));
                                }
                            } else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                        response, "Error :" + methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<AccountVerificationListResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                    t.getMessage(), "Error :" + methodName));
                        }
                    });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}
