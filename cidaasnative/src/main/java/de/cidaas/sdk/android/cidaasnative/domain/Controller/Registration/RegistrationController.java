package de.cidaas.sdk.android.cidaasnative.domain.Controller.Registration;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Dictionary;
import java.util.Locale;

import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegisterUser.RegisterNewUserRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegisterUser.RegisterNewUserResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegistrationEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegistrationSetup.RegistrationSetupRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegistrationSetup.RegistrationSetupResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegistrationSetup.RegistrationSetupResultDataEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Service.Registration.RegistrationService;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.properties.CidaasProperties;
import timber.log.Timber;

public class RegistrationController {


    private Context context;

    public static RegistrationController shared;
    public RegistrationSetupResultDataEntity[] registerFields;


    String accvid;

    RegistrationSetupResponseEntity validateRegistrationFilelds = new RegistrationSetupResponseEntity();

    public RegistrationController(Context contextFromCidaas) {

        context = contextFromCidaas;
    }

    public static RegistrationController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new RegistrationController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    public void getRegisterationFields(@NonNull final String requestId, final String locale,
                                       final EventResult<RegistrationSetupResponseEntity> registerFieldsresult) {
        final String methodName = "RegistrationController :getRegisterationFields()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    controlForGetRegistrationFields(requestId, locale, loginPropertiesResult, registerFieldsresult);
                }


                @Override
                public void failure(WebAuthError error) {
                    registerFieldsresult.failure(WebAuthError.getShared(context).cidaasPropertyMissingException("", methodName));
                }
            });
        } catch (Exception e) {

            registerFieldsresult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,
                    e.getMessage()));
        }
    }

    private void controlForGetRegistrationFields(@NonNull final String requestId, final String locale, Dictionary<String, String> loginPropertiesResult,
                                                 final EventResult<RegistrationSetupResponseEntity> registerFieldsresult) {
        String methodName = "RegistrationController :controlForGetRegistrationFields()";
        try {
            String baseurl = loginPropertiesResult.get("DomainURL");
            String clientId = loginPropertiesResult.get("ClientId");
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
                registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, "Error:" + methodName));
            }
        } catch (Exception e) {
            registerFieldsresult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,
                    e.getMessage()));
        }
    }

    //Service call To Registration Setup
    public void getRegisterationFields(@NonNull String baseurl, @NonNull RegistrationSetupRequestEntity registrationSetupRequestEntity,
                                       final EventResult<RegistrationSetupResponseEntity> result) {
        String methodName = "RegistrationController :getRegisterationFields()";
        try {

            if (registrationSetupRequestEntity.getAcceptedLanguage() != null && !registrationSetupRequestEntity.getAcceptedLanguage().equals("") &&
                    registrationSetupRequestEntity.getRequestId() != null && !registrationSetupRequestEntity.getRequestId().equals("")
                    && baseurl != null && !baseurl.equals("")) {

                RegistrationService.getShared(context).getRegistrationSetup(baseurl, registrationSetupRequestEntity, null,
                        new EventResult<RegistrationSetupResponseEntity>() {
                            @Override
                            public void success(RegistrationSetupResponseEntity serviceresult) {
                                validateRegistrationFilelds = serviceresult;
                                registerFields = serviceresult.getData();
                                result.success(serviceresult);
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                result.failure(error);
                            }
                        });
            } else {
                String errorMessage = "RequestId must not be empty";

                result.failure(WebAuthError.getShared(context).propertyMissingException("Accepted Language or requestId must not be null", methodName));
            }
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.REGISTRATION_SETUP_FAILURE, e.getMessage()));
        }
    }

    public void registerNewUser(@NonNull final String requestId, final RegistrationEntity registrationEntity,
                                final EventResult<RegisterNewUserResponseEntity> registerFieldsresult) {
        final String methodName = "RegistrationController :registerNewUser()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    String clientId = result.get("ClientId");
                    String language;

                    if (!requestId.equals("")) {
                        if (registerFields != null) {
                            if (registerFields.length > 0) {
                                for (RegistrationSetupResultDataEntity dataEntity : registerFields) {

                                    if (dataEntity.getFieldKey().equals("email")) {
                                        if (dataEntity.isRequired() && registrationEntity.getEmail().equals("")) {
                                            String errorMessage = "Email must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
                                            return;
                                        }
                                    }

                                    if (dataEntity.getFieldKey().equals("given_name")) {
                                        if (dataEntity.isRequired() && registrationEntity.getGiven_name().equals("")) {
                                            String errorMessage = "given_name must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
                                            return;
                                        }

                                    }

                                    if (dataEntity.getFieldKey().equals("family_name")) {
                                        if (dataEntity.isRequired() && registrationEntity.getFamily_name().equals("")) {
                                            String errorMessage = "family_name must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
                                            return;

                                        }

                                    }

                                    if (dataEntity.getFieldKey().equals("mobile_number")) {
                                        if (dataEntity.isRequired() && registrationEntity.getMobile_number().equals("")) {
                                            String errorMessage = "mobile_number must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
                                            return;
                                        }
                                    }

                                    if (dataEntity.getFieldKey().equals("password")) {
                                        if (dataEntity.isRequired() && registrationEntity.getPassword().equals("")) {
                                            String errorMessage = "password must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
                                            return;
                                        }
                                    }

                                    if (dataEntity.getFieldKey().equals("password_echo")) {
                                        if (dataEntity.isRequired() && registrationEntity.getGiven_name().equals("")) {
                                            String errorMessage = "password_echo must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
                                            return;

                                        }
                                        if (!registrationEntity.getPassword().equals(registrationEntity.getPassword_echo())) {

                                            String errorMessage = "Password and password_echo must be same";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
                                            return;

                                        }

                                    }

                                    if (dataEntity.getFieldKey().equals("username")) {
                                        if (dataEntity.isRequired() && registrationEntity.getUsername().equals("")) {
                                            String errorMessage = "username must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
                                            return;

                                        }

                                    }

                                    if (dataEntity.getFieldKey().equals("birthdate")) {
                                        if (dataEntity.isRequired() && registrationEntity.getBirthdate().equals("")) {
                                            String errorMessage = "birthdate must not be empty";
                                            registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
                                            return;
                                        }

                                    }

                                    if (registrationEntity.getProvider() != null && !registrationEntity.getProvider().equals("")) {
                                        String errorMessage = "Provider must not be empty";
                                        registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
                                        return;

                                    }


                                    if (registrationEntity.getCustomFields() != null) {
                                        if (registrationEntity.getCustomFields().size() > 0) {
                                            for (int i = 0; i < registrationEntity.getCustomFields().size(); i++) {
                                                if (registrationEntity.getCustomFields().keys().hasMoreElements()) {

                                                    // registrationEntity.getCustomFields().get()

                                                }
                                            }
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


                        registerNewUser(baseurl, registerNewUserRequestEntity, registerFieldsresult);

                    } else {
                        String errorMessage = "RequestId must not be empty";

                        registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    registerFieldsresult.failure(WebAuthError.getShared(context).cidaasPropertyMissingException("", methodName));
                }
            });
        } catch (Exception e) {
            registerFieldsresult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,
                    e.getMessage()));
        }
    }

    //Register new User
    public void registerNewUser(String baseurl, RegisterNewUserRequestEntity registrationEntity, final EventResult<RegisterNewUserResponseEntity> result) {
        try {
            // Check for Not null
            registerWithNewUserService(baseurl, registrationEntity, result);
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :RegistrationController :registerNewUser()",
                    WebAuthErrorCode.REGISTRATION_SETUP_FAILURE, e.getMessage()));
        }
    }

    //Service call To Registration Setup
    public void registerWithNewUserService(@NonNull String baseurl, @NonNull RegisterNewUserRequestEntity registerNewUserRequestEntity,
                                           final EventResult<RegisterNewUserResponseEntity> result) {
        String methodName = "RegistrationController :registerWithNewUserService()";
        try {

            if (registerNewUserRequestEntity.getRequestId() != null && !registerNewUserRequestEntity.getRequestId().equals("") &&
                    registerNewUserRequestEntity.getRegistrationEntity() != null &&
                    !registerNewUserRequestEntity.getRegistrationEntity().getFamily_name().equals("") && baseurl != null && !baseurl.equals("")) {
                // Service call
                RegistrationService.getShared(context).registerNewUser(baseurl, registerNewUserRequestEntity, result);
            } else {
                String errorMessage = "RequestId must not be empty";
                result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName));
            }
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.REGISTRATION_SETUP_FAILURE, e.getMessage()));
        }
    }



   /* public void initiateAccountVerificationService(@NonNull final String sub, @NonNull final String requestId, @NonNull final String verificationMedium,
                                          finalEventResult<InitiateAccountVerificationResponseEntity>EventResult)
    {
        final String methodName="RegistrationController :initiateAccountVerificationService()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    String clientId = result.get("ClientId");

                    InitiateAccountVerificationRequestEntity initiateAccountVerificationRequestEntity = new InitiateAccountVerificationRequestEntity();
                    initiateAccountVerificationRequestEntity.setProcessingType("CODE");
                    initiateAccountVerificationRequestEntity.setVerificationMedium(verificationMedium);
                    initiateAccountVerificationRequestEntity.setSub(sub);
                    initiateAccountVerificationRequestEntity.setRequestId(requestId);
                    initiateAccountVerificationService(baseurl, initiateAccountVerificationRequestEntity,EventResult);

                }

                @Override
                public void failure(WebAuthError error) {
                   EventResult.failure(WebAuthError.getShared(context).CidaaspropertyMissingException("",methodName));
                }
            });
        }
        catch (Exception e)
        {
   EventResult.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName,WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,e.getMessage()));
        }
    }


    //Service call To  register New User Account Verification via Email Setup
    public void initiateAccountVerificationService(@NonNull String baseurl,@NonNull InitiateAccountVerificationRequestEntity registrationEntity,
                                                    finalEventResult<InitiateAccountVerificationResponseEntity> result)
    {
        String methodName="RegistrationController :initiateAccountVerificationService()";
        try{

            if (registrationEntity.getRequestId() != null && !registrationEntity.getRequestId().equals("") &&
                    registrationEntity.getProcessingType() != null && !registrationEntity.getProcessingType().equals("") &&
                    registrationEntity.getVerificationMedium() != null && !registrationEntity.getVerificationMedium().equals("") &&
                    registrationEntity.getSub() != null && !registrationEntity.getSub().equals("")
                    && baseurl != null && !baseurl.equals("")) {

                // Service call
           RegistrationService.getShared(context).initiateAccountVerification(baseurl, registrationEntity,new EventResult<InitiateAccountVerificationResponseEntity>() {
                    @Override
                    public void success(InitiateAccountVerificationResponseEntity serviceresult) {
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
                String errorMessage="Verification medium , sub or processing type must not be null";
                result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage,methodName));
            }
        }
        catch (Exception e)
        {
 result.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName,WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,e.getMessage()));
        }
    }

    public void verifyAccountVerificationService(@NonNull final String code,@NonNull final String accvid,finalEventResult<VerifyAccountResponseEntity> result)
    {
        final String methodName="RegistrationController :verifyAccountVerificationService()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = lpresult.get("DomainURL");
                    String clientId = lpresult.get("ClientId");

                    if (code != null && !code.equals("") && accvid != null && !accvid.equals("")) {
                        verifyAccountVerificationService(baseurl, code, accvid, result);
                    }
                    else {
                        result.failure(WebAuthError.getShared(context).propertyMissingException("Verification Code or accvid must not be empty",methodName));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).CidaaspropertyMissingException("", "Error :"+methodName));
                }
            });
        }
        catch (Exception e)
        {
  result.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,e.getMessage()));
        }
    }

        //Service call To  register New User Account Verification via Email Setup
    public void verifyAccountVerificationService(@NonNull String baseurl, @NonNull String code, String accvid,
                                                 finalEventResult<VerifyAccountResponseEntity> result)
    {
        String methodName="RegistrationController :verifyAccountVerificationService()";
        try{

            if (accvid != null && !accvid.equals("") && code != null && !code.equals("") &&
                    baseurl != null && !baseurl.equals("")) {

                VerifyAccountRequestEntity registrationEntity=new VerifyAccountRequestEntity();
                registrationEntity.setCode(code);
                registrationEntity.setAccvid(accvid);

                //Service call
                RegistrationService.getShared(context).verifyAccountVerification(baseurl, registrationEntity, result);
            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException("ACCVID or CODE or BASEURL must not be null",
                        "Error :"+methodName));
            }
        }
        catch (Exception e)
        {
    result.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,e.getMessage()));
        }
    }*/

}
