package com.example.cidaasv2.Controller.Repository.Configuration.Face;

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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Face.InitiateFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Face.InitiateFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face.SetupFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face.SetupFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.Face.FaceVerificationService;
import com.example.cidaasv2.Service.Scanned.ScannedRequestEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import java.io.File;
import java.util.Dictionary;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class FaceConfigurationController {

    public static String logoURLlocal="https://cdn.shortpixel.ai/client/q_glossy,ret_img/https://www.cidaas.com/wp-content/uploads/2018/02/logo.png";
    //Local variables

    private String authenticationType;
    private String verificationType;
    private Context context;
    private String statusIdFromSetup,userDeviceIdFromSetup;

    public static FaceConfigurationController shared;

    public FaceConfigurationController(Context contextFromCidaas) {

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

    public static FaceConfigurationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new FaceConfigurationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            LogFile.getShared(contextFromCidaas).addFailureLog("FaceConfigurationController instance Creation Exception:-"+e.getMessage());
        }
        return shared;
    }


    public void configureFace(@NonNull final File FaceImageFile,@NonNull final String sub,@NonNull final String logoURL,
                              @NonNull final int attempt,
                              @NonNull final Result<EnrollFaceMFAResponseEntity> enrollresult )
    {
        try
        {
            LogFile.getShared(context).addInfoLog("Exception :FaceConfigurationController :configureFace()",
                    "FaceImageFile:-"+FaceImageFile.getAbsolutePath() +"sub"+sub);
            
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");


                    if (sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                        if(!logoURL.equals("") && logoURL!=null) {
                            logoURLlocal=logoURL;
                        }

                        // String logoUrl = "https://docs.cidaas.de/assets/logoss.png";

                        SetupFaceMFARequestEntity setupFaceMFARequestEntity = new SetupFaceMFARequestEntity();
                        setupFaceMFARequestEntity.setClient_id(result.get("ClientId"));
                        setupFaceMFARequestEntity.setLogoUrl(logoURLlocal);


                       configureFace(FaceImageFile,sub,baseurl,attempt,setupFaceMFARequestEntity,enrollresult);



                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollresult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty",
                            "Exception :Face configuration Controller :configureFace()"));
                }
            });

        }
        catch (Exception e)
        {
            enrollresult.failure(WebAuthError.getShared(context).methodException("Exception :Face configuration Controller :configureFace()",
                    WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,e.getMessage()));
        }
    }



    //Service call To SetupFaceMFA
    public void configureFace(@NonNull final File FaceImageFile,@NonNull final String sub, @NonNull final String baseurl,@NonNull final int attempt,
                               @NonNull final SetupFaceMFARequestEntity setupFaceMFARequestEntity,
                               @NonNull final Result<EnrollFaceMFAResponseEntity> enrollresult)
    {

        try{
            LogFile.getShared(context).addInfoLog("Exception :FaceConfigurationController :configureFace()","FaceImage"+FaceImageFile.getAbsolutePath()
                    +"sub"+sub);

            if(codeChallenge.equals("") || codeVerifier.equals("") || codeChallenge==null || codeVerifier==null) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.usagePass ="";

            AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>()
            {
                @Override
                public void success(final AccessTokenEntity accessTokenresult) {

                    if(attempt==1) {
                        setupFace(baseurl, accessTokenresult.getAccess_token(), FaceImageFile, setupFaceMFARequestEntity, enrollresult);
                    }
                    else {

                        EnrollFaceMFARequestEntity enrollFaceMFARequestEntityWithAttempts = new EnrollFaceMFARequestEntity();
                        enrollFaceMFARequestEntityWithAttempts.setImagetoSend(FaceImageFile);
                        enrollFaceMFARequestEntityWithAttempts.setStatusId(statusIdFromSetup);
                        enrollFaceMFARequestEntityWithAttempts.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
                        enrollFaceMFARequestEntityWithAttempts.setClient_id(setupFaceMFARequestEntity.getClient_id());
                        enrollFaceMFARequestEntityWithAttempts.setAttempt(attempt);

                        enrollFace(baseurl,accessTokenresult.getAccess_token(),enrollFaceMFARequestEntityWithAttempts,enrollresult);
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
            enrollresult.failure(WebAuthError.getShared(context).methodException("Exception :Face configuration Controller :configureFace()",
                    WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,e.getMessage()));
        }
    }


    private void setupFace(final String baseurl, final String accessToken, @NonNull final File FaceImageFile,
                           final SetupFaceMFARequestEntity setupFaceMFARequestEntity, final Result<EnrollFaceMFAResponseEntity> enrollResult)
    {
        try
        {
            //Log
            LogFile.getShared(context).addInfoLog("Info :FaceConfigurationController :setupFace()",
                    "AccessToken:-"+accessToken+"Baseurl:-"+ baseurl+"FileName:"+FaceImageFile.getAbsolutePath());

            if (baseurl != null && !baseurl.equals("") && accessToken != null && !accessToken.equals("") &&
                    setupFaceMFARequestEntity.getClient_id()!=null && !setupFaceMFARequestEntity.getClient_id().equals(""))
            {
                //Done Service call

                FaceVerificationService.getShared(context).setupFaceMFA(baseurl, accessToken,
                        setupFaceMFARequestEntity,null,new Result<SetupFaceMFAResponseEntity>() {
                            @Override
                            public void success(final SetupFaceMFAResponseEntity setupserviceresult) {

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

                                            SetupFaceMFARequestEntity setupFaceMFARequestEntity1 = new SetupFaceMFARequestEntity();
                                            setupFaceMFARequestEntity1.setUsage_pass(instceID);
                                            // call Scanned Service
                                            FaceVerificationService.getShared(context).setupFaceMFA(baseurl, accessToken,
                                                    setupFaceMFARequestEntity1, null, new Result<SetupFaceMFAResponseEntity>() {
                                                        @Override
                                                        public void success(final SetupFaceMFAResponseEntity result) {
                                                            DBHelper.getShared().setUserDeviceId(result.getData().getUdi(), baseurl);


                                                            statusIdFromSetup=result.getData().getSt();
                                                            //Entity For Face
                                                            EnrollFaceMFARequestEntity enrollFaceMFARequestEntity = new EnrollFaceMFARequestEntity();
                                                            enrollFaceMFARequestEntity.setImagetoSend(FaceImageFile);
                                                            enrollFaceMFARequestEntity.setStatusId(statusIdFromSetup);
                                                            enrollFaceMFARequestEntity.setUserDeviceId(result.getData().getUdi());
                                                            enrollFaceMFARequestEntity.setClient_id(setupFaceMFARequestEntity.getClient_id());


                                                            enrollFace(baseurl,accessToken,enrollFaceMFARequestEntity,enrollResult);


                                                        }

                                                        @Override
                                                        public void failure(WebAuthError error) {
                                                            enrollResult.failure(error);
                                                        }
                                                    });
                                        }

                                        else {
                                            enrollResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException("Error :Face configuration Controller :setupFace()"));
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
                enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl or AccessToken or clientId must not be null",
                        "Exception :Face configuration Controller :setupFace()"));
            }
        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception :Face configuration Controller :setupFace()",
                    WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,e.getMessage()));

        }
    }



    public void scannedWithFace(final String statusId, final Result<ScannedResponseEntity> scannedResult)
    {
        try {
            LogFile.getShared(context).addInfoLog("Info :FaceConfigurationController :scannedWithFace()","StatusId:-"+statusId);
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    String clientId = loginPropertiesResult.get("ClientId");

                    if (statusId != null && !statusId.equals("")) {

                        final ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
                        scannedRequestEntity.setStatusId(statusId);
                        scannedRequestEntity.setClient_id(clientId);


                        FaceScannedService(baseurl, scannedRequestEntity,scannedResult);
                    } else {
                        scannedResult.failure(WebAuthError.getShared(context).propertyMissingException(
                                "BaseURL or ClientId or StatusID must not be empty", "Error :Face configuration Controller :scannedWithFace()"));
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
            scannedResult.failure(WebAuthError.getShared(context).methodException("Exception :Face configuration Controller :scannedWithFace()",
                    WebAuthErrorCode.SCANNED_FACE_MFA_FAILURE,e.getMessage()));

        }
    }

    private void FaceScannedService(final String baseurl, ScannedRequestEntity scannedRequestEntity, final Result<ScannedResponseEntity> scannedResult) {
     try {
         LogFile.getShared(context).addInfoLog("Info :FaceConfigurationController :FaceScannedService()","Baseurl:-"+baseurl
         +" StatusId:-"+scannedRequestEntity.getStatusId()+"ClientId"+scannedRequestEntity.getClient_id());

         FaceVerificationService.getShared(context).scannedFace(baseurl, scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
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

                             FaceVerificationService.getShared(context).scannedFace(baseurl, scannedRequestEntity, null, scannedResult);
                         } else {
                             scannedResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException("Error :Face configuration Controller :FaceScannedService()"));
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
     catch (Exception e)
     {
         scannedResult.failure(WebAuthError.getShared(context).methodException("Exception :Face configuration Controller :FaceScannedService()",
                 WebAuthErrorCode.SCANNED_FACE_MFA_FAILURE,e.getMessage()));
     }

    }

    public void enrollFace(@NonNull final File Face, @NonNull final String sub,@NonNull final String statusId,@NonNull final int attempt,  final Result<EnrollFaceMFAResponseEntity> enrollResult) {
    try
    {

        //Log
        LogFile.getShared(context).addInfoLog("Info :Face configuration Controller :enrollFace()",
                " Sub:-"+ sub +" StatusId:-"+statusId
                        +" ATTEMPT:-"+attempt+" Face:-"+Face.getAbsolutePath());

        CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {

                final String baseurl = result.get("DomainURL");
                final String clientId= result.get("ClientId");
                String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);

                final EnrollFaceMFARequestEntity enrollFaceMFARequestEntity=new EnrollFaceMFARequestEntity();
                enrollFaceMFARequestEntity.setImagetoSend(Face);
                enrollFaceMFARequestEntity.setStatusId(statusId);
                enrollFaceMFARequestEntity.setUserDeviceId(userDeviceId);
                enrollFaceMFARequestEntity.setClient_id(clientId);
                enrollFaceMFARequestEntity.setAttempt(attempt);

                AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(AccessTokenEntity accessTokenResult) {
                         enrollFace(baseurl,accessTokenResult.getAccess_token(),enrollFaceMFARequestEntity,enrollResult);
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
         enrollResult.failure(WebAuthError.getShared(context).methodException("Exception :Face configuration Controller :configureFace()",
                 WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,e.getMessage()));
    }
    }


    public void enrollFace(@NonNull final String baseurl, @NonNull final String accessToken,
                           @NonNull final EnrollFaceMFARequestEntity enrollFaceMFARequestEntity, final Result<EnrollFaceMFAResponseEntity> enrollResult)
    {
        try
        {


            if(baseurl!=null && !baseurl.equals("") && accessToken!=null && !accessToken.equals("")) {

                if (enrollFaceMFARequestEntity.getUserDeviceId() != null && !enrollFaceMFARequestEntity.getUserDeviceId().equals("") &&
                        enrollFaceMFARequestEntity.getClient_id() != null && !enrollFaceMFARequestEntity.getClient_id().equals("") &&
                        enrollFaceMFARequestEntity.getStatusId() != null && !enrollFaceMFARequestEntity.getStatusId().equals("")
                      ) {


                    final EnrollFaceMFARequestEntity enrollFaceMFARequestEntityWithPass=new EnrollFaceMFARequestEntity();



                    if(enrollFaceMFARequestEntity.getImagetoSend() != null)
                    {
                        enrollFaceMFARequestEntityWithPass.setImagetoSend(enrollFaceMFARequestEntity.getImagetoSend());
                        enrollFaceMFARequestEntityWithPass.setClient_id(enrollFaceMFARequestEntity.getClient_id()
                        );
                        enrollFaceMFARequestEntity.setImagetoSend(null);
                    }
                    else
                    {
                        enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,
                                "Image must not be empty", "Error :Face configuration Controller :enrollFace()"));
                    }


                    //Log
                    LogFile.getShared(context).addInfoLog("Info :Face configuration Controller :enrollFace()",
                            " Baseurl:-"+baseurl+" AccessToken:-"+ accessToken+" ClientId:-"+enrollFaceMFARequestEntity.getClient_id()
                                    +" StatusId:-"+enrollFaceMFARequestEntity.getStatusId()
                                    +" userDeviceId:-"+enrollFaceMFARequestEntity.getUserDeviceId()+" ImageFile:-"+enrollFaceMFARequestEntity.getImagetoSend().getAbsolutePath());



                    // call Enroll Service
                    FaceVerificationService.getShared(context).enrollFace(baseurl, accessToken, enrollFaceMFARequestEntity,
                            null, new Result<EnrollFaceMFAResponseEntity>() {

                                @Override
                                public void success(final EnrollFaceMFAResponseEntity serviceresult) {

                                    Cidaas.usagePass = "";

                                    //Timer
                                    new CountDownTimer(5000, 500) {
                                        String usage_pass = "";

                                        public void onTick(long millisUntilFinished) {
                                            usage_pass = Cidaas.usagePass;

                                            Timber.e("");
                                            if (usage_pass != null && !usage_pass.equals("")) {
                                                this.cancel();
                                                onFinish();
                                            }

                                        }

                                        public void onFinish() {
                                            if (usage_pass != null && !usage_pass.equals("")) {



                                                enrollFaceMFARequestEntityWithPass.setUsage_pass(usage_pass);

                                                if(enrollFaceMFARequestEntityWithPass.getImagetoSend() != null) {

                                                    //Log
                                                    LogFile.getShared(context).addInfoLog("Info :Face configuration Controller :enrollFace()",
                                                            " UsagePass:-"+ usage_pass+" Baseurl:-"+baseurl);
                                                    // call Enroll Service
                                                    FaceVerificationService.getShared(context).enrollFace(baseurl, accessToken, enrollFaceMFARequestEntityWithPass,
                                                            null, enrollResult);
                                                }
                                                else {
                                                    enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("Image must not be empty",
                                                            "Error :Face configuration Controller :enrollFace()"));
                                                }
                                            }
                                            else {
                                                // return Error Message
                                                enrollResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException("Error :Face configuration Controller :enrollFace()"));

                                            }

                                        }
                                    }.start();
                                }

                                @Override
                                public void failure(WebAuthError error) {
                                    enrollResult.failure(error);
                                }
                            });
                } else {
                    enrollResult.failure(WebAuthError.getShared(context).propertyMissingException(
                            "UserdeviceId or Client Id or StatusID  must not be empty","Error :Face configuration Controller :enrollFace()"));
                }
            }
            else
            {
                enrollResult.failure(WebAuthError.getShared(context).propertyMissingException(
                        "BaseURL or accessToken must not be empty","Error :Face configuration Controller :enrollFace()"));
            }


        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception :Face configuration Controller :enrollFace()",
                    WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,e.getMessage()));
        }
    }

    public void LoginWithFace(@NonNull final File photo, @NonNull final PasswordlessEntity passwordlessEntity,
                              final Result<LoginCredentialsResponseEntity> loginresult) {
        try {

            LogFile.getShared(context).addInfoLog("Info :FaceConfigurationController :LoginWithFace()",
                    "Info FaceImageFile:- "+photo.getAbsolutePath() +"UsageType:-"+passwordlessEntity.getUsageType()+" Sub:- "+passwordlessEntity.getSub()+
                            " Email"+passwordlessEntity.getEmail()+ " Mobile"+passwordlessEntity.getMobile()+" RequestId:-"+passwordlessEntity.getRequestId()+
                            " TrackId:-"+passwordlessEntity.getTrackId());

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {

                    String baseurl = result.get("DomainURL");
                    String clientId = result.get("ClientId");

                    if (passwordlessEntity.getUsageType() != null && !passwordlessEntity.getUsageType().equals("") &&
                            passwordlessEntity.getRequestId() != null && !passwordlessEntity.getRequestId().equals("") &&
                            photo != null) {

                        if (baseurl == null || baseurl.equals("") && clientId == null || clientId.equals("")) {
                            String errorMessage = "baseurl or clientId must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).propertyMissingException(
                                    errorMessage,  "Error :Face configuration Controller :LoginWithFace()"));
                        }

                        if (((passwordlessEntity.getSub() == null || passwordlessEntity.getSub().equals("")) &&
                                (passwordlessEntity.getEmail() == null || passwordlessEntity.getEmail().equals("")) &&
                                (passwordlessEntity.getMobile() == null || passwordlessEntity.getMobile().equals("")))) {
                            String errorMessage = "sub or email or mobile number must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).propertyMissingException(
                                    errorMessage, "Error :Face configuration Controller :LoginWithFace()"));
                        }

                        if (passwordlessEntity.getUsageType().equals(UsageType.MFA)) {
                            if (passwordlessEntity.getTrackId() == null || passwordlessEntity.getTrackId() == "") {
                                String errorMessage = "trackId must not be empty";

                                loginresult.failure(WebAuthError.getShared(context).propertyMissingException(
                                        errorMessage,  "Error :Face configuration Controller :LoginWithFace()"));
                                return;
                            }
                        }

                        InitiateFaceMFARequestEntity initiateFaceMFARequestEntity = new InitiateFaceMFARequestEntity();
                        initiateFaceMFARequestEntity.setSub(passwordlessEntity.getSub());
                        initiateFaceMFARequestEntity.setUsageType(passwordlessEntity.getUsageType());
                        initiateFaceMFARequestEntity.setEmail(passwordlessEntity.getEmail());
                        initiateFaceMFARequestEntity.setMobile(passwordlessEntity.getMobile());

                        //Todo check for email or sub or mobile
                       LoginWithFace(photo,baseurl,clientId,passwordlessEntity.getTrackId(),passwordlessEntity.getRequestId(),initiateFaceMFARequestEntity,loginresult);


                    } else {
                        String errorMessage = "Image File or RequestId or UsageType must not be empty";
                        loginresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage,"Error :Face configuration Controller :LoginWithFace()"));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(WebAuthError.getShared(context).CidaaspropertyMissingException("DomainURL or ClientId or RedirectURL must not be empty",
                            "Error :Face configuration Controller :LoginWithFace()"));
                }
            });

        } catch (Exception e) {
            loginresult.failure(WebAuthError.getShared(context).methodException("Exception :Face configuration Controller :LoginWithFace()",
                    WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,e.getMessage()));
        }
    }

    //Login with Face
    private void LoginWithFace(@NonNull final File FaceImageFile, @NonNull final String baseurl, @NonNull final String clientId,
                              @NonNull final String trackId, @NonNull final String requestId,
                              @NonNull final InitiateFaceMFARequestEntity initiateFaceMFARequestEntity,
                              final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{
            LogFile.getShared(context).addInfoLog("Info :FaceConfigurationController :LoginWithFace()",
                    "Info FaceImageFile:- "+FaceImageFile.getAbsolutePath() +"UsageType:-"+initiateFaceMFARequestEntity.getUsageType()+
                            " Sub:- "+initiateFaceMFARequestEntity.getSub()+ " Email"+initiateFaceMFARequestEntity.getEmail()+
                            " Mobile"+initiateFaceMFARequestEntity.getMobile()+" RequestId:-"+requestId+ " TrackId:-"+trackId+" baseURL"+baseurl+" ClientId"+clientId);

            if(codeChallenge.equals("") && codeVerifier.equals("")) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.usagePass ="";
            if(initiateFaceMFARequestEntity.getUserDeviceId() != null && !initiateFaceMFARequestEntity.getUserDeviceId().equals(""))
            {
                //Do nothing
            }
            else
            {
                initiateFaceMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }
            initiateFaceMFARequestEntity.setClient_id(clientId);


            if (    initiateFaceMFARequestEntity.getUsageType() != null && !initiateFaceMFARequestEntity.getUsageType().equals("") &&
                    initiateFaceMFARequestEntity.getUserDeviceId() != null && !initiateFaceMFARequestEntity.getUserDeviceId().equals("") &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                FaceVerificationService.getShared(context).initiateFace(baseurl, initiateFaceMFARequestEntity,null,
                        new Result<InitiateFaceMFAResponseEntity>() {

                            @Override
                            public void success(final InitiateFaceMFAResponseEntity serviceresult) {

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
                                        if(usage_pass !=null && !usage_pass.equals("")) {

                                            //Todo call initiate
                                            final InitiateFaceMFARequestEntity initiateFaceMFARequestEntity=new InitiateFaceMFARequestEntity();
                                            initiateFaceMFARequestEntity.setUsage_pass(usage_pass);

                                            final String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);

                                            //Log
                                            LogFile.getShared(context).addInfoLog("Info :Face configuration Controller :LoginWithFace()",
                                                    " UserDeviceId:-"+userDeviceId+" UsagePass:-"+ usage_pass+" Baseurl:-"+baseurl);

                                            FaceVerificationService.getShared(context).initiateFace(baseurl, initiateFaceMFARequestEntity,null,
                                                    new Result<InitiateFaceMFAResponseEntity>() {

                                                        @Override
                                                        public void success(InitiateFaceMFAResponseEntity result) {
                                                            if (FaceImageFile != null && result.getData().getStatusId() != null &&
                                                                    !result.getData().getStatusId().equals("")) {


                                                                AuthenticateFaceRequestEntity authenticateFaceRequestEntity = new AuthenticateFaceRequestEntity();
                                                                authenticateFaceRequestEntity.setUserDeviceId(userDeviceId);
                                                                authenticateFaceRequestEntity.setStatusId(result.getData().getStatusId());
                                                                authenticateFaceRequestEntity.setImagetoSend(FaceImageFile);
                                                                authenticateFaceRequestEntity.setClient_id(clientId);


                                                                authenticateFace(baseurl, authenticateFaceRequestEntity, new Result<AuthenticateFaceResponseEntity>() {
                                                                    @Override
                                                                    public void success(AuthenticateFaceResponseEntity result) {

                                                                        LogFile.getShared(context).addSuccessLog("Success :Face configuration Controller :LoginWithFace()",
                                                                                "Sub:-"+result.getData().getSub()+"TrackingCode"+result.getData().getTrackingCode());

                                                                        //Todo Call Resume with Login Service

                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

                                                                        //Todo Check not Null values
                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                        resumeLoginRequestEntity.setVerificationType("FACE");
                                                                        resumeLoginRequestEntity.setUsageType(initiateFaceMFARequestEntity.getUsageType());
                                                                        resumeLoginRequestEntity.setClient_id(clientId);
                                                                        resumeLoginRequestEntity.setRequestId(requestId);

                                                                        if (initiateFaceMFARequestEntity.getUsageType().equals(UsageType.MFA)) {
                                                                            resumeLoginRequestEntity.setTrack_id(trackId);
                                                                            LoginController.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, loginresult);
                                                                        } else if (initiateFaceMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS)) {
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
                                                                String errorMessage="Status Id or Face Must not be null";
                                                                loginresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage,
                                                                        "Error :Face configuration Controller :LoginWithFace()"));

                                                            }
                                                        }

                                                        @Override
                                                        public void failure(WebAuthError error) {
                                                            loginresult.failure(error);
                                                        }
                                                    });
                                        }

                                        else {
                                            // return Error Message

                                            loginresult.failure(WebAuthError.getShared(context).deviceVerificationFailureException( "Error :Face configuration Controller :LoginWithFace()"));
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
                loginresult.failure(WebAuthError.getShared(context).propertyMissingException("UsageType or userDeviceId or baseurl must not be empty",
                        "Error :Face configuration Controller :LoginWithFace()"));
            }
        }
        catch (Exception e)
        {
            loginresult.failure(WebAuthError.getShared(context).methodException("Exception :Face configuration Controller :LoginWithFace()",
                    WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,e.getMessage()));
        }
    }


    public void authenticateFace(@NonNull final File photo,@NonNull final String statusId, final Result<AuthenticateFaceResponseEntity> result) {
        try {
            LogFile.getShared(context).addInfoLog("Info :FaceConfigurationController :authenticateFace()",
                    " FileName:- "+photo.getAbsolutePath()+" StatusId:-"+statusId);

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = lpresult.get("DomainURL");
                    String clientId = lpresult.get("ClientId");
                    //todo call enroll Face

                    AuthenticateFaceRequestEntity authenticateFaceRequestEntity=new AuthenticateFaceRequestEntity();
                    authenticateFaceRequestEntity.setStatusId(statusId);
                    authenticateFaceRequestEntity.setImagetoSend(photo);
                    authenticateFaceRequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));

                 authenticateFace(baseurl,authenticateFaceRequestEntity,result);

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty",
                            "Exception :Face configuration Controller :configureFace()"));
                }
            });
        } catch (Exception e) {
           result.failure(WebAuthError.getShared(context).methodException("Exception :Face configuration Controller :configureFace()",
                   WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,e.getMessage()));
        }

    }

    //Authenticate Face
    private void authenticateFace(final String baseurl, final AuthenticateFaceRequestEntity authenticateFaceRequestEntity,
                                  final Result<AuthenticateFaceResponseEntity> authResult)
    {
        try
        {
            LogFile.getShared(context).addInfoLog( "Info :FaceConfigurationController :authenticateFace()",
                    " baseurl:-"+baseurl+" ClientId"+authenticateFaceRequestEntity.getClient_id()+" UsagePass"+authenticateFaceRequestEntity.getUsage_pass()+
                            " StatusId:-" +authenticateFaceRequestEntity.getStatusId()+" ImageFile:-"+authenticateFaceRequestEntity.getImagetoSend()+
                            "User device id"+authenticateFaceRequestEntity.getUserDeviceId());

            if(baseurl!=null && !baseurl.equals("")) {

                if (authenticateFaceRequestEntity.getUserDeviceId() != null && !authenticateFaceRequestEntity.getUserDeviceId().equals("") &&
                        authenticateFaceRequestEntity.getStatusId() != null && !authenticateFaceRequestEntity.getStatusId().equals("")
                        ) {


                    final AuthenticateFaceRequestEntity authenticateFaceRequestEntityWithPass=new AuthenticateFaceRequestEntity();



                    if(authenticateFaceRequestEntity.getImagetoSend() != null)
                    {
                        authenticateFaceRequestEntityWithPass.setImagetoSend(authenticateFaceRequestEntity.getImagetoSend());
                       // authenticateFaceRequestEntityWithPass.setClient_id(authenticateFaceRequestEntity.getClient_id());
                        authenticateFaceRequestEntity.setImagetoSend(null);
                    }
                    else
                    {
                        authResult.failure(WebAuthError.getShared(context).propertyMissingException(
                                "Image must not be empty",  "Error :FaceConfigurationController :authenticateFace()"));
                    }


                    FaceVerificationService.getShared(context).authenticateFace(baseurl, authenticateFaceRequestEntity,
                            null, new Result<AuthenticateFaceResponseEntity>() {
                @Override
                public void success(final AuthenticateFaceResponseEntity serviceresult) {


                    Cidaas.usagePass = "";

                    //Timer
                    new CountDownTimer(5000, 500) {
                        String usage_pass = "";

                        public void onTick(long millisUntilFinished) {
                            usage_pass = Cidaas.usagePass;

                            Timber.e("");
                            if (usage_pass != null && !usage_pass.equals("")) {
                                this.cancel();
                                onFinish();
                            }

                        }

                        public void onFinish() {
                            if (usage_pass != null && !usage_pass.equals("")) {

                                authenticateFaceRequestEntityWithPass.setUsage_pass(usage_pass);
                                LogFile.getShared(context).addSuccessLog( "Success :FaceConfigurationController :authenticateFace()",
                                        "UsagePass:-"+usage_pass);

                                if(authenticateFaceRequestEntityWithPass!=null) {

                                    FaceVerificationService.getShared(context).authenticateFace(baseurl, authenticateFaceRequestEntityWithPass, null, authResult);
                                }
                                else {
                                    // return Error Message
                                    authResult.failure(WebAuthError.getShared(context).propertyMissingException(
                                            "Image must not be empty",  "Error :FaceConfigurationController :authenticateFace()"));
                                }
                            }
                            else {
                                // return Error Message
                                authResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException( "Error :FaceConfigurationController :authenticateFace()"));
                            }

                        }
                    }.start();
                }

                @Override
                public void failure(WebAuthError error) {
                    authResult.failure(error);
                }
            });
                } else {
                    authResult.failure(WebAuthError.getShared(context).propertyMissingException("UserdeviceId or  StatusID must not be empty" ,
                            "Error :FaceConfigurationController :authenticateFace()"));
                }
            }
            else
            {
                authResult.failure(WebAuthError.getShared(context).propertyMissingException("BaseURL  must not be empty",
                        "Error :FaceConfigurationController :authenticateFace()"));
            }

        }
        catch (Exception e)
        {
            authResult.failure(WebAuthError.getShared(context).methodException("Exception :FaceConfigurationController :authenticateFace()",
                    WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,e.getMessage()));
        }
    }


}
