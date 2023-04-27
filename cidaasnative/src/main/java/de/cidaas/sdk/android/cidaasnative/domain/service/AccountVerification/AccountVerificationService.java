package de.cidaas.sdk.android.cidaasnative.domain.service.AccountVerification;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import de.cidaas.sdk.android.cidaasnative.R;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.AccountVerificationListResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.InitiateAccountVerificationRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.InitiateAccountVerificationResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.VerifyAccountRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.VerifyAccountResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.service.CidaasNativeService;
import de.cidaas.sdk.android.cidaasnative.data.service.ICidaasNativeService;
import de.cidaas.sdk.android.cidaasnative.data.service.helper.NativeURLHelper;
import de.cidaas.sdk.android.cidaasnative.util.NativeConstants;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.EventResult;
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
                                            final EventResult<InitiateAccountVerificationResponseEntity> callback) {
        //Local Variables

        String methodName = "AccountVerificationService :initiateAccountVerification()";
        try {

            if (baseurl != null && !baseurl.equals("")) {
                //Construct URL For RequestId
                String initiateAccountVerificationUrl = baseurl + NativeURLHelper.getShared().getRegisterUserAccountInitiate();

                Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, NativeURLHelper.contentTypeJson);

                serviceForInitiateAccountVerification(initiateAccountVerificationUrl, initiateAccountVerificationRequestEntity, headers, callback);
            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                return;
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    private void serviceForInitiateAccountVerification(String initiateAccountVerificationUrl,
                                                       InitiateAccountVerificationRequestEntity initiateAccountVerificationRequestEntity,
                                                       Map<String, String> headers, final EventResult<InitiateAccountVerificationResponseEntity> callback) {
        final String methodName = NativeConstants.METHOD_VERIFY_ACCOUNT_VERFICATION;
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
                                            response.code(), NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                                }
                            } else {
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                                        response, NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<InitiateAccountVerificationResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                                    t.getMessage(), NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                        }
                    });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-----------------------------------------------------Register New User verify  Account Verification----------------------------------------------------
    public void verifyAccountVerification(String baseurl, final VerifyAccountRequestEntity verifyAccountRequestEntity,
                                          final EventResult<VerifyAccountResponseEntity> callback) {
        //Local Variables

        String methodName = NativeConstants.METHOD_VERIFY_ACCOUNT_VERFICATION;
        try {

            if (baseurl != null && !baseurl.equals("")) {
                //Construct URL For RequestId
                String verifyAccountVerificationUrl = baseurl + NativeURLHelper.getShared().getRegisterUserAccountVerify();

                //Header
                Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, NativeURLHelper.contentTypeJson);

                //Service
                serviceForVerifyAccountVerification(verifyAccountVerificationUrl, verifyAccountRequestEntity, headers, callback);
            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                return;
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    private void serviceForVerifyAccountVerification(String verifyAccountVerificationUrl, VerifyAccountRequestEntity
            verifyAccountRequestEntity, Map<String, String> headers, final EventResult<VerifyAccountResponseEntity> callback) {
        final String methodName = NativeConstants.METHOD_VERIFY_ACCOUNT_VERFICATION;
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
                                            response.code(), NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                                }
                            } else {
                                if(null != response.errorBody()){
                                    callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                            response, NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<VerifyAccountResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                    t.getMessage(), NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                        }
                    });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-----------------------------------------------------Register New User  Account Verification List ----------------------------------------------------
    public void getAccountVerificationList(String baseurl, final String sub,
                                           final EventResult<AccountVerificationListResponseEntity> callback) {
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
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                return;
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    private void serviceForGetAccountVerificationList(String verifyAccountVerificationUrl, Map<String, String> headers, final EventResult<AccountVerificationListResponseEntity> callback) {
        final String methodName = NativeConstants.METHOD_VERIFY_ACCOUNT_VERFICATION;
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
                                            response.code(), NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                                }
                            } else {
                                if (null != response.errorBody()){
                                    callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                            response, NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<AccountVerificationListResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                    t.getMessage(), NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                        }
                    });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}
