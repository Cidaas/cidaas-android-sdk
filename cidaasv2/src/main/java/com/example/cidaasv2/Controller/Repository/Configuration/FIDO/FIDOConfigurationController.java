package com.example.cidaasv2.Controller.Repository.Configuration.FIDO;

import android.content.Context;

import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;

import timber.log.Timber;

public class FIDOConfigurationController {

    private String authenticationType;
    private String verificationType;
    private Context context;

    public static FIDOConfigurationController shared;

    public FIDOConfigurationController(Context contextFromCidaas) {

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

    public static FIDOConfigurationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new FIDOConfigurationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


//Todo Configure FIDO by Passing the setupFIDORequestEntity
    // 1.  Check For NotNull Values
    // 2. Generate Code Challenge
    // 3.  getAccessToken From Sub
    // 4.  Call Setup FIDO
    // 5.  Call Validate FIDO
    // 3.  Call Scanned FIDO
    // 3.  Call Enroll FIDO and return the result
    // 4.  Maintain logs based on flags


/*
    //Service call To SetupFIDOMFA
    public void configureFIDO(@NonNull final String sub, @NonNull final String baseurl, @NonNull final String FIDOString,
                                 @NonNull final SetupFIDOMFARequestEntity setupFIDOMFARequestEntity,
                                 @NonNull final Result<EnrollFIDOMFAResponseEntity> enrollresult)
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
                            setupFIDOMFARequestEntity.getClient_id()!=null && !setupFIDOMFARequestEntity.getClient_id().equals(""))
                    {
                        //Todo Service call

                        FIDOVerificationService.getShared(context).setupFIDO(baseurl, accessTokenresult.getAccess_token(), codeChallenge,setupFIDOMFARequestEntity,new Result<SetupFIDOMFAResponseEntity>() {
                            @Override
                            public void success(final SetupFIDOMFAResponseEntity setupserviceresult) {

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
                                        if(instceID!=null && instceID!="" && setupserviceresult.getData().getStatusId()!=null && setupserviceresult.getData().getStatusId()!="")
                                        {
                                            //Device Validation Service
                                            DeviceVerificationService.getShared(context).validateDevice(baseurl,instceID,setupserviceresult.getData().getStatusId(),codeVerifier
                                                    , new Result<ValidateDeviceResponseEntity>() {
                                                        @Override
                                                        public void success(ValidateDeviceResponseEntity result) {
                                                            // call Scanned Service
                                                            FIDOVerificationService.getShared(context).scannedFIDO(baseurl,result.getData().getUsage_pass(),setupserviceresult.getData().getStatusId(),
                                                                    accessTokenresult.getAccess_token(),new Result<ScannedResponseEntity>() {
                                                                        @Override
                                                                        public void success(final ScannedResponseEntity result) {
                                                                            DBHelper.getShared().setUserDeviceId(result.getData().getUserDeviceId(),baseurl);

                                                                            EnrollFIDOMFARequestEntity enrollFIDOMFARequestEntity = new EnrollFIDOMFARequestEntity();
                                                                            if(sub != null && !sub.equals("") && FIDOString!=null && !FIDOString.equals("") &&
                                                                                    result.getData().getUserDeviceId()!=null && !  result.getData().getUserDeviceId().equals("")) {

                                                                                enrollFIDOMFARequestEntity.setSub(sub);
                                                                                enrollFIDOMFARequestEntity.setVerifierPassword(FIDOString);
                                                                                enrollFIDOMFARequestEntity.setStatusId(setupserviceresult.getData().getStatusId());
                                                                                enrollFIDOMFARequestEntity.setUserDeviceId(result.getData().getUserDeviceId());
                                                                            }
                                                                            else {
                                                                                enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
                                                                            }

                                                                            // call Enroll Service
                                                                            FIDOVerificationService.getShared(context).enrollFIDO(baseurl, accessTokenresult.getAccess_token(), enrollFIDOMFARequestEntity,new Result<EnrollFIDOMFAResponseEntity>() {
                                                                                @Override
                                                                                public void success(EnrollFIDOMFAResponseEntity serviceresult) {
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


    //Login with FIDO
    public void LoginWithFIDO(@NonNull final String baseurl,@NonNull String clientId,@NonNull final String FIDOString,
                                 @NonNull final InitiateFIDOMFARequestEntity initiateFIDOMFARequestEntity,
                                 final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(initiateFIDOMFARequestEntity.getUserDeviceId() != null && initiateFIDOMFARequestEntity.getUserDeviceId() != "" )
            {
                //Do nothing
            }
            else
            {
                initiateFIDOMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }

            if (    initiateFIDOMFARequestEntity.getUsageType() != null && initiateFIDOMFARequestEntity.getUsageType() != "" &&
                    initiateFIDOMFARequestEntity.getUserDeviceId() != null && initiateFIDOMFARequestEntity.getUserDeviceId() != ""&&
                    initiateFIDOMFARequestEntity.getSub() != null && initiateFIDOMFARequestEntity.getSub() != "" &&
                    initiateFIDOMFARequestEntity.getEmail() != null && initiateFIDOMFARequestEntity.getEmail() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                FIDOVerificationService.getShared(context).initiateFIDO(baseurl, initiateFIDOMFARequestEntity,
                        new Result<InitiateFIDOMFAResponseEntity>() {

                            //Todo Call Validate Device

                            @Override
                            public void success(InitiateFIDOMFAResponseEntity serviceresult) {
                                if (FIDOString != null && !FIDOString.equals("") && serviceresult.getData().getStatusId() != null &&
                                        !serviceresult.getData().getStatusId().equals("")) {


                                    AuthenticateFIDORequestEntity authenticateFIDORequestEntity = new AuthenticateFIDORequestEntity();
                                    authenticateFIDORequestEntity.setUserDeviceId(initiateFIDOMFARequestEntity.getUserDeviceId());
                                    authenticateFIDORequestEntity.setStatusId(serviceresult.getData().getStatusId());
                                    authenticateFIDORequestEntity.setVerifierPassword(FIDOString);


                                    FIDOVerificationService.getShared(context).authenticateFIDO(baseurl, authenticateFIDORequestEntity, new Result<AuthenticateFIDOResponseEntity>() {
                                        @Override
                                        public void success(AuthenticateFIDOResponseEntity result) {
                                            //Todo Call Resume with Login Service
                                            //  loginresult.success(result);
                                            Toast.makeText(context, "Sucess FIDO", Toast.LENGTH_SHORT).show();
                               */
/* ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();

                                //Todo Check not Null values
                                resumeLoginRequestEntity.setSub("");
                                resumeLoginRequestEntity.setTrack_id("");
                                resumeLoginRequestEntity.setTrackingCode("");
                                resumeLoginRequestEntity.setUsageType("");
                                resumeLoginRequestEntity.setVerificationType("");
                                resumeLoginRequestEntity.setClient_id("");

                                LoginController.getShared(context).resumeLogin();*//*

                                        }

                                        @Override
                                        public void failure(WebAuthError error) {
                                            loginresult.failure(error);
                                        }
                                    });
                                    // result.success(serviceresult);
                                }
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
*/


}
