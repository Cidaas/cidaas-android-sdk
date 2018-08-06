package com.example.cidaasv2.Controller.Repository.Verification.Voice;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.cidaasv2.Helper.Entity.PhysicalVerificationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceResponseEntity;
import com.example.cidaasv2.Service.Repository.OauthService;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import java.util.Dictionary;

import timber.log.Timber;

public class VoiceVerificationController {
    //setupVoiceMFA

/*
    //initiate sms MFA
    public void initiateVoiceMFA(@NonNull PhysicalVerificationEntity physicalVerificationEntity, @NonNull final Result<InitiateVoiceMFAResponseEntity> result){
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
                String loggerMessage = "initiate Voice MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
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

                    InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntity=new InitiateVoiceMFARequestEntity();
                    initiateVoiceMFARequestEntity.setPhysicalVerificationId(physicalVerificationEntity.getPhysicalVerificationId());
                    initiateVoiceMFARequestEntity.setSub(physicalVerificationEntity.getSub());
                    initiateVoiceMFARequestEntity.setUsageType(physicalVerificationEntity.getUsageType());
                    initiateVoiceMFARequestEntity.setUserDeviceId(physicalVerificationEntity.getUserDeviceId());
                    initiateVoiceMFARequestEntity.setVerificationType("Voice");
                    initiateVoiceMFAService(baseurl,initiateVoiceMFARequestEntity,result);
                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }
    private void initiateVoiceMFAService(@NonNull String baseurl, @NonNull InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntity, final Result<InitiateVoiceMFAResponseEntity> result){
        try{

            if (initiateVoiceMFARequestEntity.getPhysicalVerificationId() != null && initiateVoiceMFARequestEntity.getPhysicalVerificationId() != "" &&
                    initiateVoiceMFARequestEntity.getUsageType() != null && initiateVoiceMFARequestEntity.getUsageType() != "" &&
                    initiateVoiceMFARequestEntity.getSub() != null && initiateVoiceMFARequestEntity.getSub() != "" &&
                    initiateVoiceMFARequestEntity.getUserDeviceId() != null && initiateVoiceMFARequestEntity.getUserDeviceId() != "" &&
                    initiateVoiceMFARequestEntity.getVerificationType() != null && initiateVoiceMFARequestEntity.getVerificationType() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).initiateVoiceMFA(baseurl, initiateVoiceMFARequestEntity, new Result<InitiateVoiceMFAResponseEntity>() {
                    @Override
                    public void success(InitiateVoiceMFAResponseEntity serviceresult) {
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

    //authenticateVoiceMFA
    public void authenticateVoiceMFA(@NonNull String statusId,@NonNull String verificationCode, @NonNull String deviceId,@NonNull final Result<AuthenticateVoiceResponseEntity> result){
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
                String loggerMessage = "Authenticate Voice MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");



                AuthenticateVoiceRequestEntity authenticateVoiceRequestEntity=new AuthenticateVoiceRequestEntity();
                authenticateVoiceRequestEntity.setCode(verificationCode);
                authenticateVoiceRequestEntity.setStatusId(statusId);
                authenticateVoiceMFAService(baseurl,authenticateVoiceRequestEntity,result);


            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("authenticateVoiceMFA exception"+e.getMessage());
            Timber.e("authenticateVoiceMFA exception"+e.getMessage());
        }
    }

    //Service call To authenticateVoiceMFA
    private void authenticateVoiceMFAService(@NonNull String baseurl, @NonNull AuthenticateVoiceRequestEntity authenticateVoiceRequestEntity, final Result<AuthenticateVoiceResponseEntity> result){
        try{

            if (authenticateVoiceRequestEntity.getCode() != null && authenticateVoiceRequestEntity.getCode() != "" &&
                    authenticateVoiceRequestEntity.getStatusId() != null && authenticateVoiceRequestEntity.getStatusId() != "" &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).authenticateVoiceMFA(baseurl, authenticateVoiceRequestEntity, new Result<AuthenticateVoiceResponseEntity>() {
                    @Override
                    public void success(AuthenticateVoiceResponseEntity serviceresult) {
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

    //Service call To initiateVoiceMFA
}
