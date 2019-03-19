package com.example.cidaasv2.Controller.Repository.Registration;

import android.content.Context;

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

import androidx.annotation.NonNull;
import timber.log.Timber;

public class RegistrationController {


    private Context context;

    public static RegistrationController shared;

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
