package com.example.cidaasv2.Controller.Repository.Configuration.FIDO;

import android.content.Context;
import android.nfc.tech.IsoDep;
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

import java.util.Dictionary;

import androidx.annotation.NonNull;
import timber.log.Timber;

import static com.example.cidaasv2.Controller.Cidaas.FIDO_VERSION;

public class FIDOConfigurationController {

    //Local variables

    private String authenticationType;
    private String verificationType;
    private Context context;
    public static String logoURLlocal="https://cdn.shortpixel.ai/client/q_glossy,ret_img/https://www.cidaas.com/wp-content/uploads/2018/02/logo.png";


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


    public void configureFIDO(@NonNull final IsoDep isoTag, @NonNull final String sub, @NonNull final String logoURL,
                              final Result<EnrollFIDOMFAResponseEntity> enrollresult)
    {
        try {


            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");


                    if (sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                        final String finalBaseurl = baseurl;


                        if(!logoURL.equals("") && logoURL!=null) {
                            logoURLlocal=logoURL;
                        }




                        SetupFIDOMFARequestEntity setupFIDOMFARequestEntity = new SetupFIDOMFARequestEntity();
                        setupFIDOMFARequestEntity.setClient_id(result.get("ClientId"));
                        setupFIDOMFARequestEntity.setLogoUrl(logoURLlocal);

                        configureFIDO(isoTag,sub, finalBaseurl, setupFIDOMFARequestEntity,enrollresult);


                    } else {
                        String errorMessage = "Sub or FIDO or logoURL cannot be null";
                        enrollresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING, errorMessage,"Error :FIDOConfigurationController :setupFIDO()"));
                    }
                }

                @Override
                public void failure(WebAuthError error) {

                    enrollresult.failure(error);
                }
            });

        } catch (Exception e) {


            enrollresult.failure(WebAuthError.getShared(context).methodException("Exception :FIDOConfigurationController :configureFIDO()",WebAuthErrorCode.SETUP_FIDO_MFA_FAILURE, e.getMessage()));
        }
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
            Cidaas.usagePass ="";

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
            enrollresult.failure(WebAuthError.getShared(context).methodException("Exception :FIDOConfigurationController :configureFIDO()",WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE,e.getMessage()));
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
            LogFile.getShared(context).addFailureLog("FIDO Generate Enroll Entity Exception:" + e.getMessage() + WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE);
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
                    LogFile.getShared(context).addFailureLog("FIDO  Generate Authenticate Entity Exception:" + ex.getMessage() + WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE);
                }
            }


            return fidoAuthenticateServiceEntity;
        }
        catch (Exception e)
        {
            LogFile.getShared(context).addFailureLog("FIDO Generate Authenticate Entity Exception:" + e.getMessage() + WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE);
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
                                                                        .customException(WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE,"One of the property is missing","Error :FIDOConfigurationController :setupFIDO()"));
                                                            }
                                                        }


                                                        @Override
                                                        public void failure(WebAuthError error) {
                                                            enrollResult.failure(error);
                                                        }
                                                    });
                                        }

                                        else {
                                            enrollResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException("Error :FIDOConfigurationController :setupFIDO()"));
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

                enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("BaseURL or AccessToken or ClientId must not be null","Error :FIDOConfigurationController :setupFIDO()"));
            }
        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception :FIDOConfigurationController :setupFIDO()",WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE,e.getMessage()));
        }
    }



    public void scannedWithFIDO(final String statusId, final Result<ScannedResponseEntity> scannedResult)
    {
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    final String baseurl = result.get("DomainURL");
                    String clientId=result.get("ClientId");

                    if (statusId!=null && !statusId.equals("")) {

                        final ScannedRequestEntity scannedRequestEntity = new ScannedRequestEntity();
                        scannedRequestEntity.setStatusId(statusId);
                        scannedRequestEntity.setClient_id(clientId);


                        FidoScannedService(baseurl, scannedRequestEntity,scannedResult);
                    }
                    else {
                        scannedResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.SCANNED_FIDO_MFA_FAILURE,
                                "BaseURL or ClientId or StatusID must not be empty", "Error :FIDOConfigurationController :scannedWithFIDO()"));
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
            scannedResult.failure(WebAuthError.getShared(context).methodException("Exception :FIDOConfigurationController :scannedWithFIDO()",WebAuthErrorCode.SCANNED_FIDO_MFA_FAILURE,e.getMessage()));
        }
    }

    private void FidoScannedService(final String baseurl, ScannedRequestEntity scannedRequestEntity,final Result<ScannedResponseEntity> scannedResult) {
       try {
           FIDOVerificationService.getShared(context).scannedFIDO(baseurl, scannedRequestEntity, null, new Result<ScannedResponseEntity>() {
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
                               FIDOVerificationService.getShared(context).scannedFIDO(baseurl, scannedRequestEntity, null, scannedResult);
                           } else {
                               scannedResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException("Error :FIDOConfigurationController :FidoScannedService()"));
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
           scannedResult.failure(WebAuthError.getShared(context).methodException("Exception :FIDOConfigurationController :FidoScannedService()",WebAuthErrorCode.SCANNED_FIDO_MFA_FAILURE,e.getMessage()));
       }
    }

    public void enrollFIDO(@NonNull final FIDOTouchResponse fidoResponse,  @NonNull final String sub, @NonNull final String statusId, final Result<EnrollFIDOMFAResponseEntity> enrollResult)
    {
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    final String baseurl = lpresult.get("DomainURL");
                    final String clientId= lpresult.get("ClientId");

                    AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accessTOkenresult) {

                            EnrollFIDOMFARequestEntity enrollFIDOMFARequestEntity=new EnrollFIDOMFARequestEntity();
                            enrollFIDOMFARequestEntity.setFidoTouchResponse(fidoResponse);
                            enrollFIDOMFARequestEntity.setStatusId(statusId);
                            enrollFIDOMFARequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
                            enrollFIDOMFARequestEntity.setClient_id(clientId);

                            enrollFIDO(baseurl,accessTOkenresult.getAccess_token(),enrollFIDOMFARequestEntity,enrollResult);
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
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception :FIDOConfigurationController :enrollFIDO()",WebAuthErrorCode.PROPERTY_MISSING,e.getMessage()));
            
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
                                                enrollResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException("Error :FIDOConfigurationController :enrollFIDO()"));
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
                    enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("UserdeviceId or Verifierpassword or StatusID must not be empty","Error :FIDOConfigurationController :enrollFIDO()"));
                }
            }
            else
            {
                enrollResult.failure(WebAuthError.getShared(context).propertyMissingException("BaseURL or accessToken must not be empty", "Error :FIDOConfigurationController :enrollFIDO()"));
            }


        }
        catch (Exception e)
        {
            enrollResult.failure(WebAuthError.getShared(context).methodException("Exception :FIDOConfigurationController :enrollFIDO()",
                    WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE,e.getMessage()));
        }
    }




    public void LoginWithFIDO(@NonNull final IsoDep isoTag, @NonNull final PasswordlessEntity passwordlessEntity,
                              final Result<LoginCredentialsResponseEntity> loginresult) {
        try {


            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    String clientId = result.get("ClientId");

                    if (passwordlessEntity.getUsageType() != null && !passwordlessEntity.getUsageType().equals("")
                            && passwordlessEntity.getRequestId() != null && !passwordlessEntity.getRequestId().equals("")) {

                        if (baseurl == null || baseurl.equals("") && clientId == null || clientId.equals("")) {
                            String errorMessage = "baseurl or clientId or mobile number must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage,
                                    "Error :FIDOConfigurationController :LoginWithFIDO()"));
                            return;
                        }

                        if (((passwordlessEntity.getSub() == null || passwordlessEntity.getSub().equals("")) &&
                                (passwordlessEntity.getEmail() == null || passwordlessEntity.getEmail().equals("")) &&
                                (passwordlessEntity.getMobile() == null || passwordlessEntity.getMobile().equals("")))) {
                            String errorMessage = "sub or email or mobile number must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage,
                                    "Error :FIDOConfigurationController :LoginWithFIDO()"));
                            return;
                        }

                        if (passwordlessEntity.getUsageType().equals(UsageType.MFA)) {
                            if (passwordlessEntity.getTrackId() == null || passwordlessEntity.getTrackId() == "") {
                                String errorMessage = "trackId must not be empty";

                                loginresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage,
                                        "Error :FIDOConfigurationController :LoginWithFIDO()"));
                                return;
                            }
                        }

                        InitiateFIDOMFARequestEntity initiateFIDOMFARequestEntity = new InitiateFIDOMFARequestEntity();
                        initiateFIDOMFARequestEntity.setSub(passwordlessEntity.getSub());
                        initiateFIDOMFARequestEntity.setUsageType(passwordlessEntity.getUsageType());
                        initiateFIDOMFARequestEntity.setEmail(passwordlessEntity.getEmail());
                        initiateFIDOMFARequestEntity.setMobile(passwordlessEntity.getMobile());

                        //Todo check for email or sub or mobile

                        LoginWithFIDO(isoTag, baseurl, clientId, passwordlessEntity,initiateFIDOMFARequestEntity, loginresult);
                    } else {
                        String errorMessage = "UsageType or FIDOCode or requestId must not be empty";

                        loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage, "Error :FIDOConfigurationController :LoginWithFIDO()"));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(error);
                }
            });


        } catch (Exception e) {
          
            loginresult.failure(WebAuthError.getShared(context).methodException("Exception :FIDOConfigurationController :LoginWithFIDO()",
                    WebAuthErrorCode.PROPERTY_MISSING,
                    e.getMessage()));
           
        }
    }

    //Login with FIDO
    public void LoginWithFIDO(@NonNull final IsoDep isoTag, @NonNull final String baseurl, @NonNull final String clientId,
                              @NonNull final PasswordlessEntity passwordlessEntity,
                              @NonNull final InitiateFIDOMFARequestEntity initiateFIDOMFARequestEntity,
                              final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            if(codeChallenge.equals("") && codeVerifier.equals("")) {
                //Generate Challenge
                generateChallenge();
            }
            Cidaas.usagePass ="";
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

                //Todo call initiate
                LogFile.getShared(context).addInfoLog("Info :PatternConfigurationController :InitiatePatternAfterDeviceVerification()",
                        "Info UsageType:-"+passwordlessEntity.getUsageType()+" Sub:- "+passwordlessEntity.getSub()+
                                " Email"+passwordlessEntity.getEmail()+ " Mobile"+passwordlessEntity.getMobile()+" RequestId:-"+passwordlessEntity.getRequestId()+
                                " TrackId:-"+passwordlessEntity.getTrackId());
                //Todo Service call
                FIDOVerificationService.getShared(context).initiateFIDO(baseurl, initiateFIDOMFARequestEntity,null,
                        new Result<InitiateFIDOMFAResponseEntity>() {

                            @Override
                            public void success(final InitiateFIDOMFAResponseEntity serviceresult) {

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

                                                                        LogFile.getShared(context).addSuccessLog("Success :Pattern configuration Controller :InitiatePatternAfterDeviceVerification()",
                                                                                "Sub:-"+result.getData().getSub()+"TrackingCode"+result.getData().getTrackingCode());

                                                                        //Todo Call Resume with Login Service

                                                                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

                                                                        //Todo Check not Null values
                                                                        resumeLoginRequestEntity.setSub(result.getData().getSub());
                                                                        resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                                                        resumeLoginRequestEntity.setVerificationType("FIDO");
                                                                        resumeLoginRequestEntity.setUsageType(initiateFIDOMFARequestEntity.getUsageType());
                                                                        resumeLoginRequestEntity.setClient_id(clientId);
                                                                        resumeLoginRequestEntity.setRequestId(passwordlessEntity.getRequestId());

                                                                        if (initiateFIDOMFARequestEntity.getUsageType().equals(UsageType.MFA)) {
                                                                            resumeLoginRequestEntity.setTrack_id(passwordlessEntity.getTrackId());
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
                                                                loginresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, "Error :FIDOConfigurationController :LoginWithFIDO()"));

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

                                            loginresult.failure(WebAuthError.getShared(context).deviceVerificationFailureException("Error :FIDOConfigurationController :LoginWithFIDO()"));
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

                loginresult.failure(WebAuthError.getShared(context).propertyMissingException("UsageType or userDeviceId or baseurl must not be null","Error :FIDOConfigurationController :LoginWithFIDO()"));
            }
        }
        catch (Exception e)
        {
          
            loginresult.failure(WebAuthError.getShared(context).methodException("Exception :FIDOConfigurationController :LoginWithFIDO()",WebAuthErrorCode.AUTHENTICATE_FIDO_MFA_FAILURE,e.getMessage()));
        }
    }

    public void authenticateFIDO(final FidoSignTouchResponse fidoSignTouchResponse, final String statusId, final Result<AuthenticateFIDOResponseEntity> result) {
        try {

            LogFile.getShared(context).addInfoLog("Info :PatternConfigurationController :authenticatePattern()",
                    " FidoResponse:- "+fidoSignTouchResponse.getClientData()+" StatusId:-"+statusId);

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = lpresult.get("DomainURL");
                    String clientId = lpresult.get("ClientId");
                    //todo call verify FIDO

                    AuthenticateFIDORequestEntity authenticateFIDORequestEntity=new AuthenticateFIDORequestEntity();
                    authenticateFIDORequestEntity.setStatusId(statusId);
                    authenticateFIDORequestEntity.setUserDeviceId(DBHelper.getShared().getUserDeviceId(baseurl));
                    authenticateFIDORequestEntity.setFidoSignTouchResponse(fidoSignTouchResponse);
                    authenticateFIDORequestEntity.setClient_id(clientId);

                    authenticateFIDO(baseurl,authenticateFIDORequestEntity,result);
                    // FIDOVerificationService.getShared(context).authenticateFIDO(baseurl,authenticateFIDORequestEntity,null,result);

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty","Error :FIDOConfigurationController :authenticateFIDO()"));
                }
            });
        } catch (Exception e) {
          result.failure(WebAuthError.getShared(context).methodException("Exception :FIDOConfigurationController :authenticateFIDO()",WebAuthErrorCode.AUTHENTICATE_FIDO_MFA_FAILURE,e.getMessage()));
        }
    }


        //Authenticate FIDO

    public void authenticateFIDO(final String baseurl, final AuthenticateFIDORequestEntity authenticateFIDORequestEntity, final Result<AuthenticateFIDOResponseEntity> authResult) {
        try {
            LogFile.getShared(context).addInfoLog("Info :PatternConfigurationController :authenticatePattern()",
                    " BaseURL:- "+baseurl+" ClientId:-"+ authenticateFIDORequestEntity.getClient_id());


            FIDOVerificationService.getShared(context).authenticateFIDO(baseurl, authenticateFIDORequestEntity,
                    null, new Result<AuthenticateFIDOResponseEntity>() {
                        @Override
                        public void success(final AuthenticateFIDOResponseEntity serviceresult) {


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
                                        AuthenticateFIDORequestEntity authenticateFIDORequestEntity = new AuthenticateFIDORequestEntity();
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
                                    } else {
                                        // return Error Message
                                        authResult.failure(WebAuthError.getShared(context).deviceVerificationFailureException("Error :FIDOConfigurationController :authenticateFIDO()"));
                                    }

                                }
                            }.start();
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            authResult.failure(error);
                        }
                    });
        } catch (Exception e) {
            authResult.failure(WebAuthError.getShared(context).methodException("Exception :FIDOConfigurationController :authenticateFIDO()",
                    WebAuthErrorCode.AUTHENTICATE_FIDO_MFA_FAILURE,e.getMessage()));
        }
    }
}
