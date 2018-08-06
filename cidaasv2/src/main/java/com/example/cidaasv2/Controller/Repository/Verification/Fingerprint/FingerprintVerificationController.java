package com.example.cidaasv2.Controller.Repository.Verification.Fingerprint;

import android.support.annotation.NonNull;

import com.example.cidaasv2.Helper.Entity.PhysicalVerificationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.OauthService;

import java.util.Dictionary;

import timber.log.Timber;

public class FingerprintVerificationController {

 /*   //initiate sms MFA
    public void initiateFingerprintMFA(@NonNull PhysicalVerificationEntity physicalVerificationEntity, @NonNull final Result<InitiateFingerprintMFAResponseEntity> result){
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
                String loggerMessage = "initiate Fingerprint MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
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


                    InitiateFingerprintMFARequestEntity initiateFingerprintMFARequestEntity=new InitiateFingerprintMFARequestEntity();
                    initiateFingerprintMFARequestEntity.setPhysicalVerificationId(physicalVerificationEntity.getPhysicalVerificationId());
                    initiateFingerprintMFARequestEntity.setSub(physicalVerificationEntity.getSub());
                    initiateFingerprintMFARequestEntity.setUsageType(physicalVerificationEntity.getUsageType());
                    initiateFingerprintMFARequestEntity.setUserDeviceId(physicalVerificationEntity.getUserDeviceId());
                    initiateFingerprintMFARequestEntity.setVerificationType("Fingerprint");
                    initiateFingerprintMFAService(baseurl,initiateFingerprintMFARequestEntity,result);
                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To initiateFingerprintMFA
    private void initiateFingerprintMFAService(@NonNull String baseurl, @NonNull InitiateFingerprintMFARequestEntity initiateFingerprintMFARequestEntity,
                                               final Result<InitiateFingerprintMFAResponseEntity> result){
        try{

            if (initiateFingerprintMFARequestEntity.getPhysicalVerificationId() != null && initiateFingerprintMFARequestEntity.getPhysicalVerificationId() != "" &&
                    initiateFingerprintMFARequestEntity.getUsageType() != null && initiateFingerprintMFARequestEntity.getUsageType() != "" &&
                    initiateFingerprintMFARequestEntity.getSub() != null && initiateFingerprintMFARequestEntity.getSub() != "" &&
                    initiateFingerprintMFARequestEntity.getUserDeviceId() != null && initiateFingerprintMFARequestEntity.getUserDeviceId() != "" &&
                    initiateFingerprintMFARequestEntity.getVerificationType() != null && initiateFingerprintMFARequestEntity.getVerificationType() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).initiateFingerprintMFA(baseurl, initiateFingerprintMFARequestEntity, new Result<InitiateFingerprintMFAResponseEntity>() {
                    @Override
                    public void success(InitiateFingerprintMFAResponseEntity serviceresult) {
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

    //authenticateFingerprintMFA
    public void authenticateFingerprintMFA(@NonNull String statusId,@NonNull String verificationCode,@NonNull final Result<AuthenticateFingerprintResponseEntity> result){
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
                String loggerMessage = "Authenticate Fingerprint MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");



                AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity=new AuthenticateFingerprintRequestEntity();
                authenticateFingerprintRequestEntity.setCode(verificationCode);
                authenticateFingerprintRequestEntity.setStatusId(statusId);


                authenticateFingerprintMFAService(baseurl,authenticateFingerprintRequestEntity,result);


            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("authenticateFingerprintMFA exception"+e.getMessage());
            Timber.e("authenticateFingerprintMFA exception"+e.getMessage());
        }
    }

    //Service call To authenticateFingerprintMFA
    private void authenticateFingerprintMFAService(@NonNull String baseurl, @NonNull AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity,
                                                   final Result<AuthenticateFingerprintResponseEntity> result){
        try{

            if (authenticateFingerprintRequestEntity.getCode() != null && authenticateFingerprintRequestEntity.getCode() != "" &&
                    authenticateFingerprintRequestEntity.getStatusId() != null && authenticateFingerprintRequestEntity.getStatusId() != "" &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).authenticateFingerprintMFA(baseurl, authenticateFingerprintRequestEntity, new Result<AuthenticateFingerprintResponseEntity>() {
                    @Override
                    public void success(AuthenticateFingerprintResponseEntity serviceresult) {
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
