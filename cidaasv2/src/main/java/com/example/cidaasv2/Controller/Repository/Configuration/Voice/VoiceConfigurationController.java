package com.example.cidaasv2.Controller.Repository.Configuration.Voice;

import android.content.Context;
import android.os.CountDownTimer;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.ResumeLogin.ResumeLogin;
import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.Voice.VoiceVerificationService;
import com.example.cidaasv2.Service.Scanned.ScannedRequestEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import java.io.File;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class VoiceConfigurationController {
    
   //Variables
    private Context context;
    String codeVerifier="", codeChallenge="";
    public static VoiceConfigurationController shared;

    //Constructor
    public VoiceConfigurationController(Context contextFromCidaas) {
        context=contextFromCidaas;
    }

   
    //#### -Methods-#####
    // Generate Code Challenge and Code verifier
    public void generateChallenge(){
        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        codeVerifier=generator.getCodeVerifier();
        codeChallenge= generator.getCodeChallenge(codeVerifier);

    }

    //Shared Instance
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
    public void configureVoice(@NonNull final File VoiceImageFile, @NonNull final String sub, @NonNull final String baseurl,
                               @NonNull final SetupVoiceMFARequestEntity setupVoiceMFARequestEntity,
                               @NonNull final Result<EnrollVoiceMFAResponseEntity> enrollresult)
    {
        try{

            //Check For Null
            if (baseurl != null && !baseurl.equals("")  && setupVoiceMFARequestEntity.getClient_id()!=null &&
                    !setupVoiceMFARequestEntity.getClient_id().equals("")) {

                if (codeChallenge.equals("") || codeVerifier.equals("") || codeChallenge == null || codeVerifier == null) {
                    //Generate Challenge
                    generateChallenge();
                }

                //Make Instance Id Null
                Cidaas.usagePass = "";

                //get Access Token
                AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessTokenresult) {

                        setupVoice(baseurl, accessTokenresult.getAccess_token(), VoiceImageFile, setupVoiceMFARequestEntity, enrollresult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        enrollresult.failure(error);
                    }

                });
            }
            else
            {

                enrollresult.failure(WebAuthError.getShared(context).propertyMissingException("BaseURL or ClientId must not be null"));
            }

        }
        catch (Exception e)
        {
            enrollresult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE));
            LogFile.getShared(context).addRecordToLog("Configure Voice Exception"+e.getMessage()+WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE);

            Timber.e(e.getMessage());
        }
    }


    private void setupVoice(final String baseurl, final String accessToken, @NonNull final File VoiceImageFile,
                            final SetupVoiceMFARequestEntity setupVoiceMFARequestEntity, final Result<EnrollVoiceMFAResponseEntity> enrollResult)
    {
        try
        {
            if (accessToken != null && !accessToken.equals(""))
            {
                //Done Service call

                VoiceVerificationService.getShared(context).setupVoiceMFA(baseurl, accessToken,
                        setupVoiceMFARequestEntity,null,new Result<SetupVoiceMFAResponseEntity>() {
                            @Override
                            public void success(final SetupVoiceMFAResponseEntity setupserviceresult) {

                                new CountDownTimer(5000, 500) {
                                    String usagePassFromService="";
                                    public void onTick(long millisUntilFinished) {
                                        usagePassFromService= Cidaas.usagePass;

                                        Timber.e("");
                                        if(usagePassFromService!=null && !usagePassFromService.equals(""))
                                        {
                                            this.cancel();
                                            onFinish();
                                        }

                                    }
                                    public void onFinish() {
                                        if(usagePassFromService!=null && !usagePassFromService.equals("") ) {

                                            setupVoiceAfterDeviceVerification(usagePassFromService,baseurl,accessToken,VoiceImageFile,setupVoiceMFARequestEntity,enrollResult);
                                        }

                                        else {
                                            enrollResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException());
                                        }
                                    }
                                }.start();
                            }
                            @Override
                            public void failure(WebAuthError error) {
                                enrollResult.failure(error);
                            }
                        });
            }
            else
            {

                enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("Access Token must not be empty"));
            }
        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.SETUP_VOICE_MFA_FAILURE));
            LogFile.getShared(context).addRecordToLog("Setup voice exception"+e.getMessage()+WebAuthErrorCode.SETUP_VOICE_MFA_FAILURE);

        }
    }

    private void setupVoiceAfterDeviceVerification(String usagePassFromService,final String baseurl, final String accessToken, @NonNull final File VoiceImageFile,
                                                   final SetupVoiceMFARequestEntity setupVoiceMFARequestEntity, final Result<EnrollVoiceMFAResponseEntity> enrollResult) {
        try {
            SetupVoiceMFARequestEntity setupVoiceMFARequestEntityWithUsagePass = new SetupVoiceMFARequestEntity();
            setupVoiceMFARequestEntityWithUsagePass.setUsage_pass(usagePassFromService);
            // call Scanned Service
            VoiceVerificationService.getShared(context).setupVoiceMFA(baseurl, accessToken,
                    setupVoiceMFARequestEntityWithUsagePass, null, new Result<SetupVoiceMFAResponseEntity>() {
                        @Override
                        public void success(final SetupVoiceMFAResponseEntity result) {
                            DBHelper.getShared().setUserDeviceId(result.getData().getUdi(), baseurl);

                            //Entity For Voice
                            EnrollVoiceMFARequestEntity enrollVoiceMFARequestEntity = new EnrollVoiceMFARequestEntity();
                            enrollVoiceMFARequestEntity.setAudioFile(VoiceImageFile);
                            enrollVoiceMFARequestEntity.setStatusId(result.getData().getSt());
                            enrollVoiceMFARequestEntity.setUserDeviceId(result.getData().getUdi());
                            enrollVoiceMFARequestEntity.setClient_id(setupVoiceMFARequestEntity.getClient_id());


                            enrollVoice(baseurl, accessToken, enrollVoiceMFARequestEntity, enrollResult);


                        }

                        @Override
                        public void failure(WebAuthError error) {
                            enrollResult.failure(error);
                        }
                    });
        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.SETUP_VOICE_MFA_FAILURE));
            LogFile.getShared(context).addRecordToLog("Setup voice After device verification exception"+e.getMessage()+WebAuthErrorCode.SETUP_VOICE_MFA_FAILURE);
        }
    }

    public void scannedWithVoice(final String baseurl,  String statusId, String clientId, final Result<ScannedResponseEntity> scannedResult)
    {
        try
        {
            if (baseurl != null && !baseurl.equals("")  && statusId!=null && !statusId.equals("") && clientId!=null && !clientId.equals("")) {

                final ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
                scannedRequestEntity.setStatusId(statusId);
                scannedRequestEntity.setClient_id(clientId);


                VoiceVerificationService.getShared(context).scannedVoice(baseurl,  scannedRequestEntity,
                        null, new Result<ScannedResponseEntity>()
                        {
                    @Override
                    public void success(ScannedResponseEntity result) {
                        Cidaas.usagePass ="";


                        new CountDownTimer(5000, 500) {
                            String usagePassFromService = "";

                            public void onTick(long millisUntilFinished) {
                                usagePassFromService = Cidaas.usagePass;

                                Timber.e("");
                                if (usagePassFromService != null && !usagePassFromService.equals("")) {
                                    this.cancel();
                                    onFinish();
                                }

                            }

                            public void onFinish() {

                                if(usagePassFromService!=null && !usagePassFromService.equals("") ) {

                                    ScannedRequestEntity scannedRequestEntity= new ScannedRequestEntity();
                                    scannedRequestEntity.setUsage_pass(usagePassFromService);

                                    VoiceVerificationService.getShared(context).scannedVoice(baseurl,  scannedRequestEntity, null, scannedResult);
                                }
                                else
                                {
                                    scannedResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException());
                                }
                            }
                        }.start();

                    }

                    @Override
                    public void failure(WebAuthError error) {
                        scannedResult.failure(error);
                    }
                });
            }
            else {
                scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_VOICE_MFA_FAILURE,
                        "BaseURL or ClientId or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }

        }
        catch (Exception e)
        {
            scannedResult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.SCANNED_VOICE_MFA_FAILURE));
            LogFile.getShared(context).addRecordToLog("Scanned voice exception"+e.getMessage()+WebAuthErrorCode.SCANNED_VOICE_MFA_FAILURE);

        }
    }



    public void enrollVoice(@NonNull final String baseurl, @NonNull final String accessToken,
                            @NonNull final EnrollVoiceMFARequestEntity enrollVoiceMFARequestEntity, final Result<EnrollVoiceMFAResponseEntity> enrollResult)
    {
        try
        {

            if(baseurl!=null && !baseurl.equals("") && accessToken!=null && !accessToken.equals("")) {

                if (enrollVoiceMFARequestEntity.getUserDeviceId() != null && !enrollVoiceMFARequestEntity.getUserDeviceId().equals("") &&
                        enrollVoiceMFARequestEntity.getClient_id() != null && !enrollVoiceMFARequestEntity.getClient_id().equals("") &&
                        enrollVoiceMFARequestEntity.getStatusId() != null && !enrollVoiceMFARequestEntity.getStatusId().equals("")) {


                    final EnrollVoiceMFARequestEntity enrollVoiceMFARequestEntityWithPass=new EnrollVoiceMFARequestEntity();
                    if( enrollVoiceMFARequestEntity.getAudioFile() != null)
                    {
                        enrollVoiceMFARequestEntityWithPass.setAudioFile(enrollVoiceMFARequestEntity.getAudioFile());
                        enrollVoiceMFARequestEntity.setAudioFile(null);
                    }
                    else
                    {
                        enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE,
                                "Voice must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                    }


                    // call Enroll Service
                    VoiceVerificationService.getShared(context).enrollVoice(baseurl, accessToken, enrollVoiceMFARequestEntity,
                            null, new Result<EnrollVoiceMFAResponseEntity>() {
                                @Override
                                public void success(final EnrollVoiceMFAResponseEntity serviceresult) {

                                    Cidaas.usagePass = "";
                                    //Timer
                                    new CountDownTimer(5000, 500) {
                                        String usagePassFromService = "";

                                        public void onTick(long millisUntilFinished) {
                                            usagePassFromService = Cidaas.usagePass;

                                            Timber.e("");
                                            if (usagePassFromService != null && !usagePassFromService.equals("")) {
                                                this.cancel();
                                                onFinish();
                                            }

                                        }

                                        public void onFinish() {
                                            if (usagePassFromService != null && !usagePassFromService.equals("")) {
                                                //enroll

                                                enrollVoiceMFARequestEntityWithPass.setUsage_pass(usagePassFromService);
                                                if(enrollVoiceMFARequestEntityWithPass.getAudioFile()!=null) {

                                                    // call Enroll Service
                                                    VoiceVerificationService.getShared(context).enrollVoice(baseurl, accessToken, enrollVoiceMFARequestEntityWithPass,
                                                            null, enrollResult);
                                                }
                                                else
                                                {
                                                    // return Error Message
                                                    enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE,
                                                            "Voice must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                                                }
                                            }
                                            else {
                                                // return Error Message
                                                enrollResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException());
                                            }

                                        }
                                    }.start();
                                }

                                @Override
                                public void failure(WebAuthError error) {
                                    enrollResult.failure(error);
                                    //   Toast.makeText(context, "Error on Scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE,
                            "UserdeviceId or Verifier password or clientId or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                }
            }
            else
            {
                enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE,
                        "BaseURL or accessToken must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }
        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE));
            LogFile.getShared(context).addRecordToLog("Enroll voice exception"+e.getMessage()+WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE);
        }
    }


    //Login with Voice
    public void LoginWithVoice(@NonNull final File VoiceImageFile, @NonNull final String baseurl, @NonNull final String clientId,
                               @NonNull final String trackId, @NonNull final String requestId,
                               @NonNull final InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntity,
                               final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(codeChallenge.equals("") && codeVerifier.equals("")) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.usagePass ="";
            if(initiateVoiceMFARequestEntity.getUserDeviceId() != null && !initiateVoiceMFARequestEntity.getUserDeviceId().equals(""))
            {
                //Do nothing
            }
            else
            {
                initiateVoiceMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }
            initiateVoiceMFARequestEntity.setClient_id(clientId);


            if (    initiateVoiceMFARequestEntity.getUsageType() != null && !initiateVoiceMFARequestEntity.getUsageType().equals("") &&
                    initiateVoiceMFARequestEntity.getUserDeviceId() != null && !initiateVoiceMFARequestEntity.getUserDeviceId().equals("") &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                VoiceVerificationService.getShared(context).initiateVoice(baseurl, initiateVoiceMFARequestEntity,null,
                        new Result<InitiateVoiceMFAResponseEntity>() {

                            @Override
                            public void success(final InitiateVoiceMFAResponseEntity serviceresult) {

                                Cidaas.usagePass ="";
                                new CountDownTimer(5000, 500) {
                                    String usagePassFromService="";
                                    public void onTick(long millisUntilFinished) {
                                        usagePassFromService= Cidaas.usagePass;

                                        Timber.e("");
                                        if(usagePassFromService!=null && !usagePassFromService.equals(""))
                                        {
                                            this.cancel();
                                            onFinish();
                                        }

                                    }
                                    public void onFinish() {
                                        if(usagePassFromService!=null && !usagePassFromService.equals("")) {
                                            initiateVoiceAfterDeviceVerification(usagePassFromService,VoiceImageFile,baseurl,clientId,trackId,requestId,initiateVoiceMFARequestEntity.getUsageType(),loginresult);
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
                loginresult.failure(WebAuthError.getShared(context).propertyMissingException("Usage Type or UserdeviceId or baseurl must not be empty"));
            }
        }
        catch (Exception e)
        {
            loginresult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.INITIATE_VOICE_MFA_FAILURE));
            LogFile.getShared(context).addRecordToLog("Login voice exception"+e.getMessage()+WebAuthErrorCode.INITIATE_VOICE_MFA_FAILURE);
        }
    }

    //Initiate service after DeviceVerification
    private void initiateVoiceAfterDeviceVerification(String usagePassFromService, @NonNull final File VoiceImageFile, @NonNull final String baseurl, @NonNull final String clientId,
                                                      @NonNull final String trackId, @NonNull final String requestId, final String usageType,
                                                      final Result<LoginCredentialsResponseEntity> loginresult) {
        try {
            //Todo call initiate
            final InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntityWithUsagePass = new InitiateVoiceMFARequestEntity();
            initiateVoiceMFARequestEntityWithUsagePass.setUsagePass(usagePassFromService);

            final String userDeviceId = DBHelper.getShared().getUserDeviceId(baseurl);

            VoiceVerificationService.getShared(context).initiateVoice(baseurl, initiateVoiceMFARequestEntityWithUsagePass, null,
                    new Result<InitiateVoiceMFAResponseEntity>() {

                        @Override
                        public void success(InitiateVoiceMFAResponseEntity result) {
                            if (VoiceImageFile != null && result.getData().getStatusId() != null &&
                                    !result.getData().getStatusId().equals("")) {


                                AuthenticateVoiceRequestEntity authenticateVoiceRequestEntity = new AuthenticateVoiceRequestEntity();
                                authenticateVoiceRequestEntity.setUserDeviceId(userDeviceId);
                                authenticateVoiceRequestEntity.setStatusId(result.getData().getStatusId());
                                authenticateVoiceRequestEntity.setVoiceFile(VoiceImageFile);
                                authenticateVoiceRequestEntity.setClient_id(clientId);


                                authenticateVoice(baseurl, authenticateVoiceRequestEntity, new Result<AuthenticateVoiceResponseEntity>() {
                                    @Override
                                    public void success(AuthenticateVoiceResponseEntity result) {
                                        ResumeLogin.getShared(context).resumeLoginAfterSuccessfullAuthentication(result.getData().getSub(), result.getData().getTrackingCode(),
                                                AuthenticationType.voice, usageType, clientId, requestId, trackId, baseurl, loginresult);

                                    }

                                    @Override
                                    public void failure(WebAuthError error) {
                                        loginresult.failure(error);
                                    }
                                });
                            } else {
                                String errorMessage = "Status Id or Voice Must not be null";
                                loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.INITIATE_VOICE_MFA_FAILURE, errorMessage, HttpStatusCode.EXPECTATION_FAILED));

                            }
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            loginresult.failure(error);
                            //  Toast.makeText(context, "Error on validate Device" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        catch (Exception e)
        {
            loginresult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.INITIATE_VOICE_MFA_FAILURE));
            LogFile.getShared(context).addRecordToLog("Initiate voice After device verification exception"+e.getMessage()+WebAuthErrorCode.INITIATE_VOICE_MFA_FAILURE);
        }
    }

    //Authenticate Voice

    public void authenticateVoice(final String baseurl, final AuthenticateVoiceRequestEntity authenticateVoiceRequestEntity, final Result<AuthenticateVoiceResponseEntity> authResult)
    {
        try
        {

            if(baseurl!=null && !baseurl.equals("") ) {

                if (authenticateVoiceRequestEntity.getUserDeviceId() != null && !authenticateVoiceRequestEntity.getUserDeviceId().equals("") &&
                        authenticateVoiceRequestEntity.getStatusId() != null && !authenticateVoiceRequestEntity.getStatusId().equals("")) {


                    final AuthenticateVoiceRequestEntity authenticateVoiceRequestEntityWithPass=new AuthenticateVoiceRequestEntity();
                    if( authenticateVoiceRequestEntity.getVoiceFile() != null)
                    {
                        authenticateVoiceRequestEntityWithPass.setVoiceFile(authenticateVoiceRequestEntity.getVoiceFile());
                        authenticateVoiceRequestEntity.setVoiceFile(null);
                    }
                    else
                    {
                        authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE,
                                "Voice must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                    }


                    VoiceVerificationService.getShared(context).authenticateVoice(baseurl, authenticateVoiceRequestEntity,null, new Result<AuthenticateVoiceResponseEntity>() {
                @Override
                public void success(final AuthenticateVoiceResponseEntity serviceresult) {


                    Cidaas.usagePass = "";

                    //Timer
                    new CountDownTimer(5000, 500) {
                        String usagePassFromService = "";

                        public void onTick(long millisUntilFinished) {
                            usagePassFromService = Cidaas.usagePass;

                            Timber.e("");
                            if (usagePassFromService != null && !usagePassFromService.equals("")) {
                                this.cancel();
                                onFinish();
                            }

                        }

                        public void onFinish() {
                            if (usagePassFromService != null && !usagePassFromService.equals("")) {

                                authenticateVoiceRequestEntityWithPass.setUsage_pass(usagePassFromService);

                                if( authenticateVoiceRequestEntityWithPass.getVoiceFile() != null) {


                                    VoiceVerificationService.getShared(context).authenticateVoice(baseurl, authenticateVoiceRequestEntityWithPass, null, authResult);
                                }
                                else
                                {
                                    authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE,
                                            "Voice must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                                }
                            }
                            else {
                                // return Error Message
                                authResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException());
                            }

                        }
                    }.start();
                }

                @Override
                public void failure(WebAuthError error) {
                    authResult.failure(error);
                }
            });
                } else {
                    authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE,
                            "UserdeviceId or Verifier password or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                }
            }
            else
            {
                authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE,
                        "BaseURL or accessToken must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }

        }
        catch (Exception e)
        {
            authResult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE));
            LogFile.getShared(context).addRecordToLog("Authenticate voice exception"+e.getMessage()+WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE);
        }
    }


}
