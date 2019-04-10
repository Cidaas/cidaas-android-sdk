package com.example.cidaasv2.Controller.Repository.ChangePassword;

import android.content.Context;

import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import com.example.cidaasv2.Service.Repository.ChangePassword.ChangePasswordService;

import java.util.Dictionary;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class ChangePasswordController {
    private String authenticationType;
    private String verificationType;
    private Context context;

    public static ChangePasswordController shared;

    public ChangePasswordController(Context contextFromCidaas) {

        verificationType="";
        context=contextFromCidaas;
        authenticationType="";
        //Todo setValue for authenticationType

    }

    String codeVerifier, codeChallenge;
    // Generate Code Challenge and Code verifier
    public void generateChallenge(){
        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        codeVerifier=generator.getCodeVerifier();
        codeChallenge= generator.getCodeChallenge(codeVerifier);

    }

    public static ChangePasswordController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new ChangePasswordController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //ChangePasswordService
    public void changePassword(@NonNull final ChangePasswordRequestEntity changePasswordRequestEntity, final Result<ChangePasswordResponseEntity> resetpasswordResult)
    {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl=result.get("DomainURL");
                    if(changePasswordRequestEntity.getConfirm_password() != null && !changePasswordRequestEntity.getConfirm_password().equals("")
                            && changePasswordRequestEntity.getNew_password() != null && !changePasswordRequestEntity.getNew_password().equals("")
                            && changePasswordRequestEntity.getIdentityId() != null && !changePasswordRequestEntity.getIdentityId().equals("")
                            && changePasswordRequestEntity.getOld_password() != null && !changePasswordRequestEntity.getOld_password().equals("")
                            && baseurl != null && !baseurl.equals("")){

                        ChangePasswordService.getShared(context).changePassword(changePasswordRequestEntity, baseurl,null, resetpasswordResult);
                    }
                    else{
                        resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException("Confirm Password or new password or IdentityID or Old password or baseurl must not be null"));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                   resetpasswordResult.failure(error);
                }
            });


        }
        catch (Exception e)
        {
            resetpasswordResult.failure(WebAuthError.getShared(context).serviceException("Exception :ChangePassword Controller :changePassword()",
                    WebAuthErrorCode.CHANGE_PASSWORD_FAILURE,e.getMessage()));
            Timber.e(e.getMessage());
        }
    }
}
