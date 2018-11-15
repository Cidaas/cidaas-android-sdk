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
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.Device.DeviceVerificationService;
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
    public void configureVoice(@NonNull final String sub, @NonNull final String baseurl, final File audioFile,
                          @NonNull final SetupVoiceMFARequestEntity setupVoiceMFARequestEntity,
                          @NonNull final Result<EnrollVoiceMFAResponseEntity> enrollresult)
    {
        try{
            Cidaas.instanceId="";
            //Todo check For Not null

            if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {
                //Todo Service call

                if(codeChallenge==null || codeChallenge==""){
                    generateChallenge();
                }


                AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessTokenresult) {
                        VoiceVerificationService.getShared(context).setupVoiceMFA(baseurl, accessTokenresult.getAccess_token(),codeChallenge,setupVoiceMFARequestEntity,null,
                                new Result<SetupVoiceMFAResponseEntity>() {
                                    @Override
                                    public void success(final SetupVoiceMFAResponseEntity setupserviceresult) {

                                        new CountDownTimer(5000, 500) {
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
                                                            ,null, new Result<ValidateDeviceResponseEntity>() {
                                                                @Override
                                                                public void success(ValidateDeviceResponseEntity result) {
                                                                    // call Scanned Service
                                                                    VoiceVerificationService.getShared(context).scannedVoice(baseurl,result.getData().getUsage_pass(),setupserviceresult.getData().getStatusId(),
                                                                            accessTokenresult.getAccess_token(),null,new Result<ScannedResponseEntity>() {
                                                                                @Override
                                                                                public void success(final ScannedResponseEntity result) {
                                                                                    DBHelper.getShared().setUserDeviceId(result.getData().getUserDeviceId(),baseurl);

                                                                                    EnrollVoiceMFARequestEntity enrollVoiceMFARequestEntity = new EnrollVoiceMFARequestEntity();
                                                                                    if(sub != null && !sub.equals("")  &&
                                                                                            result.getData().getUserDeviceId()!=null && !  result.getData().getUserDeviceId().equals("")) {

                                                                                        enrollVoiceMFARequestEntity.setSub(sub);
                                                                                        enrollVoiceMFARequestEntity.setAudioFile(audioFile);
                                                                                        enrollVoiceMFARequestEntity.setStatusId(setupserviceresult.getData().getStatusId());
                                                                                        enrollVoiceMFARequestEntity.setUserDeviceId(result.getData().getUserDeviceId());
                                                                                    }
                                                                                    else {
                                                                                        enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
                                                                                    }

                                                                                    // call Enroll Service
                                                                                    VoiceVerificationService.getShared(context).enrollVoice(baseurl, accessTokenresult.getAccess_token(), enrollVoiceMFARequestEntity,null,new Result<EnrollVoiceMFAResponseEntity>() {
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
                                                                                   // Toast.makeText(context, result.getData().getUserDeviceId()+"User Device id", Toast.LENGTH_SHORT).show();
                                                                                }

                                                                                @Override
                                                                                public void failure(WebAuthError error) {
                                                                                    enrollresult.failure(error);
                                                                                  //  Toast.makeText(context, "Error on Scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                }

                                                                @Override
                                                                public void failure(WebAuthError error) {
                                                                    enrollresult.failure(error);
                                                                   // Toast.makeText(context, "Error on validate Device"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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
             initiateVoiceMFARequestEntity.setClient_id(clientId);
            if(initiateVoiceMFARequestEntity.getUserDeviceId() != null && initiateVoiceMFARequestEntity.getUserDeviceId() != "" )
            {
                //Do nothing
            }
            else
            {
                initiateVoiceMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }

            if(codeChallenge=="" || codeVerifier=="" || codeChallenge==null || codeVerifier==null) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";
            if (    initiateVoiceMFARequestEntity.getUsageType() != null && initiateVoiceMFARequestEntity.getUsageType() != "" &&
                    initiateVoiceMFARequestEntity.getClient_id() != null && initiateVoiceMFARequestEntity.getClient_id() != "" &&
                    initiateVoiceMFARequestEntity.getUserDeviceId() != null && initiateVoiceMFARequestEntity.getUserDeviceId() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                VoiceVerificationService.getShared(context).initiateVoice(baseurl, codeChallenge,initiateVoiceMFARequestEntity,null,
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
                                                    , null,new Result<ValidateDeviceResponseEntity>() {

                                                        @Override
                                                        public void success(ValidateDeviceResponseEntity result) {

                                                            //Todo call initiate
                                                            initiateVoiceMFARequestEntity.setUsagePass(result.getData().getUsage_pass());
                                                            VoiceVerificationService.getShared(context).initiateVoice(baseurl, codeChallenge, initiateVoiceMFARequestEntity,null,
                                                                    new Result<InitiateVoiceMFAResponseEntity>() {

                                                                        @Override
                                                                        public void success(InitiateVoiceMFAResponseEntity serviceresult) {

                                                                            if ( serviceresult.getData().getStatusId() != null && !serviceresult.getData().getStatusId().equals("")) {


                                                                                AuthenticateVoiceRequestEntity authenticateVoiceRequestEntity = new AuthenticateVoiceRequestEntity();
                                                                                authenticateVoiceRequestEntity.setUserDeviceId(initiateVoiceMFARequestEntity.getUserDeviceId());
                                                                                authenticateVoiceRequestEntity.setStatusId(serviceresult.getData().getStatusId());
                                                                                authenticateVoiceRequestEntity.setVoiceFile(VoiceImageFile);


                                                                                VoiceVerificationService.getShared(context).authenticateVoice(baseurl, authenticateVoiceRequestEntity,null, new Result<AuthenticateVoiceResponseEntity>() {
                                                                                    @Override
                                                                                    public void success(AuthenticateVoiceResponseEntity result) {
                                                                                        //Todo Call Resume with Login Service

                                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();

                                                                                        //Todo Check not Null values
                                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                                        resumeLoginRequestEntity.setUsageType(initiateVoiceMFARequestEntity.getUsageType());
                                                                                        resumeLoginRequestEntity.setVerificationType("voice");
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
                                                                                  //      Toast.makeText(context, "Success Voice", Toast.LENGTH_SHORT).show();

                                                                                    }

                                                                                    @Override
                                                                                    public void failure(WebAuthError error) {
                                                                                        loginresult.failure(error);
                                                                                    }
                                                                                });
                                                                                // result.success(serviceresult);
                                                                            }
                                                                            else {
                                                                                String errorMessage = "Status Id Must not be null";
                                                                                loginresult.failure(WebAuthError.getShared(context).customException(417, errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                                                                            }
                                                                        }


                                                                        // result.success(serviceresult);


                                                                        @Override
                                                                        public void failure(WebAuthError error) {
                                                                            loginresult.failure(error);
                                                                          //  Toast.makeText(context, "Error on validate Device" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });

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
            else
            {
                String message="Usage type ,ClientId,Userdevice Id must not be null";
                loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,message,HttpStatusCode.EXPECTATION_FAILED));
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

}
