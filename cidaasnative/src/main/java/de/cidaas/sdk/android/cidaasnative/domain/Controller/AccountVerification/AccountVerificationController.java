package de.cidaas.sdk.android.cidaasnative.domain.Controller.AccountVerification;

import android.content.Context;

import androidx.annotation.NonNull;

import de.cidaas.sdk.android.cidaas.Helper.CidaasProperties.CidaasProperties;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Enums.WebAuthErrorCode;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;

import java.util.Dictionary;

import timber.log.Timber;
import de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification.AccountVerificationListResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification.InitiateAccountVerificationRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification.InitiateAccountVerificationResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification.VerifyAccountRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification.VerifyAccountResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Service.AccountVerification.AccountVerificationService;

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

    public void initiateAccountVerificationService(@NonNull final String sub, @NonNull final String requestId, @NonNull final String verificationMedium,
                                                   final Result<InitiateAccountVerificationResponseEntity> Result) {
        final String methodName = "AccountVerificationController :initiateAccountVerificationService()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    String clientId = result.get("ClientId");

                    InitiateAccountVerificationRequestEntity initiateAccountVerificationRequestEntity = new InitiateAccountVerificationRequestEntity();
                    initiateAccountVerificationRequestEntity.setProcessingType("CODE");
                    initiateAccountVerificationRequestEntity.setVerificationMedium(verificationMedium);
                    initiateAccountVerificationRequestEntity.setSub(sub);
                    initiateAccountVerificationRequestEntity.setRequestId(requestId);
                    initiateAccountVerificationService(baseurl, initiateAccountVerificationRequestEntity, Result);

                }

                @Override
                public void failure(WebAuthError error) {
                    Result.failure(WebAuthError.getShared(context).CidaaspropertyMissingException("", methodName));
                }
            });
        } catch (Exception e) {
            Result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }


    //Service call To  register New User Account Verification via Email Setup
    public void initiateAccountVerificationService(@NonNull String baseurl, @NonNull InitiateAccountVerificationRequestEntity registrationEntity,
                                                   final Result<InitiateAccountVerificationResponseEntity> result) {
        String methodName = "AccountVerificationController :initiateAccountVerificationService()";
        try {

            if (registrationEntity.getRequestId() != null && !registrationEntity.getRequestId().equals("") &&
                    registrationEntity.getProcessingType() != null && !registrationEntity.getProcessingType().equals("") &&
                    registrationEntity.getVerificationMedium() != null && !registrationEntity.getVerificationMedium().equals("") &&
                    registrationEntity.getSub() != null && !registrationEntity.getSub().equals("")
                    && baseurl != null && !baseurl.equals("")) {

                // Service call
                AccountVerificationService.getShared(context).initiateAccountVerification(baseurl, registrationEntity, new Result<InitiateAccountVerificationResponseEntity>() {
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
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    public void verifyAccountVerificationService(@NonNull final String code, @NonNull final String accvid, final Result<VerifyAccountResponseEntity> result) {
        final String methodName = "AccountVerificationController :verifyAccountVerificationService()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = lpresult.get("DomainURL");
                    String clientId = lpresult.get("ClientId");

                    if (code != null && !code.equals("") && accvid != null && !accvid.equals("")) {
                        verifyAccountVerificationService(baseurl, code, accvid, result);
                    } else {
                        result.failure(WebAuthError.getShared(context).propertyMissingException("Verification Code or accvid must not be empty", methodName));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).CidaaspropertyMissingException("", "Error :" + methodName));
                }
            });
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //Service call To  register New User Account Verification via Email Setup
    public void verifyAccountVerificationService(@NonNull String baseurl, @NonNull String code, String accvid, final Result<VerifyAccountResponseEntity> result) {
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
                        "Error :" + methodName));
            }
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    public void getAccountVerificationList(@NonNull final String sub, final Result<AccountVerificationListResponseEntity> result) {
        final String methodName = "AccountVerificationController :AccountVerificationListResponseEntity()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = lpresult.get("DomainURL");
                    String clientId = lpresult.get("ClientId");

                    if (sub != null && !sub.equals("")) {
                        getAccountVerificationList(baseurl, sub, result);
                    } else {
                        result.failure(WebAuthError.getShared(context).propertyMissingException("Sub must not be empty", methodName));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).CidaaspropertyMissingException("", "Error :" + methodName));
                }
            });
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //Service call To  register New User Account Verification via Email Setup
    private void getAccountVerificationList(@NonNull String baseurl, @NonNull String sub, final Result<AccountVerificationListResponseEntity> result) {
        String methodName = "AccountVerificationController :verifyAccountVerificationService()";
        try {

            if (sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                // Service call
                AccountVerificationService.getShared(context).getAccountVerificationList(baseurl, sub, result);
            } else {
                result.failure(WebAuthError.getShared(context).propertyMissingException("SUB or BASEURL must not be null",
                        "Error :" + methodName));
            }
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }
}



