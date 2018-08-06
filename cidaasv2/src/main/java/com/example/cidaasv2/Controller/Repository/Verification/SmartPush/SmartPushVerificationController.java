package com.example.cidaasv2.Controller.Repository.Verification.SmartPush;

import android.support.annotation.NonNull;

import com.example.cidaasv2.Helper.Entity.PhysicalVerificationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.OauthService;

import java.util.Dictionary;

import timber.log.Timber;

public class SmartPushVerificationController {

/*

    //initiate sms MFA
    public void initiateSmartPushMFA(@NonNull PhysicalVerificationEntity physicalVerificationEntity, @NonNull final Result<InitiateSmartPushMFAResponseEntity> result){
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
                String loggerMessage = "initiate SmartPush MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");

                if (physicalVerificationEntity.getPhysicalVerificationId() != null && physicalVerificationEntity.getPhysicalVerificationId() != "" &&
                        physicalVerificationEntity.getUsageType() != null && physicalVerificationEntity.getUsageType() != "" &&
                        physicalVerificationEntity.getSub() != null && physicalVerificationEntity.getSub() != "" &&
                        physicalVerificationEntity.getUserDeviceId() != null && physicalVerificationEntity.getUserDeviceId() != "" &&
                        baseurl != null && !baseurl.equals("")) {


                    InitiateSmartPushMFARequestEntity initiateSmartPushMFARequestEntity=new InitiateSmartPushMFARequestEntity();
                    initiateSmartPushMFARequestEntity.setPhysicalVerificationId(physicalVerificationEntity.getPhysicalVerificationId());
                    initiateSmartPushMFARequestEntity.setSub(physicalVerificationEntity.getSub());
                    initiateSmartPushMFARequestEntity.setUsageType(physicalVerificationEntity.getUsageType());
                    initiateSmartPushMFARequestEntity.setUserDeviceId(physicalVerificationEntity.getUserDeviceId());
                    initiateSmartPushMFARequestEntity.setVerificationType("PUSH");
                    initiateSmartPushMFAService(baseurl,initiateSmartPushMFARequestEntity,result);
                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To initiateSmartPushMFA
    private void initiateSmartPushMFAService(@NonNull String baseurl, @NonNull InitiateSmartPushMFARequestEntity initiateSmartPushMFARequestEntity, final Result<InitiateSmartPushMFAResponseEntity> result){
        try{

            if (initiateSmartPushMFARequestEntity.getPhysicalVerificationId() != null && initiateSmartPushMFARequestEntity.getPhysicalVerificationId() != "" &&
                    initiateSmartPushMFARequestEntity.getUsageType() != null && initiateSmartPushMFARequestEntity.getUsageType() != "" &&
                    initiateSmartPushMFARequestEntity.getSub() != null && initiateSmartPushMFARequestEntity.getSub() != "" &&
                    initiateSmartPushMFARequestEntity.getUserDeviceId() != null && initiateSmartPushMFARequestEntity.getUserDeviceId() != "" &&
                    initiateSmartPushMFARequestEntity.getVerificationType() != null && initiateSmartPushMFARequestEntity.getVerificationType() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).initiateSmartPushMFA(baseurl, initiateSmartPushMFARequestEntity, new Result<InitiateSmartPushMFAResponseEntity>() {
                    @Override
                    public void success(InitiateSmartPushMFAResponseEntity serviceresult) {
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
                webAuthError.ErrorMessage="one of the Login properties missing";
                result.failure(webAuthError);
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

    //authenticateSmartPushMFA
    public void authenticateSmartPushMFA(@NonNull String statusId,@NonNull String verificationCode,@NonNull final Result<AuthenticateSmartPushResponseEntity> result){
        try {
            String baseurl="";
            if(savedProperties==null){

                savedProperties=DBHelper.getShared().getLoginProperties();
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
                String loggerMessage = "Authenticate SmartPush MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");



                AuthenticateSmartPushRequestEntity authenticateSmartPushRequestEntity=new AuthenticateSmartPushRequestEntity();
                authenticateSmartPushRequestEntity.setVerifierPassword(verificationCode);
                authenticateSmartPushRequestEntity.setStatusId(statusId);

                authenticateSmartPushMFAService(baseurl,authenticateSmartPushRequestEntity,result);


            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("authenticateSmartPushMFA exception"+e.getMessage());
            Timber.e("authenticateSmartPushMFA exception"+e.getMessage());
        }
    }

    //Service call To authenticateSmartPushMFA
    private void authenticateSmartPushMFAService(@NonNull String baseurl, @NonNull AuthenticateSmartPushRequestEntity authenticateSmartPushRequestEntity,
                                                 final Result<AuthenticateSmartPushResponseEntity> result)
    {
        try{

            if (authenticateSmartPushRequestEntity.getVerifierPassword() != null && authenticateSmartPushRequestEntity.getVerifierPassword() != "" &&
                    authenticateSmartPushRequestEntity.getStatusId() != null && authenticateSmartPushRequestEntity.getStatusId() != "" &&
                    baseurl != null && !baseurl.equals(""))  {
                //Todo Service call
                OauthService.getShared(context).authenticateSmartPushMFA(baseurl, authenticateSmartPushRequestEntity, new Result<AuthenticateSmartPushResponseEntity>() {
                    @Override
                    public void success(AuthenticateSmartPushResponseEntity serviceresult) {
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
                webAuthError.ErrorMessage="one of the Login properties missing";
                result.failure(webAuthError);
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }
*/

}
