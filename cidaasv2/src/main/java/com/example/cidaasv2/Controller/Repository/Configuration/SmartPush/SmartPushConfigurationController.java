package com.example.cidaasv2.Controller.Repository.Configuration.SmartPush;

import android.content.Context;
import android.os.CountDownTimer;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
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
import com.example.cidaasv2.Service.Repository.Verification.SmartPush.SmartPushVerificationService;
import com.example.cidaasv2.Service.Scanned.ScannedRequestEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import java.util.Dictionary;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

import timber.log.Timber;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class SmartPushConfigurationController {


    private String verificationType;
    private Context context;
    public static String logoURLlocal="https://cdn.shortpixel.ai/client/q_glossy,ret_img/https://www.cidaas.com/wp-content/uploads/2018/02/logo.png";


    public static SmartPushConfigurationController shared;

    public SmartPushConfigurationController(Context contextFromCidaas) {

        verificationType="";
        context=contextFromCidaas;

        //Todo setValue for authenticationType

    }

    String codeVerifier="", codeChallenge="";
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




    public void configureSmartPush(final String sub,@NonNull final String logoURL, final Result<EnrollSmartPushMFAResponseEntity> enrollresult)
    {
        try {
            LogFile.getShared(context).addInfoLog("Exception :PatternConfigurationController :configurePattern()","Sub"+sub);


            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    final String clientId= result.get("ClientId");

                    if (sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                        final String finalBaseurl = baseurl;

                        //String logoUrl = "https://docs.cidaas.de/assets/logoss.png";

                        if(!logoURL.equals("") && logoURL!=null) {
                            logoURLlocal=logoURL;
                        }

                        SetupSmartPushMFARequestEntity setupSmartPushMFARequestEntity = new SetupSmartPushMFARequestEntity();
                        setupSmartPushMFARequestEntity.setClient_id(result.get("ClientId"));
                        setupSmartPushMFARequestEntity.setLogoUrl(logoURLlocal);

                        configureSmartPush(sub, finalBaseurl, setupSmartPushMFARequestEntity, enrollresult);

                    } else {
                        String errorMessage = "Sub or SmartPush cannot be null";
                        enrollresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING, errorMessage,
                                "Error :SmartPushConfigurationController :configureSmartPush()"));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollresult.failure(error);
                }
            });

        } catch (Exception e) {
            enrollresult.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushConfigurationController :configureSmartPush()",WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE, e.getMessage()));
        }

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
            LogFile.getShared(context).addInfoLog("Exception :PatternConfigurationController :configurePattern()","Baseurl"+baseurl
                    +"sub"+sub);


            if(codeChallenge=="" || codeVerifier=="" || codeChallenge==null || codeVerifier==null) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.usagePass ="";

            AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>()
            {
                @Override
                public void success(final AccessTokenEntity accessTokenresult) {

                    setupSmartPush(baseurl,accessTokenresult.getAccess_token(),setupSmartPushMFARequestEntity,enrollresult);
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollresult.failure(error);
                }

            });

        }
        catch (Exception e)
        {
            enrollresult.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushConfigurationController :configureSmartPush()",WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,e.getMessage()));

        }
    }


    //Scanned push


    public void scannedWithSmartPush(final String statusId, final Result<ScannedResponseEntity> scannedResult)
    {
        try
        {
            LogFile.getShared(context).addInfoLog("Info :PatternConfigurationController :scannedWithSmartPush()","StatusId:-"+statusId);
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    String clientId = loginPropertiesResult.get("ClientId");

                    if ( statusId!=null && !statusId.equals("")) {

                        final ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
                        scannedRequestEntity.setStatusId(statusId);
                        scannedRequestEntity.setClient_id(clientId);

                        SmartpushScannedCall(baseurl, scannedRequestEntity,scannedResult);
                    }
                    else {
                        scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_SMARTPUSH_MFA_FAILURE,
                                "StatusID must not be empty","Error :SmartPushConfigurationController :scannedWithSmartPush()"));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    scannedResult.failure(error);
                }
            });

        }
        catch (Exception e)
        {
            scannedResult.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushConfigurationController :scannedWithSmartPush()",WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,e.getMessage()));
        }
    }

    private void SmartpushScannedCall(final String baseurl, ScannedRequestEntity scannedRequestEntity,final Result<ScannedResponseEntity> scannedResult) {
      try {
          SmartPushVerificationService.getShared(context).scannedSmartPush(baseurl, scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
              @Override
              public void success(ScannedResponseEntity result) {
                  Cidaas.usagePass = "";


                  new CountDownTimer(5000, 500) {
                      String instceID = "";

                      public void onTick(long millisUntilFinished) {
                          instceID = Cidaas.usagePass;

                          Timber.e("");
                          if (instceID != null && !instceID.equals("")) {
                              this.cancel();
                              onFinish();
                          }

                      }

                      public void onFinish() {

                          if (instceID != null && !instceID.equals("")) {

                              ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
                              scannedRequestEntity.setUsage_pass(instceID);

                              SmartPushVerificationService.getShared(context).scannedSmartPush(baseurl, scannedRequestEntity, null, scannedResult);
                          } else {
                              scannedResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException("Error :SmartPushConfigurationController :SmartpushScannedCall()"));
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
      catch (Exception e){
          scannedResult.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushConfigurationController :SmartpushScannedCall()",WebAuthErrorCode.SCANNED_SMARTPUSH_MFA_FAILURE,e.getMessage()));
      }
    }


    private void setupSmartPush(final String baseurl, final String accessToken,
                                final SetupSmartPushMFARequestEntity setupSmartPushMFARequestEntity, final Result<EnrollSmartPushMFAResponseEntity> enrollResult)
    {
        try
        {
            //Log
            LogFile.getShared(context).addInfoLog("Info :PatternConfigurationController :setupPattern()",
                    "AccessToken:-"+accessToken+"Baseurl:-"+ baseurl);
            if (baseurl != null && !baseurl.equals("") && accessToken != null && !accessToken.equals("") &&
                    setupSmartPushMFARequestEntity.getClient_id()!=null && !setupSmartPushMFARequestEntity.getClient_id().equals(""))
            {
                //Done Service call

                SmartPushVerificationService.getShared(context).setupSmartPush(baseurl, accessToken,
                        setupSmartPushMFARequestEntity,null,new Result<SetupSmartPushMFAResponseEntity>() {
                            @Override
                            public void success(final SetupSmartPushMFAResponseEntity setupserviceresult) {

                                Cidaas.usagePass ="";

                                new CountDownTimer(5000, 500) {
                                    String usage_pass ="";
                                    public void onTick(long millisUntilFinished) {
                                        usage_pass = Cidaas.usagePass;

                                        Timber.e("");
                                        if(usage_pass !=null && !usage_pass.equals(""))
                                        {
                                            this.cancel();
                                            onFinish();
                                        }

                                    }
                                    public void onFinish() {
                                        if(usage_pass !=null && !usage_pass.equals("") ) {
                                            //Log
                                            LogFile.getShared(context).addInfoLog("Info :PatternConfigurationController :setupAfterDeviceVerification()",
                                                    "UsagePass:-"+ usage_pass +"Baseurl:-"+ baseurl);

                                            final SetupSmartPushMFARequestEntity setupSmartPushMFARequestEntityWithUsagePass = new SetupSmartPushMFARequestEntity();
                                            setupSmartPushMFARequestEntityWithUsagePass.setUsage_pass(usage_pass);
                                            // call Scanned Service
                                            SmartPushVerificationService.getShared(context).setupSmartPush(baseurl, accessToken,
                                                    setupSmartPushMFARequestEntityWithUsagePass, null, new Result<SetupSmartPushMFAResponseEntity>() {
                                                        @Override
                                                        public void success(final SetupSmartPushMFAResponseEntity result) {
                                                            DBHelper.getShared().setUserDeviceId(result.getData().getUdi(), baseurl);

                                                            //Entity For SmartPush
                                                            EnrollSmartPushMFARequestEntity enrollSmartPushMFARequestEntity = new EnrollSmartPushMFARequestEntity();
                                                            enrollSmartPushMFARequestEntity.setVerifierPassword(result.getData().getPrn());
                                                            enrollSmartPushMFARequestEntity.setStatusId(result.getData().getSt());
                                                            enrollSmartPushMFARequestEntity.setUserDeviceId(result.getData().getUdi());
                                                            enrollSmartPushMFARequestEntity.setClient_id(setupSmartPushMFARequestEntity.getClient_id());

                                                            enrollSmartPush(baseurl,accessToken,enrollSmartPushMFARequestEntity,enrollResult);


                                                        }

                                                        @Override
                                                        public void failure(WebAuthError error) {
                                                            enrollResult.failure(error);
                                                        }
                                                    });
                                        }

                                        else {
                                            enrollResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException( "Error :SmartPushConfigurationController :setupSmartPush()"));
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

                enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl accessToken or clientId must not be null",
                        "Error :SmartPushConfigurationController :setupSmartPush()"));
            }
        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushConfigurationController :setupSmartPush()",WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,e.getMessage()));
        }
    }



    public void enrollSmartPush(@NonNull final String randomNumber, @NonNull final String sub,@NonNull final String statusId,  final Result<EnrollSmartPushMFAResponseEntity> enrollResult)
    {
        try
        {
            LogFile.getShared(context).addInfoLog("Info :PatternConfigurationController :enrollPattern()", "RandomNumber:-"+randomNumber+"Sub:-"+sub+"statusId:-"+statusId);

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {

                    final String baseurl = result.get("DomainURL");
                    final String clientId= result.get("ClientId");

                    String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);

                    final EnrollSmartPushMFARequestEntity enrollSmartPushMFARequestEntity=new EnrollSmartPushMFARequestEntity();
                    enrollSmartPushMFARequestEntity.setVerifierPassword(randomNumber);
                    enrollSmartPushMFARequestEntity.setStatusId(statusId);
                    enrollSmartPushMFARequestEntity.setUserDeviceId(userDeviceId);
                    enrollSmartPushMFARequestEntity.setClient_id(clientId);

                    AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accessTokenResult) {
                           enrollSmartPush(baseurl,accessTokenResult.getAccess_token(),enrollSmartPushMFARequestEntity,enrollResult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            enrollResult.failure(error);
                        }
                    });



                }

                @Override
                public void failure(WebAuthError error) {
                    enrollResult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushConfigurationController :enrollSmartPush()",
                    WebAuthErrorCode.PROPERTY_MISSING,e.getMessage()));
        }
    }

    public void enrollSmartPush(@NonNull final String baseurl, @NonNull final String accessToken,
                                @NonNull EnrollSmartPushMFARequestEntity enrollSmartPushMFARequestEntity, final Result<EnrollSmartPushMFAResponseEntity> enrollResult)
    {
        try
        {
            //Log
            LogFile.getShared(context).addInfoLog("Info :Face configuration Controller :enrollSmartPush()",
                    " Baseurl:-"+baseurl+" AccessToken:-"+ accessToken+" ClientId:-"+enrollSmartPushMFARequestEntity.getClient_id()
                            +" StatusId:-"+enrollSmartPushMFARequestEntity.getStatusId()+" AccessToken:-"+accessToken
                            +" userDeviceId:-"+enrollSmartPushMFARequestEntity.getUserDeviceId()+" VerifierPass:-"+enrollSmartPushMFARequestEntity.getVerifierPassword());


            if(baseurl!=null && !baseurl.equals("") && accessToken!=null && !accessToken.equals("")) {

                if (isNotEmpty(enrollSmartPushMFARequestEntity.getUserDeviceId()) &&
                        isNotEmpty(enrollSmartPushMFARequestEntity.getStatusId()) &&
                        isNotEmpty(enrollSmartPushMFARequestEntity.getClient_id()) &&
                        isNotEmpty(enrollSmartPushMFARequestEntity.getVerifierPassword())) {

                    // call Enroll Service
                    SmartPushVerificationService.getShared(context).enrollSmartPush(baseurl, accessToken, enrollSmartPushMFARequestEntity,
                            null, new Result<EnrollSmartPushMFAResponseEntity>() {

                                @Override
                                public void success(final EnrollSmartPushMFAResponseEntity serviceresult) {

                                    Cidaas.usagePass = "";

                                    //Timer
                                    new CountDownTimer(5000, 500) {
                                        String instceID = "";

                                        public void onTick(long millisUntilFinished) {
                                            instceID = Cidaas.usagePass;

                                            Timber.e("");
                                            if (instceID != null && !instceID.equals("")) {
                                                this.cancel();
                                                onFinish();
                                            }

                                        }

                                        public void onFinish() {
                                            if (instceID != null && !instceID.equals("")) {

                                                //enroll
                                                EnrollSmartPushMFARequestEntity enrollSmartPushMFARequestEntity = new EnrollSmartPushMFARequestEntity();
                                                enrollSmartPushMFARequestEntity.setUsage_pass(instceID);

                                                // call Enroll Service
                                                SmartPushVerificationService.getShared(context).enrollSmartPush(baseurl, accessToken, enrollSmartPushMFARequestEntity,
                                                        null, new Result<EnrollSmartPushMFAResponseEntity>() {
                                                            @Override
                                                            public void success(EnrollSmartPushMFAResponseEntity serviceresult) {
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
                                                enrollResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException("Error :SmartPushConfigurationController :enrollSmartPush()"));
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
                    enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,
                            "UserdeviceId or Verifierpassword or clientId or StatusID must not be empty", "Error :SmartPushConfigurationController :enrollSmartPush()"));
                }
            }
            else
            {
                enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,
                        "BaseURL or accessToken must not be empty", "Error :SmartPushConfigurationController :enrollSmartPush()"));
            }


        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushConfigurationController :enrollSmartPush()",WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,e.getMessage()));

        }
    }



    public void LoginWithSmartPush(final PasswordlessEntity passwordlessEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
        try {
            LogFile.getShared(context).addInfoLog("Info :PatternConfigurationController :LoginWithPattern()",
                    "Info UsageType:-"+passwordlessEntity.getUsageType()+" Sub:- "+passwordlessEntity.getSub()+
                            " Email"+passwordlessEntity.getEmail()+ " Mobile"+passwordlessEntity.getMobile()+" RequestId:-"+passwordlessEntity.getRequestId()+
                            " TrackId:-"+passwordlessEntity.getTrackId());

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    String clientId = result.get("ClientId");
                    if (passwordlessEntity.getUsageType() != null && !passwordlessEntity.getUsageType().equals("") &&
                            passwordlessEntity.getRequestId() != null && !passwordlessEntity.getRequestId().equals("")) {

                        if (baseurl == null || baseurl.equals("") && clientId == null || clientId.equals("")) {
                            String errorMessage = "baseurl or clientId  must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage, "Error :SmartPushConfigurationController :LoginWithSmartPush()"));
                        }

                        if (((passwordlessEntity.getSub() == null || passwordlessEntity.getSub().equals("")) &&
                                (passwordlessEntity.getEmail() == null || passwordlessEntity.getEmail().equals("")) &&
                                (passwordlessEntity.getMobile() == null || passwordlessEntity.getMobile().equals("")))) {
                            String errorMessage = "sub or email or mobile number must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage, "Error :SmartPushConfigurationController :LoginWithSmartPush()"));
                        }

                        if (passwordlessEntity.getUsageType().equals(UsageType.MFA)) {
                            if (passwordlessEntity.getTrackId() == null || passwordlessEntity.getTrackId() == "") {
                                String errorMessage = "trackId must not be empty";

                                loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                        errorMessage, "Error :SmartPushConfigurationController :LoginWithSmartPush()"));
                                return;
                            }
                        }

                        InitiateSmartPushMFARequestEntity initiateSmartPushMFARequestEntity = new InitiateSmartPushMFARequestEntity();
                        initiateSmartPushMFARequestEntity.setSub(passwordlessEntity.getSub());
                        initiateSmartPushMFARequestEntity.setUsageType(passwordlessEntity.getUsageType());
                        initiateSmartPushMFARequestEntity.setEmail(passwordlessEntity.getEmail());
                        initiateSmartPushMFARequestEntity.setMobile(passwordlessEntity.getMobile());
                        initiateSmartPushMFARequestEntity.setClient_id(clientId);

                        //Todo check for email or sub or mobile

                        LoginWithSmartPush(baseurl,clientId,passwordlessEntity,initiateSmartPushMFARequestEntity,loginresult);


                    } else {
                        String errorMessage = "UsageType or SmartPushCode or requestId must not be empty";

                        loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,"Error :SmartPushConfigurationController :LoginWithSmartPush()"));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty",
                            "Error :SmartPushConfigurationController :LoginWithSmartPush()"));
                }
            });
        } catch (Exception e) {
            loginresult.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushConfigurationController :LoginWithSmartPush()",WebAuthErrorCode.AUTHENTICATE_SMARTPUSH_MFA_FAILURE,e.getMessage()));

        }
    }


    //Login with SmartPush
    public void LoginWithSmartPush(@NonNull final String baseurl, @NonNull final String clientId,
                                  @NonNull final PasswordlessEntity passwordlessEntity,
                                  @NonNull final InitiateSmartPushMFARequestEntity initiateSmartPushMFARequestEntity,
                                  final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{



            if(codeChallenge.equals("") && codeVerifier.equals("")) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.usagePass ="";
            if(initiateSmartPushMFARequestEntity.getUserDeviceId() != null && !initiateSmartPushMFARequestEntity.getUserDeviceId().equals(""))
            {
                //Do nothing
            }
            else
            {
                initiateSmartPushMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }
            initiateSmartPushMFARequestEntity.setClient_id(clientId);


            if (    initiateSmartPushMFARequestEntity.getUsageType() != null && !initiateSmartPushMFARequestEntity.getUsageType().equals("") &&
                    initiateSmartPushMFARequestEntity.getUserDeviceId() != null && !initiateSmartPushMFARequestEntity.getUserDeviceId().equals("") &&
                    baseurl != null && !baseurl.equals("")) {

                //Todo call initiate
                LogFile.getShared(context).addInfoLog("Info :PatternConfigurationController :InitiatePatternAfterDeviceVerification()",
                        "UsageType:-"+passwordlessEntity.getUsageType()+" Sub:- "+passwordlessEntity.getSub()+
                                " Email"+passwordlessEntity.getEmail()+ " Mobile"+passwordlessEntity.getMobile()+" RequestId:-"+passwordlessEntity.getRequestId()+
                                " TrackId:-"+passwordlessEntity.getTrackId());

                //Todo Service call
                SmartPushVerificationService.getShared(context).initiateSmartPush(baseurl,initiateSmartPushMFARequestEntity,null,
                        new Result<InitiateSmartPushMFAResponseEntity>() {

                            @Override
                            public void success(final InitiateSmartPushMFAResponseEntity serviceresult) {

                                Cidaas.usagePass ="";
                                new CountDownTimer(5000, 500) {
                                    String instceID="";
                                    public void onTick(long millisUntilFinished) {
                                        instceID= Cidaas.usagePass;

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
                                            final InitiateSmartPushMFARequestEntity initiateSmartPushMFARequestEntityWithUsagePass=new InitiateSmartPushMFARequestEntity();
                                            initiateSmartPushMFARequestEntityWithUsagePass.setUsage_pass(instceID);

                                            final String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);

                                            SmartPushVerificationService.getShared(context).initiateSmartPush(baseurl, initiateSmartPushMFARequestEntityWithUsagePass,null,
                                                    new Result<InitiateSmartPushMFAResponseEntity>() {

                                                        @Override
                                                        public void success(InitiateSmartPushMFAResponseEntity result) {
                                                            if (result.getData().getRandomNumber() != null && !result.getData().getRandomNumber().equals("") && result.getData().getStatusId() != null &&
                                                                    !result.getData().getStatusId().equals("")) {


                                                                AuthenticateSmartPushRequestEntity authenticateSmartPushRequestEntity = new AuthenticateSmartPushRequestEntity();
                                                                authenticateSmartPushRequestEntity.setUserDeviceId(userDeviceId);
                                                                authenticateSmartPushRequestEntity.setStatusId(result.getData().getStatusId());
                                                                authenticateSmartPushRequestEntity.setVerifierPassword(result.getData().getRandomNumber());
                                                                authenticateSmartPushRequestEntity.setClient_id(initiateSmartPushMFARequestEntity.getClient_id());



                                                                authenticateSmartPush(baseurl, authenticateSmartPushRequestEntity, new Result<AuthenticateSmartPushResponseEntity>() {
                                                                    @Override
                                                                    public void success(AuthenticateSmartPushResponseEntity result) {
                                                                        //Todo Call Resume with Login Service
                                                                        //  loginresult.success(result);
                                                                        //        Toast.makeText(context, "Sucess SmartPush", Toast.LENGTH_SHORT).show();

                                                                        LogFile.getShared(context).addSuccessLog("Success :Pattern configuration Controller :InitiatePatternAfterDeviceVerification()",
                                                                                "Sub:-"+result.getData().getSub()+"TrackingCode:-"+result.getData().getTrackingCode());


                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

                                                                        //Todo Check not Null values
                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                        resumeLoginRequestEntity.setUsageType(initiateSmartPushMFARequestEntity.getUsageType());
                                                                        resumeLoginRequestEntity.setVerificationType("push");
                                                                        resumeLoginRequestEntity.setClient_id(clientId);
                                                                        resumeLoginRequestEntity.setRequestId(passwordlessEntity.getRequestId());

                                                                        if (initiateSmartPushMFARequestEntity.getUsageType().equals(UsageType.MFA)) {
                                                                            resumeLoginRequestEntity.setTrack_id(passwordlessEntity.getTrackId());
                                                                            LoginController.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, loginresult);
                                                                        } else if (initiateSmartPushMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS)) {
                                                                            resumeLoginRequestEntity.setTrack_id("");
                                                                            LoginController.getShared(context).continuePasswordless(baseurl, resumeLoginRequestEntity, loginresult);

                                                                        }

                                                                    }

                                                                    @Override
                                                                    public void failure(WebAuthError error)
                                                                    {
                                                                        loginresult.failure(error);
                                                                    }
                                                                });



                                                            }
                                                            else {
                                                                String errorMessage="Status Id or SmartPush Must not be null";
                                                                loginresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage,"Error :SmartPushConfigurationController :LoginWithSmartPush()"));

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

                                            loginresult.failure(WebAuthError.getShared(context).deviceVerificationFailureException("Error :SmartPushConfigurationController :LoginWithSmartPush()"));
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

                loginresult.failure(WebAuthError.getShared(context).propertyMissingException("UsageType or usageDeviceId or baseurl must not be null",
                        "Error :SmartPushConfigurationController :LoginWithSmartPush()"));
            }
        }
        catch (Exception e)
        {
           loginresult.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushConfigurationController :LoginWithSmartPush()",WebAuthErrorCode.ENROLL_IVR_MFA_FAILURE,e.getMessage()));
        }
    }

    public void authenticateSmartPush(final String randomNumber, final String statusId, final Result<AuthenticateSmartPushResponseEntity> result)
    {
        try {

            LogFile.getShared(context).addInfoLog("Info :PatternConfigurationController :authenticatePattern()",
                    " RandomNumber:- "+randomNumber+" StatusId:-"+statusId);

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = lpresult.get("DomainURL");
                    String clientId = lpresult.get("ClientId");
                    //todo call enroll Email


                    AuthenticateSmartPushRequestEntity authenticateSmartPushRequestEntity=new AuthenticateSmartPushRequestEntity();
                    authenticateSmartPushRequestEntity.setStatusId(statusId);
                    authenticateSmartPushRequestEntity.setVerifierPassword(randomNumber);
                    authenticateSmartPushRequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
                    authenticateSmartPushRequestEntity.setClient_id(clientId);

                    authenticateSmartPush(baseurl,authenticateSmartPushRequestEntity,result);

                    //SmartPushVerificationService.getShared(context).authenticateSmartPush(baseurl,authenticateSmartPushRequestEntity,null,result);



                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty",
                            "Error :SmartPushConfigurationController :authenticateSmartPush()"));
                }
            });
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushConfigurationController :authenticateSmartPush()",WebAuthErrorCode.AUTHENTICATE_SMARTPUSH_MFA_FAILURE,e.getMessage()));
        }
    }

    //Authenticate SmartPush

    public void authenticateSmartPush(final String baseurl, final AuthenticateSmartPushRequestEntity authenticateSmartPushRequestEntity, final Result<AuthenticateSmartPushResponseEntity> authResult)
    {
        try
        {
            LogFile.getShared(context).addInfoLog("Info :PatternConfigurationController :authenticatePattern()",
                    " BaseURL:- "+baseurl+" ClientId:-"+ authenticateSmartPushRequestEntity.getClient_id());

            SmartPushVerificationService.getShared(context).authenticateSmartPush(baseurl, authenticateSmartPushRequestEntity,null, new Result<AuthenticateSmartPushResponseEntity>() {
                @Override
                public void success(final AuthenticateSmartPushResponseEntity serviceresult) {


                    Cidaas.usagePass = "";

                    //Timer
                    new CountDownTimer(5000, 500) {
                        String instceID = "";

                        public void onTick(long millisUntilFinished) {
                            instceID = Cidaas.usagePass;

                            Timber.e("");
                            if (instceID != null && !instceID.equals("")) {
                                this.cancel();
                                onFinish();
                            }

                        }

                        public void onFinish() {
                            if (instceID != null && !instceID.equals("")) {
                                AuthenticateSmartPushRequestEntity authenticateSmartPushRequestEntity=new AuthenticateSmartPushRequestEntity();
                                authenticateSmartPushRequestEntity.setUsage_pass(instceID);

                                SmartPushVerificationService.getShared(context).authenticateSmartPush(baseurl, authenticateSmartPushRequestEntity,null, authResult);
                            }
                            else {
                                // return Error Message
                                authResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException("Error :SmartPushConfigurationController :authenticateSmartPush()"));
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
            authResult.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushConfigurationController :authenticateSmartPush()",
                    WebAuthErrorCode.AUTHENTICATE_SMARTPUSH_MFA_FAILURE,e.getMessage()));
        }
    }


}
