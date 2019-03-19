package com.example.cidaasv2.Controller.Repository.Configuration.Pattern;

import android.content.Context;
import android.os.CountDownTimer;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Controller.Repository.ResumeLogin.ResumeLogin;
import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
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


    private Context context;
    String usagePassFromService="";

    public static PatternConfigurationController shared;

    public PatternConfigurationController(Context contextFromCidaas) {

        context=contextFromCidaas;

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
            Cidaas.usagePass ="";

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
            LogFile.getShared(context).addRecordToLog("Configure Pattern Exception:"+ e.getMessage()+WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE);
            enrollresult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE));
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

                                Cidaas.usagePass ="";
                                usagePassFromService="";

                                new CountDownTimer(5000, 500) {

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

                                            // Check for Pattern Service
                                             setupAfterDeviceVerification(usagePassFromService,baseurl,accessToken,patternString,setupPatternMFARequestEntity,enrollResult);
                                        }

                                        else {
                                            enrollResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException());
                                            LogFile.getShared(context).addRecordToLog("Setup Pattern Exception:Device Failed to verify"+ WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE);
                                        }
                                    }

                                }.start();

                            }
                            @Override
                            public void failure(WebAuthError error) {
                                enrollResult.failure(error);
                                LogFile.getShared(context).addRecordToLog("Setup Pattern Error:"+error.getErrorMessage()+ WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE);
                            }
                        });
            }
            else
            {

                enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("Base url ,Access Token Or clientId Must not be null"));
                Timber.e("Setup Pattern Exception:Base url Access Token Or clientId Must not be null"+ WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE);
                LogFile.getShared(context).addRecordToLog("Setup Pattern Exception:Base url Access Token Or clientId Must not be null"+ WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE);
            }
        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Setup Pattern Exception:"+ e.getMessage()+WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE);
            enrollResult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE));
        }
    }


    private void setupAfterDeviceVerification(String usagePassFromService,final String baseurl, final String accessToken, final String patternString,
                                              final SetupPatternMFARequestEntity setupPatternMFARequestEntity, final Result<EnrollPatternMFAResponseEntity> enrollResult) {
      try {
          SetupPatternMFARequestEntity setupPatternMFARequestEntityWithUsagePass = new SetupPatternMFARequestEntity();
          setupPatternMFARequestEntityWithUsagePass.setUsage_pass(usagePassFromService);
          // call Scanned Service
          PatternVerificationService.getShared(context).setupPattern(baseurl, accessToken,
                  setupPatternMFARequestEntityWithUsagePass, null, new Result<SetupPatternMFAResponseEntity>() {
                      @Override
                      public void success(final SetupPatternMFAResponseEntity setupPatternresult) {
                          DBHelper.getShared().setUserDeviceId(setupPatternresult.getData().getUdi(), baseurl);

                          //Entity For Pattern
                          EnrollPatternMFARequestEntity enrollPatternMFARequestEntity = new EnrollPatternMFARequestEntity();
                          enrollPatternMFARequestEntity.setVerifierPassword(patternString);
                          enrollPatternMFARequestEntity.setStatusId(setupPatternresult.getData().getSt());
                          enrollPatternMFARequestEntity.setUserDeviceId(setupPatternresult.getData().getUdi());
                          enrollPatternMFARequestEntity.setClientId(setupPatternMFARequestEntity.getClient_id());


                          enrollPattern(baseurl, accessToken, enrollPatternMFARequestEntity, enrollResult);


                      }

                      @Override
                      public void failure(WebAuthError error) {

                          enrollResult.failure(error);
                          LogFile.getShared(context).addRecordToLog("Setup After Device Pattern Exception:"+ error.getMessage()+WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE);
                      }
                  });
      }
      catch (Exception e)
      {
          LogFile.getShared(context).addRecordToLog("Setup After Device Pattern Exception:"+ e.getMessage()+WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE);
          enrollResult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE));
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

                                    ScannedRequestEntity scannedRequestEntityWithUsagePass= new ScannedRequestEntity();
                                    scannedRequestEntityWithUsagePass.setUsage_pass(usagePassFromService);

                                    PatternVerificationService.getShared(context).scannedPattern(baseurl,  scannedRequestEntityWithUsagePass, null, scannedResult);
                                }
                                else
                                {
                                    scannedResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException());
                                    LogFile.getShared(context).addRecordToLog("Scanned Pattern Exception:Device Failed to verify"+ WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE);
                                }
                            }
                        }.start();

                    }

                    @Override
                    public void failure(WebAuthError error) {
                        scannedResult.failure(error);
                        LogFile.getShared(context).addRecordToLog("Scanned Pattern Error:"+error.getErrorMessage()+ WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE);
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
            scannedResult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE));
            LogFile.getShared(context).addRecordToLog("Scanned Pattern Exception:"+e.getMessage()+ WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE);

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
                                                EnrollPatternMFARequestEntity enrollPatternMFARequestEntity = new EnrollPatternMFARequestEntity();
                                                enrollPatternMFARequestEntity.setUsage_pass(usagePassFromService);

                                                // call Enroll Service
                                                PatternVerificationService.getShared(context).enrollPattern(baseurl, accessToken, enrollPatternMFARequestEntity,
                                                        null, enrollResult);
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
                            "UserdeviceId or Verifier password or StatusID or Client id must not be empty", HttpStatusCode.EXPECTATION_FAILED));
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
            enrollResult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE));
            LogFile.getShared(context).addRecordToLog("Enroll Pattern Exception:"+e.getMessage()+ WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE);
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
            Cidaas.usagePass ="";
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

                                            InititePatternAfterDeviceVerification(usagePassFromService,patternString,baseurl,clientId,trackId,requestId,initiatePatternMFARequestEntity.getUsageType(),loginresult);
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

                loginresult.failure(WebAuthError.getShared(context).propertyMissingException("UserdeviceId or baseurl must not be null"));
            }
        }
        catch (Exception e)
        {
            loginresult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE));
            LogFile.getShared(context).addRecordToLog("Initiate Pattern Exception:"+e.getMessage()+ WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE);
            Timber.e(e.getMessage());
        }
    }


    //Initiate Patter After Device Verification
    private void InititePatternAfterDeviceVerification(String usagePassFromService, @NonNull final String patternString, @NonNull final String baseurl, @NonNull final String clientId,
                                                       @NonNull final String trackId, @NonNull final String requestId, final String usageType,
                                                       final Result<LoginCredentialsResponseEntity> loginresult) {
        try {
            //Todo call initiate
            final InitiatePatternMFARequestEntity initiatePatternMFARequestEntityWithUsagePass = new InitiatePatternMFARequestEntity();
            initiatePatternMFARequestEntityWithUsagePass.setUsagePass(usagePassFromService);

            final String userDeviceId = DBHelper.getShared().getUserDeviceId(baseurl);

            PatternVerificationService.getShared(context).initiatePattern(baseurl, initiatePatternMFARequestEntityWithUsagePass, null,
                    new Result<InitiatePatternMFAResponseEntity>() {

                        @Override
                        public void success(InitiatePatternMFAResponseEntity result) {
                            if (patternString != null && !patternString.equals("") && result.getData().getStatusId() != null &&
                                    !result.getData().getStatusId().equals("")) {


                                AuthenticatePatternRequestEntity authenticatePatternRequestEntity = new AuthenticatePatternRequestEntity();
                                authenticatePatternRequestEntity.setUserDeviceId(userDeviceId);
                                authenticatePatternRequestEntity.setStatusId(result.getData().getStatusId());
                                authenticatePatternRequestEntity.setVerifierPassword(patternString);
                                authenticatePatternRequestEntity.setClient_id(clientId);


                                authenticatePattern(baseurl, authenticatePatternRequestEntity, new Result<AuthenticatePatternResponseEntity>() {
                                    @Override
                                    public void success(AuthenticatePatternResponseEntity result) {
                                        //Call Resume with Login Service
                                        ResumeLogin.getShared(context).resumeLoginAfterSuccessfullAuthentication(result.getData().getSub(), result.getData().getTrackingCode(),
                                                AuthenticationType.pattern, usageType, clientId, requestId, trackId, baseurl, loginresult);
                                    }

                                    @Override
                                    public void failure(WebAuthError error) {
                                        loginresult.failure(error);
                                    }
                                });
                            } else {
                                String errorMessage = "Status Id or Pattern Must not be null";
                                loginresult.failure(WebAuthError.getShared(context).customException(417, errorMessage, HttpStatusCode.EXPECTATION_FAILED));

                            }
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            loginresult.failure(error);
                        }
                    });
        }
        catch (Exception e)
        {
            loginresult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE));
            LogFile.getShared(context).addRecordToLog("Initiate Pattern Exception:"+e.getMessage()+ WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE);
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
                              AuthenticatePatternRequestEntity authenticatePatternRequestEntity=new AuthenticatePatternRequestEntity();
                              authenticatePatternRequestEntity.setUsage_pass(usagePassFromService);

                              PatternVerificationService.getShared(context).authenticatePattern(baseurl, authenticatePatternRequestEntity,null, authResult);
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
          authResult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE));
          LogFile.getShared(context).addRecordToLog("Authenticate Pattern Exception:"+e.getMessage()+ WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE);
          Timber.e(e.getMessage());
      }
    }
}
