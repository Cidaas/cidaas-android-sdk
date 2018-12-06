package com.example.cidaasv2.Controller.Repository.Configuration.Fingerprint;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Configuration.Fingerprint.FingerprintConfigurationController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceResponseEntity;
import com.example.cidaasv2.Service.Repository.OauthService;
import com.example.cidaasv2.Service.Repository.Verification.Device.DeviceVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.Fingerprint.FingerprintVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.Fingerprint.FingerprintVerificationService;
import com.example.cidaasv2.Service.Scanned.ScannedRequestEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import java.util.Dictionary;

import timber.log.Timber;

public class FingerprintConfigurationController {


    private String authenticationType;
    private String verificationType;
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


//Todo Configure Fingerprint by Passing the setupFingerprintRequestEntity
    // 1.  Check For NotNull Values
    // 2. Generate Code Challenge
    // 3.  getAccessToken From Sub
    // 4.  Call Setup Fingerprint
    // 5.  Call Validate Fingerprint
    // 3.  Call Scanned Fingerprint
    // 3.  Call Enroll Fingerprint and return the result
    // 4.  Maintain logs based on flags


   /* //Service call To SetupFingerprintMFA
    public void configureFingerprint(@NonNull final String sub, @NonNull final String baseurl,
                                 @NonNull final SetupFingerprintMFARequestEntity setupFingerprintMFARequestEntity,
                                 @NonNull final Result<EnrollFingerprintMFAResponseEntity> enrollresult)
    {
        try{
            //Generate Challenge
            generateChallenge();
            Cidaas.instanceId="";

            AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>()
            {
                @Override
                public void success(final AccessTokenEntity accessTokenresult) {

                    if (baseurl != null && !baseurl.equals("") && accessTokenresult.getAccess_token() != null && !accessTokenresult.getAccess_token().equals("") &&
                            setupFingerprintMFARequestEntity.getClient_id()!=null && !setupFingerprintMFARequestEntity.getClient_id().equals(""))
                    {
                        //Todo Service call

                        FingerprintVerificationService.getShared(context).setupFingerprint(baseurl, accessTokenresult.getAccess_token(), codeChallenge,setupFingerprintMFARequestEntity,null,new Result<SetupFingerprintMFAResponseEntity>() {
                            @Override
                            public void success(final SetupFingerprintMFAResponseEntity setupserviceresult) {

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
                                        if(instceID!=null && instceID!="" )
                                        {
                                            //Device Validation Service
                                            DeviceVerificationService.getShared(context).validateDevice(baseurl,instceID,"",codeVerifier
                                                    , null,new Result<ValidateDeviceResponseEntity>() {
                                                        @Override
                                                        public void success(ValidateDeviceResponseEntity result) {
                                                            // call Scanned Service
                                                            FingerprintVerificationService.getShared(context).scannedFingerprint(baseurl,result.getData().getUsage_pass(),"",
                                                                    accessTokenresult.getAccess_token(),null,new Result<ScannedResponseEntity>() {
                                                                        @Override
                                                                        public void success(final ScannedResponseEntity result) {
                                                                            DBHelper.getShared().setUserDeviceId(result.getData().getUserDeviceId(),baseurl);

                                                                            EnrollFingerprintMFARequestEntity enrollFingerprintMFARequestEntity = new EnrollFingerprintMFARequestEntity();
                                                                            if(sub != null && !sub.equals("") &&
                                                                                    result.getData().getUserDeviceId()!=null && !  result.getData().getUserDeviceId().equals("")) {

                                                                                enrollFingerprintMFARequestEntity.setSub(sub);
                                                                                enrollFingerprintMFARequestEntity.setStatusId("");
                                                                                enrollFingerprintMFARequestEntity.setUserDeviceId(result.getData().getUserDeviceId());
                                                                            }
                                                                            else {
                                                                                enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
                                                                            }

                                                                            // call Enroll Service
                                                                            FingerprintVerificationService.getShared(context).enrollFingerprint(baseurl, accessTokenresult.getAccess_token(), enrollFingerprintMFARequestEntity,null,new Result<EnrollFingerprintMFAResponseEntity>() {
                                                                                @Override
                                                                                public void success(EnrollFingerprintMFAResponseEntity serviceresult) {
                                                                                    enrollresult.success(serviceresult);
                                                                                }

                                                                                @Override
                                                                                public void failure(WebAuthError error) {
                                                                                    enrollresult.failure(error);
                                                                                }
                                                                            });

                                                                            Timber.i(result.getData().getUserDeviceId()+"User Device id");
                                                                         //   Toast.makeText(context, result.getData().getUserDeviceId()+"User Device id", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                        @Override
                                                                        public void failure(WebAuthError error) {
                                                                            enrollresult.failure(error);
                                                                         //   Toast.makeText(context, "Error on Scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }

                                                        @Override
                                                        public void failure(WebAuthError error) {
                                                            enrollresult.failure(error);
                                                            //Toast.makeText(context, "Error on validate Device"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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


    //Login with Fingerprint
    public void LoginWithFingerprint(@NonNull final String baseurl, @NonNull final String clientId, @NonNull final String trackId, @NonNull final String requestId,
                                     @NonNull final InitiateFingerprintMFARequestEntity initiateFingerprintMFARequestEntity,
                                     final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(initiateFingerprintMFARequestEntity.getUserDeviceId() != null && !initiateFingerprintMFARequestEntity.getUserDeviceId().equals(""))
            {
                //Do nothing
            }
            else
            {
                initiateFingerprintMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }
            if(codeChallenge=="" || codeVerifier=="" || codeChallenge==null || codeVerifier==null) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";

            if (    initiateFingerprintMFARequestEntity.getUsageType() != null && !initiateFingerprintMFARequestEntity.getUsageType().equals("") &&
                    initiateFingerprintMFARequestEntity.getUserDeviceId() != null && !initiateFingerprintMFARequestEntity.getUserDeviceId().equals("")
                    && baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                FingerprintVerificationService.getShared(context).initiateFingerprint(baseurl,codeChallenge, initiateFingerprintMFARequestEntity,null,
                        new Result<InitiateFingerprintMFAResponseEntity>() {

                            //Todo Call Validate Device

                            @Override
                            public void success(final InitiateFingerprintMFAResponseEntity serviceresult) {

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
                                        if(instceID!=null && instceID!="" && serviceresult.getData().getStatusId()!=null && !serviceresult.getData().getStatusId().equals("")) {
                                            //Device Validation Service
                                            DeviceVerificationService.getShared(context).validateDevice(baseurl, instceID, serviceresult.getData().getStatusId(), codeVerifier
                                                    ,null, new Result<ValidateDeviceResponseEntity>() {

                                                        @Override
                                                        public void success(ValidateDeviceResponseEntity result) {

                                                            //Todo call initiate
                                                            initiateFingerprintMFARequestEntity.setUsagePass(result.getData().getUsage_pass());
                                                            FingerprintVerificationService.getShared(context).initiateFingerprint(baseurl, codeChallenge, initiateFingerprintMFARequestEntity,null,
                                                                    new Result<InitiateFingerprintMFAResponseEntity>() {

                                                                        @Override
                                                                        public void success(InitiateFingerprintMFAResponseEntity serviceresult) {

                                                                            if (serviceresult.getData().getStatusId() != null &&
                                                                                    !serviceresult.getData().getStatusId().equals("")) {


                                                                                AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity = new AuthenticateFingerprintRequestEntity();
                                                                                authenticateFingerprintRequestEntity.setUserDeviceId(initiateFingerprintMFARequestEntity.getUserDeviceId());
                                                                                authenticateFingerprintRequestEntity.setStatusId(serviceresult.getData().getStatusId());



                                                                                FingerprintVerificationService.getShared(context).authenticateFingerprint(baseurl, authenticateFingerprintRequestEntity, null,new Result<AuthenticateFingerprintResponseEntity>() {
                                                                                    @Override
                                                                                    public void success(AuthenticateFingerprintResponseEntity result) {
                                                                                        //Todo Call Resume with Login Service
                                                                                        //  loginresult.success(result);
                                                                                     //   Toast.makeText(context, "Sucess Fingerprint", Toast.LENGTH_SHORT).show();



                                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();

                                                                                        //Todo Check not Null values
                                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                                        resumeLoginRequestEntity.setUsageType(initiateFingerprintMFARequestEntity.getUsageType());
                                                                                        resumeLoginRequestEntity.setVerificationType("touchid");
                                                                                        resumeLoginRequestEntity.setClient_id(clientId);
                                                                                        resumeLoginRequestEntity.setRequestId(requestId);

                                                                                        if(initiateFingerprintMFARequestEntity.getUsageType().equals(UsageType.MFA))
                                                                                        {
                                                                                            resumeLoginRequestEntity.setTrack_id(trackId);
                                                                                            LoginController.getShared(context).continueMFA(baseurl,resumeLoginRequestEntity,loginresult);
                                                                                        }

                                                                                        else if(initiateFingerprintMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS))
                                                                                        {
                                                                                            resumeLoginRequestEntity.setTrack_id("");
                                                                                            LoginController.getShared(context).continuePasswordless(baseurl,resumeLoginRequestEntity,loginresult);

                                                                                        }
                                                                                    }

                                                                                    @Override
                                                                                    public void failure(WebAuthError error) {
                                                                                        loginresult.failure(error);
                                                                                    }
                                                                                });
                                                                                // result.success(serviceresult);
                                                                            }
                                                                            else
                                                                            {
                                                                                String errorMessage="Status Id Must not be null";
                                                                                loginresult.failure(WebAuthError.getShared(context).customException(417,errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                                                                            }
                                                                        }


                                                                        // result.success(serviceresult);


                                                                        @Override
                                                                        public void failure(WebAuthError error) {
                                                                            loginresult.failure(error);
                                                                           // Toast.makeText(context, "Error on validate Device" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }
*/


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
            Cidaas.instanceId="";

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
            enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e(e.getMessage());
        }
    }


    private void setupFingerprint(final String baseurl, final String accessToken,
                              SetupFingerprintMFARequestEntity setupFingerprintMFARequestEntity,final Result<EnrollFingerprintMFAResponseEntity> enrollResult)
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

                enrollResult.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FINGERPRINT_MFA_FAILURE,
                    "Fingerprint Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }



    public void scannedWithFingerprint(final String baseurl,String statusId, String clientId, final Result<ScannedResponseEntity> scannedResult)
    {
        try
        {
            if (baseurl != null && !baseurl.equals("")  && statusId!=null && !statusId.equals("") && clientId!=null && !clientId.equals("")) {

                final ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
                scannedRequestEntity.setStatusId(statusId);
                scannedRequestEntity.setClient_id(clientId);


                FingerprintVerificationService.getShared(context).scannedFingerprint(baseurl,  scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
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

                                    FingerprintVerificationService.getShared(context).scannedFingerprint(baseurl,  scannedRequestEntity, null, new Result<ScannedResponseEntity>() {

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
                scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_FINGERPRINT_MFA_FAILURE,
                        "BaseURL or ClientId or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }

        }
        catch (Exception e)
        {
            scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_FINGERPRINT_MFA_FAILURE,
                    "Fingerprint Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }



    public void enrollFingerprint(@NonNull final String baseurl, @NonNull final String accessToken,
                              @NonNull EnrollFingerprintMFARequestEntity enrollFingerprintMFARequestEntity, final Result<EnrollFingerprintMFAResponseEntity> enrollResult)
    {
        try
        {

            if(baseurl!=null && !baseurl.equals("") && accessToken!=null && !accessToken.equals("")) {

                if (enrollFingerprintMFARequestEntity.getUserDeviceId() != null && !enrollFingerprintMFARequestEntity.getUserDeviceId().equals("") &&
                        enrollFingerprintMFARequestEntity.getStatusId() != null && !enrollFingerprintMFARequestEntity.getStatusId().equals("") ) {

                    // call Enroll Service
                    FingerprintVerificationService.getShared(context).enrollFingerprint(baseurl, accessToken, enrollFingerprintMFARequestEntity,
                            null, new Result<EnrollFingerprintMFAResponseEntity>() {

                                @Override
                                public void success(final EnrollFingerprintMFAResponseEntity serviceresult) {

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
                            "UserdeviceId or Verifierpassword or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
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
            enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FINGERPRINT_MFA_FAILURE,
                    "Fingerprint Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }


    //Login with Fingerprint
    public void LoginWithFingerprint( @NonNull final String baseurl, @NonNull final String clientId,
                                  @NonNull final String trackId, @NonNull final String requestId,
                                  @NonNull final InitiateFingerprintMFARequestEntity initiateFingerprintMFARequestEntity,
                                  final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(codeChallenge.equals("") && codeVerifier.equals("")) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";
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
                                            final InitiateFingerprintMFARequestEntity initiateFingerprintMFARequestEntity=new InitiateFingerprintMFARequestEntity();
                                            initiateFingerprintMFARequestEntity.setUsagePass(instceID);

                                            final String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);

                                            FingerprintVerificationService.getShared(context).initiateFingerprint(baseurl,  initiateFingerprintMFARequestEntity,null,
                                                    new Result<InitiateFingerprintMFAResponseEntity>() {

                                                        @Override
                                                        public void success(InitiateFingerprintMFAResponseEntity result) {
                                                            if (serviceresult.getData().getStatusId() != null &&
                                                                    !serviceresult.getData().getStatusId().equals("")) {


                                                                AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity = new AuthenticateFingerprintRequestEntity();
                                                                authenticateFingerprintRequestEntity.setUserDeviceId(userDeviceId);
                                                                authenticateFingerprintRequestEntity.setStatusId(serviceresult.getData().getStatusId());


                                                                authenticateFingerprint(baseurl, authenticateFingerprintRequestEntity, new Result<AuthenticateFingerprintResponseEntity>() {
                                                                    @Override
                                                                    public void success(AuthenticateFingerprintResponseEntity result) {

                                                                        //Todo Call Resume with Login Service

                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

                                                                        //Todo Check not Null values
                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                        resumeLoginRequestEntity.setVerificationType("FINGERPRINT");
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

                loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
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
                                AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity=new AuthenticateFingerprintRequestEntity();
                                authenticateFingerprintRequestEntity.setUsage_pass(instceID);

                                FingerprintVerificationService.getShared(context).authenticateFingerprint(baseurl, authenticateFingerprintRequestEntity,null, new Result<AuthenticateFingerprintResponseEntity>() {
                                    @Override
                                    public void success(AuthenticateFingerprintResponseEntity result) {
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
            authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_FINGERPRINT_MFA_FAILURE,
                    "Fingerprint Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
        }
    }




}
