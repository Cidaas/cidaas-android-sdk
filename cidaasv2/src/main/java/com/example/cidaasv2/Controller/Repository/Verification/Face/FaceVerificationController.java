package com.example.cidaasv2.Controller.Repository.Verification.Face;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.cidaasv2.Helper.Entity.PhysicalVerificationEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Face.InitiateFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Face.InitiateFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face.SetupFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face.SetupFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceResponseEntity;
import com.example.cidaasv2.Service.Repository.OauthService;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import java.io.File;
import java.util.Dictionary;

import timber.log.Timber;

public class FaceVerificationController {



/*

    //initiate Face MFA
    public void initiateFaceMFA(@NonNull PhysicalVerificationEntity physicalVerificationEntity, @NonNull final Result<InitiateFaceMFAResponseEntity> result){
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
                String loggerMessage = "initiate FACE MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
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


                    InitiateFaceMFARequestEntity initiateFaceMFARequestEntity=new InitiateFaceMFARequestEntity();
                    initiateFaceMFARequestEntity.setPhysicalVerificationId(physicalVerificationEntity.getPhysicalVerificationId());
                    initiateFaceMFARequestEntity.setSub(physicalVerificationEntity.getSub());
                    initiateFaceMFARequestEntity.setUsageType(physicalVerificationEntity.getUsageType());
                    initiateFaceMFARequestEntity.setUserDeviceId(physicalVerificationEntity.getUserDeviceId());
                    initiateFaceMFARequestEntity.setVerificationType("Face");
                    initiateFaceMFAService(baseurl,initiateFaceMFARequestEntity,result);
                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("Face exception"+e.getMessage());
            Timber.e("Face exception"+e.getMessage());
        }
    }

    //Service call To initiateFaceMFA
    private void initiateFaceMFAService(@NonNull String baseurl, @NonNull InitiateFaceMFARequestEntity initiateFaceMFARequestEntity, final Result<InitiateFaceMFAResponseEntity> result){
        try{

            if (initiateFaceMFARequestEntity.getPhysicalVerificationId() != null && initiateFaceMFARequestEntity.getPhysicalVerificationId() != "" &&
                    initiateFaceMFARequestEntity.getUsageType() != null && initiateFaceMFARequestEntity.getUsageType() != "" &&
                    initiateFaceMFARequestEntity.getSub() != null && initiateFaceMFARequestEntity.getSub() != "" &&
                    initiateFaceMFARequestEntity.getUserDeviceId() != null && initiateFaceMFARequestEntity.getUserDeviceId() != "" &&
                    initiateFaceMFARequestEntity.getVerificationType() != null && initiateFaceMFARequestEntity.getVerificationType() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).initiateFaceMFA(baseurl, initiateFaceMFARequestEntity, new Result<InitiateFaceMFAResponseEntity>() {
                    @Override
                    public void success(InitiateFaceMFAResponseEntity serviceresult) {
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
    public void authenticateFaceMFA(@NonNull String statusId, @NonNull File imagetoSend, @NonNull String userDeviceid, @NonNull final Result<AuthenticateFaceResponseEntity> result){
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
                String loggerMessage = "Authenticate Face MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");



                AuthenticateFaceRequestEntity authenticateFaceRequestEntity=new AuthenticateFaceRequestEntity();
                authenticateFaceRequestEntity.setImagetoSend(imagetoSend);
                authenticateFaceRequestEntity.setStatusId(statusId);
                authenticateFaceRequestEntity.setUserDeviceId(userDeviceid);
                authenticateFaceMFAService(baseurl,authenticateFaceRequestEntity,result);


            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("authenticateFaceMFA exception"+e.getMessage());
            Timber.e("authenticateFaceMFA exception"+e.getMessage());
        }
    }

    //Service call To authenticateFaceMFA
    private void authenticateFaceMFAService(@NonNull String baseurl, @NonNull AuthenticateFaceRequestEntity authenticateFaceRequestEntity, final Result<AuthenticateFaceResponseEntity> result){
        try{

            if (
                    authenticateFaceRequestEntity.getStatusId() != null && authenticateFaceRequestEntity.getStatusId() != "" &&
                            baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).authenticateFaceMFA(baseurl, authenticateFaceRequestEntity, new Result<AuthenticateFaceResponseEntity>() {
                    @Override
                    public void success(AuthenticateFaceResponseEntity serviceresult) {
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
