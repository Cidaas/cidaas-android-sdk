package com.example.cidaasv2.Controller.Repository.Configuration.Pattern;

import android.content.Context;
import android.os.CountDownTimer;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Controller.Repository.ResumeLogin.ResumeLogin;
import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
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

import java.util.Dictionary;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class PatternConfigurationController {

    //Local variables


    private Context context;
    String usagePassFromService="";
    String logoURLlocal="https://cdn.shortpixel.ai/client/q_glossy,ret_img/https://www.cidaas.com/wp-content/uploads/2018/02/logo.png";


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
    public void configurePattern( @NonNull final String patternString,@NonNull final String sub,@NonNull final String logoURL,
                                 @NonNull final Result<EnrollPatternMFAResponseEntity> enrollresult)
    {
        try{

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    final String baseurl=result.get("DomainURL");

                    if(sub != null && !sub.equals("") && logoURL != null && !logoURL.equals("") && patternString != null && !patternString.equals(""))
                    {
                        if(!logoURL.equals("") && logoURL!=null) {
                            logoURLlocal=logoURL;
                        }
                    }


                    final SetupPatternMFARequestEntity setupPatternMFARequestEntity = new SetupPatternMFARequestEntity();
                    setupPatternMFARequestEntity.setClient_id(result.get("ClientId"));
                    setupPatternMFARequestEntity.setLogoUrl(logoURLlocal);

                    if(codeChallenge.equals("") || codeVerifier.equals("") || codeChallenge==null || codeVerifier==null) {
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

                @Override
                public void failure(WebAuthError error) {
                    enrollresult.failure(error);
                }
            });

            }
        catch (Exception e)
        {
            enrollresult.failure(WebAuthError.getShared(context).serviceException("Exception :PatternConfigurationController :configurePattern()",WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,e.getMessage()));
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
            enrollResult.failure(WebAuthError.getShared(context).serviceException("Exception :PatternConfigurationController :setupPattern()",WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,e.getMessage()));
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
                          enrollPatternMFARequestEntity.setClient_id(setupPatternMFARequestEntity.getClient_id());


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
          enrollResult.failure(WebAuthError.getShared(context).serviceException("Exception :PatternConfigurationController :setupAfterDeviceVerification()",WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,e.getMessage()));
      }
    }

    public void scannedWithPattern(final String statusId, final Result<ScannedResponseEntity> scannedResult)
    {
        try
        {
            if (statusId!=null && !statusId.equals("")) {

                CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> loginPropertiesresult) {
                        final String baseurl = loginPropertiesresult.get("DomainURL");
                        String clientId = loginPropertiesresult.get("ClientId");

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
                            }
                        });
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        scannedResult.failure(error);
                    }
                });


            }
            else {
                scannedResult.failure(WebAuthError.getShared(context).propertyMissingException("StatusID must not be empty"));
            }

        }
        catch (Exception e)
        {
            scannedResult.failure(WebAuthError.getShared(context).serviceException("Exception :PatternConfigurationController :scannedWithPattern()",WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE,e.getMessage()));
        }
    }


    public void enrollPattern(@NonNull final String patternString, @NonNull final String sub,@NonNull final String statusId,
                              final Result<EnrollPatternMFAResponseEntity> enrollResult)
    {
        try
        {
          CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> result) {

                        final String baseurl = result.get("DomainURL");
                        final String clientId = result.get("ClientId");

                        String userDeviceId = DBHelper.getShared().getUserDeviceId(baseurl);

                        if (patternString != null && !patternString.equals("") && sub != null && !sub.equals("") && statusId != null
                                && !statusId.equals("") && !userDeviceId.equals("") && userDeviceId!=null) {


                            final EnrollPatternMFARequestEntity enrollPatternMFARequestEntity = new EnrollPatternMFARequestEntity();
                            enrollPatternMFARequestEntity.setVerifierPassword(patternString);
                            enrollPatternMFARequestEntity.setStatusId(statusId);
                            enrollPatternMFARequestEntity.setUserDeviceId(userDeviceId);
                            enrollPatternMFARequestEntity.setClient_id(clientId);


                            AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                                @Override
                                public void success(AccessTokenEntity result) {
                                    enrollPattern(baseurl, result.getAccess_token(),enrollPatternMFARequestEntity,enrollResult );
                                }

                                @Override
                                public void failure(WebAuthError error) {
                                    enrollResult.failure(error);
                                }
                            });

                        } else {
                            enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                                    "UserdeviceId or Pattern String or StatusID or Client id must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                        }
                    }

                    @Override
                    public void failure(WebAuthError error) {
                     enrollResult.failure(error);
                    }
                });

        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).serviceException("Exception :PatternConfigurationController :enrollPattern()",WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,e.getMessage()));
        }
    }



    public void enrollPattern(@NonNull final String baseurl, @NonNull final String accessToken,
                              @NonNull EnrollPatternMFARequestEntity enrollPatternMFARequestEntity, final Result<EnrollPatternMFAResponseEntity> enrollResult)
    {
        try
        {

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

        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).serviceException("Exception :PatternConfigurationController :enrollPattern()",WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,e.getMessage()));
        }
    }


        //Login with Pattern
    public void LoginWithPattern(@NonNull final String patternString, final PasswordlessEntity passwordlessEntity,
                                 final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{


            if(codeChallenge.equals("") && codeVerifier.equals("")) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.usagePass ="";

            if(checkNull(patternString,passwordlessEntity,loginresult)) {

                PatternConfigrationServiceCall(patternString,passwordlessEntity,loginresult);

            }
            else
            {
                return;
            }

        }
        catch (Exception e)
        {
            loginresult.failure(WebAuthError.getShared(context).serviceException("Exception :PatternConfigurationController :LoginWithPattern()",WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,e.getMessage()));
        }
    }


    private boolean checkNull(String patternString,PasswordlessEntity passwordlessEntity,Result<LoginCredentialsResponseEntity> loginresult) {
        if (((passwordlessEntity.getSub() == null || passwordlessEntity.getSub().equals("")) &&
                (passwordlessEntity.getEmail() == null || passwordlessEntity.getEmail().equals("")) &&
                (passwordlessEntity.getMobile() == null || passwordlessEntity.getMobile().equals("")))) {

            String errorMessage = "sub or email or mobile number must not be empty";
            loginresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage));
            return false;
        }


        if (patternString == null && patternString.equals("") && passwordlessEntity.getRequestId() != null &&
                !passwordlessEntity.getRequestId().equals("")) {

            String errorMessage = "PatternString or requestId must not be empty";
            loginresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage));
            return false;
        }


        if (passwordlessEntity.getUsageType().equals(UsageType.MFA)) {
                if (passwordlessEntity.getTrackId() == null || passwordlessEntity.getTrackId() == "") {
                    String errorMessage = "trackId must not be empty";

                    loginresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage));
                    return false;
                }
            }
        return true;
    }


    private InitiatePatternMFARequestEntity  getInitiatePatternEntity(final PasswordlessEntity passwordlessEntity,String clientId,String userDeviceId)
    {
      try
      {

          InitiatePatternMFARequestEntity initiatePatternMFARequestEntity = new InitiatePatternMFARequestEntity();


          initiatePatternMFARequestEntity.setEmail(passwordlessEntity.getEmail());
          initiatePatternMFARequestEntity.setMobile(passwordlessEntity.getMobile());


          if (passwordlessEntity.getUsageType() != null && !passwordlessEntity.getUsageType().equals(""))
          {
              initiatePatternMFARequestEntity.setUsageType(passwordlessEntity.getUsageType());
          }
          if(passwordlessEntity.getSub() != null && !passwordlessEntity.getSub().equals(""))
          {
              initiatePatternMFARequestEntity.setSub(passwordlessEntity.getSub());
          }
          if (passwordlessEntity.getEmail() != null && !passwordlessEntity.getEmail().equals(""))
          {
              initiatePatternMFARequestEntity.setEmail(passwordlessEntity.getEmail());
          }
          if(passwordlessEntity.getMobile() != null && !passwordlessEntity.getMobile().equals(""))
          {
              initiatePatternMFARequestEntity.setMobile(passwordlessEntity.getSub());
          }

          initiatePatternMFARequestEntity.setUserDeviceId(userDeviceId);
          initiatePatternMFARequestEntity.setClient_id(clientId);

         return initiatePatternMFARequestEntity;

      }
      catch (Exception e)
      {
          return null;
//todo
      }

    }

    private void PatternConfigrationServiceCall(final String patternString, final PasswordlessEntity passwordlessEntity,final Result<LoginCredentialsResponseEntity> loginresult) {

      try {


          CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
              @Override
              public void success(Dictionary<String, String> result) {
                  final String baseurl = result.get("DomainURL");
                  final String clientId = result.get("ClientId");
                  String userDeviceId = DBHelper.getShared().getUserDeviceId(baseurl);

                  InitiatePatternMFARequestEntity initiatePatternMFARequestEntity = null;

                  if(userDeviceId!=null && !userDeviceId.equals(""))
                  {
                   initiatePatternMFARequestEntity=getInitiatePatternEntity(passwordlessEntity,clientId,userDeviceId);
                  }



                  PatternVerificationService.getShared(context).initiatePattern(baseurl, initiatePatternMFARequestEntity, null,
                          new Result<InitiatePatternMFAResponseEntity>() {

                              @Override
                              public void success(final InitiatePatternMFAResponseEntity serviceresult) {

                                  Cidaas.usagePass = "";
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

                                              InititePatternAfterDeviceVerification(usagePassFromService, patternString, passwordlessEntity, loginresult);
                                          } else {
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

              @Override
              public void failure(WebAuthError error) {
//todo
                loginresult.failure(error);
              }
          });


      }
      catch (Exception e)
      {
          loginresult.failure(WebAuthError.getShared(context).serviceException("Exception :PatternConfigurationController :PatternConfigrationServiceCall()",WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,e.getMessage()));
      }
    }

    //Initiate Patter After Device Verification
    private void InititePatternAfterDeviceVerification(final String usagePassFromService, @NonNull final String patternString, final PasswordlessEntity passwordlessEntity,
                                                       final Result<LoginCredentialsResponseEntity> loginresult) {
        try {
            //Todo call initiate

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    final String baseurl = result.get("DomainURL");
                    final String clientId = result.get("ClientId");

                    final InitiatePatternMFARequestEntity initiatePatternMFARequestEntityWithUsagePass = new InitiatePatternMFARequestEntity();
                    initiatePatternMFARequestEntityWithUsagePass.setUsage_pass(usagePassFromService);

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
                                                        AuthenticationType.pattern, passwordlessEntity, clientId, baseurl, loginresult);
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

                @Override
                public void failure(WebAuthError error) {
               loginresult.failure(error);
                }
            });



        }
        catch (Exception e)
        {
            loginresult.failure(WebAuthError.getShared(context).serviceException("Exception :PatternConfigurationController :InititePatternAfterDeviceVerification()",WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,e.getMessage()));

        }
    }




    public void authenticatePattern(final String patternString, final String statusId, final Result<AuthenticatePatternResponseEntity> loginresult)
    {
        CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {

                AuthenticatePatternRequestEntity authenticatePatternRequestEntity=new AuthenticatePatternRequestEntity();
                authenticatePatternRequestEntity.setStatusId(statusId);
                authenticatePatternRequestEntity.setVerifierPassword(patternString);
                authenticatePatternRequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(result.get("DomainURL")));
                authenticatePatternRequestEntity.setClient_id(result.get("ClientId"));

                authenticatePattern(result.get("DomainURL"),authenticatePatternRequestEntity,loginresult);
            }

            @Override
            public void failure(WebAuthError error) {
             loginresult.failure(error);
            }
        });
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
          authResult.failure(WebAuthError.getShared(context).serviceException("Exception :PatternConfigurationController :authenticatePattern()",WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,e.getMessage()));
      }
    }
}
