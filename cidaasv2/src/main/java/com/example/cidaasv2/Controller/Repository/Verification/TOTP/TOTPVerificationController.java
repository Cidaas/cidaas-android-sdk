package com.example.cidaasv2.Controller.Repository.Verification.TOTP;

import android.support.annotation.NonNull;

import com.example.cidaasv2.Helper.Entity.PhysicalVerificationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP.AuthenticateTOTPRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP.AuthenticateTOTPResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP.InitiateTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP.InitiateTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.OauthService;

import java.util.Dictionary;

import timber.log.Timber;

public class TOTPVerificationController {

  /*  //initiate sms MFA
    public void initiateTOTPMFA(@NonNull PhysicalVerificationEntity physicalVerificationEntity, @NonNull final Result<InitiateTOTPMFAResponseEntity> result){
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
                String loggerMessage = "initiate TOTP MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
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


                    InitiateTOTPMFARequestEntity initiateTOTPMFARequestEntity=new InitiateTOTPMFARequestEntity();
                    initiateTOTPMFARequestEntity.setPhysicalVerificationId(physicalVerificationEntity.getPhysicalVerificationId());
                    initiateTOTPMFARequestEntity.setSub(physicalVerificationEntity.getSub());
                    initiateTOTPMFARequestEntity.setUsageType(physicalVerificationEntity.getUsageType());
                    initiateTOTPMFARequestEntity.setUserDeviceId(physicalVerificationEntity.getUserDeviceId());
                    initiateTOTPMFARequestEntity.setVerificationType("TOTP");
                    initiateTOTPMFAService(baseurl,initiateTOTPMFARequestEntity,result);
                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To initiateTOTPMFA
    private void initiateTOTPMFAService(@NonNull String baseurl, @NonNull InitiateTOTPMFARequestEntity initiateTOTPMFARequestEntity, final Result<InitiateTOTPMFAResponseEntity> result){
        try{

            if (initiateTOTPMFARequestEntity.getPhysicalVerificationId() != null && initiateTOTPMFARequestEntity.getPhysicalVerificationId() != "" &&
                    initiateTOTPMFARequestEntity.getUsageType() != null && initiateTOTPMFARequestEntity.getUsageType() != "" &&
                    initiateTOTPMFARequestEntity.getSub() != null && initiateTOTPMFARequestEntity.getSub() != "" &&
                    initiateTOTPMFARequestEntity.getUserDeviceId() != null && initiateTOTPMFARequestEntity.getUserDeviceId() != "" &&
                    initiateTOTPMFARequestEntity.getVerificationType() != null && initiateTOTPMFARequestEntity.getVerificationType() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).initiateTOTPMFA(baseurl, initiateTOTPMFARequestEntity, new Result<InitiateTOTPMFAResponseEntity>() {
                    @Override
                    public void success(InitiateTOTPMFAResponseEntity serviceresult) {
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

    //authenticateTOTPMFA
    public void authenticateTOTPMFA(@NonNull String statusId,@NonNull String verificationCode,@NonNull final Result<AuthenticateTOTPResponseEntity> result){
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
                String loggerMessage = "Authenticate TOTP MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");



                AuthenticateTOTPRequestEntity authenticateTOTPRequestEntity=new AuthenticateTOTPRequestEntity();
                authenticateTOTPRequestEntity.setVerifierPassword(verificationCode);
                authenticateTOTPRequestEntity.setStatusId(statusId);

                authenticateTOTPMFAService(baseurl,authenticateTOTPRequestEntity,result);


            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("authenticateTOTPMFA exception"+e.getMessage());
            Timber.e("authenticateTOTPMFA exception"+e.getMessage());
        }
    }

    //Service call To authenticateTOTPMFA
    private void authenticateTOTPMFAService(@NonNull String baseurl, @NonNull AuthenticateTOTPRequestEntity authenticateTOTPRequestEntity, final Result<AuthenticateTOTPResponseEntity> result){
        try{

            if (authenticateTOTPRequestEntity.getVerifierPassword() != null && authenticateTOTPRequestEntity.getVerifierPassword() != "" &&
                    authenticateTOTPRequestEntity.getStatusId() != null && authenticateTOTPRequestEntity.getStatusId() != "" &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).authenticateTOTPMFA(baseurl, authenticateTOTPRequestEntity, new Result<AuthenticateTOTPResponseEntity>() {
                    @Override
                    public void success(AuthenticateTOTPResponseEntity serviceresult) {
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
