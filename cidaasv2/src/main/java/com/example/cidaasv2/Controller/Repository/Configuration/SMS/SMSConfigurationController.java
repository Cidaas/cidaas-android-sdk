package com.example.cidaasv2.Controller.Repository.Configuration.SMS;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS.EnrollSMSMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS.EnrollSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS.SetupSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS.SetupSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.SMS.SMSVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.SMS.SMSVerificationService;

import timber.log.Timber;

public class SMSConfigurationController {
    private String TrackId,RequestId,StatusId,UsageTypeFromSMS,Sub;
    private String verificationType;
    private Context context;

    public static SMSConfigurationController shared;

    public SMSConfigurationController(Context contextFromCidaas) {

        verificationType="";
        context=contextFromCidaas;
        TrackId="";
        RequestId="";
        StatusId="";
        UsageTypeFromSMS="";
        Sub="";
        //Todo setValue for authenticationType

    }



    public void configureSMS(@NonNull String sub, @NonNull final String baseurl, @NonNull final Result<SetupSMSMFAResponseEntity> result){
        try{

            Sub=sub;
            if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {

                AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessTokenresult) {
                        //Todo Service call
                        SMSVerificationService.getShared(context).setupSMSMFA(baseurl, accessTokenresult.getAccess_token(),
                                new Result<SetupSMSMFAResponseEntity>()
                                {
                                    @Override
                                    public void success(SetupSMSMFAResponseEntity serviceresult) {
                                        StatusId=serviceresult.getData().getStatusId();
                                        result.success(serviceresult);
                                    }

                                    @Override
                                    public void failure(WebAuthError error) {
                                        result.failure(error);
                                    }
                                });
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });

            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

    //Service call To enrollSMSMFA
    public void enrollSMSMFA(@NonNull final String code,@NonNull final String baseurl, @NonNull final Result<EnrollSMSMFAResponseEntity> result)
    {
        try{

            if(Sub!="" && StatusId!="")
            {
                final EnrollSMSMFARequestEntity enrollSMSMFARequestEntity=new EnrollSMSMFARequestEntity();
                enrollSMSMFARequestEntity.setCode(code);
                enrollSMSMFARequestEntity.setStatusId(StatusId);

                AccessTokenController.getShared(context).getAccessToken(Sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(AccessTokenEntity accessresult) {

                        if (enrollSMSMFARequestEntity.getSub() != null && enrollSMSMFARequestEntity.getStatusId()  != null &&
                                baseurl != null && !baseurl.equals("") && accessresult.getAccess_token() != null && !accessresult.getAccess_token().equals(""))
                        {
                            //Done Service call
                            SMSVerificationService.getShared(context).enrollSMSMFA(baseurl, accessresult.getAccess_token(), enrollSMSMFARequestEntity,
                                    new Result<EnrollSMSMFAResponseEntity>() {
                                        @Override
                                        public void success(EnrollSMSMFAResponseEntity serviceresult) {
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
                            result.failure(WebAuthError.getShared(context).propertyMissingException());
                        }
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }


        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }


    public static SMSConfigurationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new SMSConfigurationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }



    public void loginWithSMS(@NonNull final String baseurl, @NonNull final String trackId, @NonNull final String clientId,
                               @NonNull final String requestId, @NonNull InitiateSMSMFARequestEntity initiateSMSMFARequestEntity,
                               final Result<InitiateSMSMFAResponseEntity> result)
    {
        try{

            TrackId=trackId;
            RequestId=requestId;
            UsageTypeFromSMS=initiateSMSMFARequestEntity.getUsageType();

            //Todo call Inititate

            if ( initiateSMSMFARequestEntity.getUsageType() != null && initiateSMSMFARequestEntity.getUsageType() != "" &&
                    initiateSMSMFARequestEntity.getSub() != null && initiateSMSMFARequestEntity.getSub() != "" &&
                    initiateSMSMFARequestEntity.getVerificationType() != null && initiateSMSMFARequestEntity.getVerificationType() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                SMSVerificationService.getShared(context).initiateSMSMFA(baseurl, initiateSMSMFARequestEntity, new Result<InitiateSMSMFAResponseEntity>() {
                    @Override
                    public void success(InitiateSMSMFAResponseEntity serviceresult) {
                        StatusId=serviceresult.getData().getStatusId();
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
                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }


        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }





    public void verifySMS(@NonNull final String baseurl, @NonNull final String clientId, final AuthenticateSMSRequestEntity authenticateSMSRequestEntity,
                          final Result<LoginCredentialsResponseEntity> result)
    {
        try{

            if(StatusId!=null && StatusId!="") {
                authenticateSMSRequestEntity.setStatusId(StatusId);

            }
            else {
                String errorMessage="StatusId must not be empty";

                result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                        errorMessage, HttpStatusCode.EXPECTATION_FAILED));
            }

            if ( baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                SMSVerificationService.getShared(context).authenticateSMSMFA(baseurl, authenticateSMSRequestEntity, new Result<AuthenticateSMSResponseEntity>() {
                    @Override
                    public void success(AuthenticateSMSResponseEntity serviceresult) {


                        //Todo decide to move to MFA or Paswword Less Based on usageType
                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();
                        resumeLoginRequestEntity.setSub(serviceresult.getData().getSub());
                        resumeLoginRequestEntity.setTrack_id(TrackId);
                        resumeLoginRequestEntity.setTrackingCode(serviceresult.getData().getTrackingCode());
                        resumeLoginRequestEntity.setUsageType(UsageTypeFromSMS);
                        resumeLoginRequestEntity.setRequestId(RequestId);
                        resumeLoginRequestEntity.setVerificationType("SMS");
                        resumeLoginRequestEntity.setClient_id(clientId);

                        if(UsageTypeFromSMS.equals(UsageType.PASSWORDLESS)){

                            LoginController.getShared(context).continuePasswordless(baseurl,resumeLoginRequestEntity,result);
                        }

                        else if(UsageTypeFromSMS.equals(UsageType.MFA)) {


                            LoginController.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, result);
                        }

                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

































    /* //setupSMSMFA
    public void setupSMSMFA(@NonNull String sub, @NonNull final Result<SetupSMSMFAResponseEntity> result){
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
                String loggerMessage = "Setup SMS MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");

                if ( sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                    final String finalBaseurl = baseurl;
                    getAccessToken(sub, new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accesstokenresult) {
                            setupSMSMFAService(accesstokenresult.getAccess_token(), finalBaseurl,result);
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
*/
    //Service call To SetupSMSMFA
    public void setupSMSMFA(@NonNull String AccessToken,@NonNull String baseurl, @NonNull final Result<SetupSMSMFAResponseEntity> result){
        try{

            if (baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call
                SMSVerificationService.getShared(context).setupSMSMFA(baseurl, AccessToken, new Result<SetupSMSMFAResponseEntity>() {
                    @Override
                    public void success(SetupSMSMFAResponseEntity serviceresult) {
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
                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }


   /* //enrollSMSMFA
    public void enrollSMSMFA(@NonNull final EnrollSMSMFARequestEntity enrollSMSMFARequestEntity, @NonNull final Result<EnrollSMSMFAResponseEntity> result){
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
                String loggerMessage = "Setup SMS MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");

                if ( enrollSMSMFARequestEntity.getCode() != null && !enrollSMSMFARequestEntity.getCode().equals("") &&
                        enrollSMSMFARequestEntity.getSub() != null && enrollSMSMFARequestEntity.getSub()  != null &&
                        enrollSMSMFARequestEntity.getStatusId() != null && enrollSMSMFARequestEntity.getStatusId()  != null &&
                        baseurl != null && !baseurl.equals("")) {

                    final String finalBaseurl = baseurl;
                    getAccessToken(enrollSMSMFARequestEntity.getSub(), new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accesstokenresult) {
                            enrollSMSMFAService(accesstokenresult.getAccess_token(), finalBaseurl,enrollSMSMFARequestEntity,result);
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
*/
   /* //Service call To enrollSMSMFA
    public void enrollSMS(@NonNull String AccessToken,@NonNull String baseurl,
                                    @NonNull final EnrollSMSMFARequestEntity enrollSMSMFARequestEntity, @NonNull final Result<EnrollSMSMFAResponseEntity> result){
        try{

            if (enrollSMSMFARequestEntity.getCode() != null && !enrollSMSMFARequestEntity.getCode().equals("") &&
                    enrollSMSMFARequestEntity.getSub() != null && enrollSMSMFARequestEntity.getSub()  != null &&
                    enrollSMSMFARequestEntity.getStatusId() != null && enrollSMSMFARequestEntity.getStatusId()  != null &&
                    baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call
                SMSVerificationService.getShared(context).enrollSMSMFA(baseurl, AccessToken, enrollSMSMFARequestEntity,new Result<EnrollSMSMFAResponseEntity>() {
                    @Override
                    public void success(EnrollSMSMFAResponseEntity serviceresult) {
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
                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

    public void initiateSMSMFA(@NonNull String baseurl, @NonNull InitiateSMSMFARequestEntity initiateSMSMFARequestEntity, final Result<InitiateSMSMFAResponseEntity> result){
        try{

            if ( initiateSMSMFARequestEntity.getUsageType() != null && initiateSMSMFARequestEntity.getUsageType() != "" &&
                    initiateSMSMFARequestEntity.getSub() != null && initiateSMSMFARequestEntity.getSub() != "" &&
                    initiateSMSMFARequestEntity.getUserDeviceId() != null && initiateSMSMFARequestEntity.getUserDeviceId() != "" &&
                    initiateSMSMFARequestEntity.getVerificationType() != null && initiateSMSMFARequestEntity.getVerificationType() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                SMSVerificationService.getShared(context).initiateSMSMFA(baseurl, initiateSMSMFARequestEntity, new Result<InitiateSMSMFAResponseEntity>() {
                    @Override
                    public void success(InitiateSMSMFAResponseEntity serviceresult) {
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
                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }




    public void loginWithSMS(@NonNull final String baseurl, @NonNull final String trackId, @NonNull final String clientId, @NonNull final String usageType, @NonNull AuthenticateSMSRequestEntity authenticateSMSRequestEntity,
                               final Result<AccessTokenEntity> result){
        try{

            if (authenticateSMSRequestEntity.getCode() != null && authenticateSMSRequestEntity.getCode() != "" &&
                    authenticateSMSRequestEntity.getStatusId() != null && authenticateSMSRequestEntity.getStatusId() != "" &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                SMSVerificationService.getShared(context).authenticateSMSMFA(baseurl, authenticateSMSRequestEntity, new Result<AuthenticateSMSResponseEntity>() {
                    @Override
                    public void success(AuthenticateSMSResponseEntity serviceresult) {

                        ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();
                        resumeLoginRequestEntity.setSub(serviceresult.getData().getSub());
                        resumeLoginRequestEntity.setTrack_id(trackId);
                        resumeLoginRequestEntity.setTrackingCode(serviceresult.getData().getTrackingCode());
                        resumeLoginRequestEntity.setUsageType(usageType);
                        resumeLoginRequestEntity.setVerificationType("SMS");
                        resumeLoginRequestEntity.setClient_id(clientId);

                        LoginController.getShared(context).resumeLogin(baseurl,resumeLoginRequestEntity,result);

                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }

*/

}
