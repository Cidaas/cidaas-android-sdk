package com.example.cidaasv2.Controller.Repository.Verification.Email;

public class EmailVerificationController {



/*

    //initiateEmailMFA
    public void initiateEmailMFA(@NonNull PhysicalVerificationEntity physicalVerificationEntity, @NonNull final Result<InitiateEmailMFAResponseEntity> result){
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
                String loggerMessage = "initiate Email MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");

                if (physicalVerificationEntity.getPhysicalVerificationId() != null && physicalVerificationEntity.getPhysicalVerificationId() != "" &&
                        physicalVerificationEntity.getUsageType() != null && physicalVerificationEntity.getUsageType() != "" &&
                        physicalVerificationEntity.getSub() != null && physicalVerificationEntity.getSub() != "" &&
                        physicalVerificationEntity.getUserDeviceId() != null && physicalVerificationEntity.getUserDeviceId() != ""
                        && baseurl != null && !baseurl.equals("")) {

                    InitiateEmailMFARequestEntity initiateEmailMFARequestEntity=new InitiateEmailMFARequestEntity();
                    initiateEmailMFARequestEntity.setPhysicalVerificationId(physicalVerificationEntity.getPhysicalVerificationId());
                    initiateEmailMFARequestEntity.setSub(physicalVerificationEntity.getSub());
                    initiateEmailMFARequestEntity.setUsageType(physicalVerificationEntity.getUsageType());
                    initiateEmailMFARequestEntity.setUserDeviceId(physicalVerificationEntity.getUserDeviceId());
                    initiateEmailMFARequestEntity.setVerificationType("email");

                    initiateEmailMFAService(baseurl,initiateEmailMFARequestEntity,result);
                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To AcceptConsent
    private void initiateEmailMFAService(@NonNull String baseurl,@NonNull InitiateEmailMFARequestEntity initiateEmailMFARequestEntity, final Result<InitiateEmailMFAResponseEntity> result){
        try{

            if (initiateEmailMFARequestEntity.getPhysicalVerificationId() != null && initiateEmailMFARequestEntity.getPhysicalVerificationId() != "" &&
                    initiateEmailMFARequestEntity.getUsageType() != null && initiateEmailMFARequestEntity.getUsageType() != "" &&
                    initiateEmailMFARequestEntity.getSub() != null && initiateEmailMFARequestEntity.getSub() != "" &&
                    initiateEmailMFARequestEntity.getUserDeviceId() != null && initiateEmailMFARequestEntity.getUserDeviceId() != "" &&
                    initiateEmailMFARequestEntity.getVerificationType() != null && initiateEmailMFARequestEntity.getVerificationType() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).initiateEmailMFA(baseurl, initiateEmailMFARequestEntity, new Result<InitiateEmailMFAResponseEntity>() {
                    @Override
                    public void success(InitiateEmailMFAResponseEntity serviceresult) {
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

    //authenticateEmailMFA
    public void authenticateEmailMFA(@NonNull String statusId,@NonNull String verificationCode, @NonNull final Result<AuthenticateEmailResponseEntity> result){
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
                String loggerMessage = "Authenticate Email MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");



                AuthenticateEmailRequestEntity authenticateEmailRequestEntity=new AuthenticateEmailRequestEntity();
                authenticateEmailRequestEntity.setCode(verificationCode);
                authenticateEmailRequestEntity.setStatusId(statusId);

                authenticateEmailMFAService(baseurl,authenticateEmailRequestEntity,result);


            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("authenticateEmailMFA exception"+e.getMessage());
            Timber.e("authenticateEmailMFA exception"+e.getMessage());
        }
    }

    //Service call To authenticateEmailMFA
    private void authenticateEmailMFAService(@NonNull String baseurl,@NonNull AuthenticateEmailRequestEntity authenticateEmailRequestEntity, final Result<AuthenticateEmailResponseEntity> result){
        try{

            if (authenticateEmailRequestEntity.getCode() != null && authenticateEmailRequestEntity.getCode() != "" &&
                    authenticateEmailRequestEntity.getStatusId() != null && authenticateEmailRequestEntity.getStatusId() != "" &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                OauthService.getShared(context).authenticateEmailMFA(baseurl, authenticateEmailRequestEntity, new Result<AuthenticateEmailResponseEntity>() {
                    @Override
                    public void success(AuthenticateEmailResponseEntity serviceresult) {
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
*/

}
