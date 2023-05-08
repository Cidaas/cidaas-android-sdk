package de.cidaas.sdk.android.cidaasnative.domain.controller.accountverification;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Dictionary;

import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.AccountVerificationListResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.InitiateAccountVerificationRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.InitiateAccountVerificationResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.VerifyAccountRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.VerifyAccountResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.service.AccountVerification.AccountVerificationService;
import de.cidaas.sdk.android.cidaasnative.util.NativeConstants;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.properties.CidaasProperties;
import timber.log.Timber;

public class AccountVerificationController {

    private Context context;

    public static AccountVerificationController shared;

    String accvid;


    public AccountVerificationController(Context contextFromCidaas) {

        context = contextFromCidaas;
    }

    public static AccountVerificationController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new AccountVerificationController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    public void initiateAccountVerificationService(@NonNull final InitiateAccountVerificationRequestEntity initiateAccountVerificationRequestEntity,
                                                   final EventResult<InitiateAccountVerificationResponseEntity> EventResult) {
        final String methodName = "AccountVerificationController :initiateAccountVerificationService()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get(NativeConstants.DOMAIN_URL);
                    String clientId = result.get(NativeConstants.CLIENT_ID);


                    if (initiateAccountVerificationRequestEntity.getProcessingType() == null || initiateAccountVerificationRequestEntity.getVerificationMedium() == null ||
                            initiateAccountVerificationRequestEntity.getProcessingType().equals("")
                            || initiateAccountVerificationRequestEntity.getRequestId() == null ||
                            initiateAccountVerificationRequestEntity.getRequestId().equals("") || initiateAccountVerificationRequestEntity.getVerificationMedium().equals("")
                    ) {

                        String errorMessage = "Verification medium , requestId or processing type must not be null";
                        EventResult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
                    }

                    initiateAccountVerificationService(baseurl, initiateAccountVerificationRequestEntity, EventResult);


                }

                @Override
                public void failure(WebAuthError error) {
                    EventResult.failure(error);
                }
            });
        } catch (Exception e) {
            EventResult.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }


    //Service call To  register New User Account Verification via Email Setup
    public void initiateAccountVerificationService(@NonNull String baseurl, @NonNull InitiateAccountVerificationRequestEntity registrationEntity,
                                                   final EventResult<InitiateAccountVerificationResponseEntity> result) {
        String methodName = "AccountVerificationController :initiateAccountVerificationService()";
        try {

            if (registrationEntity.getRequestId() != null && !registrationEntity.getRequestId().equals("") &&
                    registrationEntity.getProcessingType() != null && !registrationEntity.getProcessingType().equals("") &&
                    registrationEntity.getVerificationMedium() != null && !registrationEntity.getVerificationMedium().equals("")

                    && baseurl != null && !baseurl.equals("")) {

                // Service call
                AccountVerificationService.getShared(context).initiateAccountVerification(baseurl, registrationEntity, new EventResult<InitiateAccountVerificationResponseEntity>() {
                    @Override
                    public void success(InitiateAccountVerificationResponseEntity serviceresult) {
                        accvid = serviceresult.getData().getAccvid();
                        result.success(serviceresult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            } else {
                String errorMessage = "Verification medium , sub or processing type must not be null";
                result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
            }
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    public void verifyAccountVerificationService(@NonNull final String code, @NonNull final String accvid, final EventResult<VerifyAccountResponseEntity> result) {
        final String methodName = "AccountVerificationController :verifyAccountVerificationService()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = lpresult.get(NativeConstants.DOMAIN_URL);
                    String clientId = lpresult.get(NativeConstants.CLIENT_ID);

                    if (code != null && !code.equals("") && accvid != null && !accvid.equals("")) {
                        verifyAccountVerificationService(baseurl, code, accvid, result);
                    } else {
                        result.failure(WebAuthError.getShared(context).propertyMissingException("Verification Code or accvid must not be empty", methodName));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).cidaasPropertyMissingException("", NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                }
            });
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //Service call To  register New User Account Verification via Email Setup
    public void verifyAccountVerificationService(@NonNull String baseurl, @NonNull String code, String accvid, final EventResult<VerifyAccountResponseEntity> result) {
        String methodName = "AccountVerificationController :verifyAccountVerificationService()";
        try {

            if (accvid != null && !accvid.equals("") && code != null && !code.equals("") &&
                    baseurl != null && !baseurl.equals("")) {

                VerifyAccountRequestEntity registrationEntity = new VerifyAccountRequestEntity();
                registrationEntity.setCode(code);
                registrationEntity.setAccvid(accvid);

                // Service call
                AccountVerificationService.getShared(context).verifyAccountVerification(baseurl, registrationEntity, result);
            } else {
                result.failure(WebAuthError.getShared(context).propertyMissingException("ACCVID or CODE or BASEURL must not be null",
                        NativeConstants.ERROR_LOGGING_PREFIX + methodName));
            }
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    public void getAccountVerificationList(@NonNull final String sub, final EventResult<AccountVerificationListResponseEntity> result) {
        final String methodName = "AccountVerificationController :AccountVerificationListResponseEntity()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = lpresult.get(NativeConstants.DOMAIN_URL);
                    String clientId = lpresult.get(NativeConstants.CLIENT_ID);

                    if (sub != null && !sub.equals("")) {
                        getAccountVerificationList(baseurl, sub, result);
                    } else {
                        result.failure(WebAuthError.getShared(context).propertyMissingException("Sub must not be empty", methodName));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).cidaasPropertyMissingException("", NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                }
            });
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //Service call To  register New User Account Verification via Email Setup
    private void getAccountVerificationList(@NonNull String baseurl, @NonNull String sub, final EventResult<AccountVerificationListResponseEntity> result) {
        String methodName = "AccountVerificationController :verifyAccountVerificationService()";
        try {

            if (sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                // Service call
                AccountVerificationService.getShared(context).getAccountVerificationList(baseurl, sub, result);
            } else {
                result.failure(WebAuthError.getShared(context).propertyMissingException("SUB or BASEURL must not be null",
                        NativeConstants.ERROR_LOGGING_PREFIX + methodName));
            }
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }
}



