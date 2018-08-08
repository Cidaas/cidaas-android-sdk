package com.example.cidaasv2.Controller.Repository.Configuration.Pattern;

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
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern.InitiatePatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern.InitiatePatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceResponseEntity;
import com.example.cidaasv2.Service.Repository.OauthService;
import com.example.cidaasv2.Service.Repository.Verification.Device.DeviceVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.Pattern.PatternVerificationService;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import timber.log.Timber;

public class PatternConfigurationController {

    //Local variables

    private String authenticationType;
    private String verificationType;
    private Context context;

    public static PatternConfigurationController shared;

    public PatternConfigurationController(Context contextFromCidaas) {

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

    public static PatternConfigurationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new PatternConfigurationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


//Todo Configure pattern by Passing the setupPatternRequestEntity
    // 1.  Check For NotNull Values
    // 2. Generate Code Challenge
    // 3.  getAccessToken From Sub
    // 4.  Call Setup Pattern
    // 5.  Call Validate Pattern
    // 3.  Call Scanned Pattern
    // 3.  Call Enroll Pattern and return the result
    // 4.  Maintain logs based on flags


    //Service call To SetupPatternMFA
    public void configurePattern(@NonNull final String sub, @NonNull final String baseurl,@NonNull final String patternString,
                                        @NonNull final SetupPatternMFARequestEntity setupPatternMFARequestEntity,
                                        @NonNull final Result<EnrollPatternMFAResponseEntity> enrollresult)
    {
        try{

            if(codeChallenge=="" && codeVerifier=="") {
                //Generate Challenge
                generateChallenge();
            }
         Cidaas.instanceId="";

            AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>()
            {
                @Override
                public void success(final AccessTokenEntity accessTokenresult) {

                    if (baseurl != null && !baseurl.equals("") && accessTokenresult.getAccess_token() != null && !accessTokenresult.getAccess_token().equals("") &&
                            setupPatternMFARequestEntity.getClient_id()!=null && !setupPatternMFARequestEntity.getClient_id().equals(""))
                    {
                        //Done Service call

                        PatternVerificationService.getShared(context).setupPattern(baseurl, accessTokenresult.getAccess_token(), codeChallenge,setupPatternMFARequestEntity,new Result<SetupPatternMFAResponseEntity>() {
                            @Override
                            public void success(final SetupPatternMFAResponseEntity setupserviceresult) {

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
                                        if(instceID!=null && !instceID.equals("") && setupserviceresult.getData().getStatusId()!=null && !setupserviceresult.getData().getStatusId().equals(""))
                                        {
                                            //Device Validation Service
                                            DeviceVerificationService.getShared(context).validateDevice(baseurl,instceID,setupserviceresult.getData().getStatusId(),codeVerifier
                                                    , new Result<ValidateDeviceResponseEntity>() {
                                                        @Override
                                                        public void success(ValidateDeviceResponseEntity result) {
                                                            // call Scanned Service
                                                            PatternVerificationService.getShared(context).scannedPattern(baseurl,result.getData().getUsage_pass(),setupserviceresult.getData().getStatusId(),
                                                                    accessTokenresult.getAccess_token(),new Result<ScannedResponseEntity>() {
                                                                        @Override
                                                                        public void success(final ScannedResponseEntity result) {
                                                                            DBHelper.getShared().setUserDeviceId(result.getData().getUserDeviceId(),baseurl);

                                                                            EnrollPatternMFARequestEntity enrollPatternMFARequestEntity = new EnrollPatternMFARequestEntity();
                                                                     if(sub != null && !sub.equals("") && patternString!=null && !patternString.equals("") &&
                                                                             result.getData().getUserDeviceId()!=null && !  result.getData().getUserDeviceId().equals("")) {

                                                                         enrollPatternMFARequestEntity.setSub(sub);
                                                                         enrollPatternMFARequestEntity.setVerifierPassword(patternString);
                                                                         enrollPatternMFARequestEntity.setStatusId(setupserviceresult.getData().getStatusId());
                                                                         enrollPatternMFARequestEntity.setUserDeviceId(result.getData().getUserDeviceId());
                                                                     }
                                                                     else {
                                                                         enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
                                                                     }

                                                                            // call Enroll Service
                                                                            PatternVerificationService.getShared(context).enrollPattern(baseurl, accessTokenresult.getAccess_token(), enrollPatternMFARequestEntity,new Result<EnrollPatternMFAResponseEntity>() {
                                                                                @Override
                                                                                public void success(EnrollPatternMFAResponseEntity serviceresult) {
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


    //Login with Pattern
    public void LoginWithPattern( @NonNull final String patternString,@NonNull final String baseurl, @NonNull final String clientId,
                                 @NonNull final String trackId, @NonNull final String requestId,
                                 @NonNull final InitiatePatternMFARequestEntity initiatePatternMFARequestEntity,
                                 final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(codeChallenge=="" && codeVerifier=="") {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";
            if(initiatePatternMFARequestEntity.getUserDeviceId() != null && !initiatePatternMFARequestEntity.getUserDeviceId().equals(""))
            {
                //Do nothing
            }
            else
            {
                initiatePatternMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }
            initiatePatternMFARequestEntity.setClient_id(clientId);


            if (    initiatePatternMFARequestEntity.getUsageType() != null && !initiatePatternMFARequestEntity.getUsageType().equals("") &&
                    initiatePatternMFARequestEntity.getUserDeviceId() != null && !initiatePatternMFARequestEntity.getUserDeviceId().equals("") &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                PatternVerificationService.getShared(context).initiatePattern(baseurl,codeChallenge, initiatePatternMFARequestEntity,
                        new Result<InitiatePatternMFAResponseEntity>() {

                            @Override
                            public void success(final InitiatePatternMFAResponseEntity serviceresult) {

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
                                                            initiatePatternMFARequestEntity.setUsagePass(result.getData().getUsage_pass());
                                                            PatternVerificationService.getShared(context).initiatePattern(baseurl, codeChallenge, initiatePatternMFARequestEntity,
                                                                    new Result<InitiatePatternMFAResponseEntity>() {

                                                                        @Override
                                                                        public void success(InitiatePatternMFAResponseEntity result) {
                                                                            if (patternString != null && !patternString.equals("") && serviceresult.getData().getStatusId() != null &&
                                                                                    !serviceresult.getData().getStatusId().equals("")) {


                                                                                AuthenticatePatternRequestEntity authenticatePatternRequestEntity = new AuthenticatePatternRequestEntity();
                                                                                authenticatePatternRequestEntity.setUserDeviceId(initiatePatternMFARequestEntity.getUserDeviceId());
                                                                                authenticatePatternRequestEntity.setStatusId(serviceresult.getData().getStatusId());
                                                                                authenticatePatternRequestEntity.setVerifierPassword(patternString);


                                                                                PatternVerificationService.getShared(context).authenticatePattern(baseurl, authenticatePatternRequestEntity, new Result<AuthenticatePatternResponseEntity>() {
                                                                                    @Override
                                                                                    public void success(AuthenticatePatternResponseEntity result) {
                                                                                        //Todo Call Resume with Login Service

                                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

                                                                                        //Todo Check not Null values
                                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                                        resumeLoginRequestEntity.setVerificationType("PATTERN");
                                                                                        resumeLoginRequestEntity.setUsageType(initiatePatternMFARequestEntity.getUsageType());
                                                                                        resumeLoginRequestEntity.setClient_id(clientId);
                                                                                        resumeLoginRequestEntity.setRequestId(requestId);

                                                                                        if (initiatePatternMFARequestEntity.getUsageType().equals(UsageType.MFA)) {
                                                                                            resumeLoginRequestEntity.setTrack_id(trackId);
                                                                                            LoginController.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, loginresult);
                                                                                        } else if (initiatePatternMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS)) {
                                                                                            resumeLoginRequestEntity.setTrack_id("");
                                                                                            LoginController.getShared(context).continuePasswordless(baseurl, resumeLoginRequestEntity, loginresult);

                                                                                        }
                                                                                        //  loginresult.success(result);
                                                                                        Toast.makeText(context, "Sucess Pattern", Toast.LENGTH_SHORT).show();
                               /*

                                LoginController.getShared(context).resumeLogin();*/
                                                                                    }

                                                                                    @Override
                                                                                    public void failure(WebAuthError error) {
                                                                                        loginresult.failure(error);
                                                                                    }
                                                                                });
                                                                            }
                                                                            else {
                                                                                String errorMessage="Status Id or Pattern Must not be null";
                                                                                loginresult.failure(WebAuthError.getShared(context).customException(417,errorMessage, HttpStatusCode.EXPECTATION_FAILED));

                                                                            }
                                                                        }


                                                                        // result.success(serviceresult);


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



}
