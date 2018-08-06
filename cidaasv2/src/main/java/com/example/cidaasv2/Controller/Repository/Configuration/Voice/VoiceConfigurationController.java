package com.example.cidaasv2.Controller.Repository.Configuration.Voice;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.Device.DeviceVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.Voice.VoiceVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.Voice.VoiceVerificationService;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import java.io.File;

import timber.log.Timber;

public class VoiceConfigurationController {

    private String authenticationType;
    private String verificationType;
    private Context context;

    public static VoiceConfigurationController shared;

    public VoiceConfigurationController(Context contextFromCidaas) {

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

    public static VoiceConfigurationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new VoiceConfigurationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }



    //Service call To SetupVoiceMFA
    public void configureVoice(@NonNull final String sub, @NonNull final String baseurl, final File imageFile,
                          @NonNull final SetupVoiceMFARequestEntity setupVoiceMFARequestEntity,
                          @NonNull final Result<EnrollVoiceMFAResponseEntity> enrollresult)
    {
        try{
            Cidaas.instanceId="";
            //Todo check For Not null

            if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {
                //Todo Service call

                if(codeChallenge==null){
                    generateChallenge();
                }


                AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessTokenresult) {
                        VoiceVerificationService.getShared(context).setupVoiceMFA(baseurl, accessTokenresult.getAccess_token(),codeChallenge,setupVoiceMFARequestEntity,
                                new Result<SetupVoiceMFAResponseEntity>() {
                                    @Override
                                    public void success(final SetupVoiceMFAResponseEntity setupserviceresult) {

                                        new CountDownTimer(50000, 500) {
                                            String instceID="";
                                            public void onTick(long millisUntilFinished) {
                                                instceID= Cidaas.instanceId;
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
                                                                    VoiceVerificationService.getShared(context).scannedVoice(baseurl,result.getData().getUsage_pass(),setupserviceresult.getData().getStatusId(),
                                                                            accessTokenresult.getAccess_token(),new Result<ScannedResponseEntity>() {
                                                                                @Override
                                                                                public void success(final ScannedResponseEntity result) {
                                                                                    DBHelper.getShared().setUserDeviceId(result.getData().getUserDeviceId(),baseurl);

                                                                                    EnrollVoiceMFARequestEntity enrollVoiceMFARequestEntity = new EnrollVoiceMFARequestEntity();
                                                                                    if(sub != null && !sub.equals("")  &&
                                                                                            result.getData().getUserDeviceId()!=null && !  result.getData().getUserDeviceId().equals("")) {

                                                                                        enrollVoiceMFARequestEntity.setSub(sub);
                                                                                        enrollVoiceMFARequestEntity.setAudioFile(imageFile);
                                                                                        enrollVoiceMFARequestEntity.setStatusId(setupserviceresult.getData().getStatusId());
                                                                                        enrollVoiceMFARequestEntity.setUserDeviceId(result.getData().getUserDeviceId());
                                                                                    }
                                                                                    else {
                                                                                        enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
                                                                                    }

                                                                                    // call Enroll Service
                                                                                    VoiceVerificationService.getShared(context).enrollVoice(baseurl, accessTokenresult.getAccess_token(), enrollVoiceMFARequestEntity,new Result<EnrollVoiceMFAResponseEntity>() {
                                                                                        @Override
                                                                                        public void success(EnrollVoiceMFAResponseEntity serviceresult) {
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


                                    }

                                    @Override
                                    public void failure(WebAuthError error) {
                                        enrollresult.failure(error);
                                    }
                                });

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
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }



    //Login with Voice
    public void LoginWithVoice( @NonNull final File VoiceImageFile,@NonNull final String baseurl, @NonNull final String clientId,
                               @NonNull final String trackId, @NonNull final String requestId,
                               @NonNull final InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntity,
                               final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

          /*  if(initiateVoiceMFARequestEntity.getUserDeviceId() != null && initiateVoiceMFARequestEntity.getUserDeviceId() != "" )
            {
                //Do nothing
            }
            else
            {
                initiateVoiceMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }*/

            if(codeChallenge=="" && codeVerifier=="") {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";
            if (    initiateVoiceMFARequestEntity.getUsageType() != null && initiateVoiceMFARequestEntity.getUsageType() != "" &&
                    initiateVoiceMFARequestEntity.getUserDeviceId() != null && initiateVoiceMFARequestEntity.getUserDeviceId() != ""&&
                    initiateVoiceMFARequestEntity.getSub() != null && initiateVoiceMFARequestEntity.getSub() != "" &&
                    initiateVoiceMFARequestEntity.getEmail() != null && initiateVoiceMFARequestEntity.getEmail() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                VoiceVerificationService.getShared(context).initiateVoice(baseurl, codeChallenge,initiateVoiceMFARequestEntity,
                        new Result<InitiateVoiceMFAResponseEntity>() {

                            //Todo Call Validate Device

                            @Override
                            public void success(final InitiateVoiceMFAResponseEntity serviceresult) {

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
                                            DeviceVerificationService.getShared(context).validateDevice(baseurl,instceID, serviceresult.getData().getStatusId(), codeVerifier
                                                    , new Result<ValidateDeviceResponseEntity>() {

                                                        @Override
                                                        public void success(ValidateDeviceResponseEntity result) {

                                                            //Todo call initiate
                                                            initiateVoiceMFARequestEntity.setUsagePass(result.getData().getUsage_pass());
                                                            VoiceVerificationService.getShared(context).initiateVoice(baseurl, codeChallenge, initiateVoiceMFARequestEntity,
                                                                    new Result<InitiateVoiceMFAResponseEntity>() {

                                                                        @Override
                                                                        public void success(InitiateVoiceMFAResponseEntity result) {

                                                                        }


                                                                        // result.success(serviceresult);


                                                                        @Override
                                                                        public void failure(WebAuthError error) {
                                                                            loginresult.failure(error);
                                                                            Toast.makeText(context, "Error on validate Device" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });

                                                            if ( serviceresult.getData().getStatusId() != null && !serviceresult.getData().getStatusId().equals("")) {


                                    AuthenticateVoiceRequestEntity authenticateVoiceRequestEntity = new AuthenticateVoiceRequestEntity();
                                    authenticateVoiceRequestEntity.setUserDeviceId(initiateVoiceMFARequestEntity.getUserDeviceId());
                                    authenticateVoiceRequestEntity.setStatusId(serviceresult.getData().getStatusId());
                                    authenticateVoiceRequestEntity.setVoiceFile(VoiceImageFile);


                                    VoiceVerificationService.getShared(context).authenticateVoice(baseurl, authenticateVoiceRequestEntity, new Result<AuthenticateVoiceResponseEntity>() {
                                        @Override
                                        public void success(AuthenticateVoiceResponseEntity result) {
                                            //Todo Call Resume with Login Service

                                            ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();

                                            //Todo Check not Null values
                                            resumeLoginRequestEntity.setSub(result.getData().getSub());
                                            resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                            resumeLoginRequestEntity.setUsageType(result.getData().getUsageType());
                                            resumeLoginRequestEntity.setVerificationType(result.getData().getVerificationType());
                                            resumeLoginRequestEntity.setClient_id(clientId);
                                            resumeLoginRequestEntity.setRequestId(requestId);

                                            if(initiateVoiceMFARequestEntity.getUsageType().equals(UsageType.MFA))
                                            {
                                                resumeLoginRequestEntity.setTrack_id(trackId);
                                                LoginController.getShared(context).continueMFA(baseurl,resumeLoginRequestEntity,loginresult);
                                            }

                                            else if(initiateVoiceMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS))
                                            {
                                                resumeLoginRequestEntity.setTrack_id("");
                                                LoginController.getShared(context).continuePasswordless(baseurl,resumeLoginRequestEntity,loginresult);

                                            }
                                            //  loginresult.success(result);
                                            Toast.makeText(context, "Sucess Voice", Toast.LENGTH_SHORT).show();
                               /*

                                LoginController.getShared(context).resumeLogin();*/
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























    /*//setupVoiceMFA
    public void setupVoiceMFA(@NonNull String sub, @NonNull final Result<SetupVoiceMFAResponseEntity> result){
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
                String loggerMessage = "Setup Voice MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
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



                            SetupVoiceMFARequestEntity setupVoiceMFARequestEntity=new SetupVoiceMFARequestEntity();
                            setupVoiceMFARequestEntity.setClient_id( savedProperties.get("ClientId"));
                            setupVoiceMFARequestEntity.setLogoUrl(logoUrl);


                            setupVoiceMFAService(accesstokenresult.getAccess_token(), finalBaseurl,setupVoiceMFARequestEntity,result);
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

    //Service call To SetupVoiceMFA
    private void setupVoiceMFAService(@NonNull final String AccessToken, @NonNull String baseurl,
                                      @NonNull SetupVoiceMFARequestEntity setupVoiceMFARequestEntity,
                                      @NonNull final Result<SetupVoiceMFAResponseEntity> result)
    {
        try{

            if (baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call
                OauthService.getShared(context).setupVoiceMFA(baseurl, AccessToken, codeChallenge,setupVoiceMFARequestEntity,
                        new Result<SetupVoiceMFAResponseEntity>() {
                            @Override
                            public void success(final SetupVoiceMFAResponseEntity serviceresult) {

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
                                                    scannedVoice(result.getData().getUsage_pass(), serviceresult.getData().getStatusId(), AccessToken,new Result<ScannedResponseEntity>() {
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


    //Scanned Voice
    private void scannedVoice(@NonNull String usagePass,@NonNull String statusId,@NonNull String AccessToken,
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
                String loggerMessage = "Setup Voice MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
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

                    scannedVoiceService(usagePass, baseurl,statusId,AccessToken,result);


                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To Scanned Voice Service
    private void scannedVoiceService(@NonNull String usagePass,@NonNull String baseurl,
                                     @NonNull String statusId,@NonNull String AccessToken,
                                     @NonNull final Result<ScannedResponseEntity> scannedResponseResult)
    {
        try{

            if ( statusId != null && !statusId.equals("") && usagePass != null && !usagePass.equals("") && baseurl != null
                    && !baseurl.equals("")) {
                //Todo Service call

                if(codeChallenge==null){
                    generateChallenge();
                }
                OauthService.getShared(context).scannedVoice(baseurl, usagePass, statusId,AccessToken,
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
    //enrollVoiceMFA
    public void enrollVoiceMFA(@NonNull final EnrollVoiceMFARequestEntity enrollVoiceMFARequestEntity, @NonNull final Result<EnrollVoiceMFAResponseEntity> result){
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
                String loggerMessage = "Setup Voice MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");

                if ( enrollVoiceMFARequestEntity.getVerifierPassword() != null && !enrollVoiceMFARequestEntity.getVerifierPassword().equals("") &&
                        enrollVoiceMFARequestEntity.getSub() != null && enrollVoiceMFARequestEntity.getSub()  != null &&
                        enrollVoiceMFARequestEntity.getStatusId() != null && enrollVoiceMFARequestEntity.getStatusId()  != null &&
                        baseurl != null && !baseurl.equals("")) {

                    final String finalBaseurl = baseurl;
                    getAccessToken(enrollVoiceMFARequestEntity.getSub(), new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accesstokenresult) {
                            enrollVoiceMFAService(accesstokenresult.getAccess_token(), finalBaseurl,enrollVoiceMFARequestEntity,result);
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

    //Service call To enrollVoiceMFA
    private void enrollVoiceMFAService(@NonNull String AccessToken, @NonNull String baseurl,
                                       @NonNull final EnrollVoiceMFARequestEntity enrollVoiceMFARequestEntity, @NonNull final Result<EnrollVoiceMFAResponseEntity> result){
        try{

            if (enrollVoiceMFARequestEntity.getVerifierPassword() != null && !enrollVoiceMFARequestEntity.getVerifierPassword().equals("") &&
                    enrollVoiceMFARequestEntity.getSub() != null && enrollVoiceMFARequestEntity.getSub()  != null &&
                    enrollVoiceMFARequestEntity.getStatusId() != null && enrollVoiceMFARequestEntity.getStatusId()  != null &&
                    baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call
                OauthService.getShared(context).enrollVoiceMFA(baseurl, AccessToken, enrollVoiceMFARequestEntity,new Result<EnrollVoiceMFAResponseEntity>() {
                    @Override
                    public void success(EnrollVoiceMFAResponseEntity serviceresult) {
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
