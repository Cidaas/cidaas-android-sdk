package com.example.cidaasv2.Controller.Repository.Configuration.Fingerprint;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.FingerPrintEntity;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Library.BiometricAuthentication.BiometricCallback;
import com.example.cidaasv2.Library.BiometricAuthentication.BiometricManager;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.Fingerprint.FingerprintVerificationService;
import com.example.cidaasv2.Service.Scanned.ScannedRequestEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import java.util.Dictionary;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class FingerprintConfigurationController {


    private String authenticationType;
    private String verificationType;

    public static String logoURLlocal="https://cdn.shortpixel.ai/client/q_glossy,ret_img/https://www.cidaas.com/wp-content/uploads/2018/02/logo.png";


    private Context context;

    public static FingerprintConfigurationController shared;

    public FingerprintConfigurationController(Context contextFromCidaas) {

        verificationType="";
        context=contextFromCidaas;
        authenticationType="";
        //Todo setValue for authenticationType

    }

    String codeVerifier="" ;
            String codeChallenge="";
    // Generate Code Challenge and Code verifier
    public void generateChallenge(){
        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        codeVerifier=generator.getCodeVerifier();
        codeChallenge= generator.getCodeChallenge(codeVerifier);

    }

    public static FingerprintConfigurationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new FingerprintConfigurationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    public void configureFingerprint(final Context context, final String sub, @NonNull final String logoURL, FingerPrintEntity fingerPrintEntity, final Result<EnrollFingerprintMFAResponseEntity> enrollresult) {
        try{
            if (Build.VERSION.SDK_INT >= 23) {


                callFingerPrint(context, fingerPrintEntity, new Result<String>() {
                    @Override
                    public void success(String result) {
                        CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                            @Override
                            public void success(Dictionary<String, String> result) {
                                final String baseurl = result.get("DomainURL");
                                final String clinetId = result.get("ClientId");


                                if (sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                                    final String finalBaseurl = baseurl;


                                    // String logoUrl = "https://docs.cidaas.de/assets/logoss.png";


                                    if (!logoURL.equals("") && logoURL != null) {
                                        logoURLlocal = logoURL;
                                    }

                                    SetupFingerprintMFARequestEntity setupFingerprintMFARequestEntity = new SetupFingerprintMFARequestEntity();
                                    setupFingerprintMFARequestEntity.setClient_id(clinetId);
                                    setupFingerprintMFARequestEntity.setLogoUrl(logoURLlocal);

                                    configureFingerprint(sub,baseurl,setupFingerprintMFARequestEntity,enrollresult);


                                } else {
                                    String errorMessage = "Sub or Pattern cannot be null";
                                    enrollresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING, errorMessage,
                                            HttpStatusCode.EXPECTATION_FAILED));
                                }
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                enrollresult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty"));
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
                String ErrorMessage="Fingerprint doesnot Support in your mobile";
                enrollresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_FAILED,ErrorMessage,HttpStatusCode.EXPECTATION_FAILED));

            }
        }
        catch (Exception e)
        {
            enrollresult.failure(WebAuthError.getShared(context).serviceException("Exception :FingerprintConfigurationController :configureFingerprint()",WebAuthErrorCode.ENROLL_FINGERPRINT_MFA_FAILURE,e.getMessage()));
           
        }
    }

    //Service call To SetupFingerprintMFA
    public void configureFingerprint(@NonNull final String sub, @NonNull final String baseurl,
                                 @NonNull final SetupFingerprintMFARequestEntity setupFingerprintMFARequestEntity,
                                 @NonNull final Result<EnrollFingerprintMFAResponseEntity> enrollresult)
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

                    setupFingerprint(baseurl,accessTokenresult.getAccess_token(),setupFingerprintMFARequestEntity,enrollresult);
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollresult.failure(error);
                }

            });

        }
        catch (Exception e)
        {
            enrollresult.failure(WebAuthError.getShared(context).serviceException("Exception :FingerprintConfigurationController :configureFingerprint()",WebAuthErrorCode.ENROLL_FINGERPRINT_MFA_FAILURE,e.getMessage()));
           
        }
    }


    private void setupFingerprint(final String baseurl, final String accessToken,
                                  final SetupFingerprintMFARequestEntity setupFingerprintMFARequestEntity, final Result<EnrollFingerprintMFAResponseEntity> enrollResult)
    {
        try
        {
            if (baseurl != null && !baseurl.equals("") && accessToken != null && !accessToken.equals("") &&
                    setupFingerprintMFARequestEntity.getClient_id()!=null && !setupFingerprintMFARequestEntity.getClient_id().equals(""))
            {
                //Done Service call

                FingerprintVerificationService.getShared(context).setupFingerprint(baseurl, accessToken,
                        setupFingerprintMFARequestEntity,null,new Result<SetupFingerprintMFAResponseEntity>() {
                            @Override
                            public void success(final SetupFingerprintMFAResponseEntity setupserviceresult) {

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
                                        if(instceID!=null && !instceID.equals("") ) {

                                            SetupFingerprintMFARequestEntity setupFingerprintMFARequestEntity1 = new SetupFingerprintMFARequestEntity();
                                            setupFingerprintMFARequestEntity1.setUsage_pass(instceID);
                                            // call Scanned Service
                                            FingerprintVerificationService.getShared(context).setupFingerprint(baseurl, accessToken,
                                                    setupFingerprintMFARequestEntity1, null, new Result<SetupFingerprintMFAResponseEntity>() {
                                                        @Override
                                                        public void success(final SetupFingerprintMFAResponseEntity result) {
                                                            DBHelper.getShared().setUserDeviceId(result.getData().getUdi(), baseurl);

                                                            //Entity For Fingerprint
                                                            EnrollFingerprintMFARequestEntity enrollFingerprintMFARequestEntity = new EnrollFingerprintMFARequestEntity();
                                                            enrollFingerprintMFARequestEntity.setStatusId(result.getData().getSt());
                                                            enrollFingerprintMFARequestEntity.setUserDeviceId(result.getData().getUdi());
                                                            enrollFingerprintMFARequestEntity.setClient_id(setupFingerprintMFARequestEntity.getClient_id());


                                                            enrollFingerprint(baseurl,accessToken,enrollFingerprintMFARequestEntity,enrollResult);


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

                enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl or AccessToken or ClientId must not be null"));
            }
        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).serviceException("Exception :FingerprintConfigurationController :setupFingerprint()",WebAuthErrorCode.ENROLL_FINGERPRINT_MFA_FAILURE,e.getMessage()));

        }
    }



    public void scannedWithFingerprint(final String statusId, final Result<ScannedResponseEntity> scannedResult)
    {
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    String clientId = loginPropertiesResult.get("ClientId");

                    if (statusId!=null && !statusId.equals("") ) {

                        final ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
                        scannedRequestEntity.setStatusId(statusId);
                        scannedRequestEntity.setClient_id(clientId);


                        FingerprintVerificationService.getShared(context).scannedFingerprint(baseurl,  scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
                            @Override
                            public void success(ScannedResponseEntity result) {
                                Cidaas.usagePass ="";


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

                                        if(instceID!=null && !instceID.equals("") ) {

                                            ScannedRequestEntity scannedRequestEntity= new ScannedRequestEntity();
                                            scannedRequestEntity.setUsage_pass(instceID);

                                            FingerprintVerificationService.getShared(context).scannedFingerprint(baseurl,  scannedRequestEntity, null, scannedResult);
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
                        scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_FINGERPRINT_MFA_FAILURE,
                                "BaseURL or ClientId or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
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
            scannedResult.failure(WebAuthError.getShared(context).serviceException("Exception :FingerprintConfigurationController :scannedWithFingerprint()",WebAuthErrorCode.SCANNED_FINGERPRINT_MFA_FAILURE,e.getMessage()));

        }
    }


    public void enrollFingerprint(final Context context, @NonNull final String sub, @NonNull final String statusId, FingerPrintEntity fingerPrintEntity, final Result<EnrollFingerprintMFAResponseEntity> enrollResult) {
    try{
        callFingerPrint(context, fingerPrintEntity, new Result<String>() {
            @Override
            public void success(String result) {
                CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(final Dictionary<String, String> result) {

                        final String baseurl = result.get("DomainURL");
                        final String clientId= result.get("ClientId");

                        final String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);

                        AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                            @Override
                            public void success(AccessTokenEntity accessTokenresult) {
                                EnrollFingerprintMFARequestEntity enrollFingerprintMFARequestEntity=new EnrollFingerprintMFARequestEntity();
                                enrollFingerprintMFARequestEntity.setStatusId(statusId);
                                enrollFingerprintMFARequestEntity.setUserDeviceId(userDeviceId);
                                enrollFingerprintMFARequestEntity.setClient_id(clientId);

                                enrollFingerprint(baseurl,accessTokenresult.getAccess_token(),enrollFingerprintMFARequestEntity,enrollResult);

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

            @Override
            public void failure(WebAuthError error) {
                enrollResult.failure(error);
            }
        });
    }
    catch (Exception e)
    {
        enrollResult.failure(WebAuthError.getShared(context).serviceException("Exception :FingerprintConfigurationController :enrollFingerprint()",WebAuthErrorCode.ENROLL_FINGERPRINT_MFA_FAILURE,e.getMessage()));
    }
    }

    public void enrollFingerprint(@NonNull final String baseurl, @NonNull final String accessToken,
                              @NonNull EnrollFingerprintMFARequestEntity enrollFingerprintMFARequestEntity, final Result<EnrollFingerprintMFAResponseEntity> enrollResult)
    {
        try
        {

            if(baseurl!=null && !baseurl.equals("") && accessToken!=null && !accessToken.equals("")) {

                if (enrollFingerprintMFARequestEntity.getUserDeviceId() != null && !enrollFingerprintMFARequestEntity.getUserDeviceId().equals("") &&
                        enrollFingerprintMFARequestEntity.getClient_id() != null && !enrollFingerprintMFARequestEntity.getClient_id().equals("") &&
                        enrollFingerprintMFARequestEntity.getStatusId() != null && !enrollFingerprintMFARequestEntity.getStatusId().equals("") ) {

                    // call Enroll Service
                    FingerprintVerificationService.getShared(context).enrollFingerprint(baseurl, accessToken, enrollFingerprintMFARequestEntity,
                            null, new Result<EnrollFingerprintMFAResponseEntity>() {

                                @Override
                                public void success(final EnrollFingerprintMFAResponseEntity serviceresult) {

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
                                                EnrollFingerprintMFARequestEntity enrollFingerprintMFARequestEntity = new EnrollFingerprintMFARequestEntity();
                                                enrollFingerprintMFARequestEntity.setUsage_pass(instceID);

                                                // call Enroll Service
                                                FingerprintVerificationService.getShared(context).enrollFingerprint(baseurl, accessToken, enrollFingerprintMFARequestEntity,
                                                        null, new Result<EnrollFingerprintMFAResponseEntity>() {
                                                            @Override
                                                            public void success(EnrollFingerprintMFAResponseEntity serviceresult) {
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
                    enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FINGERPRINT_MFA_FAILURE,
                            "UserdeviceId or ClientID or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                }
            }
            else
            {
                enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FINGERPRINT_MFA_FAILURE,
                        "BaseURL or accessToken must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }


        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).serviceException("Exception :FingerprintConfigurationController :enrollFingerprint()",WebAuthErrorCode.ENROLL_FINGERPRINT_MFA_FAILURE,e.getMessage()));
        }
    }

    public void LoginWithFingerprint(final Context context, final PasswordlessEntity passwordlessEntity, FingerPrintEntity fingerPrintEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
   try{
       callFingerPrint(context, fingerPrintEntity, new Result<String>() {
           @Override
           public void success(String result) {
               CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                   @Override
                   public void success(Dictionary<String, String> result) {
                       final String baseurl = result.get("DomainURL");
                       final String clientId = result.get("ClientId");


                       if (passwordlessEntity.getUsageType() != null && !passwordlessEntity.getUsageType().equals("") &&
                               passwordlessEntity.getRequestId() != null && !passwordlessEntity.getRequestId().equals("")) {

                           if (baseurl == null || baseurl.equals("") && clientId == null || clientId.equals("")) {
                               String errorMessage = "baseurl or clientId  must not be empty";

                               loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                       errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                           }


                           if (((passwordlessEntity.getSub() == null || passwordlessEntity.getSub().equals("")) &&
                                   (passwordlessEntity.getEmail() == null || passwordlessEntity.getEmail().equals("")) &&
                                   (passwordlessEntity.getMobile() == null || passwordlessEntity.getMobile().equals("")))) {
                               String errorMessage = "sub or email or mobile number must not be empty";

                               loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                       errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                           }

                           if (passwordlessEntity.getUsageType().equals(UsageType.MFA)) {
                               if (passwordlessEntity.getTrackId() == null || passwordlessEntity.getTrackId() == "") {
                                   String errorMessage = "trackId must not be empty For Multifactor Authentication";

                                   loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                           errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                                   return;
                               }
                           }

                           InitiateFingerprintMFARequestEntity initiateFingerprintMFARequestEntity = new InitiateFingerprintMFARequestEntity();
                           initiateFingerprintMFARequestEntity.setSub(passwordlessEntity.getSub());
                           initiateFingerprintMFARequestEntity.setUsageType(passwordlessEntity.getUsageType());
                           initiateFingerprintMFARequestEntity.setEmail(passwordlessEntity.getEmail());
                           initiateFingerprintMFARequestEntity.setMobile(passwordlessEntity.getMobile());

                           //Todo check for email or sub or mobile


                          LoginWithFingerprint(baseurl, clientId,
                                   passwordlessEntity.getTrackId(), passwordlessEntity.getRequestId(),
                                   initiateFingerprintMFARequestEntity, loginresult);
                       } else {
                           String errorMessage = "UsageType or FingerprintCode or requestId must not be empty";

                           loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                   errorMessage, HttpStatusCode.EXPECTATION_FAILED));
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
       loginresult.failure(WebAuthError.getShared(context).serviceException("Exception :FingerprintConfigurationController :LoginWithFingerprint()",WebAuthErrorCode.AUTHENTICATE_FINGERPRINT_MFA_FAILURE,e.getMessage()));
   }
    }

    public void LoginWithFingerprint(@NonNull final String baseurl, @NonNull final String clientId,
                                     @NonNull final String trackId, @NonNull final String requestId,
                                     @NonNull final InitiateFingerprintMFARequestEntity initiateFingerprintMFARequestEntity,
                                     final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(codeChallenge.equals("") && codeVerifier.equals("")) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.usagePass ="";
            if(initiateFingerprintMFARequestEntity.getUserDeviceId() != null && !initiateFingerprintMFARequestEntity.getUserDeviceId().equals(""))
            {
                //Do nothing
            }
            else
            {
                initiateFingerprintMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }
            initiateFingerprintMFARequestEntity.setClient_id(clientId);


            if (    initiateFingerprintMFARequestEntity.getUsageType() != null && !initiateFingerprintMFARequestEntity.getUsageType().equals("") &&
                    initiateFingerprintMFARequestEntity.getUserDeviceId() != null && !initiateFingerprintMFARequestEntity.getUserDeviceId().equals("") &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                FingerprintVerificationService.getShared(context).initiateFingerprint(baseurl, initiateFingerprintMFARequestEntity,null,
                        new Result<InitiateFingerprintMFAResponseEntity>() {

                            @Override
                            public void success(final InitiateFingerprintMFAResponseEntity serviceresult) {

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
                                            final InitiateFingerprintMFARequestEntity initiateFingerprintMFARequestEntityWithUsagePass=new InitiateFingerprintMFARequestEntity();
                                            initiateFingerprintMFARequestEntityWithUsagePass.setUsage_pass(instceID);

                                            final String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);

                                            FingerprintVerificationService.getShared(context).initiateFingerprint(baseurl,  initiateFingerprintMFARequestEntityWithUsagePass,null,
                                                    new Result<InitiateFingerprintMFAResponseEntity>() {

                                                        @Override
                                                        public void success(InitiateFingerprintMFAResponseEntity result) {
                                                            if (result.getData().getStatusId() != null &&
                                                                    !result.getData().getStatusId().equals("")) {


                                                                AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity = new AuthenticateFingerprintRequestEntity();
                                                                authenticateFingerprintRequestEntity.setUserDeviceId(userDeviceId);
                                                                authenticateFingerprintRequestEntity.setStatusId(result.getData().getStatusId());
                                                                authenticateFingerprintRequestEntity.setClient_id(clientId);


                                                                authenticateFingerprint(baseurl, authenticateFingerprintRequestEntity, new Result<AuthenticateFingerprintResponseEntity>() {
                                                                    @Override
                                                                    public void success(AuthenticateFingerprintResponseEntity result) {

                                                                        //Todo Call Resume with Login Service

                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

                                                                        //Todo Check not Null values
                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                        resumeLoginRequestEntity.setVerificationType(AuthenticationType.touch);
                                                                        resumeLoginRequestEntity.setUsageType(initiateFingerprintMFARequestEntity.getUsageType());
                                                                        resumeLoginRequestEntity.setClient_id(clientId);
                                                                        resumeLoginRequestEntity.setRequestId(requestId);

                                                                        if (initiateFingerprintMFARequestEntity.getUsageType().equals(UsageType.MFA)) {
                                                                            resumeLoginRequestEntity.setTrack_id(trackId);
                                                                            LoginController.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, loginresult);
                                                                        } else if (initiateFingerprintMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS)) {
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
                                                                String errorMessage="Status Id or Fingerprint Must not be null";
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

                loginresult.failure(WebAuthError.getShared(context).propertyMissingException("UsageType or Usage DeviceID or baseurl must not be null"));
            }
        }
        catch (Exception e)
        {
            loginresult.failure(WebAuthError.getShared(context).serviceException("Exception :FingerprintConfigurationController :LoginWithFingerprint()",WebAuthErrorCode.AUTHENTICATE_FINGERPRINT_MFA_FAILURE,e.getMessage()));
        }
    }

    public void authenticateFingerprint(final Context context, final String statusId, FingerPrintEntity fingerPrintEntity, final Result<AuthenticateFingerprintResponseEntity> callBackresult) {
        try {
            callFingerPrint(context, fingerPrintEntity, new Result<String>() {
                @Override
                public void success(String result) {
                    CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                        @Override
                        public void success(Dictionary<String, String> result) {
                            String baseurl = result.get("DomainURL");
                            String clientId = result.get("ClientId");
                            //todo call enroll Email

                            AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity=new AuthenticateFingerprintRequestEntity();
                            authenticateFingerprintRequestEntity.setStatusId(statusId);
                            authenticateFingerprintRequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
                            authenticateFingerprintRequestEntity.setClient_id(clientId);

                            authenticateFingerprint(baseurl,authenticateFingerprintRequestEntity,callBackresult);


                        }

                        @Override
                        public void failure(WebAuthError error) {
                            callBackresult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty"));
                        }
                    });
                }

                @Override
                public void failure(WebAuthError error) {
                    callBackresult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            callBackresult.failure(WebAuthError.getShared(context).serviceException("Exception :FingerprintConfigurationController :authenticateFingerprint()",WebAuthErrorCode.AUTHENTICATE_FINGERPRINT_MFA_FAILURE,e.getMessage()));
        }
    }

            //Authenticate Fingerprint

    public void authenticateFingerprint(final String baseurl, final AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity, final Result<AuthenticateFingerprintResponseEntity> authResult)
    {
        try
        {
            FingerprintVerificationService.getShared(context).authenticateFingerprint(baseurl, authenticateFingerprintRequestEntity,null, new Result<AuthenticateFingerprintResponseEntity>() {
                @Override
                public void success(final AuthenticateFingerprintResponseEntity serviceresult) {


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
                                AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity=new AuthenticateFingerprintRequestEntity();
                                authenticateFingerprintRequestEntity.setUsage_pass(instceID);

                                FingerprintVerificationService.getShared(context).authenticateFingerprint(baseurl, authenticateFingerprintRequestEntity,null, authResult);
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
            authResult.failure(WebAuthError.getShared(context).serviceException("Exception :FingerprintConfigurationController :authenticateFingerprint()",WebAuthErrorCode.AUTHENTICATE_FINGERPRINT_MFA_FAILURE,e.getMessage()));
        }
    }

    public void callFingerPrint(final Context context, FingerPrintEntity fingerPrintEntity, final Result<String> result)
    {
        try
        {
            if (Build.VERSION.SDK_INT >= 23) {

                FingerPrintEntity fingerPrintEntityForPassing = new FingerPrintEntity();
                if (fingerPrintEntity == null) {
                    fingerPrintEntity = fingerPrintEntityForPassing;
                }

                new BiometricManager.BiometricBuilder(context)
                        .setTitle(fingerPrintEntity.getTitle())
                        .setSubtitle(fingerPrintEntity.getSubtitle())
                        .setDescription(fingerPrintEntity.getDescription())
                        .setNegativeButtonText(fingerPrintEntity.getNegativeButtonString())
                        .build()
                        .authenticate(new BiometricCallback() {
                            @Override
                            public void onSdkVersionNotSupported() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_SDK_VERSION_NOT_SUPPORTED,"SDK Version Not Supported"));
                                return;
                            }

                            @Override
                            public void onBiometricAuthenticationNotSupported() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_BIOMETRIC_AUTHENTICATION_NOT_SUPPORTED,"Biometric Authentication  Not Supported"));
                                return;
                            }

                            @Override
                            public void onBiometricAuthenticationNotAvailable() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_BIOMERTIC_AUTHENTICATION_NOT_AVAILABLE,
                                        "Biometric Authentication  Not Available"));
                                return;
                            }

                            @Override
                            public void onBiometricAuthenticationPermissionNotGranted() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_BIOMERTIC_AUTHENTICATION_PERMISSION_NOT_GRANTED,
                                        "Biometric Authentication  Permission Not Granted"));
                                return;
                            }

                            @Override
                            public void onBiometricAuthenticationInternalError(String error) {

                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_BIOMERTIC_AUTHENTICATION_INTERNAL_ERROR,
                                        "Biometric Authentication  Internal Error"));
                                return;
                            }

                            @Override
                            public void onAuthenticationFailed() {
                                // result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_FAILED,"Biometric Authentication  Failed"));
                            }

                            @Override
                            public void onAuthenticationCancelled() {
                                result.failure(WebAuthError.getShared(context).fingerPrintError(WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_CANCELLED,
                                        "Biometric Authentication  Cancelled"));
                                return;
                            }

                            @Override
                            public void onAuthenticationSuccessful() {
                                result.success("Success");
                                return;
                            }

                            @Override
                            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

                                String errorMessage = helpString.toString();
                                result.failure(WebAuthError.getShared(context).fingerPrintError(helpCode, errorMessage));
                                return;
                            }

                            @Override
                            public void onAuthenticationError(int errorCode, CharSequence errString) {

                                String errorMessage = errString.toString();
                                result.failure(WebAuthError.getShared(context).fingerPrintError(errorCode, errorMessage));
                                return;
                            }
                        });
            }
            else
            {
                String ErrorMessage="Fingerprint doesnot Support in your mobile";
                result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_FAILED,ErrorMessage,HttpStatusCode.EXPECTATION_FAILED));
                return;
            }
        }
        catch (Exception e)
        {
            result.failure( WebAuthError.getShared(context).serviceException("Exception :FingerprintConfigurationController :callFingerPrint()",WebAuthErrorCode.FINGERPRINT_AUTHENTICATION_FAILED,e.getMessage()));
           
        }

    }
}
