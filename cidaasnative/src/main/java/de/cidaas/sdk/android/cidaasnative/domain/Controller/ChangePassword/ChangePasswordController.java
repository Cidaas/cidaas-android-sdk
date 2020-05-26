package de.cidaas.sdk.android.cidaasnative.domain.Controller.ChangePassword;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Dictionary;

import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Service.ChangePassword.ChangePasswordService;
import de.cidaas.sdk.android.helper.enums.Result;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.logger.LogFile;
import de.cidaas.sdk.android.helper.pkce.OAuthChallengeGenerator;
import de.cidaas.sdk.android.properties.CidaasProperties;


public class ChangePasswordController {
    private String authenticationType;
    private String verificationType;
    private Context context;

    public static ChangePasswordController shared;

    public ChangePasswordController(Context contextFromCidaas) {

        verificationType = "";
        context = contextFromCidaas;
        authenticationType = "";

    }

    String codeVerifier, codeChallenge;

    // Generate Code Challenge and Code verifier
    public void generateChallenge() {
        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        codeVerifier = generator.getCodeVerifier();
        codeChallenge = generator.getCodeChallenge(codeVerifier);

    }

    public static ChangePasswordController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new ChangePasswordController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("ChangePasswordController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //ChangePasswordService
    public void changePassword(@NonNull final ChangePasswordRequestEntity changePasswordRequestEntity, final Result<ChangePasswordResponseEntity> resetpasswordResult) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    checkandChangePasswordService(baseurl, changePasswordRequestEntity, resetpasswordResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    resetpasswordResult.failure(error);
                }
            });


        } catch (Exception e) {
            resetpasswordResult.failure(WebAuthError.getShared(context).methodException("Exception :ChangePassword Controller :changePassword()",
                    WebAuthErrorCode.CHANGE_PASSWORD_FAILURE, e.getMessage()));
        }
    }

    private void checkandChangePasswordService(String baseurl, @NonNull final ChangePasswordRequestEntity changePasswordRequestEntity, final Result<ChangePasswordResponseEntity> resetpasswordResult) {
        String methodName = "ChangePassword Controller :checkandChangePasswordService()";
        try {

            if (changePasswordRequestEntity.getConfirm_password().isEmpty() && !changePasswordRequestEntity.getConfirm_password().equals("")
                    && changePasswordRequestEntity.getNew_password() != null && !changePasswordRequestEntity.getNew_password().equals("")) {
                if (changePasswordRequestEntity.getIdentityId() != null && !changePasswordRequestEntity.getIdentityId().equals("")
                        && changePasswordRequestEntity.getOld_password().isEmpty() && !changePasswordRequestEntity.getOld_password().equals("")
                ) {
                    if (baseurl != null && !baseurl.equals("")) {
                        ChangePasswordService.getShared(context).changePassword(changePasswordRequestEntity, baseurl, null, resetpasswordResult);
                    } else {
                        resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl must not be null"
                                , "Error :" + methodName));
                    }
                } else {
                    resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException("Confirm Password or new password must not be null"
                            , "Error :" + methodName));
                }

            } else {
                resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException("Confirm Password or new password  must not be null"
                        , "Error :" + methodName));
            }
        } catch (Exception e) {
            resetpasswordResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName,
                    WebAuthErrorCode.CHANGE_PASSWORD_FAILURE, e.getMessage()));
        }
    }
}
