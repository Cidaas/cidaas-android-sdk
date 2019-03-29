package com.example.cidaasv2.Controller.Repository.Registration;

import android.content.Context;

import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.RegistrationEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyResponseEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupRequestEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupResponseEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupResultDataEntity;
import com.example.cidaasv2.Service.Repository.Registration.RegistrationService;

import java.util.Dictionary;
import java.util.Locale;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class RegistrationController {


    private Context context;

    public static RegistrationController shared;
    public RegistrationSetupResultDataEntity[] registerFields;


    String accvid;

    RegistrationSetupResponseEntity validateRegistrationFilelds=new RegistrationSetupResponseEntity();

    public RegistrationController(Context contextFromCidaas) {

        context=contextFromCidaas;
        //Todo setValue for authenticationType

    }

    public static RegistrationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new RegistrationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }
    public void getRegisterationFields(@NonNull final String requestId, final String locale,
                                      final Result<RegistrationSetupResponseEntity> registerFieldsresult) {

        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    String clientId = result.get("ClientId");
                    String language;

                    if (!requestId.equals("")) {

                        final RegistrationSetupRequestEntity registrationSetupRequestEntity;

                        registrationSetupRequestEntity = new RegistrationSetupRequestEntity();
                        registrationSetupRequestEntity.setRequestId(requestId);

                        if (locale == null || locale == "") {
                            language = Locale.getDefault().getLanguage();
                            registrationSetupRequestEntity.setAcceptedLanguage(language);

                        } else {
                            language = locale;
                            registrationSetupRequestEntity.setAcceptedLanguage(language);
                        }

                        getRegisterationFields(baseurl, registrationSetupRequestEntity, registerFieldsresult);

                    } else {
                        String errorMessage = "RequestId must not be empty";

                        registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty"));
                }
            });
        } catch (Exception e) {
            String errorMessage = "Get Registration Fields Custom Exception" + e.getMessage();

            registerFieldsresult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE));
            LogFile.getShared(context).addRecordToLog(errorMessage + WebAuthErrorCode.REGISTRATION_SETUP_FAILURE);
        }
    }

    //Service call To Registration Setup
    public void getRegisterationFields(@NonNull String baseurl, @NonNull RegistrationSetupRequestEntity registrationSetupRequestEntity,
                                       final Result<RegistrationSetupResponseEntity> result)
    {
        try{

            if (registrationSetupRequestEntity.getAcceptedLanguage() != null && !registrationSetupRequestEntity.getAcceptedLanguage().equals("") &&
                    registrationSetupRequestEntity.getRequestId() != null && !registrationSetupRequestEntity.getRequestId().equals("")
                    && baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                RegistrationService.getShared(context).getRegistrationSetup(baseurl, registrationSetupRequestEntity,null,
                        new Result<RegistrationSetupResponseEntity>() {
                    @Override
                    public void success(RegistrationSetupResponseEntity serviceresult) {
                        validateRegistrationFilelds=serviceresult;
                        registerFields = serviceresult.getData();
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
                String errorMessage="RequestId must not be empty";

                result.failure(WebAuthError.getShared(context).propertyMissingException("Accepted Language or requestId must not be null"));
            }
        }
        catch (Exception e)
        {

            String errorMessage="Exception"+e.getMessage();

            LogFile.getShared(context).addRecordToLog("Get Registration Fields Exception:"+e.getMessage()+WebAuthErrorCode.REGISTRATION_SETUP_FAILURE);
            result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE));
        }
    }

    public void registerNewUser(@NonNull final String requestId, final RegistrationEntity registrationEntity,
                             final Result<RegisterNewUserResponseEntity> registerFieldsresult) {
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    String clientId = result.get("ClientId");
                    String language;

                    if (!requestId.equals("")) {
                        if (registerFields != null)

                        {
                            if (registerFields.length > 0) {
                                for (RegistrationSetupResultDataEntity dataEntity : registerFields) {

                                    if (dataEntity.getFieldKey().equals("email")) {
                                        if (dataEntity.isRequired() && registrationEntity.getEmail().equals("")) {
                                            String errorMessage = "Email must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                                    errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                                            return;


                                        }

                                    }

                                    if (dataEntity.getFieldKey().equals("given_name")) {
                                        if (dataEntity.isRequired() && registrationEntity.getGiven_name().equals("")) {
                                            String errorMessage = "given_name must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                                    errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                                            return;
                                        }

                                    }

                                    if (dataEntity.getFieldKey().equals("family_name")) {
                                        if (dataEntity.isRequired() && registrationEntity.getFamily_name().equals("")) {
                                            String errorMessage = "family_name must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                                    errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                                            return;

                                        }

                                    }

                                    if (dataEntity.getFieldKey().equals("mobile_number")) {
                                        if (dataEntity.isRequired() && registrationEntity.getMobile_number().equals("")) {
                                            String errorMessage = "mobile_number must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                                    errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                                            return;

                                        }

                                    }

                                    if (dataEntity.getFieldKey().equals("password")) {
                                        if (dataEntity.isRequired() && registrationEntity.getPassword().equals("")) {
                                            String errorMessage = "password must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                                    errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                                            return;


                                        }

                                    }

                                    if (dataEntity.getFieldKey().equals("password_echo")) {
                                        if (dataEntity.isRequired() && registrationEntity.getGiven_name().equals("")) {
                                            String errorMessage = "password_echo must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                                    errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                                            return;

                                        }
                                        if (!registrationEntity.getPassword().equals(registrationEntity.getPassword_echo())) {

                                            String errorMessage = "Password and password_echo must be same";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                                    errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                                            return;

                                        }

                                    }

                                    if (dataEntity.getFieldKey().equals("username")) {
                                        if (dataEntity.isRequired() && registrationEntity.getUsername().equals("")) {
                                            String errorMessage = "username must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                                    errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                                            return;

                                        }

                                    }

                                    if (dataEntity.getFieldKey().equals("birthdate")) {
                                        if (dataEntity.isRequired() && registrationEntity.getBirthdate().equals("")) {
                                            String errorMessage = "birthdate must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                                    errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                                            return;
                                        }

                                    }

                                    if (registrationEntity.getProvider() != null && !registrationEntity.getProvider().equals("")) {

                                        String errorMessage = "Provider must not be empty";
                                        registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                                errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                                        return;

                                    }

                                    for (int i = 0; i < registrationEntity.getCustomFields().size(); i++) {
                                        if (registrationEntity.getCustomFields().keys().hasMoreElements()) {

                                            // registrationEntity.getCustomFields().get()
                                        }
                                    }


                                }
                            } else {

                            }
                        } else {

                        }

                        final RegisterNewUserRequestEntity registerNewUserRequestEntity;

                        registerNewUserRequestEntity = new RegisterNewUserRequestEntity();
                        registerNewUserRequestEntity.setRequestId(requestId);
                        registrationEntity.setProvider("self");
                        registerNewUserRequestEntity.setRegistrationEntity(registrationEntity);


                        registerNewUser(baseurl, registerNewUserRequestEntity,registerFieldsresult);

                    } else {
                        String errorMessage = "RequestId must not be empty";

                        registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty"));
                }
            });
        } catch (Exception e) {
            String errorMessage = "Custom Exception" + e.getMessage();

            registerFieldsresult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE));
            LogFile.getShared(context).addRecordToLog(errorMessage);
        }
    }

    //Register new User
    public void registerNewUser(String baseurl, RegisterNewUserRequestEntity registrationEntity, final Result<RegisterNewUserResponseEntity> result) {
        try {
            //Todo Check for Not null

            /*if(validateRegistrationFilelds!=null) {
                for (RegistrationSetupResultDataEntity dataEntity : validateRegistrationFilelds.getData()
                ) {
                    if (dataEntity.isRequired()) {

                    }
                }
            }*/
                registerWithNewUserService(baseurl,registrationEntity,result);


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Register new user Failure"+e.getMessage()+WebAuthErrorCode.REGISTRATION_SETUP_FAILURE);
            result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE));
        }
    }

    //Service call To Registration Setup
    public void registerWithNewUserService(@NonNull String baseurl,@NonNull RegisterNewUserRequestEntity registerNewUserRequestEntity,
                                           final Result<RegisterNewUserResponseEntity> result){
        try{

            if (registerNewUserRequestEntity.getRequestId() != null && !registerNewUserRequestEntity.getRequestId().equals("") &&
                    registerNewUserRequestEntity.getRegistrationEntity() != null &&
                    !registerNewUserRequestEntity.getRegistrationEntity().getFamily_name().equals("") && baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                RegistrationService.getShared(context).registerNewUser(baseurl, registerNewUserRequestEntity,null, result);
            }
            else
            {

                String errorMessage="RequestId must not be empty";

                result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage));
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
            LogFile.getShared(context).addRecordToLog("Register new user Service Failure"+e.getMessage()+WebAuthErrorCode.REGISTRATION_SETUP_FAILURE);
            result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE));
        }
    }



    public void initiateAccountVerificationService(@NonNull final String sub, @NonNull final String requestId, @NonNull final String verificationMedium,
                                          final Result<RegisterUserAccountInitiateResponseEntity> Result) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    String clientId = result.get("ClientId");

                    RegisterUserAccountInitiateRequestEntity registerUserAccountInitiateRequestEntity = new RegisterUserAccountInitiateRequestEntity();
                    registerUserAccountInitiateRequestEntity.setProcessingType("CODE");
                    registerUserAccountInitiateRequestEntity.setVerificationMedium(verificationMedium);
                    registerUserAccountInitiateRequestEntity.setSub(sub);
                    registerUserAccountInitiateRequestEntity.setRequestId(requestId);
                    initiateAccountVerificationService(baseurl, registerUserAccountInitiateRequestEntity, Result);

                }

                @Override
                public void failure(WebAuthError error) {
                    Result.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty"));
                }
            });
        } catch (Exception e) {
            Result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE));
        }
    }


    //Service call To  register New User Account Verification via Email Setup
    public void initiateAccountVerificationService(@NonNull String baseurl,@NonNull RegisterUserAccountInitiateRequestEntity registrationEntity,
                                                    final Result<RegisterUserAccountInitiateResponseEntity> result)
    {
        try{

            if (registrationEntity.getRequestId() != null && !registrationEntity.getRequestId().equals("") &&
                    registrationEntity.getProcessingType() != null && !registrationEntity.getProcessingType().equals("") &&
                    registrationEntity.getVerificationMedium() != null && !registrationEntity.getVerificationMedium().equals("") &&
                    registrationEntity.getSub() != null && !registrationEntity.getSub().equals("")
                    && baseurl != null && !baseurl.equals("")) {

                //Todo Service call
                RegistrationService.getShared(context).initiateAccountVerification(baseurl, registrationEntity,null,
                        new Result<RegisterUserAccountInitiateResponseEntity>() {
                    @Override
                    public void success(RegisterUserAccountInitiateResponseEntity serviceresult) {
                        accvid=serviceresult.getData().getAccvid();


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
                result.failure(WebAuthError.getShared(context).propertyMissingException("Verification medium , sub or processing type must not be null"));
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
            result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE));
            LogFile.getShared(context).addRecordToLog("Initiate Account verification Failure"+e.getMessage()+WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE);

        }
    }

    public void verifyAccountVerificationService(@NonNull final String code, @NonNull final String accvid, final Result<RegisterUserAccountVerifyResponseEntity> result) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = lpresult.get("DomainURL");
                    String clientId = lpresult.get("ClientId");

                    if (code != null && !code.equals("") && accvid != null && !accvid.equals("")) {
                        verifyAccountVerificationService(baseurl, code, accvid, result);
                    } else {
                        result.failure(WebAuthError.getShared(context).customException(417,"Verification Code or accvid must not be empty",417));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty"));
                }
            });
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE));
        }
    }

        //Service call To  register New User Account Verification via Email Setup
    public void verifyAccountVerificationService(@NonNull String baseurl, @NonNull String code, String accvid,
                                                 final Result<RegisterUserAccountVerifyResponseEntity> result)
    {
        try{

            if (accvid != null && !accvid.equals("") && code != null && !code.equals("") &&
                    baseurl != null && !baseurl.equals("")) {

                RegisterUserAccountVerifyRequestEntity registrationEntity=new RegisterUserAccountVerifyRequestEntity();
                registrationEntity.setCode(code);
                registrationEntity.setAccvid(accvid);

                //Todo Service call
                RegistrationService.getShared(context).verifyAccountVerification(baseurl, registrationEntity,null,
                        result);
            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException("ACCVID or CODE or BASEURL must not be null"));
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
            result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE));
            LogFile.getShared(context).addRecordToLog("Verify Account verification Failure"+e.getMessage()+WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE);
        }
    }

}
