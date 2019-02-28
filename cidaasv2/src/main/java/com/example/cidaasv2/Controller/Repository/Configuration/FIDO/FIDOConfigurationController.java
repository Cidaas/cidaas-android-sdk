package com.example.cidaasv2.Controller.Repository.Configuration.FIDO;

import android.content.Context;
import android.nfc.tech.IsoDep;
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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.FIDOKey.AuthenticateFIDORequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.FIDOKey.AuthenticateFIDOResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.FIDOKey.FidoSignTouchResponse;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey.EnrollFIDOMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey.EnrollFIDOMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey.FIDOTouchResponse;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey.U2F_V2;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.FIDOKey.InitiateFIDOMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.FIDOKey.InitiateFIDOMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.FIDO.SetupFIDOMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.FIDO.SetupFIDOMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.NFCSignObject;
import com.example.cidaasv2.Service.Repository.Verification.FIDO.FIDOVerificationService;
import com.example.cidaasv2.Service.Scanned.ScannedRequestEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import timber.log.Timber;

import static com.example.cidaasv2.Controller.Cidaas.FIDO_VERSION;

public class FIDOConfigurationController {

    //Local variables

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

    String codeVerifier="", codeChallenge="";
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

    //Service call To SetupFIDOMFA
    public void configureFIDO(@NonNull final IsoDep isoTag, @NonNull final String sub, @NonNull final String baseurl,
                              @NonNull final SetupFIDOMFARequestEntity setupFIDOMFARequestEntity,
                              @NonNull final Result<EnrollFIDOMFAResponseEntity> enrollresult)
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

                    setupFIDO(isoTag,baseurl,accessTokenresult.getAccess_token(),setupFIDOMFARequestEntity,enrollresult);
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


    public EnrollFIDOMFARequestEntity generateEnrollEntity(final IsoDep isoTag,String challenge,String appId,String FIDORequestID,String baseURL) {
        try {

            U2F_V2 u2f=new U2F_V2(isoTag);

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("version", FIDO_VERSION);
            jsonObj.put("challenge", challenge);
            jsonObj.put("appId", appId);

            String challengeJson = jsonObj.toString();

            Timber.d("challenge json" + challengeJson);

            String siginData = u2f.enroll(challengeJson, baseURL);
            JSONObject signJson = new JSONObject(siginData);

            EnrollFIDOMFARequestEntity fidoRequest = new EnrollFIDOMFARequestEntity();

            FIDOTouchResponse fidoTouchResponse = new FIDOTouchResponse();
            fidoTouchResponse.setChallenge(challenge);
            fidoTouchResponse.setClientData(signJson.get("clientData").toString());
            fidoTouchResponse.setRegistrationData(signJson.get("registrationData").toString());
            fidoTouchResponse.setVersion(FIDO_VERSION);
            fidoTouchResponse.setFidoRequestId(FIDORequestID);

            fidoRequest.setFidoTouchResponse(fidoTouchResponse);

            return fidoRequest;
        }
        catch (Exception e)
        {
              return null;
        }


    }

    public AuthenticateFIDORequestEntity generateAuthenticateEntity(IsoDep isoTag, String baseURL, NFCSignObject signEntity) {
        try {
            U2F_V2 u2f = new U2F_V2(isoTag);
            Timber.d("base url signning time" + baseURL);
            Timber.d("signEntity " + new ObjectMapper().writeValueAsString(signEntity));
            String value = u2f.sign(signEntity, baseURL);
            JSONObject json = new JSONObject(value);

            AuthenticateFIDORequestEntity fidoAuthenticateServiceEntity = new AuthenticateFIDORequestEntity();
            if (json != null) {
                try {

                    FidoSignTouchResponse fidoSignTouchResponse = new FidoSignTouchResponse();
                    if (json.getString("clientData") != null){
                        fidoSignTouchResponse.setClientData(json.getString("clientData"));
                    }
                    fidoSignTouchResponse.setFidoRequestId(signEntity.getFidoRequestId());

                    if (signEntity.getRegisteredKeys().length > 0) {
                        fidoSignTouchResponse.setKeyHandle(signEntity.getRegisteredKeys()[0].getKeyHandle());
                    }
                    if (json.getString("signatureData") != null) {
                        fidoSignTouchResponse.setSignatureData(json.getString("signatureData"));
                    }

                    fidoAuthenticateServiceEntity.setFidoSignTouchResponse(fidoSignTouchResponse);

                } catch (Exception ex) {
                    Timber.d(ex.getMessage());
                }
            }


            return fidoAuthenticateServiceEntity;
        }
        catch (Exception e)
        {
          return null;
        }
    }


    private void setupFIDO(@NonNull final IsoDep isoTag,final String baseurl, final String accessToken,
                              SetupFIDOMFARequestEntity setupFIDOMFARequestEntity,final Result<EnrollFIDOMFAResponseEntity> enrollResult)
    {
        try
        {
            if (baseurl != null && !baseurl.equals("") && accessToken != null && !accessToken.equals("") &&
                    setupFIDOMFARequestEntity.getClient_id()!=null && !setupFIDOMFARequestEntity.getClient_id().equals(""))
            {
                //Done Service call

                FIDOVerificationService.getShared(context).setupFIDO(baseurl, accessToken,
                        setupFIDOMFARequestEntity,null,new Result<SetupFIDOMFAResponseEntity>() {
                            @Override
                            public void success(final SetupFIDOMFAResponseEntity setupserviceresult) {

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

                                            SetupFIDOMFARequestEntity setupFIDOMFARequestEntity1 = new SetupFIDOMFARequestEntity();
                                            setupFIDOMFARequestEntity1.setUsage_pass(instceID);



                                            // call Scanned Service
                                            FIDOVerificationService.getShared(context).setupFIDO(baseurl, accessToken,
                                                    setupFIDOMFARequestEntity1, null, new Result<SetupFIDOMFAResponseEntity>() {
                                                        @Override
                                                        public void success(final SetupFIDOMFAResponseEntity result) {
                                                            DBHelper.getShared().setUserDeviceId(result.getData().getUdi(), baseurl);

                                                            //TOdo

                                                            //Entity For FIDO
                                                            if (result != null && result.getData().getFidoInitRequest() != null
                                                                    && result.getData().getFidoInitRequest().getRegisterRequests() != null
                                                                    && result.getData().getFidoInitRequest().getRegisterRequests().length > 0) {


                                                                EnrollFIDOMFARequestEntity enrollFIDOMFARequestEntity =
                                                                        generateEnrollEntity(isoTag, result.getData().getFidoInitRequest().getRegisterRequests()[0].getChallenge(),
                                                                                result.getData().getFidoInitRequest().getAppId(),result.getData().getFidoInitRequest().getFidoRequestId(),baseurl);

                                                                enrollFIDOMFARequestEntity.setStatusId(result.getData().getSt());
                                                                enrollFIDOMFARequestEntity.setUserDeviceId(result.getData().getUdi());
                                                                enrollFIDO(baseurl,accessToken,enrollFIDOMFARequestEntity,enrollResult);
                                                            }
                                                            else
                                                            {
                                                                enrollResult.failure(WebAuthError.getShared(context)
                                                                        .customException(WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE,"One of the property is missing",HttpStatusCode.EXPECTATION_FAILED));
                                                            }
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
            enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE,
                    "FIDO Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }



    public void scannedWithFIDO(final String baseurl,  String statusId, String clientId, final Result<ScannedResponseEntity> scannedResult)
    {
        try
        {
            if (baseurl != null && !baseurl.equals("")  && statusId!=null && !statusId.equals("") && clientId!=null && !clientId.equals("")) {

                final ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
                scannedRequestEntity.setStatusId(statusId);
                scannedRequestEntity.setClient_id(clientId);


                FIDOVerificationService.getShared(context).scannedFIDO(baseurl,  scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
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

                                    FIDOVerificationService.getShared(context).scannedFIDO(baseurl,  scannedRequestEntity, null, new Result<ScannedResponseEntity>() {

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
                scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_FIDO_MFA_FAILURE,
                        "BaseURL or ClientId or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }

        }
        catch (Exception e)
        {
            scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_FIDO_MFA_FAILURE,
                    "FIDO Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }



    public void enrollFIDO(@NonNull final String baseurl, @NonNull final String accessToken,
                              @NonNull EnrollFIDOMFARequestEntity enrollFIDOMFARequestEntity, final Result<EnrollFIDOMFAResponseEntity> enrollResult)
    {
        try
        {

            if(baseurl!=null && !baseurl.equals("") && accessToken!=null && !accessToken.equals("")) {

                if (enrollFIDOMFARequestEntity.getUserDeviceId() != null && !enrollFIDOMFARequestEntity.getUserDeviceId().equals("") &&
                        enrollFIDOMFARequestEntity.getStatusId() != null && !enrollFIDOMFARequestEntity.getStatusId().equals("") &&
                        enrollFIDOMFARequestEntity.getFidoTouchResponse().getFidoRequestId() != null && !enrollFIDOMFARequestEntity.getFidoTouchResponse().getFidoRequestId().equals("")) {

                    // call Enroll Service
                    FIDOVerificationService.getShared(context).enrollFIDO(baseurl, accessToken, enrollFIDOMFARequestEntity,
                            null, new Result<EnrollFIDOMFAResponseEntity>() {

                                @Override
                                public void success(final EnrollFIDOMFAResponseEntity serviceresult) {

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
                                                EnrollFIDOMFARequestEntity enrollFIDOMFARequestEntity = new EnrollFIDOMFARequestEntity();
                                                enrollFIDOMFARequestEntity.setUsage_pass(instceID);

                                                // call Enroll Service
                                                FIDOVerificationService.getShared(context).enrollFIDO(baseurl, accessToken, enrollFIDOMFARequestEntity,
                                                        null, new Result<EnrollFIDOMFAResponseEntity>() {
                                                            @Override
                                                            public void success(EnrollFIDOMFAResponseEntity serviceresult) {
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
                    enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE,
                            "UserdeviceId or Verifierpassword or StatusID must not be empty", HttpStatusCode.EXPECTATION_FAILED));
                }
            }
            else
            {
                enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE,
                        "BaseURL or accessToken must not be empty", HttpStatusCode.EXPECTATION_FAILED));
            }


        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE,
                    "FIDO Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));

        }
    }







    //Login with FIDO
    public void LoginWithFIDO(@NonNull final IsoDep isoTag, @NonNull final String baseurl, @NonNull final String clientId,
                              @NonNull final String trackId, @NonNull final String requestId,
                              @NonNull final InitiateFIDOMFARequestEntity initiateFIDOMFARequestEntity,
                              final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(codeChallenge.equals("") && codeVerifier.equals("")) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.instanceId="";
            if(initiateFIDOMFARequestEntity.getUserDeviceId() != null && !initiateFIDOMFARequestEntity.getUserDeviceId().equals(""))
            {
                //Do nothing
            }
            else
            {
                initiateFIDOMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
            }
            initiateFIDOMFARequestEntity.setClient_id(clientId);


            if (    initiateFIDOMFARequestEntity.getUsageType() != null && !initiateFIDOMFARequestEntity.getUsageType().equals("") &&
                    initiateFIDOMFARequestEntity.getUserDeviceId() != null && !initiateFIDOMFARequestEntity.getUserDeviceId().equals("") &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                FIDOVerificationService.getShared(context).initiateFIDO(baseurl, initiateFIDOMFARequestEntity,null,
                        new Result<InitiateFIDOMFAResponseEntity>() {

                            @Override
                            public void success(final InitiateFIDOMFAResponseEntity serviceresult) {

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
                                        if(instceID!=null && instceID!="") {

                                            //Todo call initiate
                                            final InitiateFIDOMFARequestEntity initiateFIDOMFARequestEntity=new InitiateFIDOMFARequestEntity();
                                            initiateFIDOMFARequestEntity.setUsagePass(instceID);

                                            final String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);

                                            FIDOVerificationService.getShared(context).initiateFIDO(baseurl,  initiateFIDOMFARequestEntity,null,
                                                    new Result<InitiateFIDOMFAResponseEntity>() {

                                                        @Override
                                                        public void success(InitiateFIDOMFAResponseEntity initiateresult) {
                                                            if (isoTag != null && !isoTag.equals("") && initiateresult.getData().getStatusId() != null &&
                                                                    !initiateresult.getData().getStatusId().equals("")) {


                                                                AuthenticateFIDORequestEntity authenticateFIDORequestEntity = generateAuthenticateEntity(isoTag,baseurl,initiateresult.getData().getFido_init_request_data());

                                                                authenticateFIDORequestEntity.setUserDeviceId(userDeviceId);
                                                                authenticateFIDORequestEntity.setStatusId(initiateresult.getData().getStatusId());
                                                                authenticateFIDORequestEntity.setClient_id(clientId);


                                                                authenticateFIDO(baseurl, authenticateFIDORequestEntity, new Result<AuthenticateFIDOResponseEntity>() {
                                                                    @Override
                                                                    public void success(AuthenticateFIDOResponseEntity result) {

                                                                        //Todo Call Resume with Login Service

                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

                                                                        //Todo Check not Null values
                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                        resumeLoginRequestEntity.setVerificationType("FIDO");
                                                                        resumeLoginRequestEntity.setUsageType(initiateFIDOMFARequestEntity.getUsageType());
                                                                        resumeLoginRequestEntity.setClient_id(clientId);
                                                                        resumeLoginRequestEntity.setRequestId(requestId);

                                                                        if (initiateFIDOMFARequestEntity.getUsageType().equals(UsageType.MFA)) {
                                                                            resumeLoginRequestEntity.setTrack_id(trackId);
                                                                            LoginController.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, loginresult);
                                                                        } else if (initiateFIDOMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS)) {
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
                                                                String errorMessage="Status Id or FIDO Must not be null";
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



    //Authenticate FIDO

    public void authenticateFIDO(final String baseurl, final AuthenticateFIDORequestEntity authenticateFIDORequestEntity, final Result<AuthenticateFIDOResponseEntity> authResult)
    {
        try
        {
            FIDOVerificationService.getShared(context).authenticateFIDO(baseurl, authenticateFIDORequestEntity,
                    null, new Result<AuthenticateFIDOResponseEntity>() {
                @Override
                public void success(final AuthenticateFIDOResponseEntity serviceresult) {


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
                                AuthenticateFIDORequestEntity authenticateFIDORequestEntity=new AuthenticateFIDORequestEntity();
                                authenticateFIDORequestEntity.setUsage_pass(instceID);

                                FIDOVerificationService.getShared(context).authenticateFIDO(baseurl, authenticateFIDORequestEntity,
                                        null, new Result<AuthenticateFIDOResponseEntity>() {
                                    @Override
                                    public void success(AuthenticateFIDOResponseEntity result) {
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
            authResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.AUTHENTICATE_FIDO_MFA_FAILURE,
                    "FIDO Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
        }
    }



}
