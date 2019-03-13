package com.example.cidaasv2.Controller.Repository.Configuration.Pattern;

import android.content.Context;
import android.os.CountDownTimer;

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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern.InitiatePatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern.InitiatePatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.Pattern.PatternVerificationService;
import com.example.cidaasv2.Service.Scanned.ScannedRequestEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import androidx.annotation.NonNull;
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

            if(codeChallenge=="" || codeVerifier=="" || codeChallenge==null || codeVerifier==null) {
                //Generate Challenge
                generateChallenge();
             }
            Cidaas.instanceId="";

            AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>()
                    {
                        @Override
                        public void success(final AccessTokenEntity accessTokenresult) {

                          setupPattern(baseurl,accessTokenresult.getAccess_token(),patternString,setupPatternMFARequestEntity,enrollresult);
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


    private void setupPattern(final String baseurl, final String accessToken, final String patternString,
                              final SetupPatternMFARequestEntity setupPatternMFARequestEntity, final Result<EnrollPatternMFAResponseEntity> enrollResult)
    {
        try
        {
            if (baseurl != null && !baseurl.equals("") && accessToken != null && !accessToken.equals("") &&
                    setupPatternMFARequestEntity.getClient_id()!=null && !setupPatternMFARequestEntity.getClient_id().equals(""))
            {
                //Done Service call

                PatternVerificationService.getShared(context).setupPattern(baseurl, accessToken,
                        setupPatternMFARequestEntity,null,new Result<SetupPatternMFAResponseEntity>() {
                            @Override
                            public void success(final SetupPatternMFAResponseEntity setupserviceresult) {

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

                                            SetupPatternMFARequestEntity setupPatternMFARequestEntity1 = new SetupPatternMFARequestEntity();
                                            setupPatternMFARequestEntity1.setUsage_pass(instceID);
                                            // call Scanned Service
                                            PatternVerificationService.getShared(context).setupPattern(baseurl, accessToken,
                                                    setupPatternMFARequestEntity1, null, new Result<SetupPatternMFAResponseEntity>() {
                                                        @Override
                                                        public void success(final SetupPatternMFAResponseEntity setupPatternresult) {
                                                            DBHelper.getShared().setUserDeviceId(setupPatternresult.getData().getUdi(), baseurl);

                                                            //Entity For Pattern
                                                            EnrollPatternMFARequestEntity enrollPatternMFARequestEntity = new EnrollPatternMFARequestEntity();
                                                            enrollPatternMFARequestEntity.setVerifierPassword(patternString);
                                                            enrollPatternMFARequestEntity.setStatusId(setupPatternresult.getData().getSt());
                                                            enrollPatternMFARequestEntity.setUserDeviceId(setupPatternresult.getData().getUdi());
                                                            enrollPatternMFARequestEntity.setClientId(setupPatternMFARequestEntity.getClient_id());


                                                            enrollPattern(baseurl,accessToken,enrollPatternMFARequestEntity,enrollResult);


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
            enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                    "Pattern Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }



    public void scannedWithPattern(final String baseurl,  String statusId, String clientId, final Result<ScannedResponseEntity> scannedResult)
    {
        try
        {
            if (baseurl != null && !baseurl.equals("")  && statusId!=null && !statusId.equals("") && clientId!=null && !clientId.equals("")) {

                final ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
                scannedRequestEntity.setStatusId(statusId);
                scannedRequestEntity.setClient_id(clientId);


                PatternVerificationService.getShared(context).scannedPattern(baseurl,  scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
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

                                    PatternVerificationService.getShared(context).scannedPattern(baseurl,  scannedRequestEntity, null, new Result<ScannedResponseEntity>() {

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
                scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE,
                        "BaseURL or ClientId or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }

        }
        catch (Exception e)
        {
            scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE,
                    "Pattern Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }



    public void enrollPattern(@NonNull final String baseurl, @NonNull final String accessToken,
                              @NonNull EnrollPatternMFARequestEntity enrollPatternMFARequestEntity, final Result<EnrollPatternMFAResponseEntity> enrollResult)
    {
        try
        {

            if(baseurl!=null && !baseurl.equals("") && accessToken!=null && !accessToken.equals("")) {

                if (enrollPatternMFARequestEntity.getUserDeviceId() != null && !enrollPatternMFARequestEntity.getUserDeviceId().equals("") &&
                        enrollPatternMFARequestEntity.getStatusId() != null && !enrollPatternMFARequestEntity.getStatusId().equals("") &&
                        enrollPatternMFARequestEntity.getClientId() != null && !enrollPatternMFARequestEntity.getClientId().equals("") &&
                        enrollPatternMFARequestEntity.getVerifierPassword() != null && !enrollPatternMFARequestEntity.getVerifierPassword().equals("")) {

                    // call Enroll Service
                    PatternVerificationService.getShared(context).enrollPattern(baseurl, accessToken, enrollPatternMFARequestEntity,
                            null, new Result<EnrollPatternMFAResponseEntity>() {

                                @Override
                                public void success(final EnrollPatternMFAResponseEntity serviceresult) {

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
                                                EnrollPatternMFARequestEntity enrollPatternMFARequestEntity = new EnrollPatternMFARequestEntity();
                                                enrollPatternMFARequestEntity.setUsage_pass(instceID);

                                                // call Enroll Service
                                                PatternVerificationService.getShared(context).enrollPattern(baseurl, accessToken, enrollPatternMFARequestEntity,
                                                        null, new Result<EnrollPatternMFAResponseEntity>() {
                                                            @Override
                                                            public void success(EnrollPatternMFAResponseEntity serviceresult) {
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
                    enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                            "UserdeviceId or Verifierpassword or StatusID or Client id must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                }
            }
            else
            {
                enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                        "BaseURL or accessToken must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }


        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                    "Pattern Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }


        //Login with Pattern
    public void LoginWithPattern(@NonNull final String patternString, @NonNull final String baseurl, @NonNull final String clientId,
                                 @NonNull final String trackId, @NonNull final String requestId,
                                 @NonNull final InitiatePatternMFARequestEntity initiatePatternMFARequestEntity,
                                 final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(codeChallenge.equals("") && codeVerifier.equals("")) {
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
                PatternVerificationService.getShared(context).initiatePattern(baseurl, initiatePatternMFARequestEntity,null,
                        new Result<InitiatePatternMFAResponseEntity>() {

                            @Override
                            public void success(final InitiatePatternMFAResponseEntity serviceresult) {

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
                                        if(instceID!=null && !instceID.equals("")) {

                                            //Todo call initiate
                                            final InitiatePatternMFARequestEntity initiatePatternMFARequestEntityWithUsagePass=new InitiatePatternMFARequestEntity();
                                            initiatePatternMFARequestEntityWithUsagePass.setUsagePass(instceID);

                                            final String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);

                                            PatternVerificationService.getShared(context).initiatePattern(baseurl,  initiatePatternMFARequestEntityWithUsagePass,null,
                                                    new Result<InitiatePatternMFAResponseEntity>() {

                                                        @Override
                                                        public void success(InitiatePatternMFAResponseEntity result) {
                                                            if (patternString != null && !patternString.equals("") && result.getData().getStatusId() != null &&
                                                                    !result .getData().getStatusId().equals("")) {


                                                                AuthenticatePatternRequestEntity authenticatePatternRequestEntity = new AuthenticatePatternRequestEntity();
                                                                authenticatePatternRequestEntity.setUserDeviceId(userDeviceId);
                                                                authenticatePatternRequestEntity.setStatusId(result.getData().getStatusId());
                                                                authenticatePatternRequestEntity.setVerifierPassword(patternString);
                                                                authenticatePatternRequestEntity.setClient_id(initiatePatternMFARequestEntity.getClient_id());


                                                                authenticatePattern(baseurl, authenticatePatternRequestEntity, new Result<AuthenticatePatternResponseEntity>() {
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


    //Authenticate Pattern

    public void authenticatePattern(final String baseurl, final AuthenticatePatternRequestEntity authenticatePatternRequestEntity, final Result<AuthenticatePatternResponseEntity> authResult)
    {
      try
      {
          PatternVerificationService.getShared(context).authenticatePattern(baseurl, authenticatePatternRequestEntity,null, new Result<AuthenticatePatternResponseEntity>() {
              @Override
              public void success(final AuthenticatePatternResponseEntity serviceresult) {


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
                              AuthenticatePatternRequestEntity authenticatePatternRequestEntity=new AuthenticatePatternRequestEntity();
                              authenticatePatternRequestEntity.setUsage_pass(instceID);

                              PatternVerificationService.getShared(context).authenticatePattern(baseurl, authenticatePatternRequestEntity,null, new Result<AuthenticatePatternResponseEntity>() {
                                  @Override
                                  public void success(AuthenticatePatternResponseEntity result) {
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
          authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,
                  "Pattern Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
      }
    }


}
