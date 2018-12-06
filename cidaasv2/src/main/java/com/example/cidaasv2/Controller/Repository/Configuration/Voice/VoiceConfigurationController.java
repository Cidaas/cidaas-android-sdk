package com.example.cidaasv2.Controller.Repository.Configuration.Voice;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;

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

    String codeVerifier="", codeChallenge="";
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
    public void configureVoice(@NonNull final File VoiceImageFile,@NonNull final String sub, @NonNull final String baseurl,
                                 @NonNull final SetupVoiceMFARequestEntity setupVoiceMFARequestEntity,
                                 @NonNull final Result<EnrollVoiceMFAResponseEntity> enrollresult)
    {
        try{

            if(codeChallenge=="" || codeVerifier=="" || codeChallenge==null || codeVerifier==null) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";

            AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>()
            {
                @Override
                public void success(final AccessTokenEntity accessTokenresult) {

                    setupVoice(baseurl,accessTokenresult.getAccess_token(),VoiceImageFile,setupVoiceMFARequestEntity,enrollresult);
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


    private void setupVoice(final String baseurl, final String accessToken, @NonNull final File VoiceImageFile,
                              SetupVoiceMFARequestEntity setupVoiceMFARequestEntity,final Result<EnrollVoiceMFAResponseEntity> enrollResult)
    {
        try
        {
            if (baseurl != null && !baseurl.equals("") && accessToken != null && !accessToken.equals("") &&
                    setupVoiceMFARequestEntity.getClient_id()!=null && !setupVoiceMFARequestEntity.getClient_id().equals(""))
            {
                //Done Service call

                VoiceVerificationService.getShared(context).setupVoiceMFA(baseurl, accessToken,
                        setupVoiceMFARequestEntity,null,new Result<SetupVoiceMFAResponseEntity>() {
                            @Override
                            public void success(final SetupVoiceMFAResponseEntity setupserviceresult) {

                                Cidaas.instanceId="";

                                new CountDownTimer(5000, 500) {
                                    String instceID="";
                                    public void onTick(long millisUntilFinished) {
                                        instceID= Cidaas.instanceId;

                                        Timber.e("");
                                        if(instceID!=null && !instceID.equals(""))
                                        {
                                            this.cancel();
                                            onFinish();
                                        }

                                    }
                                    public void onFinish() {
                                        if(instceID!=null && !instceID.equals("") ) {

                                            SetupVoiceMFARequestEntity setupVoiceMFARequestEntity1 = new SetupVoiceMFARequestEntity();
                                            setupVoiceMFARequestEntity1.setUsage_pass(instceID);
                                            // call Scanned Service
                                            VoiceVerificationService.getShared(context).setupVoiceMFA(baseurl, accessToken,
                                                    setupVoiceMFARequestEntity1, null, new Result<SetupVoiceMFAResponseEntity>() {
                                                        @Override
                                                        public void success(final SetupVoiceMFAResponseEntity result) {
                                                            DBHelper.getShared().setUserDeviceId(result.getData().getUdi(), baseurl);

                                                            //Entity For Voice
                                                            EnrollVoiceMFARequestEntity enrollVoiceMFARequestEntity = new EnrollVoiceMFARequestEntity();
                                                            enrollVoiceMFARequestEntity.setAudioFile(VoiceImageFile);
                                                            enrollVoiceMFARequestEntity.setStatusId(result.getData().getSt());
                                                            enrollVoiceMFARequestEntity.setUserDeviceId(result.getData().getUdi());


                                                            enrollVoice(baseurl,accessToken,enrollVoiceMFARequestEntity,enrollResult);


                                                        }

                                                        @Override
                                                        public void failure(WebAuthError error) {
                                                            enrollResult.failure(error);
                                                        }
                                                    });
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

                enrollResult.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE,
                    "Voice Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

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
                        Cidaas.instanceId="";


                        new CountDownTimer(5000, 500) {
                            String instceID = "";

                            public void onTick(long millisUntilFinished) {
                                instceID = Cidaas.instanceId;

                                Timber.e("");
                                if (instceID != null && !instceID.equals("")) {
                                    this.cancel();
                                    onFinish();
                                }

                            }

                            public void onFinish() {

                                if(instceID!=null && !instceID.equals("") ) {

                                    ScannedRequestEntity scannedRequestEntity= new ScannedRequestEntity();
                                    scannedRequestEntity.setUsage_pass(instceID);

                                    VoiceVerificationService.getShared(context).scannedVoice(baseurl,  scannedRequestEntity,
                                            null, new Result<ScannedResponseEntity>()
                                            {

                                        @Override
                                        public void success(ScannedResponseEntity result) {
                                            DBHelper.getShared().setUserDeviceId(result.getData().getUserDeviceId(),baseurl);
                                            scannedResult.success(result);
                                        }

                                        @Override
                                        public void failure(WebAuthError error) {
                                            scannedResult.failure(error);
                                        }
                                    });
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
            scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_VOICE_MFA_FAILURE,
                    "Voice Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }



    public void enrollVoice(@NonNull final String baseurl, @NonNull final String accessToken,
                            @NonNull final EnrollVoiceMFARequestEntity enrollVoiceMFARequestEntity, final Result<EnrollVoiceMFAResponseEntity> enrollResult)
    {
        try
        {

            if(baseurl!=null && !baseurl.equals("") && accessToken!=null && !accessToken.equals("")) {

                if (enrollVoiceMFARequestEntity.getUserDeviceId() != null && !enrollVoiceMFARequestEntity.getUserDeviceId().equals("") &&
                        enrollVoiceMFARequestEntity.getStatusId() != null && !enrollVoiceMFARequestEntity.getStatusId().equals("") &&
                        enrollVoiceMFARequestEntity.getAudioFile() != null ) {

                    // call Enroll Service
                    VoiceVerificationService.getShared(context).enrollVoice(baseurl, accessToken, enrollVoiceMFARequestEntity,
                            null, new Result<EnrollVoiceMFAResponseEntity>() {

                                @Override
                                public void success(final EnrollVoiceMFAResponseEntity serviceresult) {

                                    Cidaas.instanceId = "";

                                    //Timer
                                    new CountDownTimer(5000, 500) {
                                        String instceID = "";

                                        public void onTick(long millisUntilFinished) {
                                            instceID = Cidaas.instanceId;

                                            Timber.e("");
                                            if (instceID != null && !instceID.equals("")) {
                                                this.cancel();
                                                onFinish();
                                            }

                                        }

                                        public void onFinish() {
                                            if (instceID != null && !instceID.equals("")) {

                                                //enroll
                                                EnrollVoiceMFARequestEntity enrollVoiceMFARequest = new EnrollVoiceMFARequestEntity();
                                                enrollVoiceMFARequest.setUsage_pass(instceID);
                                                enrollVoiceMFARequest.setAudioFile(enrollVoiceMFARequestEntity.getAudioFile());

                                                // call Enroll Service
                                                VoiceVerificationService.getShared(context).enrollVoice(baseurl, accessToken, enrollVoiceMFARequest,
                                                        null, new Result<EnrollVoiceMFAResponseEntity>() {
                                                            @Override
                                                            public void success(EnrollVoiceMFAResponseEntity serviceresult) {
                                                                enrollResult.success(serviceresult);
                                                            }

                                                            @Override
                                                            public void failure(WebAuthError error) {
                                                                enrollResult.failure(error);
                                                            }
                                                        });
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
                            "UserdeviceId or Verifierpassword or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
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
            enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE,
                    "Voice Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }


    //Login with Voice
    public void LoginWithVoice(@NonNull final File VoiceImageFile,@NonNull final String baseurl, @NonNull final String clientId,
                                  @NonNull final String trackId, @NonNull final String requestId,
                                  @NonNull final InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntity,
                                  final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(codeChallenge.equals("") && codeVerifier.equals("")) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";
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

                                Cidaas.instanceId="";
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

                                            //Todo call initiate
                                            final InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntity=new InitiateVoiceMFARequestEntity();
                                            initiateVoiceMFARequestEntity.setUsagePass(instceID);

                                            final String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);

                                            VoiceVerificationService.getShared(context).initiateVoice(baseurl, initiateVoiceMFARequestEntity,null,
                                                    new Result<InitiateVoiceMFAResponseEntity>() {

                                                        @Override
                                                        public void success(InitiateVoiceMFAResponseEntity result) {
                                                            if (VoiceImageFile != null && serviceresult.getData().getStatusId() != null &&
                                                                    !serviceresult.getData().getStatusId().equals("")) {


                                                                AuthenticateVoiceRequestEntity authenticateVoiceRequestEntity = new AuthenticateVoiceRequestEntity();
                                                                authenticateVoiceRequestEntity.setUserDeviceId(userDeviceId);
                                                                authenticateVoiceRequestEntity.setStatusId(serviceresult.getData().getStatusId());
                                                                authenticateVoiceRequestEntity.setVoiceFile(VoiceImageFile);


                                                                authenticateVoice(baseurl, authenticateVoiceRequestEntity, new Result<AuthenticateVoiceResponseEntity>() {
                                                                    @Override
                                                                    public void success(AuthenticateVoiceResponseEntity result) {

                                                                        //Todo Call Resume with Login Service

                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

                                                                        //Todo Check not Null values
                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                        resumeLoginRequestEntity.setVerificationType("VOICE");
                                                                        resumeLoginRequestEntity.setUsageType(initiateVoiceMFARequestEntity.getUsageType());
                                                                        resumeLoginRequestEntity.setClient_id(clientId);
                                                                        resumeLoginRequestEntity.setRequestId(requestId);

                                                                        if (initiateVoiceMFARequestEntity.getUsageType().equals(UsageType.MFA)) {
                                                                            resumeLoginRequestEntity.setTrack_id(trackId);
                                                                            LoginController.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, loginresult);
                                                                        } else if (initiateVoiceMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS)) {
                                                                            resumeLoginRequestEntity.setTrack_id("");
                                                                            LoginController.getShared(context).continuePasswordless(baseurl, resumeLoginRequestEntity, loginresult);

                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void failure(WebAuthError error) {
                                                                        loginresult.failure(error);
                                                                    }
                                                                });



                                                            }
                                                            else {
                                                                String errorMessage="Status Id or Voice Must not be null";
                                                                loginresult.failure(WebAuthError.getShared(context).customException(417,errorMessage, HttpStatusCode.EXPECTATION_FAILED));

                                                            }

                                                        }

                                                        @Override
                                                        public void failure(WebAuthError error) {
                                                            loginresult.failure(error);
                                                            //  Toast.makeText(context, "Error on validate Device" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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


    //Authenticate Voice

    public void authenticateVoice(final String baseurl, final AuthenticateVoiceRequestEntity authenticateVoiceRequestEntity, final Result<AuthenticateVoiceResponseEntity> authResult)
    {
        try
        {
            VoiceVerificationService.getShared(context).authenticateVoice(baseurl, authenticateVoiceRequestEntity,null, new Result<AuthenticateVoiceResponseEntity>() {
                @Override
                public void success(final AuthenticateVoiceResponseEntity serviceresult) {


                    Cidaas.instanceId = "";

                    //Timer
                    new CountDownTimer(5000, 500) {
                        String instceID = "";

                        public void onTick(long millisUntilFinished) {
                            instceID = Cidaas.instanceId;

                            Timber.e("");
                            if (instceID != null && !instceID.equals("")) {
                                this.cancel();
                                onFinish();
                            }

                        }

                        public void onFinish() {
                            if (instceID != null && !instceID.equals("")) {
                                AuthenticateVoiceRequestEntity authenticateVoiceRequest=new AuthenticateVoiceRequestEntity();
                                authenticateVoiceRequest.setUsage_pass(instceID);
                                authenticateVoiceRequest.setVoiceFile(authenticateVoiceRequestEntity.getVoiceFile());


                                VoiceVerificationService.getShared(context).authenticateVoice(baseurl, authenticateVoiceRequest,null, new Result<AuthenticateVoiceResponseEntity>() {
                                    @Override
                                    public void success(AuthenticateVoiceResponseEntity result) {
                                        authResult.success(result);
                                    }

                                    @Override
                                    public void failure(WebAuthError error) {
                                        authResult.failure(error);
                                    }
                                });
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
        }
        catch (Exception e)
        {
            authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE,
                    "Voice Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
        }
    }


}
