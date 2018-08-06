package com.example.cidaasv2.Controller.Repository.Registration;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
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

            if (registrationSetupRequestEntity.getAcceptedLanguage() != null && registrationSetupRequestEntity.getAcceptedLanguage() != "" &&
                    registrationSetupRequestEntity.getRequestId() != null && registrationSetupRequestEntity.getRequestId() != ""
                    && baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                RegistrationService.getShared(context).getRegistrationSetup(baseurl, registrationSetupRequestEntity, new Result<RegistrationSetupResponseEntity>() {
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

                result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                        errorMessage, HttpStatusCode.EXPECTATION_FAILED));
            }
        }
        catch (Exception e)
        {

            String errorMessage="Exception"+e.getMessage();

            result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    errorMessage, HttpStatusCode.EXPECTATION_FAILED));
        }
    }

    //Register new User
    public void registerNewUser(String baseurl, RegisterNewUserRequestEntity registrationEntity, final Result<RegisterNewUserResponseEntity> result) {
        try {
            //Todo Check for Not null

            for (RegistrationSetupResultDataEntity dataEntity:validateRegistrationFilelds.getData()
                 ) {
                if(dataEntity.isRequired())
                {

                }
            }
                registerWithNewUserService(baseurl,registrationEntity,result);


        }
        catch (Exception e)
        {

            String errorMessage="RequestId must not be empty";

            result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
        }
    }

    //Service call To Registration Setup
    public void registerWithNewUserService(@NonNull String baseurl,@NonNull RegisterNewUserRequestEntity registerNewUserRequestEntity, final Result<RegisterNewUserResponseEntity> result){
        try{

            if (registerNewUserRequestEntity.getRequestId() != null && registerNewUserRequestEntity.getRequestId() != "" &&
                    registerNewUserRequestEntity.getRegistrationEntity() != null && registerNewUserRequestEntity.getRegistrationEntity().getFamily_name() != ""
                    && baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                RegistrationService.getShared(context).registerNewUser(baseurl, registerNewUserRequestEntity, new Result<RegisterNewUserResponseEntity>() {
                    @Override
                    public void success(RegisterNewUserResponseEntity serviceresult) {
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

                result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                        errorMessage, HttpStatusCode.EXPECTATION_FAILED));
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());

            String errorMessage="RequestId must not be empty";

            result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
        }
    }





    //Service call To  register New User Account Verification via Email Setup
    public void initiateAccountVerificationService(@NonNull String baseurl,@NonNull RegisterUserAccountInitiateRequestEntity registrationEntity,
                                                    final Result<RegisterUserAccountInitiateResponseEntity> result)
    {
        try{

            if (registrationEntity.getRequestId() != null && registrationEntity.getRequestId() != "" &&
                    registrationEntity.getProcessingType() != null && registrationEntity.getProcessingType() != "" &&
                    registrationEntity.getVerificationMedium() != null && registrationEntity.getVerificationMedium() != "" &&
                    registrationEntity.getSub() != null && registrationEntity.getSub() != ""
                    && baseurl != null && !baseurl.equals("")) {

                //Todo Service call
                RegistrationService.getShared(context).initiateAccountVerification(baseurl, registrationEntity, new Result<RegisterUserAccountInitiateResponseEntity>() {
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
                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }


    //Service call To  register New User Account Verification via Email Setup
    public void verifyAccountVerificationService(@NonNull String baseurl,@NonNull String code, final Result<RegisterUserAccountVerifyResponseEntity> result)
    {
        try{

            if (accvid != null && accvid != "" && code != null && code != "" &&
                    baseurl != null && !baseurl.equals("")) {

                RegisterUserAccountVerifyRequestEntity registrationEntity=new RegisterUserAccountVerifyRequestEntity();
                registrationEntity.setCode(code);
                registrationEntity.setAccvid(accvid);

                //Todo Service call
                RegistrationService.getShared(context).verifyAccountVerification(baseurl, registrationEntity, new Result<RegisterUserAccountVerifyResponseEntity>() {
                    @Override
                    public void success(RegisterUserAccountVerifyResponseEntity serviceresult) {
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

}
