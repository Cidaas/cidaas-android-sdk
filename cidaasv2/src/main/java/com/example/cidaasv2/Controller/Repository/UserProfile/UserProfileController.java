package com.example.cidaasv2.Controller.Repository.UserProfile;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.cidaasv2.Controller.Repository.Tenant.TenantController;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.UserProfile.UserprofileResponseEntity;
import com.example.cidaasv2.Service.Repository.OauthService;
import com.example.cidaasv2.Service.Repository.UserProfile.UserProfileService;

import java.util.Dictionary;

import timber.log.Timber;

public class UserProfileController {



    private Context context;

    public static UserProfileController shared;

    public UserProfileController(Context contextFromCidaas) {

        context=contextFromCidaas;
        //Todo setValue for authenticationType

    }

    public static UserProfileController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new UserProfileController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    /*//Get Internal userProfile
    public void getInternalUserProfile(@NonNull String AccessToken, @NonNull String sub, @NonNull final Result<UserprofileResponseEntity> result){
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
                        result.failure(error);
                    }
                });
            }

            if (savedProperties.get("DomainURL").equals("") || savedProperties.get("DomainURL") == null || savedProperties == null) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Verify Email MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");
                getInternalUserProfileService(baseurl,AccessToken,sub,result);
            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To get InternalUserProfile
    private void getInternalUserProfileService(@NonNull String baseurl, @NonNull String AccessToken,@NonNull String sub,final Result<UserprofileResponseEntity> result){
        try{

            if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {
                // Change service call to private
                OauthService.getShared(context).getInternalUserProfileInfo(baseurl,AccessToken,sub, new Result<UserprofileResponseEntity>() {

                    @Override
                    public void success(UserprofileResponseEntity serviceresult) {
                        result.success(serviceresult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }
            else
            {
                webAuthError=webAuthError.propertyMissingException();
                webAuthError.ErrorMessage="one of the  get Tenant Info properties missing";
                result.failure(webAuthError);
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

*/
    public void getUserProfile()
    {
        try
        {
            UserProfileService.getShared(context);
        }
        catch (Exception e)
        {

        }
    }

}
