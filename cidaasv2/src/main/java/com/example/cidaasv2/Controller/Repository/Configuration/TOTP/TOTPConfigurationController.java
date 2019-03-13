package com.example.cidaasv2.Controller.Repository.Configuration.TOTP;

import android.content.Context;
import android.os.CountDownTimer;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Configuration.TOTP.TOTPGenerator.GoogleAuthenticator;
import com.example.cidaasv2.Controller.Repository.Configuration.TOTP.TOTPGenerator.TotpClock;
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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP.AuthenticateTOTPRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP.AuthenticateTOTPResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP.InitiateTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP.InitiateTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP.SetupTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP.SetupTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.TOTPEntity.TOTPEntity;
import com.example.cidaasv2.Service.Repository.Verification.TOTP.TOTPVerificationService;
import com.example.cidaasv2.Service.Scanned.ScannedRequestEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class TOTPConfigurationController {

    private String authenticationType,secretWithValue,secret;
    private String verificationType;
    private Context context;

    public static TOTPConfigurationController shared;

    public TOTPConfigurationController(Context contextFromCidaas) {

        verificationType="";
        context=contextFromCidaas;
        authenticationType="";
        //Todo setValue for authenticationType

    }

    String codeVerifier, codeChallenge;
    // Generate Code Challenge and Code verifier
    public void generateChallenge(){
        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        codeVerifier=generator.getCodeVerifier();
        codeChallenge= generator.getCodeChallenge(codeVerifier);

    }

    public static TOTPConfigurationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new TOTPConfigurationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


//Todo Configure TOTP by Passing the setupTOTPRequestEntity
    // 1.  Check For NotNull Values
    // 2. Generate Code Challenge
    // 3.  getAccessToken From Sub
    // 4.  Call Setup TOTP
    // 5.  Call Validate TOTP
    // 3.  Call Scanned TOTP
    // 3.  Call Enroll TOTP and return the result
    // 4.  Maintain logs based on flags

/*

    //Service call To SetupTOTPMFA
    public void configureTOTP(@NonNull final String sub, @NonNull final String baseurl,
                                 @NonNull final SetupTOTPMFARequestEntity setupTOTPMFARequestEntity,
                                 @NonNull final Result<EnrollTOTPMFAResponseEntity> enrollresult)
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
                            setupTOTPMFARequestEntity.getClient_id()!=null && !setupTOTPMFARequestEntity.getClient_id().equals(""))
                    {
                        //Todo Service call

                        TOTPVerificationService.getShared(context).setupTOTP(baseurl, accessTokenresult.getAccess_token(), codeChallenge,setupTOTPMFARequestEntity,null,new Result<SetupTOTPMFAResponseEntity>() {
                            @Override
                            public void success(final SetupTOTPMFAResponseEntity setupserviceresult) {

                            //    String queryString=;

                             */
/*   String [] stringArray = queryString.split("&", 2);
                                 secretWithValue=stringArray[0];
                                String [] stringArray1=secretWithValue.split("=",2);*//*

                                secret=setupserviceresult.getData().getSecret();

                                 if(secret!=null && !secret.equals(""))
                                 {
                                   DBHelper.getShared().addSecret(secret,sub);
                                 }
                                 else
                                 {
                                     String errorMessage="Invalid TOTP Secret";
                                     enrollresult.failure(WebAuthError.getShared(context).customException(417,errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                                 }

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
                                                    ,null, new Result<ValidateDeviceResponseEntity>() {
                                                        @Override
                                                        public void success(ValidateDeviceResponseEntity result) {
                                                            // call Scanned Service
                                                            TOTPVerificationService.getShared(context).scannedTOTP(baseurl,result.getData().getUsage_pass(),"",
                                                                    accessTokenresult.getAccess_token(),null,new Result<ScannedResponseEntity>() {
                                                                        @Override
                                                                        public void success(final ScannedResponseEntity result) {
                                                                            DBHelper.getShared().setUserDeviceId(result.getData().getUserDeviceId(),baseurl);

                                                                            EnrollTOTPMFARequestEntity enrollTOTPMFARequestEntity = new EnrollTOTPMFARequestEntity();
                                                                            if(sub != null && !sub.equals("") &&
                                                                                    result.getData().getUserDeviceId()!=null && !  result.getData().getUserDeviceId().equals("")) {

                                                                                TOTPEntity totp;
                                                                                if(secret!=null) {
                                                                                    totp=  generateTOTP(secret);

                                                                                    enrollTOTPMFARequestEntity.setVerifierPassword(totp.getTotp_string());
                                                                                    enrollTOTPMFARequestEntity.setStatusId("");
                                                                                    enrollTOTPMFARequestEntity.setUserDeviceId(result.getData().getUserDeviceId());
                                                                                }

                                                                            }
                                                                            else {
                                                                                enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
                                                                            }

                                                                            // call Enroll Service
                                                                            TOTPVerificationService.getShared(context).enrollTOTP(baseurl, accessTokenresult.getAccess_token(), enrollTOTPMFARequestEntity,null,new Result<EnrollTOTPMFAResponseEntity>() {
                                                                                @Override
                                                                                public void success(EnrollTOTPMFAResponseEntity serviceresult) {
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
                                                                     //       Toast.makeText(context, "Error on Scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }

                                                        @Override
                                                        public void failure(WebAuthError error) {
                                                            enrollresult.failure(error);
                                                          //  Toast.makeText(context, "Error on validate Device"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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



    //Login with TOTP
    public void LoginWithTOTP(@NonNull final String baseurl, @NonNull final String clientId, @NonNull final String trackId, @NonNull final String requestId,
                              @NonNull final InitiateTOTPMFARequestEntity initiateTOTPMFARequestEntity,
                              final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try {
            if(codeChallenge=="" || codeVerifier=="" || codeChallenge==null || codeVerifier==null) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";

            if (initiateTOTPMFARequestEntity.getUserDeviceId() != null && initiateTOTPMFARequestEntity.getUserDeviceId() != "") {
                //Do nothing
            } else {
                initiateTOTPMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }

            if (initiateTOTPMFARequestEntity.getUsageType() != null && initiateTOTPMFARequestEntity.getUsageType() != "" &&
                    initiateTOTPMFARequestEntity.getUserDeviceId() != null && initiateTOTPMFARequestEntity.getUserDeviceId() != "" &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                TOTPVerificationService.getShared(context).initiateTOTP(baseurl,codeChallenge ,initiateTOTPMFARequestEntity,null,
                        new Result<InitiateTOTPMFAResponseEntity>() {

                            //Todo Call Validate Device

                            @Override
                            public void success(final InitiateTOTPMFAResponseEntity serviceresult) {

                                new CountDownTimer(5000, 500) {
                                    String instceID = "";

                                    public void onTick(long millisUntilFinished) {
                                        instceID = Cidaas.instanceId;

                                        Timber.e("");
                                        if (instceID != null && instceID != "") {
                                            this.cancel();
                                            onFinish();
                                        }

                                    }

                                    public void onFinish() {
                                        if (instceID != null && instceID != "" && serviceresult.getData().getStatusId() != null && serviceresult.getData().getStatusId() != "") {
                                            //Device Validation Service
                                            DeviceVerificationService.getShared(context).validateDevice(baseurl, instceID, serviceresult.getData().getStatusId(), codeVerifier
                                                  , null , new Result<ValidateDeviceResponseEntity>() {

                                                        @Override
                                                        public void success(ValidateDeviceResponseEntity result) {

                                                            //Todo call initiate
                                                            initiateTOTPMFARequestEntity.setUsagePass(result.getData().getUsage_pass());
                                                            TOTPVerificationService.getShared(context).initiateTOTP(baseurl, codeChallenge, initiateTOTPMFARequestEntity,
                                                                  null,  new Result<InitiateTOTPMFAResponseEntity>() {

                                                                        @Override
                                                                        public void success(InitiateTOTPMFAResponseEntity serviceresult) {
                                                                            if (requestId != null && !requestId.equals("") && serviceresult.getData().getStatusId() != null &&
                                                                                    !serviceresult.getData().getStatusId().equals("")) {

                                                                                String sub="";

                                                                                AuthenticateTOTPRequestEntity authenticateTOTPRequestEntity = new AuthenticateTOTPRequestEntity();
                                                                                authenticateTOTPRequestEntity.setUserDeviceId(initiateTOTPMFARequestEntity.getUserDeviceId());
                                                                                authenticateTOTPRequestEntity.setStatusId(serviceresult.getData().getStatusId());

                                                                                String secretFromDB=DBHelper.getShared().getSecret(sub);
                                                                                String totp = generateTOTP(secretFromDB).getTotp_string();

                                                                                authenticateTOTPRequestEntity.setVerifierPassword(totp);


                                                                                TOTPVerificationService.getShared(context).authenticateTOTP(baseurl, authenticateTOTPRequestEntity, null,new Result<AuthenticateTOTPResponseEntity>() {
                                                                                    @Override
                                                                                    public void success(AuthenticateTOTPResponseEntity result) {
                                                                                        //Todo Call Resume with Login Service
                                                                                        //  loginresult.success(result);
                                                                                       // Toast.makeText(context, "Sucess TOTP", Toast.LENGTH_SHORT).show();
                                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

                                                                                        //Todo Check not Null values
                                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                                        resumeLoginRequestEntity.setUsageType(initiateTOTPMFARequestEntity.getUsageType());
                                                                                        resumeLoginRequestEntity.setVerificationType("TOTP");
                                                                                        resumeLoginRequestEntity.setClient_id(clientId);
                                                                                        resumeLoginRequestEntity.setRequestId(requestId);

                                                                                        if (initiateTOTPMFARequestEntity.getUsageType().equals(UsageType.MFA)) {
                                                                                            resumeLoginRequestEntity.setTrack_id(trackId);
                                                                                            LoginController.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, loginresult);
                                                                                        } else if (initiateTOTPMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS)) {
                                                                                            resumeLoginRequestEntity.setTrack_id("");
                                                                                            LoginController.getShared(context).continuePasswordless(baseurl, resumeLoginRequestEntity, loginresult);

                                                                                        }
                                                                                    }

                                                                                    @Override
                                                                                    public void failure(WebAuthError error) {
                                                                                        loginresult.failure(error);
                                                                                    }
                                                                                });
                                                                                // result.success(serviceresult);
                                                                            }
                                                                            else {
                                                                                String errorMessage="Status Id Must not be null";
                                                                                loginresult.failure(WebAuthError.getShared(context).customException(417,errorMessage, HttpStatusCode.EXPECTATION_FAILED));

                                                                            }
                                                                        }


                                                                        // result.success(serviceresult);


                                                                        @Override
                                                                        public void failure(WebAuthError error) {
                                                                            loginresult.failure(error);
                                                                   //         Toast.makeText(context, "Error on validate Device" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });


                                                        }

                                                        @Override
                                                        public void failure(WebAuthError error) {
                                                            loginresult.failure(error);
                                                        }
                                                    });
                                        } else {

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


    public TOTPEntity generateTOTP(String secret)
    {
        String TOTP="";
        try
        {
            TotpClock totpClock;
            int local_totp, local_totp1;



            totpClock = new TotpClock(context);
            long temp = totpClock.currentTimeMillis();
            local_totp = (int)((temp / 1000) % 30);
            long temp1 = temp - 1000;
            local_totp1 = (int)((temp1 / 100) % 300);

            // set progress state

            DecimalFormat format = new DecimalFormat("00");
            String timercount = format.format(30 - local_totp);

            TOTP = GoogleAuthenticator.getTOTPCode(secret);

            TOTPEntity totpEntity=new TOTPEntity();
            totpEntity.setTimer_count(timercount);
            totpEntity.setTotp_string(TOTP);


          /*  if(local_totp == 0) {

            }
*/
            return totpEntity;
        }
        catch (Exception e) {
          return null;
        }
    }



    //Service call To SetupTOTPMFA
    public void configureTOTP(@NonNull final String sub, @NonNull final String baseurl,
                                 @NonNull final SetupTOTPMFARequestEntity setupTOTPMFARequestEntity,
                                 @NonNull final Result<EnrollTOTPMFAResponseEntity> enrollresult)
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


                    
                    setupTOTP(baseurl,sub,accessTokenresult.getAccess_token(),setupTOTPMFARequestEntity,enrollresult);
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


    private void setupTOTP(final String baseurl, final String sub, final String accessToken,
                           final SetupTOTPMFARequestEntity setupTOTPMFARequestEntity, final Result<EnrollTOTPMFAResponseEntity> enrollResult)
    {
        try
        {
            if (baseurl != null && !baseurl.equals("") && accessToken != null && !accessToken.equals("") &&
                    setupTOTPMFARequestEntity.getClient_id()!=null && !setupTOTPMFARequestEntity.getClient_id().equals(""))
            {
                //Done Service call

                TOTPVerificationService.getShared(context).setupTOTP(baseurl, accessToken,
                        setupTOTPMFARequestEntity,null,new Result<SetupTOTPMFAResponseEntity>() {
                            @Override
                            public void success(final SetupTOTPMFAResponseEntity setupserviceresult) {


                                secret=setupserviceresult.getData().getSecret();

                                if(secret!=null && !secret.equals(""))
                                {
                                    DBHelper.getShared().addSecret(secret,sub);
                                }
                                else
                                {
                                    String errorMessage="Invalid TOTP Secret";
                                    enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_TOTP_MFA_FAILURE,errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                                }

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

                                            SetupTOTPMFARequestEntity setupTOTPMFARequestEntity1 = new SetupTOTPMFARequestEntity();
                                            setupTOTPMFARequestEntity1.setUsage_pass(instceID);
                                            // call Scanned Service
                                            TOTPVerificationService.getShared(context).setupTOTP(baseurl, accessToken,
                                                    setupTOTPMFARequestEntity1, null, new Result<SetupTOTPMFAResponseEntity>() {
                                                        @Override
                                                        public void success(final SetupTOTPMFAResponseEntity result) {
                                                            DBHelper.getShared().setUserDeviceId(result.getData().getUdi(), baseurl);


                                                            EnrollTOTPMFARequestEntity enrollTOTPMFARequestEntity = new EnrollTOTPMFARequestEntity();
                                                            TOTPEntity totp;
                                                            if(secret!=null) {
                                                                totp=  generateTOTP(secret);

                                                                enrollTOTPMFARequestEntity.setVerifierPassword(totp.getTotp_string());
                                                                enrollTOTPMFARequestEntity.setStatusId(result.getData().getSt());
                                                                enrollTOTPMFARequestEntity.setUserDeviceId(result.getData().getUdi());
                                                                enrollTOTPMFARequestEntity.setClient_id(setupTOTPMFARequestEntity.getClient_id());

                                                            }


                                                            enrollTOTP(baseurl,accessToken,enrollTOTPMFARequestEntity,enrollResult);


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
            enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_TOTP_MFA_FAILURE,
                    "TOTP Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }



    public void scannedWithTOTP(final String baseurl,  String statusId,String sub,String secret, String clientId, final Result<ScannedResponseEntity> scannedResult)
    {
        try
        {
            if (baseurl != null && !baseurl.equals("")  && statusId!=null && !statusId.equals("") && clientId!=null && !clientId.equals("")) {

                final ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
                scannedRequestEntity.setStatusId(statusId);
                scannedRequestEntity.setClient_id(clientId);


                if(secret!=null && !secret.equals(""))
                {
                    DBHelper.getShared().addSecret(secret,sub);
                }
                else
                {
                    String errorMessage="Invalid TOTP Secret";
                    scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_TOTP_MFA_FAILURE,errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                }

                TOTPVerificationService.getShared(context).scannedTOTP(baseurl,  scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
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

                                    TOTPVerificationService.getShared(context).scannedTOTP(baseurl,  scannedRequestEntity, null, new Result<ScannedResponseEntity>() {

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
                scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_TOTP_MFA_FAILURE,
                        "BaseURL or ClientId or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }

        }
        catch (Exception e)
        {
            scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_TOTP_MFA_FAILURE,
                    "TOTP Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }



    public void enrollTOTP(@NonNull final String baseurl, @NonNull final String accessToken,
                              @NonNull EnrollTOTPMFARequestEntity enrollTOTPMFARequestEntity, final Result<EnrollTOTPMFAResponseEntity> enrollResult)
    {
        try
        {

            if(baseurl!=null && !baseurl.equals("") && accessToken!=null && !accessToken.equals("")) {

                if (enrollTOTPMFARequestEntity.getUserDeviceId() != null && !enrollTOTPMFARequestEntity.getUserDeviceId().equals("") &&
                        enrollTOTPMFARequestEntity.getStatusId() != null && !enrollTOTPMFARequestEntity.getStatusId().equals("") &&
                        enrollTOTPMFARequestEntity.getClient_id() != null && !enrollTOTPMFARequestEntity.getClient_id().equals("") &&
                        enrollTOTPMFARequestEntity.getVerifierPassword() != null && !enrollTOTPMFARequestEntity.getVerifierPassword().equals("")) {

                    // call Enroll Service
                    TOTPVerificationService.getShared(context).enrollTOTP(baseurl, accessToken, enrollTOTPMFARequestEntity,
                            null, new Result<EnrollTOTPMFAResponseEntity>() {

                                @Override
                                public void success(final EnrollTOTPMFAResponseEntity serviceresult) {

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
                                                EnrollTOTPMFARequestEntity enrollTOTPMFARequestEntity = new EnrollTOTPMFARequestEntity();
                                                enrollTOTPMFARequestEntity.setUsage_pass(instceID);

                                                // call Enroll Service
                                                TOTPVerificationService.getShared(context).enrollTOTP(baseurl, accessToken, enrollTOTPMFARequestEntity,
                                                        null, new Result<EnrollTOTPMFAResponseEntity>() {
                                                            @Override
                                                            public void success(EnrollTOTPMFAResponseEntity serviceresult) {
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
                    enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_TOTP_MFA_FAILURE,
                            "UserdeviceId or Verifierpassword or clientId or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                }
            }
            else
            {
                enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_TOTP_MFA_FAILURE,
                        "BaseURL or accessToken must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }


        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_TOTP_MFA_FAILURE,
                    "TOTP Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }


    //Login with TOTP
    public void LoginWithTOTP(@NonNull final String baseurl, @NonNull final String clientId,
                              @NonNull final String trackId, @NonNull final String requestId,
                              @NonNull final InitiateTOTPMFARequestEntity initiateTOTPMFARequestEntity,
                              final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(codeChallenge.equals("") && codeVerifier.equals("")) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";
            if(initiateTOTPMFARequestEntity.getUserDeviceId() != null && !initiateTOTPMFARequestEntity.getUserDeviceId().equals(""))
            {
                //Do nothing
            }
            else
            {
                initiateTOTPMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }
            initiateTOTPMFARequestEntity.setClient_id(clientId);


            if (    initiateTOTPMFARequestEntity.getUsageType() != null && !initiateTOTPMFARequestEntity.getUsageType().equals("") &&
                    initiateTOTPMFARequestEntity.getUserDeviceId() != null && !initiateTOTPMFARequestEntity.getUserDeviceId().equals("") &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                TOTPVerificationService.getShared(context).initiateTOTP(baseurl,codeChallenge, initiateTOTPMFARequestEntity,null,
                        new Result<InitiateTOTPMFAResponseEntity>() {

                            @Override
                            public void success(final InitiateTOTPMFAResponseEntity serviceresult) {

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
                                            final InitiateTOTPMFARequestEntity initiateTOTPMFARequestEntity=new InitiateTOTPMFARequestEntity();
                                            initiateTOTPMFARequestEntity.setUsagePass(instceID);

                                            final String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);

                                            TOTPVerificationService.getShared(context).initiateTOTP(baseurl, codeChallenge, initiateTOTPMFARequestEntity,null,
                                                    new Result<InitiateTOTPMFAResponseEntity>() {

                                                        @Override
                                                        public void success(InitiateTOTPMFAResponseEntity result) {
                                                            if ( result.getData().getStatusId() != null &&
                                                                    !result.getData().getStatusId().equals("")) {


                                                                AuthenticateTOTPRequestEntity authenticateTOTPRequestEntity = new AuthenticateTOTPRequestEntity();
                                                                authenticateTOTPRequestEntity.setUserDeviceId(userDeviceId);
                                                                authenticateTOTPRequestEntity.setStatusId(result.getData().getStatusId());


                                                                String secretFromDB=DBHelper.getShared().getSecret(initiateTOTPMFARequestEntity.getSub());
                                                                String totp = generateTOTP(secretFromDB).getTotp_string();

                                                                authenticateTOTPRequestEntity.setVerifierPassword(totp);
                                                                authenticateTOTPRequestEntity.setClient_id(clientId);


                                                                authenticateTOTP(baseurl, authenticateTOTPRequestEntity, new Result<AuthenticateTOTPResponseEntity>() {
                                                                    @Override
                                                                    public void success(AuthenticateTOTPResponseEntity result) {

                                                                        //Todo Call Resume with Login Service

                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

                                                                        //Todo Check not Null values
                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                        resumeLoginRequestEntity.setVerificationType("TOTP");
                                                                        resumeLoginRequestEntity.setUsageType(initiateTOTPMFARequestEntity.getUsageType());
                                                                        resumeLoginRequestEntity.setClient_id(clientId);
                                                                        resumeLoginRequestEntity.setRequestId(requestId);

                                                                        if (initiateTOTPMFARequestEntity.getUsageType().equals(UsageType.MFA)) {
                                                                            resumeLoginRequestEntity.setTrack_id(trackId);
                                                                            LoginController.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, loginresult);
                                                                        } else if (initiateTOTPMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS)) {
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
                                                                String errorMessage="Status Id or TOTP Must not be null";
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


    //Authenticate TOTP

    public void authenticateTOTP(final String baseurl, final AuthenticateTOTPRequestEntity authenticateTOTPRequestEntity, final Result<AuthenticateTOTPResponseEntity> authResult)
    {
        try
        {
            TOTPVerificationService.getShared(context).authenticateTOTP(baseurl, authenticateTOTPRequestEntity,null, new Result<AuthenticateTOTPResponseEntity>() {
                @Override
                public void success(final AuthenticateTOTPResponseEntity serviceresult) {


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
                                AuthenticateTOTPRequestEntity authenticateTOTPRequestEntity=new AuthenticateTOTPRequestEntity();
                                authenticateTOTPRequestEntity.setUsage_pass(instceID);

                                TOTPVerificationService.getShared(context).authenticateTOTP(baseurl, authenticateTOTPRequestEntity,null, new Result<AuthenticateTOTPResponseEntity>() {
                                    @Override
                                    public void success(AuthenticateTOTPResponseEntity result) {
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
            authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_TOTP_MFA_FAILURE,
                    "TOTP Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
        }
    }




   /* //setupTOTPMFA
    public void setupTOTPMFA(@NonNull String sub, @NonNull final Result<SetupTOTPMFAResponseEntity> result){
        try {
            String baseurl="";
            if(savedProperties==null){

                savedProperties= DBHelper.getShared().getLoginProperties();
            }
            if(savedProperties==null){
                //Read from file if localDB is null
                readFromFile(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> loginProperties) {
                        savedProperties=loginProperties;
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }

            if (savedProperties.get("DomainURL").equals("") || savedProperties.get("DomainURL") == null || savedProperties == null) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Setup TOTP MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } if (savedProperties.get("ClientId").equals("") || savedProperties.get("ClientId") == null || savedProperties == null) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Accept Consent readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            }
            else {
                baseurl = savedProperties.get("DomainURL");

                if ( sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                    final String finalBaseurl = baseurl;
                    getAccessToken(sub, new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accesstokenresult) {

                            String logoUrl= "https://docs.cidaas.de/assets/logoss.png";



                            SetupTOTPMFARequestEntity setupTOTPMFARequestEntity=new SetupTOTPMFARequestEntity();
                            setupTOTPMFARequestEntity.setClient_id( savedProperties.get("ClientId"));
                            setupTOTPMFARequestEntity.setLogoUrl(logoUrl);


                            setupTOTPMFAService(accesstokenresult.getAccess_token(), finalBaseurl,setupTOTPMFARequestEntity,result);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            result.failure(error);
                        }
                    });


                }

            }


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To SetupTOTPMFA
    private void setupTOTPMFAService(@NonNull final String AccessToken, @NonNull String baseurl,
                                     @NonNull SetupTOTPMFARequestEntity setupTOTPMFARequestEntity,
                                     @NonNull final Result<SetupTOTPMFAResponseEntity> result)
    {
        try{

            if (baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call
                OauthService.getShared(context).setupTOTPMFA(baseurl, AccessToken,codeChallenge,setupTOTPMFARequestEntity,
                        new Result<SetupTOTPMFAResponseEntity>() {
                            @Override
                            public void success(final SetupTOTPMFAResponseEntity serviceresult) {

                                new CountDownTimer(5000, 500) {
                                    String instceID="";
                                    public void onTick(long millisUntilFinished) {
                                        instceID=getInstanceId();
                                        if(instceID!=null && instceID!="")
                                        {
                                            onFinish();
                                        }

                                    }

                                    public void onFinish() {
                                        if(instceID!=null && instceID!="")
                                        {
                                            //Todo Call Next Service cALL TO Validate DEVICE
                                            validateDevice(instceID, serviceresult.getData().getStatusId(), new Result<ValidateDeviceResponseEntity>() {
                                                @Override
                                                public void success(ValidateDeviceResponseEntity result) {
                                                    //Todo call Next service
                                                    scannedTOTP(result.getData().getUsage_pass(), serviceresult.getData().getStatusId(), AccessToken,null,new Result<ScannedResponseEntity>() {
                                                        @Override
                                                        public void success(ScannedResponseEntity result) {
                                                            Timber.i(result.getData().getUserDeviceId()+"USewr Device id");
                                                            Toast.makeText(context, result.getData().getUserDeviceId()+"USewr Device id", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void failure(WebAuthError error) {
                                                            Toast.makeText(context, "Error on Scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void failure(WebAuthError error) {
                                                    Toast.makeText(context, "Error on validate Device"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        else {
                                            // return Error Message
                                            webAuthError=WebAuthError.getShared(context).deviceVerificationFailureException();
                                            result.failure(webAuthError);
                                        }
                                    }
                                }.start();


                            }

                            @Override
                            public void failure(WebAuthError error) {
                                result.failure(error);
                            }
                        });
            }
            else
            {
                webAuthError=webAuthError.propertyMissingException();
                webAuthError.ErrorMessage="one of the Login properties missing";
                result.failure(webAuthError);
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

    //Scanned TOTP
    private void scannedTOTP(@NonNull String usagePass,@NonNull String statusId,@NonNull String AccessToken, @NonNull final Result<ScannedResponseEntity> result)
    {
        try {
            String baseurl="";
            if(savedProperties==null){

                savedProperties=DBHelper.getShared().getLoginProperties();
            }
            if(savedProperties==null){
                //Read from file if localDB is null
                readFromFile(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> loginProperties) {
                        savedProperties=loginProperties;
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }



            if (savedProperties.get("DomainURL").equals("") || savedProperties.get("DomainURL") == null || savedProperties == null) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Setup TOTP MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            }
            if (savedProperties.get("ClientId").equals("") || savedProperties.get("ClientId") == null || savedProperties == null) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Accept Consent readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            }
            else {
                baseurl = savedProperties.get("DomainURL");

                if ( statusId != null && !statusId.equals("") && usagePass != null && !usagePass.equals("") && baseurl != null
                        && !baseurl.equals("")) {

                    scannedTOTPService(usagePass, baseurl,statusId,AccessToken,result);


                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To Scanned TOTP Service
    private void scannedTOTPService(@NonNull String usagePass,@NonNull String baseurl,
                                    @NonNull String statusId,@NonNull String AccessToken,
                                    @NonNull final Result<ScannedResponseEntity> scannedResponseResult)
    {
        try{

            if ( statusId != null && !statusId.equals("") && usagePass != null && !usagePass.equals("") && baseurl != null
                    && !baseurl.equals("")) {
                //Todo Service call

                if(codeChallenge==null){
                    generateChallenge();
                }
                OauthService.getShared(context).scannedTOTP(baseurl, usagePass, statusId,AccessToken,
                        new Result<ScannedResponseEntity>() {
                            @Override
                            public void success(final ScannedResponseEntity serviceresult) {
                                //Todo Call Scanned Service


                                scannedResponseResult.success(serviceresult);
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                scannedResponseResult.failure(error);
                            }
                        });
            }
            else
            {
                webAuthError=webAuthError.propertyMissingException();
                webAuthError.ErrorMessage="one of the Login properties missing";
                scannedResponseResult.failure(webAuthError);
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

    //enrollTOTPMFA
    public void enrollTOTPMFA(@NonNull final EnrollTOTPMFARequestEntity enrollTOTPMFARequestEntity, @NonNull final Result<EnrollTOTPMFAResponseEntity> result){
        try {
            String baseurl="";
            if(savedProperties==null){

                savedProperties=DBHelper.getShared().getLoginProperties();
            }
            if(savedProperties==null){
                //Read from file if localDB is null
                readFromFile(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> loginProperties) {
                        savedProperties=loginProperties;
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }

            if (savedProperties.get("DomainURL").equals("") || savedProperties.get("DomainURL") == null || savedProperties == null) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Setup TOTP MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");

                if ( enrollTOTPMFARequestEntity.getVerifierPassword() != null && !enrollTOTPMFARequestEntity.getVerifierPassword().equals("") &&
                        enrollTOTPMFARequestEntity.getSub() != null && enrollTOTPMFARequestEntity.getSub()  != null &&
                        enrollTOTPMFARequestEntity.getStatusId() != null && enrollTOTPMFARequestEntity.getStatusId()  != null &&
                        baseurl != null && !baseurl.equals("")) {

                    final String finalBaseurl = baseurl;
                    getAccessToken(enrollTOTPMFARequestEntity.getSub(), new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accesstokenresult) {
                            enrollTOTPMFAService(accesstokenresult.getAccess_token(), finalBaseurl,enrollTOTPMFARequestEntity,result);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            result.failure(error);
                        }
                    });


                }
                else {
                    webAuthError=webAuthError.propertyMissingException();
                    webAuthError.ErrorMessage="one of the Login properties missing";
                    result.failure(webAuthError);
                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To enrollTOTPMFA
    private void enrollTOTPMFAService(@NonNull String AccessToken, @NonNull String baseurl,
                                      @NonNull final EnrollTOTPMFARequestEntity enrollTOTPMFARequestEntity, @NonNull final Result<EnrollTOTPMFAResponseEntity> result){
        try{

            if (enrollTOTPMFARequestEntity.getVerifierPassword() != null && !enrollTOTPMFARequestEntity.getVerifierPassword().equals("") &&
                    enrollTOTPMFARequestEntity.getSub() != null && enrollTOTPMFARequestEntity.getSub()  != null &&
                    enrollTOTPMFARequestEntity.getStatusId() != null && enrollTOTPMFARequestEntity.getStatusId()  != null &&
                    baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call
                OauthService.getShared(context).enrollTOTPMFA(baseurl, AccessToken, enrollTOTPMFARequestEntity,null,new Result<EnrollTOTPMFAResponseEntity>() {
                    @Override
                    public void success(EnrollTOTPMFAResponseEntity serviceresult) {
                        result.success(serviceresult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }
            else
            {
                webAuthError=webAuthError.propertyMissingException();
                webAuthError.ErrorMessage="one of the Login properties missing";
                result.failure(webAuthError);
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

*/}
