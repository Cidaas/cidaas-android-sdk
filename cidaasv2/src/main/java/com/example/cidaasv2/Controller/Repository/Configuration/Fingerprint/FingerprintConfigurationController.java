package com.example.cidaasv2.Controller.Repository.Configuration.Fingerprint;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Configuration.Fingerprint.FingerprintConfigurationController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceResponseEntity;
import com.example.cidaasv2.Service.Repository.OauthService;
import com.example.cidaasv2.Service.Repository.Verification.Device.DeviceVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.Fingerprint.FingerprintVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.Fingerprint.FingerprintVerificationService;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import java.util.Dictionary;

import timber.log.Timber;

public class FingerprintConfigurationController {


    private String authenticationType;
    private String verificationType;
    private Context context;

    public static FingerprintConfigurationController shared;

    public FingerprintConfigurationController(Context contextFromCidaas) {

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

    public static FingerprintConfigurationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new FingerprintConfigurationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


//Todo Configure Fingerprint by Passing the setupFingerprintRequestEntity
    // 1.  Check For NotNull Values
    // 2. Generate Code Challenge
    // 3.  getAccessToken From Sub
    // 4.  Call Setup Fingerprint
    // 5.  Call Validate Fingerprint
    // 3.  Call Scanned Fingerprint
    // 3.  Call Enroll Fingerprint and return the result
    // 4.  Maintain logs based on flags


    //Service call To SetupFingerprintMFA
    public void configureFingerprint(@NonNull final String sub, @NonNull final String baseurl,
                                 @NonNull final SetupFingerprintMFARequestEntity setupFingerprintMFARequestEntity,
                                 @NonNull final Result<EnrollFingerprintMFAResponseEntity> enrollresult)
    {
        try{
            //Generate Challenge
            generateChallenge();
            Cidaas.instanceId="";

            AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>()
            {
                @Override
                public void success(final AccessTokenEntity accessTokenresult) {

                    if (baseurl != null && !baseurl.equals("") && accessTokenresult.getAccess_token() != null && !accessTokenresult.getAccess_token().equals("") &&
                            setupFingerprintMFARequestEntity.getClient_id()!=null && !setupFingerprintMFARequestEntity.getClient_id().equals(""))
                    {
                        //Todo Service call

                        FingerprintVerificationService.getShared(context).setupFingerprint(baseurl, accessTokenresult.getAccess_token(), codeChallenge,setupFingerprintMFARequestEntity,new Result<SetupFingerprintMFAResponseEntity>() {
                            @Override
                            public void success(final SetupFingerprintMFAResponseEntity setupserviceresult) {

                                new CountDownTimer(5000, 500) {
                                    String instceID="";
                                    public void onTick(long millisUntilFinished) {
                                        instceID= Cidaas.instanceId;

                                        Timber.e("");
                                        if(instceID!=null && instceID!="")
                                        {
                                            this.cancel();
                                            onFinish();
                                        }

                                    }
                                    public void onFinish() {
                                        if(instceID!=null && instceID!="" && setupserviceresult.getData().getStatusId()!=null && setupserviceresult.getData().getStatusId()!="")
                                        {
                                            //Device Validation Service
                                            DeviceVerificationService.getShared(context).validateDevice(baseurl,instceID,setupserviceresult.getData().getStatusId(),codeVerifier
                                                    , new Result<ValidateDeviceResponseEntity>() {
                                                        @Override
                                                        public void success(ValidateDeviceResponseEntity result) {
                                                            // call Scanned Service
                                                            FingerprintVerificationService.getShared(context).scannedFingerprint(baseurl,result.getData().getUsage_pass(),setupserviceresult.getData().getStatusId(),
                                                                    accessTokenresult.getAccess_token(),new Result<ScannedResponseEntity>() {
                                                                        @Override
                                                                        public void success(final ScannedResponseEntity result) {
                                                                            DBHelper.getShared().setUserDeviceId(result.getData().getUserDeviceId(),baseurl);

                                                                            EnrollFingerprintMFARequestEntity enrollFingerprintMFARequestEntity = new EnrollFingerprintMFARequestEntity();
                                                                            if(sub != null && !sub.equals("") &&
                                                                                    result.getData().getUserDeviceId()!=null && !  result.getData().getUserDeviceId().equals("")) {

                                                                                enrollFingerprintMFARequestEntity.setSub(sub);
                                                                                enrollFingerprintMFARequestEntity.setStatusId(setupserviceresult.getData().getStatusId());
                                                                                enrollFingerprintMFARequestEntity.setUserDeviceId(result.getData().getUserDeviceId());
                                                                            }
                                                                            else {
                                                                                enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
                                                                            }

                                                                            // call Enroll Service
                                                                            FingerprintVerificationService.getShared(context).enrollFingerprint(baseurl, accessTokenresult.getAccess_token(), enrollFingerprintMFARequestEntity,new Result<EnrollFingerprintMFAResponseEntity>() {
                                                                                @Override
                                                                                public void success(EnrollFingerprintMFAResponseEntity serviceresult) {
                                                                                    enrollresult.success(serviceresult);
                                                                                }

                                                                                @Override
                                                                                public void failure(WebAuthError error) {
                                                                                    enrollresult.failure(error);
                                                                                }
                                                                            });

                                                                            Timber.i(result.getData().getUserDeviceId()+"User Device id");
                                                                            Toast.makeText(context, result.getData().getUserDeviceId()+"User Device id", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                        @Override
                                                                        public void failure(WebAuthError error) {
                                                                            enrollresult.failure(error);
                                                                            Toast.makeText(context, "Error on Scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }

                                                        @Override
                                                        public void failure(WebAuthError error) {
                                                            enrollresult.failure(error);
                                                            Toast.makeText(context, "Error on validate Device"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                        else {
                                            // return Error Message

                                            enrollresult.failure(WebAuthError.getShared(context).deviceVerificationFailureException());
                                        }
                                    }
                                }.start();


                                // result.success(serviceresult);
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                enrollresult.failure(error);
                            }
                        });
                    }
                    else
                    {

                        enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollresult.failure(error);
                }
            });

        }
        catch (Exception e)
        {
            enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e(e.getMessage());
        }
    }


    //Login with Fingerprint
    public void LoginWithFingerprint(@NonNull final String baseurl, @NonNull final String clientId, @NonNull final String trackId, @NonNull final String requestId,
                                     @NonNull final InitiateFingerprintMFARequestEntity initiateFingerprintMFARequestEntity,
                                     final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

          /*  if(initiateFingerprintMFARequestEntity.getUserDeviceId() != null && initiateFingerprintMFARequestEntity.getUserDeviceId() != "" )
            {
                //Do nothing
            }
            else
            {
                initiateFingerprintMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }*/
            if(codeChallenge=="" && codeVerifier=="") {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";

            if (    initiateFingerprintMFARequestEntity.getUsageType() != null && initiateFingerprintMFARequestEntity.getUsageType() != "" &&
                    /*initiateFingerprintMFARequestEntity.getUserDeviceId() != null && initiateFingerprintMFARequestEntity.getUserDeviceId() != ""&&*/
                    initiateFingerprintMFARequestEntity.getSub() != null && initiateFingerprintMFARequestEntity.getSub() != "" &&
                    initiateFingerprintMFARequestEntity.getEmail() != null && initiateFingerprintMFARequestEntity.getEmail() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                FingerprintVerificationService.getShared(context).initiateFingerprint(baseurl,codeChallenge, initiateFingerprintMFARequestEntity,
                        new Result<InitiateFingerprintMFAResponseEntity>() {

                            //Todo Call Validate Device

                            @Override
                            public void success(final InitiateFingerprintMFAResponseEntity serviceresult) {

                                new CountDownTimer(5000, 500) {
                                    String instceID="";
                                    public void onTick(long millisUntilFinished) {
                                        instceID= Cidaas.instanceId;

                                        Timber.e("");
                                        if(instceID!=null && instceID!="")
                                        {
                                            this.cancel();
                                            onFinish();
                                        }

                                    }
                                    public void onFinish() {
                                        if(instceID!=null && instceID!="" && serviceresult.getData().getStatusId()!=null && serviceresult.getData().getStatusId()!="") {
                                            //Device Validation Service
                                            DeviceVerificationService.getShared(context).validateDevice(baseurl, instceID, serviceresult.getData().getStatusId(), codeVerifier
                                                    , new Result<ValidateDeviceResponseEntity>() {

                                                        @Override
                                                        public void success(ValidateDeviceResponseEntity result) {

                                                            //Todo call initiate
                                                            initiateFingerprintMFARequestEntity.setUsagePass(result.getData().getUsage_pass());
                                                            FingerprintVerificationService.getShared(context).initiateFingerprint(baseurl, codeChallenge, initiateFingerprintMFARequestEntity,
                                                                    new Result<InitiateFingerprintMFAResponseEntity>() {

                                                                        @Override
                                                                        public void success(InitiateFingerprintMFAResponseEntity result) {

                                                                        }


                                                                        // result.success(serviceresult);


                                                                        @Override
                                                                        public void failure(WebAuthError error) {
                                                                            loginresult.failure(error);
                                                                            Toast.makeText(context, "Error on validate Device" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });


                                                            if (serviceresult.getData().getStatusId() != null &&
                                        !serviceresult.getData().getStatusId().equals("")) {


                                    AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity = new AuthenticateFingerprintRequestEntity();
                                    authenticateFingerprintRequestEntity.setUserDeviceId(initiateFingerprintMFARequestEntity.getUserDeviceId());
                                    authenticateFingerprintRequestEntity.setStatusId(serviceresult.getData().getStatusId());
                                  


                                    FingerprintVerificationService.getShared(context).authenticateFingerprint(baseurl, authenticateFingerprintRequestEntity, new Result<AuthenticateFingerprintResponseEntity>() {
                                        @Override
                                        public void success(AuthenticateFingerprintResponseEntity result) {
                                            //Todo Call Resume with Login Service
                                            //  loginresult.success(result);
                                            Toast.makeText(context, "Sucess Fingerprint", Toast.LENGTH_SHORT).show();



                                            ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();

                                            //Todo Check not Null values
                                            resumeLoginRequestEntity.setSub(result.getData().getSub());
                                            resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                            resumeLoginRequestEntity.setUsageType(result.getData().getUsageType());
                                            resumeLoginRequestEntity.setVerificationType(result.getData().getVerificationType());
                                            resumeLoginRequestEntity.setClient_id(clientId);
                                            resumeLoginRequestEntity.setRequestId(requestId);

                                            if(initiateFingerprintMFARequestEntity.getUsageType().equals(UsageType.MFA))
                                            {
                                                resumeLoginRequestEntity.setTrack_id(trackId);
                                                LoginController.getShared(context).continueMFA(baseurl,resumeLoginRequestEntity,loginresult);
                                            }

                                            else if(initiateFingerprintMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS))
                                            {
                                                resumeLoginRequestEntity.setTrack_id("");
                                                LoginController.getShared(context).continuePasswordless(baseurl,resumeLoginRequestEntity,loginresult);

                                            }
                                        }

                                        @Override
                                        public void failure(WebAuthError error) {
                                            loginresult.failure(error);
                                        }
                                    });
                                    // result.success(serviceresult);
                                }
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                loginresult.failure(error);
                            }
                        });
            }
            else
            {

                loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
                                }.start();
                            }
                            @Override
                            public void failure(WebAuthError error) {
                                loginresult.failure(error);
                            }
                        });
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }


    /*
    //setupFingerprintMFA
    public void setupFingerprintMFA(@NonNull String sub, @NonNull final Result<SetupFingerprintMFAResponseEntity> result){
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
                String loggerMessage = "Setup Fingerprint MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } if (savedProperties.get("ClientId").equals("") || savedProperties.get("ClientId") == null || savedProperties == null) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Accept Consent readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            }
            else {
                baseurl = savedProperties.get("DomainURL");

                if ( sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                    final String finalBaseurl = baseurl;
                    getAccessToken(sub, new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accesstokenresult) {

                            String logoUrl= "https://docs.cidaas.de/assets/logoss.png";



                            SetupFingerprintMFARequestEntity setupFingerprintMFARequestEntity=new SetupFingerprintMFARequestEntity();
                            setupFingerprintMFARequestEntity.setClient_id( savedProperties.get("ClientId"));
                            setupFingerprintMFARequestEntity.setLogoUrl(logoUrl);


                            setupFingerprintMFAService(accesstokenresult.getAccess_token(), finalBaseurl,setupFingerprintMFARequestEntity,result);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            result.failure(error);
                        }
                    });


                }

            }



        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To SetupFingerprintMFA
    private void setupFingerprintMFAService(@NonNull final String AccessToken, @NonNull String baseurl, SetupFingerprintMFARequestEntity setupFingerprintMFARequestEntity,
                                            @NonNull final Result<SetupFingerprintMFAResponseEntity> result)
    {
        try{

            if (baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call

                if(codeChallenge==null){
                    generateChallenge();
                }
                OauthService.getShared(context).setupFingerprintMFA(baseurl, AccessToken,codeChallenge,setupFingerprintMFARequestEntity,
                        new Result<SetupFingerprintMFAResponseEntity>() {
                            @Override
                            public void success(final SetupFingerprintMFAResponseEntity serviceresult) {

                                new CountDownTimer(5000, 500) {
                                    String instceID="";
                                    public void onTick(long millisUntilFinished) {
                                        instceID=getInstanceId();
                                        if(instceID!=null && instceID!="")
                                        {
                                            onFinish();
                                        }

                                    }

                                    public void onFinish() {
                                        if(instceID!=null && instceID!="")
                                        {
                                            //Todo Call Next Service cALL TO Validate DEVICE
                                            validateDevice(instceID, serviceresult.getData().getStatusId(), new Result<ValidateDeviceResponseEntity>() {
                                                @Override
                                                public void success(ValidateDeviceResponseEntity result) {
                                                    //Todo call Next service
                                                    scannedFingerprint(result.getData().getUsage_pass(), serviceresult.getData().getStatusId(), AccessToken,new Result<ScannedResponseEntity>() {
                                                        @Override
                                                        public void success(ScannedResponseEntity result) {
                                                            Timber.i(result.getData().getUserDeviceId()+"USewr Device id");
                                                            Toast.makeText(context, result.getData().getUserDeviceId()+"USewr Device id", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void failure(WebAuthError error) {
                                                            Toast.makeText(context, "Error on Scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void failure(WebAuthError error) {
                                                    Toast.makeText(context, "Error on validate Device"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        else {
                                            // return Error Message
                                            webAuthError=WebAuthError.getShared(context).deviceVerificationFailureException();
                                            result.failure(webAuthError);
                                        }
                                    }
                                }.start();


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


    //Scanned Fingerprint
    private void scannedFingerprint(@NonNull String usagePass,@NonNull String statusId,@NonNull String AccessToken, @NonNull final Result<ScannedResponseEntity> result){
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
                String loggerMessage = "Setup Fingerprint MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            }
            if (savedProperties.get("ClientId").equals("") || savedProperties.get("ClientId") == null || savedProperties == null) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Accept Consent readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            }
            else {
                baseurl = savedProperties.get("DomainURL");

                if ( statusId != null && !statusId.equals("") && usagePass != null && !usagePass.equals("") && baseurl != null
                        && !baseurl.equals("")) {

                    scannedFingerprintService(usagePass, baseurl,statusId,AccessToken,result);


                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To Scanned Fingerprint Service
    private void scannedFingerprintService(@NonNull String usagePass,@NonNull String baseurl,
                                           @NonNull String statusId,@NonNull String AccessToken,
                                           @NonNull final Result<ScannedResponseEntity> scannedResponseResult){
        try{

            if ( statusId != null && !statusId.equals("") && usagePass != null && !usagePass.equals("") && baseurl != null
                    && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).scannedFingerprint(baseurl, usagePass, statusId,AccessToken,
                        new Result<ScannedResponseEntity>() {
                            @Override
                            public void success(final ScannedResponseEntity serviceresult) {
                                //Todo Call Scanned Service


                                scannedResponseResult.success(serviceresult);
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                scannedResponseResult.failure(error);
                            }
                        });
            }
            else
            {
                webAuthError=webAuthError.propertyMissingException();
                webAuthError.ErrorMessage="one of the Login properties missing";
                scannedResponseResult.failure(webAuthError);
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

    //enrollFingerprintMFA
    public void enrollFingerprintMFA(@NonNull final EnrollFingerprintMFARequestEntity enrollFingerprintMFARequestEntity,
                                     @NonNull final Result<EnrollFingerprintMFAResponseEntity> result){
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
                String loggerMessage = "Setup Fingerprint MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");

                if ( enrollFingerprintMFARequestEntity.getVerifierPassword() != null && !enrollFingerprintMFARequestEntity.getVerifierPassword().equals("") &&
                        enrollFingerprintMFARequestEntity.getSub() != null && enrollFingerprintMFARequestEntity.getSub()  != null &&
                        enrollFingerprintMFARequestEntity.getStatusId() != null && enrollFingerprintMFARequestEntity.getStatusId()  != null &&
                        baseurl != null && !baseurl.equals("")) {

                    final String finalBaseurl = baseurl;
                    getAccessToken(enrollFingerprintMFARequestEntity.getSub(), new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accesstokenresult) {
                            enrollFingerprintMFAService(accesstokenresult.getAccess_token(), finalBaseurl,enrollFingerprintMFARequestEntity,result);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            result.failure(error);
                        }
                    });


                }
                else {
                    webAuthError=webAuthError.propertyMissingException();
                    webAuthError.ErrorMessage="one of the Login properties missing";
                    result.failure(webAuthError);
                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To enrollFingerprintMFA
    private void enrollFingerprintMFAService(@NonNull String AccessToken, @NonNull String baseurl,
                                             @NonNull final EnrollFingerprintMFARequestEntity enrollFingerprintMFARequestEntity,
                                             @NonNull final Result<EnrollFingerprintMFAResponseEntity> result){
        try{

            if (enrollFingerprintMFARequestEntity.getVerifierPassword() != null && !enrollFingerprintMFARequestEntity.getVerifierPassword().equals("") &&
                    enrollFingerprintMFARequestEntity.getSub() != null && enrollFingerprintMFARequestEntity.getSub()  != null &&
                    enrollFingerprintMFARequestEntity.getStatusId() != null && enrollFingerprintMFARequestEntity.getStatusId()  != null &&
                    baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call
                OauthService.getShared(context).enrollFingerprintMFA(baseurl, AccessToken, enrollFingerprintMFARequestEntity,new Result<EnrollFingerprintMFAResponseEntity>() {
                    @Override
                    public void success(EnrollFingerprintMFAResponseEntity serviceresult) {
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
    }*/

}
