package com.example.cidaasv2.Controller.Repository.Verification.BackupCode;

import android.support.annotation.NonNull;

import com.example.cidaasv2.Helper.Entity.PhysicalVerificationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.OauthService;

import java.util.Dictionary;

import timber.log.Timber;

public class BackupCodeVerificationController {
 /*   //initiate backupcode MFA
    public void initiateBackupCodeMFA(@NonNull PhysicalVerificationEntity physicalVerificationEntity, @NonNull final Result<InitiateBackupCodeMFAResponseEntity> result){
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
                String loggerMessage = "initiate BackupCode MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
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


                    InitiateBackupCodeMFARequestEntity initiateBackupCodeMFARequestEntity=new InitiateBackupCodeMFARequestEntity();
                    initiateBackupCodeMFARequestEntity.setPhysicalVerificationId(physicalVerificationEntity.getPhysicalVerificationId());
                    initiateBackupCodeMFARequestEntity.setSub(physicalVerificationEntity.getSub());
                    initiateBackupCodeMFARequestEntity.setUsageType(physicalVerificationEntity.getUsageType());
                    initiateBackupCodeMFARequestEntity.setUserDeviceId(physicalVerificationEntity.getUserDeviceId());
                    initiateBackupCodeMFARequestEntity.setVerificationType("backupcode");
                    initiateBackupCodeMFAService(baseurl,initiateBackupCodeMFARequestEntity,result);
                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To initiatebackupcodeMFA
    private void initiateBackupCodeMFAService(@NonNull String baseurl, @NonNull InitiateBackupCodeMFARequestEntity initiateBackupCodeMFARequestEntity, final Result<InitiateBackupCodeMFAResponseEntity> result){
        try{

            if (initiateBackupCodeMFARequestEntity.getPhysicalVerificationId() != null && initiateBackupCodeMFARequestEntity.getPhysicalVerificationId() != "" &&
                    initiateBackupCodeMFARequestEntity.getUsageType() != null && initiateBackupCodeMFARequestEntity.getUsageType() != "" &&
                    initiateBackupCodeMFARequestEntity.getSub() != null && initiateBackupCodeMFARequestEntity.getSub() != "" &&
                    initiateBackupCodeMFARequestEntity.getUserDeviceId() != null && initiateBackupCodeMFARequestEntity.getUserDeviceId() != "" &&
                    initiateBackupCodeMFARequestEntity.getVerificationType() != null && initiateBackupCodeMFARequestEntity.getVerificationType() != ""&& baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).initiateBackupCodeMFA(baseurl, initiateBackupCodeMFARequestEntity, new Result<InitiateBackupCodeMFAResponseEntity>() {
                    @Override
                    public void success(InitiateBackupCodeMFAResponseEntity serviceresult) {
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

    //authenticatebackupcodeMFA
    public void authenticateBackupCodeMFA(@NonNull String statusId,@NonNull String verificationCode, @NonNull final Result<AuthenticateBackupCodeResponseEntity> result){
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
                String loggerMessage = "Authenticate BackupCode MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");



                AuthenticateBackupCodeRequestEntity authenticateBackupCodeRequestEntity=new AuthenticateBackupCodeRequestEntity();
                authenticateBackupCodeRequestEntity.setVerifierPassword(verificationCode);
                authenticateBackupCodeRequestEntity.setStatusId(statusId);
                authenticateBackupCodeMFAService(baseurl,authenticateBackupCodeRequestEntity,result);


            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("authenticateBackupCodeMFA exception"+e.getMessage());
            Timber.e("authenticateBackupCodeMFA exception"+e.getMessage());
        }
    }

    //Service call To authenticatebackupcodeMFA
    private void authenticateBackupCodeMFAService(@NonNull String baseurl, @NonNull AuthenticateBackupCodeRequestEntity authenticateBackupCodeRequestEntity, final Result<AuthenticateBackupCodeResponseEntity> result){
        try{

            if (authenticateBackupCodeRequestEntity.getVerifierPassword() != null && authenticateBackupCodeRequestEntity.getVerifierPassword() != "" &&
                    authenticateBackupCodeRequestEntity.getStatusId() != null && authenticateBackupCodeRequestEntity.getStatusId() != ""
                    && baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).authenticateBackupCodeMFA(baseurl, authenticateBackupCodeRequestEntity, new Result<AuthenticateBackupCodeResponseEntity>() {
                    @Override
                    public void success(AuthenticateBackupCodeResponseEntity serviceresult) {
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
