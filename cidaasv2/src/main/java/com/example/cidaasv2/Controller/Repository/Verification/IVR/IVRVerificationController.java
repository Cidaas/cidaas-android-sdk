package com.example.cidaasv2.Controller.Repository.Verification.IVR;

import android.support.annotation.NonNull;

import com.example.cidaasv2.Helper.Entity.PhysicalVerificationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.OauthService;

import java.util.Dictionary;

import timber.log.Timber;

public class IVRVerificationController {


  /*  //initiate ivr MFA
    public void initiateIVRMFA(@NonNull PhysicalVerificationEntity physicalVerificationEntity, @NonNull final Result<InitiateIVRMFAResponseEntity> result){
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
                String loggerMessage = "initiate IVR MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");

                if (physicalVerificationEntity.getPhysicalVerificationId() != null && physicalVerificationEntity.getPhysicalVerificationId() != "" &&
                        physicalVerificationEntity.getUsageType() != null && physicalVerificationEntity.getUsageType() != "" &&
                        physicalVerificationEntity.getSub() != null && physicalVerificationEntity.getSub() != "" &&
                        physicalVerificationEntity.getUserDeviceId() != null && physicalVerificationEntity.getUserDeviceId() != ""
                        && baseurl != null && !baseurl.equals("")) {


                    InitiateIVRMFARequestEntity initiateIVRMFARequestEntity=new InitiateIVRMFARequestEntity();
                    initiateIVRMFARequestEntity.setPhysicalVerificationId(physicalVerificationEntity.getPhysicalVerificationId());
                    initiateIVRMFARequestEntity.setSub(physicalVerificationEntity.getSub());
                    initiateIVRMFARequestEntity.setUsageType(physicalVerificationEntity.getUsageType());
                    initiateIVRMFARequestEntity.setUserDeviceId(physicalVerificationEntity.getUserDeviceId());
                    initiateIVRMFARequestEntity.setVerificationType("ivr");
                    initiateIVRMFAService(baseurl,initiateIVRMFARequestEntity,result);
                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To initiateIVRMFA
    private void initiateIVRMFAService(@NonNull String baseurl, @NonNull InitiateIVRMFARequestEntity initiateIVRMFARequestEntity, final Result<InitiateIVRMFAResponseEntity> result){
        try{

            if (initiateIVRMFARequestEntity.getPhysicalVerificationId() != null && initiateIVRMFARequestEntity.getPhysicalVerificationId() != "" &&
                    initiateIVRMFARequestEntity.getUsageType() != null && initiateIVRMFARequestEntity.getUsageType() != "" &&
                    initiateIVRMFARequestEntity.getSub() != null && initiateIVRMFARequestEntity.getSub() != "" &&
                    initiateIVRMFARequestEntity.getUserDeviceId() != null && initiateIVRMFARequestEntity.getUserDeviceId() != "" &&
                    initiateIVRMFARequestEntity.getVerificationType() != null && initiateIVRMFARequestEntity.getVerificationType() != ""&&
                    initiateIVRMFARequestEntity.getDeviceInfo().getDeviceId() != null && initiateIVRMFARequestEntity.getDeviceInfo().getDeviceId() != ""
                    && baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).initiateIVRMFA(baseurl, initiateIVRMFARequestEntity, new Result<InitiateIVRMFAResponseEntity>() {
                    @Override
                    public void success(InitiateIVRMFAResponseEntity serviceresult) {
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

    //authenticateIVRMFA
    public void authenticateIVRMFA(@NonNull String statusId,@NonNull String verificationCode, @NonNull final Result<AuthenticateIVRResponseEntity> result){
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
                String loggerMessage = "Authenticate IVR MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");



                AuthenticateIVRRequestEntity authenticateIVRRequestEntity=new AuthenticateIVRRequestEntity();
                authenticateIVRRequestEntity.setCode(verificationCode);
                authenticateIVRRequestEntity.setStatusId(statusId);

                authenticateIVRMFAService(baseurl,authenticateIVRRequestEntity,result);


            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("authenticateIVRMFA exception"+e.getMessage());
            Timber.e("authenticateIVRMFA exception"+e.getMessage());
        }
    }

    //Service call To authenticateIVRMFA
    private void authenticateIVRMFAService(@NonNull String baseurl, @NonNull AuthenticateIVRRequestEntity authenticateIVRRequestEntity, final Result<AuthenticateIVRResponseEntity> result){
        try{

            if (authenticateIVRRequestEntity.getCode() != null && authenticateIVRRequestEntity.getCode() != "" &&
                    authenticateIVRRequestEntity.getStatusId() != null && authenticateIVRRequestEntity.getStatusId() != "" &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).authenticateIVRMFA(baseurl, authenticateIVRRequestEntity, new Result<AuthenticateIVRResponseEntity>() {
                    @Override
                    public void success(AuthenticateIVRResponseEntity serviceresult) {
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
