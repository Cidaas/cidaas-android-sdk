package de.cidaas.sdk.android.cidaasnative.domain.controller.resetpassword;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Dictionary;

import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.ResetPasswordRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.ResetPasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetnewpassword.ResetNewPasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetnewpassword.ResetPasswordEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetpasswordvalidatecode.ResetPasswordValidateCodeRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetpasswordvalidatecode.ResetPasswordValidateCodeResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.service.ResetPassword.ResetPasswordService;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.properties.CidaasProperties;
import timber.log.Timber;

public class ResetPasswordController {


    private String sub;
    private String verificationType;
    private Context context;

    public static ResetPasswordController shared;

    public ResetPasswordController(Context contextFromCidaas) {
        sub = "";
        verificationType = "";
        context = contextFromCidaas;
    }

    public static ResetPasswordController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new ResetPasswordController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //----------------------------------------initiateresetPasswordService------------------------------------------------------------------
    public void initiateresetPasswordService(final String requestId, final String email, @NonNull final String resetMedium,
                                             final EventResult<ResetPasswordResponseEntity> resetPasswordResponseEntityResult) {
        final String methodName = "RegistrationController :initiateresetPasswordService()";
        try {
            if (requestId != null && !requestId.equals("") && email != null && !email.equals("") && resetMedium != null && !resetMedium.equals("")) {
                CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> loginPropertiesResult) {
                        successOfInitiateResetPassword(requestId, email, resetMedium, loginPropertiesResult, resetPasswordResponseEntityResult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        resetPasswordResponseEntityResult.failure(error);
                    }
                });
            } else {
                resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).propertyMissingException(
                        "RequestID or email or Resetmedium must not be empty", "Error:" + methodName));
            }
        } catch (Exception e) {
            resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName,
                    WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //----------------------------------------successOfInitiateResetPassword------------------------------------------------------------------
    public void successOfInitiateResetPassword(final String requestId, final String email, @NonNull final String resetMedium,
                                               Dictionary<String, String> loginPropertiesResult, final EventResult<ResetPasswordResponseEntity> resetPasswordResponseEntityResult) {
        String methodName = "RegistrationController :successOfInitiateResetPassword()";
        try {
            String baseurl = loginPropertiesResult.get("DomainURL");
            String clientId = loginPropertiesResult.get("ClientId");

            if (email != null && !email.equals("") && requestId != null && !requestId.equals("")) {
                ResetPasswordRequestEntity resetPasswordRequestEntity = new ResetPasswordRequestEntity();
                resetPasswordRequestEntity.setProcessingType("CODE");
                resetPasswordRequestEntity.setRequestId(requestId);
                resetPasswordRequestEntity.setEmail(email);
                resetPasswordRequestEntity.setResetMedium(resetMedium);

                initiateresetPasswordService(baseurl, resetPasswordRequestEntity, resetPasswordResponseEntityResult);
            } else {
                String ErrorMessage = "RequestID or email mustnot be null";
                resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).propertyMissingException(ErrorMessage, "Error:" + methodName));
            }
        } catch (Exception e) {
            resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName,
                    WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //initiateResetResetPasswordService
    public void initiateresetPasswordService(@NonNull String baseurl, @NonNull ResetPasswordRequestEntity resetPasswordRequestEntity,
                                             final EventResult<ResetPasswordResponseEntity> resetpasswordResult) {
        String methodName = "RegistrationController :initiateresetPasswordService()";
        try {

            if (resetPasswordRequestEntity.getRequestId() != null && !resetPasswordRequestEntity.getRequestId().equals("")
                    && baseurl != null && !baseurl.equals("")) {

                ResetPasswordService.getShared(context).initiateresetPassword(resetPasswordRequestEntity, baseurl, null,
                        resetpasswordResult);
            } else {

                resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException("RequestId or Baseurl must not be null",
                        "Error:" + methodName));
            }
        } catch (Exception e) {
            resetpasswordResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName,
                    WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE, e.getMessage()));
        }
    }


    //----------------------------------------------------ResetResetPasswordValidateCodeService---------------------------------------------------
    public void resetPasswordValidateCode(@NonNull final String verificationCode, @NonNull final String rprq,
                                          final EventResult<ResetPasswordValidateCodeResponseEntity> resetpasswordResult) {
        final String methodName = "RegistrationController :resetPasswordValidateCode()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    successOfResetPasswordValidateCode(baseurl, verificationCode, rprq, resetpasswordResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    resetpasswordResult.failure(error);
                }
            });
        } catch (Exception e) {
            resetpasswordResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName,
                    WebAuthErrorCode.RESET_PASSWORD_VALIDATE_CODE_FAILURE, e.getMessage()));
        }
    }

    //------------------------------------------------------------------successOfResetPasswordValidateCode--------------------------------------------
    public void successOfResetPasswordValidateCode(String baseurl, @NonNull final String verificationCode, @NonNull final String rprq,
                                                   final EventResult<ResetPasswordValidateCodeResponseEntity> resetpasswordResult) {
        String methodName = "RegistrationController :successOfResetPasswordValidateCode()";
        try {
            if (verificationCode != null && !verificationCode.equals("") && rprq != null && !rprq.equals("")) {

                ResetPasswordValidateCodeRequestEntity resetPasswordValidateCodeRequestEntity = new ResetPasswordValidateCodeRequestEntity();
                resetPasswordValidateCodeRequestEntity.setResetRequestId(rprq);
                resetPasswordValidateCodeRequestEntity.setCode(verificationCode);

                ResetPasswordService.getShared(context).resetPasswordValidateCode(resetPasswordValidateCodeRequestEntity, baseurl, resetpasswordResult);
            } else {
                resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException("RPRQ or verification Code must not be null",
                        "Error" + methodName));
            }
        } catch (Exception e) {
            resetpasswordResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName,
                    WebAuthErrorCode.RESET_PASSWORD_VALIDATE_CODE_FAILURE, e.getMessage()));
        }
    }


    public void resetNewPassword(final ResetPasswordEntity resetPasswordEntity, final EventResult<ResetNewPasswordResponseEntity> resetpasswordResult) {
        final String methodName = "";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    String clientId = result.get("ClientId");

                    notNullChecking(baseurl, resetPasswordEntity, resetpasswordResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    resetpasswordResult.failure(WebAuthError.getShared(context).cidaasPropertyMissingException("", "Error:" + methodName));
                }
            });
        } catch (Exception e) {
            resetpasswordResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.RESET_NEWPASSWORD_FAILURE,
                    e.getMessage()));
        }
    }

    public void notNullChecking(String baseurl, final ResetPasswordEntity resetPasswordEntity, final EventResult<ResetNewPasswordResponseEntity> resetpasswordResult) {
        String methodName = "RegistrationController :notNullChecking()";
        try {
            if (resetPasswordEntity.getPassword() != null && !resetPasswordEntity.getPassword().equals("") && resetPasswordEntity.getConfirmPassword() != null
                    && !resetPasswordEntity.getConfirmPassword().equals("")) {
                if (resetPasswordEntity.getPassword().equals(resetPasswordEntity.getConfirmPassword())) {

                    if (resetPasswordEntity.getResetRequestId() != null && !resetPasswordEntity.getResetRequestId().equals("") &&
                            resetPasswordEntity.getExchangeId() != null && !resetPasswordEntity.getExchangeId().equals("")) {

                        //Controller Reset Password
                        ResetPasswordController.getShared(context).resetNewPassword(baseurl, resetPasswordEntity, resetpasswordResult);
                    } else {
                        String errorMessage = "resetRequestId and ExchangeId must not be null";
                        resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, "Error:" + methodName));
                    }
                } else {
                    String errorMessage = "Password and confirmPassword must  be same";
                    resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, "Error:" + methodName));
                }

            } else {
                String errorMessage = "Password or confirmPassword must not be empty";
                resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, "Error:" + methodName));
            }
        } catch (Exception e) {
            resetpasswordResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.RESET_NEWPASSWORD_FAILURE,
                    e.getMessage()));
        }
    }


    //---------------------------------------------resetNewPasswordService
    public void resetNewPassword(@NonNull String baseurl, ResetPasswordEntity resetPasswordEntity, final EventResult<ResetNewPasswordResponseEntity>
            resetpasswordResult) {
        String methodName = "RegistrationController :resetNewPassword()";
        try {

            if (resetPasswordEntity.getPassword() != null && !resetPasswordEntity.getPassword().equals("") &&
                    resetPasswordEntity.getConfirmPassword() != null && !resetPasswordEntity.getConfirmPassword().equals("")
                    && baseurl != null && !baseurl.equals("")) {

                ResetPasswordService.getShared(context).resetNewPassword(resetPasswordEntity, baseurl,
                        new EventResult<ResetNewPasswordResponseEntity>() {

                            @Override
                            public void success(ResetNewPasswordResponseEntity serviceresult) {
                                resetpasswordResult.success(serviceresult);
                            }

                            @Override
                            public void failure(WebAuthError error) {

                                resetpasswordResult.failure(error);
                            }
                        });
            } else {
                String errorMessage = "ExchangeId or Exchange ID must not be empty";

                resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException(
                        errorMessage, "Error:" + methodName));
            }
        } catch (Exception e) {
            resetpasswordResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName,
                    WebAuthErrorCode.RESET_PASSWORD_VALIDATE_CODE_FAILURE, e.getMessage()));
        }
    }

}
