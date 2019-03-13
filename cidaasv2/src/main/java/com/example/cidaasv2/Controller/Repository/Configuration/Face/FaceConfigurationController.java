package com.example.cidaasv2.Controller.Repository.Configuration.Face;

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

import androidx.annotation.NonNull;
import timber.log.Timber;

public class FaceConfigurationController {

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
            Timber.i(e.getMessage());
        }
        return shared;
    }


    //Service call To SetupFaceMFA
    public void configureFace(@NonNull final File FaceImageFile,@NonNull final String sub, @NonNull final String baseurl,@NonNull final int attempt,
                               @NonNull final SetupFaceMFARequestEntity setupFaceMFARequestEntity,
                               @NonNull final Result<EnrollFaceMFAResponseEntity> enrollresult)
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

                    if(attempt>1) {
                        setupFace(baseurl, accessTokenresult.getAccess_token(), FaceImageFile, setupFaceMFARequestEntity, enrollresult);
                    }
                    else {

                        EnrollFaceMFARequestEntity enrollFaceMFARequestEntityWithAttempts = new EnrollFaceMFARequestEntity();
                        enrollFaceMFARequestEntityWithAttempts.setImagetoSend(FaceImageFile);
                        enrollFaceMFARequestEntityWithAttempts.setStatusId(statusIdFromSetup);
                        enrollFaceMFARequestEntityWithAttempts.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
                        enrollFaceMFARequestEntityWithAttempts.setClient_id(setupFaceMFARequestEntity.getClient_id());

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
            enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e(e.getMessage());
        }
    }


    private void setupFace(final String baseurl, final String accessToken, @NonNull final File FaceImageFile,
                           final SetupFaceMFARequestEntity setupFaceMFARequestEntity, final Result<EnrollFaceMFAResponseEntity> enrollResult)
    {
        try
        {
            if (baseurl != null && !baseurl.equals("") && accessToken != null && !accessToken.equals("") &&
                    setupFaceMFARequestEntity.getClient_id()!=null && !setupFaceMFARequestEntity.getClient_id().equals(""))
            {
                //Done Service call

                FaceVerificationService.getShared(context).setupFaceMFA(baseurl, accessToken,
                        setupFaceMFARequestEntity,null,new Result<SetupFaceMFAResponseEntity>() {
                            @Override
                            public void success(final SetupFaceMFAResponseEntity setupserviceresult) {

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
            enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,
                    "Face Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }



    public void scannedWithFace(final String baseurl,  String statusId, String clientId, final Result<ScannedResponseEntity> scannedResult)
    {
        try
        {
            if (baseurl != null && !baseurl.equals("")  && statusId!=null && !statusId.equals("") && clientId!=null && !clientId.equals("")) {

                final ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
                scannedRequestEntity.setStatusId(statusId);
                scannedRequestEntity.setClient_id(clientId);


                FaceVerificationService.getShared(context).scannedFace(baseurl,  scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
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

                                    FaceVerificationService.getShared(context).scannedFace(baseurl,  scannedRequestEntity, null, new Result<ScannedResponseEntity>() {

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
                scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_FACE_MFA_FAILURE,
                        "BaseURL or ClientId or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }

        }
        catch (Exception e)
        {
            scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_FACE_MFA_FAILURE,
                    "Face Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

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
                                "Image must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                    }

                    // call Enroll Service
                    FaceVerificationService.getShared(context).enrollFace(baseurl, accessToken, enrollFaceMFARequestEntity,
                            null, new Result<EnrollFaceMFAResponseEntity>() {

                                @Override
                                public void success(final EnrollFaceMFAResponseEntity serviceresult) {

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


                                                enrollFaceMFARequestEntityWithPass.setUsage_pass(instceID);

                                                if(enrollFaceMFARequestEntityWithPass.getImagetoSend() != null) {


                                                    // call Enroll Service
                                                    FaceVerificationService.getShared(context).enrollFace(baseurl, accessToken, enrollFaceMFARequestEntityWithPass,
                                                            null, new Result<EnrollFaceMFAResponseEntity>() {
                                                                @Override
                                                                public void success(EnrollFaceMFAResponseEntity serviceresult) {
                                                                    enrollResult.success(serviceresult);
                                                                }

                                                                @Override
                                                                public void failure(WebAuthError error) {
                                                                    enrollResult.failure(error);
                                                                }
                                                            });
                                                }
                                                else {
                                                    enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,
                                                            "Image must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                                                }
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
                    enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,
                            "UserdeviceId or Client Id or StatusID  must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                }
            }
            else
            {
                enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,
                        "BaseURL or accessToken must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }


        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,
                    "Face Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }


    //Login with Face
    public void LoginWithFace(@NonNull final File FaceImageFile, @NonNull final String baseurl, @NonNull final String clientId,
                              @NonNull final String trackId, @NonNull final String requestId,
                              @NonNull final InitiateFaceMFARequestEntity initiateFaceMFARequestEntity,
                              final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(codeChallenge.equals("") && codeVerifier.equals("")) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";
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
                                            final InitiateFaceMFARequestEntity initiateFaceMFARequestEntity=new InitiateFaceMFARequestEntity();
                                            initiateFaceMFARequestEntity.setUsagePass(instceID);

                                            final String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);

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


    //Authenticate Face
    public void authenticateFace(final String baseurl, final AuthenticateFaceRequestEntity authenticateFaceRequestEntity, final Result<AuthenticateFaceResponseEntity> authResult)
    {
        try
        {
            if(baseurl!=null && !baseurl.equals("")) {

                if (authenticateFaceRequestEntity.getUserDeviceId() != null && !authenticateFaceRequestEntity.getUserDeviceId().equals("") &&
                        authenticateFaceRequestEntity.getStatusId() != null && !authenticateFaceRequestEntity.getStatusId().equals("")
                        ) {


                    final AuthenticateFaceRequestEntity authenticateFaceRequestEntityWithPass=new AuthenticateFaceRequestEntity();



                    if(authenticateFaceRequestEntity.getImagetoSend() != null)
                    {
                        authenticateFaceRequestEntityWithPass.setImagetoSend(authenticateFaceRequestEntity.getImagetoSend());
                        authenticateFaceRequestEntity.setImagetoSend(null);
                    }
                    else
                    {
                        authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,
                                "Image must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                    }


                    FaceVerificationService.getShared(context).authenticateFace(baseurl, authenticateFaceRequestEntity,
                            null, new Result<AuthenticateFaceResponseEntity>() {
                @Override
                public void success(final AuthenticateFaceResponseEntity serviceresult) {


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


                                authenticateFaceRequestEntityWithPass.setUsage_pass(instceID);


                                if(authenticateFaceRequestEntityWithPass!=null) {

                                    FaceVerificationService.getShared(context).authenticateFace(baseurl, authenticateFaceRequestEntityWithPass, null, new Result<AuthenticateFaceResponseEntity>() {
                                        @Override
                                        public void success(AuthenticateFaceResponseEntity result) {
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
                                    authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,
                                            "Image must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                                }
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
                } else {
                    authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,
                            "UserdeviceId or  StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                }
            }
            else
            {
                authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,
                        "BaseURL  must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }

        }
        catch (Exception e)
        {
            authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,
                    "Face Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
        }
    }


}
