package com.example.cidaasv2.Controller.Repository.Configuration.Face;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;

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
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.Device.DeviceVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.Face.FaceVerificationService;
import com.example.cidaasv2.Service.Scanned.ScannedRequestEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import java.io.File;
import java.util.List;

import timber.log.Timber;

public class FaceConfigurationController {

    //Local variables

    private String authenticationType;
    private String verificationType;
    private Context context;

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


/*

    //Service call To SetupFaceMFA
    public void ConfigureFace(final File imageFile,@NonNull final String sub, @NonNull final String baseurl,
                             @NonNull final SetupFaceMFARequestEntity setupFaceMFARequestEntity,
                             @NonNull final Result<EnrollFaceMFAResponseEntity> enrollresult)
    {
        try{
            Cidaas.instanceId="";
            //Todo check For Not null

            if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {
                //Todo Service call

                if(codeChallenge==null || codeChallenge==""){
                    generateChallenge();
                }


                AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessTokenresult) {

                        FaceVerificationService.getShared(context).setupFaceMFA(baseurl, accessTokenresult.getAccess_token(),codeChallenge,setupFaceMFARequestEntity,null,
                                new Result<SetupFaceMFAResponseEntity>() {
                                    @Override
                                    public void success(final SetupFaceMFAResponseEntity setupserviceresult) {

                                        new CountDownTimer(50000, 500) {
                                            String instceID="";
                                            public void onTick(long millisUntilFinished) {
                                                instceID= Cidaas.instanceId;
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
                                                    ,null , new Result<ValidateDeviceResponseEntity>() {
                                                                @Override
                                                                public void success(ValidateDeviceResponseEntity result) {
                                                                    // call Scanned Service
                                                                    FaceVerificationService.getShared(context).scannedFace(baseurl,result.getData().getUsage_pass(),"",
                                                                            accessTokenresult.getAccess_token(),null,new Result<ScannedResponseEntity>() {
                                                                                @Override
                                                                                public void success(final ScannedResponseEntity result) {
                                                                                    DBHelper.getShared().setUserDeviceId(result.getData().getUserDeviceId(),baseurl);

                                                                                    EnrollFaceMFARequestEntity enrollFaceMFARequestEntity = new EnrollFaceMFARequestEntity();
                                                                                    if(sub != null && !sub.equals("")  &&
                                                                                            result.getData().getUserDeviceId()!=null && !  result.getData().getUserDeviceId().equals("")) {


                                                                                        enrollFaceMFARequestEntity.setImagetoSend(imageFile);
                                                                                        enrollFaceMFARequestEntity.setStatusId("");
                                                                                        enrollFaceMFARequestEntity.setUserDeviceId(result.getData().getUserDeviceId());
                                                                                    }
                                                                                    else {
                                                                                        enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
                                                                                    }

                                                                                    // call Enroll Service
                                                                                    FaceVerificationService.getShared(context).
                                                                                            enrollFace(baseurl, accessTokenresult.getAccess_token(), enrollFaceMFARequestEntity,null,new Result<EnrollFaceMFAResponseEntity>() {
                                                                                        @Override
                                                                                        public void success(EnrollFaceMFAResponseEntity serviceresult) {
                                                                                            enrollresult.success(serviceresult);
                                                                                        }

                                                                                        @Override
                                                                                        public void failure(WebAuthError error) {
                                                                                            enrollresult.failure(error);
                                                                                        }
                                                                                    });

                                                                                    Timber.i(result.getData().getUserDeviceId()+"User Device id");
                                                                                  //  Toast.makeText(context, result.getData().getUserDeviceId()+"User Device id", Toast.LENGTH_SHORT).show();
                                                                                }

                                                                                @Override
                                                                                public void failure(WebAuthError error) {
                                                                                    enrollresult.failure(error);
                                                                                  //  Toast.makeText(context, "Error on Scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                }

                                                                @Override
                                                                public void failure(WebAuthError error) {
                                                                    enrollresult.failure(error);
                                                                   // Toast.makeText(context, "Error on validate Device"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                                else {
                                                    // return Error Message

                                                    enrollresult.failure(WebAuthError.getShared(context).deviceVerificationFailureException());
                                                }
                                            }
                                        }.start();


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
            else
            {
                enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

    //Login with Face
    public void LoginWithFace( @NonNull final File faceImageFile,@NonNull final String baseurl, @NonNull final String clientId,
                                  @NonNull final String trackId, @NonNull final String requestId,
                                  @NonNull final InitiateFaceMFARequestEntity initiateFaceMFARequestEntity,
                                  final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(initiateFaceMFARequestEntity.getUserDeviceId() != null && initiateFaceMFARequestEntity.getUserDeviceId() != "" )
            {
                //Do nothing
            }
            else
            {
                initiateFaceMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }

            if(codeChallenge=="" && codeVerifier=="") {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";
            if ( initiateFaceMFARequestEntity.getUsageType() != null && initiateFaceMFARequestEntity.getUsageType() != "" &&
                    initiateFaceMFARequestEntity.getUserDeviceId() != null && initiateFaceMFARequestEntity.getUserDeviceId() != ""&&
                   clientId != null && clientId != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call


                initiateFaceMFARequestEntity.setClient_id(clientId);

                FaceVerificationService.getShared(context).initiateFace(baseurl, codeChallenge,initiateFaceMFARequestEntity,null,
                        new Result<InitiateFaceMFAResponseEntity>() {

                            //Todo Call Validate Device

                            @Override
                            public void success(final InitiateFaceMFAResponseEntity serviceresult) {


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
                                                    , null,new Result<ValidateDeviceResponseEntity>() {

                                                        @Override
                                                        public void success(ValidateDeviceResponseEntity result) {

                                                            //Todo call initiate
                                                            initiateFaceMFARequestEntity.setUsagePass(result.getData().getUsage_pass());
                                                            FaceVerificationService.getShared(context).initiateFace(baseurl, codeChallenge, initiateFaceMFARequestEntity,null,
                                                                    new Result<InitiateFaceMFAResponseEntity>() {

                                                                        @Override
                                                                        public void success(InitiateFaceMFAResponseEntity serviceresult) {
                                                                            if ( serviceresult.getData().getStatusId() != null && !serviceresult.getData().getStatusId().equals("")) {


                                                                                AuthenticateFaceRequestEntity authenticateFaceRequestEntity = new AuthenticateFaceRequestEntity();
                                                                                authenticateFaceRequestEntity.setUserDeviceId(initiateFaceMFARequestEntity.getUserDeviceId());
                                                                                authenticateFaceRequestEntity.setStatusId(serviceresult.getData().getStatusId());
                                                                                authenticateFaceRequestEntity.setImagetoSend(faceImageFile);


                                                                                FaceVerificationService.getShared(context).authenticateFace(baseurl, authenticateFaceRequestEntity,null, new Result<AuthenticateFaceResponseEntity>() {
                                                                                    @Override
                                                                                    public void success(AuthenticateFaceResponseEntity result) {
                                                                                        //Todo Call Resume with Login Service

                                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();

                                                                                        //Todo Check not Null values
                                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                                        resumeLoginRequestEntity.setUsageType(initiateFaceMFARequestEntity.getUsageType());
                                                                                        resumeLoginRequestEntity.setVerificationType("face");
                                                                                        resumeLoginRequestEntity.setClient_id(clientId);
                                                                                        resumeLoginRequestEntity.setRequestId(requestId);

                                                                                        if(initiateFaceMFARequestEntity.getUsageType().equals(UsageType.MFA))
                                                                                        {
                                                                                            resumeLoginRequestEntity.setTrack_id(trackId);
                                                                                            LoginController.getShared(context).continueMFA(baseurl,resumeLoginRequestEntity,loginresult);
                                                                                        }

                                                                                        else if(initiateFaceMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS))
                                                                                        {
                                                                                            resumeLoginRequestEntity.setTrack_id("");
                                                                                            LoginController.getShared(context).continuePasswordless(baseurl,resumeLoginRequestEntity,loginresult);

                                                                                        }
                                                                                        //  loginresult.success(result);
                                                                                     //   Toast.makeText(context, "Sucess Face", Toast.LENGTH_SHORT).show();
                               */
/*

                                LoginController.getShared(context).resumeLogin();*//*

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
                                                                         //   Toast.makeText(context, "Error on validate Device" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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

    //Service call To SetupFaceMFA
    public void configureFace(@NonNull final File FaceImageFile,@NonNull final String sub, @NonNull final String baseurl,
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

                    setupFace(baseurl,accessTokenresult.getAccess_token(),FaceImageFile,setupFaceMFARequestEntity,enrollresult);
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
                            SetupFaceMFARequestEntity setupFaceMFARequestEntity,final Result<EnrollFaceMFAResponseEntity> enrollResult)
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

                                                            //Entity For Face
                                                            EnrollFaceMFARequestEntity enrollFaceMFARequestEntity = new EnrollFaceMFARequestEntity();
                                                            enrollFaceMFARequestEntity.setImagetoSend(FaceImageFile);
                                                            enrollFaceMFARequestEntity.setStatusId(setupserviceresult.getData().getSt());
                                                            enrollFaceMFARequestEntity.setUserDeviceId(result.getData().getUdi());


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
                        enrollFaceMFARequestEntity.getStatusId() != null && !enrollFaceMFARequestEntity.getStatusId().equals("") &&
                        enrollFaceMFARequestEntity.getImagetoSend() != null ) {

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

                                                //enroll
                                                EnrollFaceMFARequestEntity enrollFaceMFARequest = new EnrollFaceMFARequestEntity();
                                                enrollFaceMFARequest.setUsage_pass(instceID);
                                                enrollFaceMFARequest.setImagetoSend(enrollFaceMFARequestEntity.getImagetoSend());

                                                // call Enroll Service
                                                FaceVerificationService.getShared(context).enrollFace(baseurl, accessToken, enrollFaceMFARequest,
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
                            "UserdeviceId or Verifierpassword or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
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
    public void LoginWithFace(@NonNull final File FaceImageFile,@NonNull final String baseurl, @NonNull final String clientId,
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
                FaceVerificationService.getShared(context).initiateFace(baseurl,codeChallenge, initiateFaceMFARequestEntity,null,
                        new Result<InitiateFaceMFAResponseEntity>() {

                            @Override
                            public void success(final InitiateFaceMFAResponseEntity serviceresult) {

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
                                            final InitiateFaceMFARequestEntity initiateFaceMFARequestEntity=new InitiateFaceMFARequestEntity();
                                            initiateFaceMFARequestEntity.setUsagePass(instceID);

                                            final String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);

                                            FaceVerificationService.getShared(context).initiateFace(baseurl, codeChallenge, initiateFaceMFARequestEntity,null,
                                                    new Result<InitiateFaceMFAResponseEntity>() {

                                                        @Override
                                                        public void success(InitiateFaceMFAResponseEntity result) {
                                                            if (FaceImageFile != null && serviceresult.getData().getStatusId() != null &&
                                                                    !serviceresult.getData().getStatusId().equals("")) {


                                                                AuthenticateFaceRequestEntity authenticateFaceRequestEntity = new AuthenticateFaceRequestEntity();
                                                                authenticateFaceRequestEntity.setUserDeviceId(userDeviceId);
                                                                authenticateFaceRequestEntity.setStatusId(serviceresult.getData().getStatusId());
                                                                authenticateFaceRequestEntity.setImagetoSend(FaceImageFile);


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
            FaceVerificationService.getShared(context).authenticateFace(baseurl, authenticateFaceRequestEntity,null, new Result<AuthenticateFaceResponseEntity>() {
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
                                AuthenticateFaceRequestEntity authenticateFaceRequest=new AuthenticateFaceRequestEntity();
                                authenticateFaceRequest.setImagetoSend(authenticateFaceRequestEntity.getImagetoSend());
                                authenticateFaceRequest.setUsage_pass(instceID);

                                FaceVerificationService.getShared(context).authenticateFace(baseurl, authenticateFaceRequest,null, new Result<AuthenticateFaceResponseEntity>() {
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
            authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,
                    "Face Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
        }
    }

/*
    //Service call To SetupFaceMFA
    public void ConfigureFaces(final List<File> imageFile, @NonNull final String sub, @NonNull final String baseurl,
                               @NonNull final SetupFaceMFARequestEntity setupFaceMFARequestEntity,
                               @NonNull final Result<EnrollFaceMFAResponseEntity> enrollresult)
    {
        try{
            Cidaas.instanceId="";
            //Todo check For Not null

            if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {
                //Todo Service call

                if(codeChallenge=="" || codeVerifier=="" || codeChallenge==null || codeVerifier==null){
                    generateChallenge();
                }


                AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessTokenresult) {

                        FaceVerificationService.getShared(context).setupFaceMFA(baseurl, accessTokenresult.getAccess_token(),codeChallenge,setupFaceMFARequestEntity,null,
                                new Result<SetupFaceMFAResponseEntity>() {
                                    @Override
                                    public void success(final SetupFaceMFAResponseEntity setupserviceresult) {

                                        new CountDownTimer(50000, 500) {
                                            String instceID="";
                                            public void onTick(long millisUntilFinished) {
                                                instceID= Cidaas.instanceId;
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
                                                            ,null , new Result<ValidateDeviceResponseEntity>() {
                                                                @Override
                                                                public void success(ValidateDeviceResponseEntity result) {
                                                                    // call Scanned Service
                                                                    FaceVerificationService.getShared(context).scannedFace(baseurl,result.getData().getUsage_pass(),"",
                                                                            accessTokenresult.getAccess_token(),null,new Result<ScannedResponseEntity>() {
                                                                                @Override
                                                                                public void success(final ScannedResponseEntity result) {
                                                                                    DBHelper.getShared().setUserDeviceId(result.getData().getUserDeviceId(),baseurl);

                                                                                    EnrollFaceMFARequestEntity enrollFaceMFARequestEntity = new EnrollFaceMFARequestEntity();
                                                                                    if(sub != null && !sub.equals("")  &&
                                                                                            result.getData().getUserDeviceId()!=null && !  result.getData().getUserDeviceId().equals("")) {


                                                                                        enrollFaceMFARequestEntity.setImagesToSend(imageFile);
                                                                                        enrollFaceMFARequestEntity.setStatusId("");
                                                                                        enrollFaceMFARequestEntity.setUserDeviceId(result.getData().getUserDeviceId());
                                                                                    }
                                                                                    else {
                                                                                        enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
                                                                                    }

                                                                                    // call Enroll Service
                                                                                    FaceVerificationService.getShared(context).
                                                                                            enrollFaces(baseurl, accessTokenresult.getAccess_token(), enrollFaceMFARequestEntity,null,new Result<EnrollFaceMFAResponseEntity>() {
                                                                                                @Override
                                                                                                public void success(EnrollFaceMFAResponseEntity serviceresult) {
                                                                                                    enrollresult.success(serviceresult);
                                                                                                }

                                                                                                @Override
                                                                                                public void failure(WebAuthError error) {
                                                                                                    enrollresult.failure(error);
                                                                                                }
                                                                                            });

                                                                                    Timber.i(result.getData().getUserDeviceId()+"User Device id");
                                                                                    //  Toast.makeText(context, result.getData().getUserDeviceId()+"User Device id", Toast.LENGTH_SHORT).show();
                                                                                }

                                                                                @Override
                                                                                public void failure(WebAuthError error) {
                                                                                    enrollresult.failure(error);
                                                                                    //  Toast.makeText(context, "Error on Scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                }

                                                                @Override
                                                                public void failure(WebAuthError error) {
                                                                    enrollresult.failure(error);
                                                                    // Toast.makeText(context, "Error on validate Device"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                                else {
                                                    // return Error Message

                                                    enrollresult.failure(WebAuthError.getShared(context).deviceVerificationFailureException());
                                                }
                                            }
                                        }.start();


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
            else
            {
                enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

    //Login with Face
    public void LoginWithFaces( @NonNull final List<File> faceImageFile,@NonNull final String baseurl, @NonNull final String clientId,
                               @NonNull final String trackId, @NonNull final String requestId,
                               @NonNull final InitiateFaceMFARequestEntity initiateFaceMFARequestEntity,
                               final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(initiateFaceMFARequestEntity.getUserDeviceId() != null && initiateFaceMFARequestEntity.getUserDeviceId() != "" )
            {
                //Do nothing
            }
            else
            {
                initiateFaceMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }

            if(codeChallenge=="" && codeVerifier=="") {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";
            if ( initiateFaceMFARequestEntity.getUsageType() != null && initiateFaceMFARequestEntity.getUsageType() != "" &&
                    initiateFaceMFARequestEntity.getUserDeviceId() != null && initiateFaceMFARequestEntity.getUserDeviceId() != ""&&
                    clientId != null && clientId != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call


                initiateFaceMFARequestEntity.setClient_id(clientId);

                FaceVerificationService.getShared(context).initiateFace(baseurl, codeChallenge,initiateFaceMFARequestEntity,null,
                        new Result<InitiateFaceMFAResponseEntity>() {

                            //Todo Call Validate Device

                            @Override
                            public void success(final InitiateFaceMFAResponseEntity serviceresult) {


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
                                                    , null,new Result<ValidateDeviceResponseEntity>() {

                                                        @Override
                                                        public void success(ValidateDeviceResponseEntity result) {

                                                            //Todo call initiate
                                                            initiateFaceMFARequestEntity.setUsagePass(result.getData().getUsage_pass());
                                                            FaceVerificationService.getShared(context).initiateFace(baseurl, codeChallenge, initiateFaceMFARequestEntity,null,
                                                                    new Result<InitiateFaceMFAResponseEntity>() {

                                                                        @Override
                                                                        public void success(InitiateFaceMFAResponseEntity serviceresult) {
                                                                            if ( serviceresult.getData().getStatusId() != null && !serviceresult.getData().getStatusId().equals("")) {


                                                                                AuthenticateFaceRequestEntity authenticateFaceRequestEntity = new AuthenticateFaceRequestEntity();
                                                                                authenticateFaceRequestEntity.setUserDeviceId(initiateFaceMFARequestEntity.getUserDeviceId());
                                                                                authenticateFaceRequestEntity.setStatusId(serviceresult.getData().getStatusId());
                                                                                authenticateFaceRequestEntity.setImagesToSend(faceImageFile);


                                                                                FaceVerificationService.getShared(context).authenticateFaces(baseurl, authenticateFaceRequestEntity,null, new Result<AuthenticateFaceResponseEntity>() {
                                                                                    @Override
                                                                                    public void success(AuthenticateFaceResponseEntity result) {
                                                                                        //Todo Call Resume with Login Service

                                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();

                                                                                        //Todo Check not Null values
                                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                                        resumeLoginRequestEntity.setUsageType(initiateFaceMFARequestEntity.getUsageType());
                                                                                        resumeLoginRequestEntity.setVerificationType("face");
                                                                                        resumeLoginRequestEntity.setClient_id(clientId);
                                                                                        resumeLoginRequestEntity.setRequestId(requestId);

                                                                                        if(initiateFaceMFARequestEntity.getUsageType().equals(UsageType.MFA))
                                                                                        {
                                                                                            resumeLoginRequestEntity.setTrack_id(trackId);
                                                                                            LoginController.getShared(context).continueMFA(baseurl,resumeLoginRequestEntity,loginresult);
                                                                                        }

                                                                                        else if(initiateFaceMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS))
                                                                                        {
                                                                                            resumeLoginRequestEntity.setTrack_id("");
                                                                                            LoginController.getShared(context).continuePasswordless(baseurl,resumeLoginRequestEntity,loginresult);

                                                                                        }
                                                                                        //  loginresult.success(result);
                                                                                        //   Toast.makeText(context, "Sucess Face", Toast.LENGTH_SHORT).show();
                               *//*

                                LoginController.getShared(context).resumeLogin();*//*
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
                                                                            //   Toast.makeText(context, "Error on validate Device" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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
    }*/


}
