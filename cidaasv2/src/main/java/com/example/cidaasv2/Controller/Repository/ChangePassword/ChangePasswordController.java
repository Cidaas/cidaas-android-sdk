package com.example.cidaasv2.Controller.Repository.ChangePassword;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import com.example.cidaasv2.Service.Repository.ChangePassword.ChangePasswordService;

import java.util.Dictionary;

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

   /* //ChangePassword
    public void changePassword(@NonNull ChangePasswordRequestEntity changePasswordRequestEntity, final Result<ChangePasswordResponseEntity> resetpasswordResult) {
        try {
            String baseurl="";
            if(savedProperties==null){

                savedProperties= DBHelper.getShared().getLoginProperties();
            }
            if(savedProperties==null){
                //Read from file if localDB is null
                readFromFile(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> loginProperties) {
                        savedProperties=loginProperties;
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        resetpasswordResult.failure(error);
                    }
                });
            }

            if (savedProperties.get("DomainURL").equals("") || savedProperties.get("DomainURL") == null || savedProperties == null) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Authenticate change password MFA readProperties failure : " + "Error Code - " +
                        webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                resetpasswordResult.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");
                changePasswordService(baseurl,changePasswordRequestEntity,resetpasswordResult);

            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }
*/
    //ChangePasswordService
    public void changePassword(@NonNull String baseurl,@NonNull ChangePasswordRequestEntity changePasswordRequestEntity, final Result<ChangePasswordResponseEntity> resetpasswordResult)
    {
        try {

            if(changePasswordRequestEntity.getConfirm_password() != null &&changePasswordRequestEntity.getConfirm_password()  != ""
                    && changePasswordRequestEntity.getNew_password() != null &&changePasswordRequestEntity.getNew_password()  != ""
                    && changePasswordRequestEntity.getIdentityId() != null &&changePasswordRequestEntity.getIdentityId()  != ""
                    && changePasswordRequestEntity.getOld_password() != null &&changePasswordRequestEntity.getOld_password()  != ""
                    && baseurl != null && !baseurl.equals("")){

                ChangePasswordService.getShared(context).changePassword(changePasswordRequestEntity, baseurl,
                        new Result<ChangePasswordResponseEntity>() {

                            @Override
                            public void success(ChangePasswordResponseEntity serviceresult) {
                                resetpasswordResult.success(serviceresult);
                            }

                            @Override
                            public void failure(WebAuthError error) {

                                resetpasswordResult.failure(error);
                            }
                        });
            }
            else{
             resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }
}
