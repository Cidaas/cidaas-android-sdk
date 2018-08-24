package com.example.cidaasv2.Controller.Repository.Configuration.SmartPush;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush.SetupSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush.SetupSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.Device.DeviceVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.SmartPush.SmartPushVerificationService;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import timber.log.Timber;

public class SmartPushConfigurationController {


    private String verificationType;
    private Context context;

    public static SmartPushConfigurationController shared;

    public SmartPushConfigurationController(Context contextFromCidaas) {

        verificationType="";
        context=contextFromCidaas;

        //Todo setValue for authenticationType

    }

    String codeVerifier, codeChallenge;
    // Generate Code Challenge and Code verifier
    public void generateChallenge(){
        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        codeVerifier=generator.getCodeVerifier();
        codeChallenge= generator.getCodeChallenge(codeVerifier);

    }

    public static SmartPushConfigurationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new SmartPushConfigurationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


//Todo Configure SmartPush by Passing the setupSmartPushRequestEntity
    // 1.  Check For NotNull Values
    // 2. Generate Code Challenge
    // 3.  getAccessToken From Sub
    // 4.  Call Setup SmartPush
    // 5.  Call Validate SmartPush
    // 3.  Call Scanned SmartPush
    // 3.  Call Enroll SmartPush and return the result
    // 4.  Maintain logs based on flags


    //Service call To SetupSmartPushMFA
    public void configureSmartPush(@NonNull final String sub, @NonNull final String baseurl,
                                 @NonNull final SetupSmartPushMFARequestEntity setupSmartPushMFARequestEntity,
                                 @NonNull final Result<EnrollSmartPushMFAResponseEntity> enrollresult)
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
                            setupSmartPushMFARequestEntity.getClient_id()!=null && !setupSmartPushMFARequestEntity.getClient_id().equals(""))
                    {
                        //Todo Service call

                        SmartPushVerificationService.getShared(context).setupSmartPush(baseurl, accessTokenresult.getAccess_token(), codeChallenge,setupSmartPushMFARequestEntity,new Result<SetupSmartPushMFAResponseEntity>() {
                            @Override
                            public void success(final SetupSmartPushMFAResponseEntity setupserviceresult) {

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
                                                            SmartPushVerificationService.getShared(context).scannedSmartPush(baseurl,result.getData().getUsage_pass(),setupserviceresult.getData().getStatusId(),
                                                                    accessTokenresult.getAccess_token(),new Result<ScannedResponseEntity>() {
                                                                        @Override
                                                                        public void success(final ScannedResponseEntity result) {
                                                                            DBHelper.getShared().setUserDeviceId(result.getData().getUserDeviceId(),baseurl);

                                                                            EnrollSmartPushMFARequestEntity enrollSmartPushMFARequestEntity = new EnrollSmartPushMFARequestEntity();

                                                                            //Validation
                                                                            if(sub != null && !sub.equals("") && setupserviceresult.getData().getRandomNumber()!=null
                                                                                    && !setupserviceresult.getData().getRandomNumber().equals("") &&
                                                                                    result.getData().getUserDeviceId()!=null && !  result.getData().getUserDeviceId().equals("")) {

                                                                                enrollSmartPushMFARequestEntity.setSub(sub);
                                                                                enrollSmartPushMFARequestEntity.setVerifierPassword(setupserviceresult.getData().getRandomNumber());
                                                                                enrollSmartPushMFARequestEntity.setStatusId(setupserviceresult.getData().getStatusId());
                                                                                enrollSmartPushMFARequestEntity.setUserDeviceId(result.getData().getUserDeviceId());
                                                                            }
                                                                            else {

                                                                                String errorMessage="Sub or SmartPush cannot be null";
                                                                                enrollresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,errorMessage,
                                                                                        HttpStatusCode.EXPECTATION_FAILED));
                                                                            }

                                                                            // call Enroll Service
                                                                            SmartPushVerificationService.getShared(context).enrollSmartPush(baseurl, accessTokenresult.getAccess_token(), enrollSmartPushMFARequestEntity,new Result<EnrollSmartPushMFAResponseEntity>() {
                                                                                @Override
                                                                                public void success(EnrollSmartPushMFAResponseEntity serviceresult) {
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


    //Login with SmartPush
    public void LoginWithSmartPush(@NonNull final String baseurl, @NonNull final String clientId, @NonNull final String trackId, @NonNull final String requestId,
                                   @NonNull final InitiateSmartPushMFARequestEntity initiateSmartPushMFARequestEntity,
                                   final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{


            if(initiateSmartPushMFARequestEntity.getUserDeviceId() != null && initiateSmartPushMFARequestEntity.getUserDeviceId() != "" )
            {
                //Do nothing
            }
            else
            {
                initiateSmartPushMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }


            if(codeChallenge=="" || codeVerifier=="" || codeChallenge==null || codeVerifier==null) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";
            if (    initiateSmartPushMFARequestEntity.getUsageType() != null && initiateSmartPushMFARequestEntity.getUsageType() != "" &&
                    initiateSmartPushMFARequestEntity.getUserDeviceId() != null && initiateSmartPushMFARequestEntity.getUserDeviceId() != ""&&
                    baseurl != null && !baseurl.equals("")) {

                initiateSmartPushMFARequestEntity.setClient_id(clientId);

                //Todo Service call
                SmartPushVerificationService.getShared(context).initiateSmartPush(baseurl, codeChallenge,initiateSmartPushMFARequestEntity,
                        new Result<InitiateSmartPushMFAResponseEntity>() {

                            //Todo Call Validate Device
                            @Override
                            public void success(final InitiateSmartPushMFAResponseEntity serviceresult) {

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
                                                            initiateSmartPushMFARequestEntity.setUsage_pass(result.getData().getUsage_pass());


                                                            SmartPushVerificationService.getShared(context).initiateSmartPush(baseurl, codeChallenge, initiateSmartPushMFARequestEntity,
                                                                    new Result<InitiateSmartPushMFAResponseEntity>() {

                                                                        @Override
                                                                        public void success(InitiateSmartPushMFAResponseEntity result) {
                                                                            if (result.getData().getRandomNumber() != null && !result.getData().getRandomNumber().equals("") && result.getData().getStatusId() != null &&
                                                                                    !result.getData().getStatusId().equals("")) {


                                                                                AuthenticateSmartPushRequestEntity authenticateSmartPushRequestEntity = new AuthenticateSmartPushRequestEntity();
                                                                                authenticateSmartPushRequestEntity.setUserDeviceId(initiateSmartPushMFARequestEntity.getUserDeviceId());
                                                                                authenticateSmartPushRequestEntity.setStatusId(result.getData().getStatusId());
                                                                                authenticateSmartPushRequestEntity.setVerifierPassword(result.getData().getRandomNumber());


                                                                                SmartPushVerificationService.getShared(context).authenticateSmartPush(baseurl, authenticateSmartPushRequestEntity,
                                                                                        new Result<AuthenticateSmartPushResponseEntity>() {

                                                                                            @Override
                                                                                            public void success(AuthenticateSmartPushResponseEntity result) {

                                                                                                //Todo Call Resume with Login Service
                                                                                                //  loginresult.success(result);
                                                                                                Toast.makeText(context, "Sucess SmartPush", Toast.LENGTH_SHORT).show();

                                                                                                ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

                                                                                                //Todo Check not Null values
                                                                                                resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                                                resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                                                resumeLoginRequestEntity.setUsageType(initiateSmartPushMFARequestEntity.getUsageType());
                                                                                                resumeLoginRequestEntity.setVerificationType("push");
                                                                                                resumeLoginRequestEntity.setClient_id(clientId);
                                                                                                resumeLoginRequestEntity.setRequestId(requestId);

                                                                                                if (initiateSmartPushMFARequestEntity.getUsageType().equals(UsageType.MFA)) {
                                                                                                    resumeLoginRequestEntity.setTrack_id(trackId);
                                                                                                    LoginController.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, loginresult);
                                                                                                } else if (initiateSmartPushMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS)) {
                                                                                                    resumeLoginRequestEntity.setTrack_id("");
                                                                                                    LoginController.getShared(context).continuePasswordless(baseurl, resumeLoginRequestEntity, loginresult);

                                                                                                }
                                                                                            }

                                                                                            @Override
                                                                                            public void failure(WebAuthError error) {
                                                                                                loginresult.failure(error);
                                                                                            }
                                                                                        });
                                                                                // result.success(serviceresult);
                                                                            } else {
                                                                                String errorMessage="Status Id or random number Must not be null";
                                                                                loginresult.failure(WebAuthError.getShared(context).customException(417,errorMessage, HttpStatusCode.EXPECTATION_FAILED));

                                                                            }
                                                                        }
                                                                        @Override
                                                                        public void failure(WebAuthError error) {
                                                                            loginresult.failure(error);
                                                                            Toast.makeText(context, "Error on validate Device" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }

                                                        @Override
                                                        public void failure(WebAuthError error) {
                                                            loginresult.failure(error);
                                                            Toast.makeText(context, "Error on validate Device" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }

                                        else {
                                            // return Error Message

                                            loginresult.failure(WebAuthError.getShared(context).deviceVerificationFailureException());
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
            else
            {

                loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }


    //Todo Save UserDeviceId After Scanned with DomainURL

   /* //setupSmartPushMFA
    public void setupSmartPushMFA(@NonNull String sub, @NonNull final Result<SetupSmartPushMFAResponseEntity> result){
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
                String loggerMessage = "Setup SmartPush MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            }if (savedProperties.get("ClientId").equals("") || savedProperties.get("ClientId") == null || savedProperties == null) {
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



                            SetupSmartPushMFARequestEntity setupSmartPushMFARequestEntity=new SetupSmartPushMFARequestEntity();
                            setupSmartPushMFARequestEntity.setClient_id( savedProperties.get("ClientId"));
                            setupSmartPushMFARequestEntity.setLogoUrl(logoUrl);


                            setupSmartPushMFAService(accesstokenresult.getAccess_token(), finalBaseurl,setupSmartPushMFARequestEntity,result);
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

    //Service call To SetupSmartPushMFA
    private void setupSmartPushMFAService(@NonNull final String AccessToken, @NonNull String baseurl,
                                          @NonNull SetupSmartPushMFARequestEntity setupSmartPushMFARequestEntity,
                                          @NonNull final Result<SetupSmartPushMFAResponseEntity> result)
    {
        try{

            if (baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call

                if(codeChallenge==null){
                    generateChallenge();
                }
                OauthService.getShared(context).setupSmartPushMFA(baseurl, AccessToken,codeChallenge,setupSmartPushMFARequestEntity,
                        new Result<SetupSmartPushMFAResponseEntity>() {
                            @Override
                            public void success(final SetupSmartPushMFAResponseEntity serviceresult) {

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
                                                    scannedSmartPush(result.getData().getUsage_pass(), serviceresult.getData().getStatusId(), AccessToken,new Result<ScannedResponseEntity>() {
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
                            public void failure(WebAuthError error)
                            {
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

    //Scanned SmartPush
    private void scannedSmartPush(@NonNull String usagePass,@NonNull String statusId,@NonNull String AccessToken,
                                  @NonNull final Result<ScannedResponseEntity> result)
    {
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
                String loggerMessage = "Setup SmartPush MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
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

                    scannedSmartPushService(usagePass, baseurl,statusId,AccessToken,result);


                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To Scanned SmartPush Service
    private void scannedSmartPushService(@NonNull String usagePass,@NonNull String baseurl,
                                         @NonNull String statusId,@NonNull String AccessToken,
                                         @NonNull final Result<ScannedResponseEntity> scannedResponseResult)
    {
        try{

            if ( statusId != null && !statusId.equals("") && usagePass != null && !usagePass.equals("") && baseurl != null
                    && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).scannedSmartPush(baseurl, usagePass, statusId,AccessToken,
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

    //enrollSmartPushMFA
    public void enrollSmartPushMFA(@NonNull final EnrollSmartPushMFARequestEntity enrollSmartPushMFARequestEntity, @NonNull final Result<EnrollSmartPushMFAResponseEntity> result){
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
                String loggerMessage = "Setup SmartPush MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");

                if ( enrollSmartPushMFARequestEntity.getVerifierPassword() != null && !enrollSmartPushMFARequestEntity.getVerifierPassword().equals("") &&
                        enrollSmartPushMFARequestEntity.getSub() != null && enrollSmartPushMFARequestEntity.getSub()  != null &&
                        enrollSmartPushMFARequestEntity.getStatusId() != null && enrollSmartPushMFARequestEntity.getStatusId()  != null &&
                        baseurl != null && !baseurl.equals("")) {

                    final String finalBaseurl = baseurl;
                    getAccessToken(enrollSmartPushMFARequestEntity.getSub(), new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accesstokenresult) {
                            enrollSmartPushMFAService(accesstokenresult.getAccess_token(), finalBaseurl,enrollSmartPushMFARequestEntity,result);
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

    //Service call To enrollSmartPushMFA
    private void enrollSmartPushMFAService(@NonNull String AccessToken, @NonNull String baseurl,
                                           @NonNull final EnrollSmartPushMFARequestEntity enrollSmartPushMFARequestEntity,
                                           @NonNull final Result<EnrollSmartPushMFAResponseEntity> result)
    {
        try{

            if (enrollSmartPushMFARequestEntity.getVerifierPassword() != null && !enrollSmartPushMFARequestEntity.getVerifierPassword().equals("") &&
                    enrollSmartPushMFARequestEntity.getSub() != null && enrollSmartPushMFARequestEntity.getSub()  != null &&
                    enrollSmartPushMFARequestEntity.getStatusId() != null && enrollSmartPushMFARequestEntity.getStatusId()  != null &&
                    baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call
                OauthService.getShared(context).enrollSmartPushMFA(baseurl, AccessToken, enrollSmartPushMFARequestEntity,new Result<EnrollSmartPushMFAResponseEntity>() {
                    @Override
                    public void success(EnrollSmartPushMFAResponseEntity serviceresult) {
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
