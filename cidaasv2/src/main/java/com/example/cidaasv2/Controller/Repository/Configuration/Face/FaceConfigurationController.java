package com.example.cidaasv2.Controller.Repository.Configuration.Face;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
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
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;

import java.io.File;

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

    String codeVerifier, codeChallenge;
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
    public void ConfigureFace(final File imageFile,@NonNull final String sub, @NonNull final String baseurl,
                             @NonNull final SetupFaceMFARequestEntity setupFaceMFARequestEntity,
                             @NonNull final Result<EnrollFaceMFAResponseEntity> enrollresult)
    {
        try{
            Cidaas.instanceId="";
            //Todo check For Not null

            if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {
                //Todo Service call

                if(codeChallenge==null){
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
                                                if(instceID!=null && instceID!="" && setupserviceresult.getData().getStatusId()!=null && setupserviceresult.getData().getStatusId()!="")
                                                {
                                                    //Device Validation Service
                                                    DeviceVerificationService.getShared(context).validateDevice(baseurl,instceID,setupserviceresult.getData().getStatusId(),codeVerifier
                                                    ,null , new Result<ValidateDeviceResponseEntity>() {
                                                                @Override
                                                                public void success(ValidateDeviceResponseEntity result) {
                                                                    // call Scanned Service
                                                                    FaceVerificationService.getShared(context).scannedFace(baseurl,result.getData().getUsage_pass(),setupserviceresult.getData().getStatusId(),
                                                                            accessTokenresult.getAccess_token(),null,new Result<ScannedResponseEntity>() {
                                                                                @Override
                                                                                public void success(final ScannedResponseEntity result) {
                                                                                    DBHelper.getShared().setUserDeviceId(result.getData().getUserDeviceId(),baseurl);

                                                                                    EnrollFaceMFARequestEntity enrollFaceMFARequestEntity = new EnrollFaceMFARequestEntity();
                                                                                    if(sub != null && !sub.equals("")  &&
                                                                                            result.getData().getUserDeviceId()!=null && !  result.getData().getUserDeviceId().equals("")) {

                                                                                        enrollFaceMFARequestEntity.setSub(sub);
                                                                                        enrollFaceMFARequestEntity.setImagetoSend(imageFile);
                                                                                        enrollFaceMFARequestEntity.setStatusId(setupserviceresult.getData().getStatusId());
                                                                                        enrollFaceMFARequestEntity.setUserDeviceId(result.getData().getUserDeviceId());
                                                                                    }
                                                                                    else {
                                                                                        enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
                                                                                    }

                                                                                    // call Enroll Service
                                                                                    FaceVerificationService.getShared(context).enrollFace(baseurl, accessTokenresult.getAccess_token(), enrollFaceMFARequestEntity,null,new Result<EnrollFaceMFAResponseEntity>() {
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
                                                                                    Toast.makeText(context, result.getData().getUserDeviceId()+"User Device id", Toast.LENGTH_SHORT).show();
                                                                                }

                                                                                @Override
                                                                                public void failure(WebAuthError error) {
                                                                                    enrollresult.failure(error);
                                                                                    Toast.makeText(context, "Error on Scanned"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                }

                                                                @Override
                                                                public void failure(WebAuthError error) {
                                                                    enrollresult.failure(error);
                                                                    Toast.makeText(context, "Error on validate Device"+error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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
            if (    initiateFaceMFARequestEntity.getUsageType() != null && initiateFaceMFARequestEntity.getUsageType() != "" &&
                    initiateFaceMFARequestEntity.getUserDeviceId() != null && initiateFaceMFARequestEntity.getUserDeviceId() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
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
                                                                                        Toast.makeText(context, "Sucess Face", Toast.LENGTH_SHORT).show();
                               /*

                                LoginController.getShared(context).resumeLogin();*/
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
                                                                            Toast.makeText(context, "Error on validate Device" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
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


}
