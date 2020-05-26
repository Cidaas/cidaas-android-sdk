package de.cidaas.sdk.android.cidaasnative.View;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Dictionary;
import java.util.HashMap;

import de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification.AccountVerificationListResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification.InitiateAccountVerificationResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification.VerifyAccountResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.AuthRequest.AuthRequestResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.ClientInfo.ClientInfoEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Deduplication.DeduplicationResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Login.LoginEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegisterUser.RegisterNewUserResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegistrationEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Register.RegistrationSetup.RegistrationSetupResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ResetNewPassword.ResetPasswordEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ResetPasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.TenantInfo.TenantInfoEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Controller.AccountVerification.AccountVerificationController;
import de.cidaas.sdk.android.cidaasnative.domain.Controller.ChangePassword.ChangePasswordController;
import de.cidaas.sdk.android.cidaasnative.domain.Controller.Client.ClientController;
import de.cidaas.sdk.android.cidaasnative.domain.Controller.Deduplication.DeduplicationController;
import de.cidaas.sdk.android.cidaasnative.domain.Controller.Login.NativeLoginController;
import de.cidaas.sdk.android.cidaasnative.domain.Controller.Registration.RegistrationController;
import de.cidaas.sdk.android.cidaasnative.domain.Controller.RequestId.RequestIdController;
import de.cidaas.sdk.android.cidaasnative.domain.Controller.ResetPassword.ResetPasswordController;
import de.cidaas.sdk.android.cidaasnative.domain.Controller.Tenant.TenantController;
import de.cidaas.sdk.android.entities.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.helper.enums.Result;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.helper.general.FileHelper;
import de.cidaas.sdk.android.properties.CidaasProperties;


public class CidaasNative {

    private Context context;
    public static CidaasNative cidaasNativeInstance;

    //Confirm it must be a static one
    //Extra parameter that is passed in URL
    public static HashMap<String, String> extraParams = new HashMap<>();

    public static CidaasNative getInstance(Context context) {
        if (cidaasNativeInstance == null) {
            cidaasNativeInstance = new CidaasNative(context);
        }

        return cidaasNativeInstance;
    }

    public CidaasNative(Context context) {
        this.context = context;
        CidaasHelper.getShared(context).initialiseObject();

        //1.Initialise DB ie shared preference
        //2.Enable log
        //3.add devi0ce info and FCM token
        //4.Set Base url

    }


    //------------------------------------------Methods without requestID and other Suppoertive methods-------------------------------------------------------------------------------

    //Get Request Id By passing loginProperties as an Object
    // 1. Read properties from file
    // 2. Call request id from dictionary method
    // 3. Maintain logs based on flags


    // -----------------------------------------------------***** REQUEST ID *****---------------------------------------------------------------

    //Get Request Id By Passing loginProperties as Value in parameters with Client Secret
    public void getRequestId(@NonNull String DomainUrl, @NonNull String ClientId, @NonNull String RedirectURL, @NonNull String ClientSecret,
                             final Result<AuthRequestResponseEntity> result) {
        try {
            FileHelper.getShared(context).paramsToDictionaryConverter(DomainUrl, ClientId, RedirectURL, ClientSecret, new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginproperties) {

                    getRequestId(loginproperties, result, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(error);
                }
            });

        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :getRequestId()", WebAuthErrorCode.READ_PROPERTIES_ERROR, e.getMessage()));
        }
    }

    //Get Request Id By Passing loginProperties as Value in parameters
    public void getRequestId(@NonNull final String DomainUrl, @NonNull String ClientId, @NonNull String RedirectURL,
                             final Result<AuthRequestResponseEntity> Primaryresult, final HashMap<String, String>... extraParams) {
        try {
            FileHelper.getShared(context).paramsToDictionaryConverter(DomainUrl, ClientId, RedirectURL, new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesresult) {
                    getRequestId(loginPropertiesresult, Primaryresult, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    Primaryresult.failure(error);
                }
            });

        } catch (Exception e) {
            Primaryresult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :getRequestId()", WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE, e.getMessage()));
        }
    }

    //Get Request Id without passing any value
    public void getRequestId(final Result<AuthRequestResponseEntity> resulttoReturn, @Nullable final HashMap<String, String>... extraParams) {
        try {


            CidaasProperties.getShared(context).saveCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {

                    getRequestId(loginPropertiesResult, resulttoReturn);
                }

                @Override
                public void failure(WebAuthError error) {

                    resulttoReturn.failure(error);
                }
            });

        } catch (Exception e) {
            resulttoReturn.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :getRequestId()", WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE, e.getMessage()));
        }
    }

    // ***** REQUEST ID CONTROLLER SENDER *****

    public void getRequestId(final Dictionary<String, String> loginproperties, final Result<AuthRequestResponseEntity> Primaryresult,
                             @Nullable HashMap<String, String>... extraParams) {
        RequestIdController.getShared(context).getRequestId(loginproperties, Primaryresult, extraParams);
    }

    // -----------------------------------------------------------*******ClientID****----------------------------------------------------------------------

    //Client Id With out Passing RequestId
    public void getClientInfo(final Result<ClientInfoEntity> clientInfoEntityResult, final HashMap<String, String>... extraParams) {
        try {
            getRequestId(new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {
                    getClientInfo(result.getData().getRequestId(), clientInfoEntityResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    clientInfoEntityResult.failure(error);
                }
            }, extraParams);
        } catch (Exception e) {
            clientInfoEntityResult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :getClientInfo()", WebAuthErrorCode.CLIENT_INFO_FAILURE, e.getMessage()));
        }
    }

    //*********ClientId With RequestId**********
    public void getClientInfo(final String requestId, final Result<ClientInfoEntity> clientInfoEntityResult) {
        ClientController.getShared(context).getClientInfo(requestId, clientInfoEntityResult);
    }


    // -----------------------------------------------------***** TENANT INFO *****------------------------------------------------------------------------
    public void getTenantInfo(final Result<TenantInfoEntity> tenantresult) {
        TenantController.getShared(context).getTenantInfo(CidaasHelper.baseurl, tenantresult);
    }

    // -----------------------------------------------------***** LOGIN WITH CREDENTIALS *****---------------------------------------------------------------

    // Login With Credentials With out request Id
    public void loginWithCredentials(final LoginEntity loginEntity, final Result<LoginCredentialsResponseEntity> loginresult, final HashMap<String, String>... extraParams) {
        try {
            getRequestId(new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {
                    loginWithCredentials(result.getData().getRequestId(), loginEntity, loginresult);
                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(error);
                }
            }, extraParams);
        } catch (Exception e) {
            loginresult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :loginWithCredentials()", WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE, e.getMessage()));
        }
    }

    // Login With Credentials With request Id
    public void loginWithCredentials(final String requestId, final LoginEntity loginEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
        NativeLoginController.getShared(context).loginwithCredentials(requestId, loginEntity, loginresult);
    }

    // -----------------------------------------------------***** REGISTER *****---------------------------------------------------------------


    public void getRegistrationFields(final String locale, final Result<RegistrationSetupResponseEntity> registerFieldsresult,
                                      final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            getRegistrationFields(result.getData().getRequestId(), locale, registerFieldsresult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            registerFieldsresult.failure(error);
                        }
                    }, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    registerFieldsresult.failure(error);
                }
            });
        } catch (Exception e) {
            registerFieldsresult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :getRegistrationFields()", WebAuthErrorCode.REGISTRATION_SETUP_FAILURE, e.getMessage()));
        }
    }

    public void registerUser(final RegistrationEntity registrationEntity, final Result<RegisterNewUserResponseEntity> registerFieldsresult,
                             final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            registerUser(result.getData().getRequestId(), registrationEntity, registerFieldsresult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            registerFieldsresult.failure(error);
                        }
                    }, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    registerFieldsresult.failure(error);
                }
            });
        } catch (Exception e) {
            registerFieldsresult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas : registerUser()", WebAuthErrorCode.REGISTRATION_SETUP_FAILURE, e.getMessage()));
        }
    }


    public void getRegistrationFields(@NonNull final String requestId, final String locale, final Result<RegistrationSetupResponseEntity> registerFieldsresult) {
        RegistrationController.getShared(context).getRegisterationFields(requestId, locale, registerFieldsresult);
    }


    public void registerUser(@NonNull final String requestId, final RegistrationEntity registrationEntity,
                             final Result<RegisterNewUserResponseEntity> registerFieldsresult) {
        RegistrationController.getShared(context).registerNewUser(requestId, registrationEntity, registerFieldsresult);
    }
    // -----------------------------------------------------***** Initiate Verification *****---------------------------------------------------------------


    public void initiateEmailVerification(@NonNull final String sub, final Result<InitiateAccountVerificationResponseEntity> Result,
                                          final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            initiateEmailVerification(sub, result.getData().getRequestId(), Result);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            Result.failure(error);
                        }
                    }, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    Result.failure(error);
                }
            });
        } catch (Exception e) {
            Result.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :initiateEmailVerification()", WebAuthErrorCode.INITIATE_EMAIL_MFA_FAILURE, e.getMessage()));
        }
    }


    public void initiateEmailVerification(@NonNull final String sub, @NonNull final String requestId,
                                          final Result<InitiateAccountVerificationResponseEntity> Result) {
        AccountVerificationController.getShared(context).initiateAccountVerificationService(sub, requestId, "email", Result);
    }

    public void initiateSMSVerification(@NonNull final String sub, final Result<InitiateAccountVerificationResponseEntity> Result,
                                        final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            initiateSMSVerification(sub, result.getData().getRequestId(), Result);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            Result.failure(error);
                        }
                    }, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    Result.failure(error);
                }
            });
        } catch (Exception e) {
            Result.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :initiateSMSVerification()", WebAuthErrorCode.INITIATE_SMS_MFA_FAILURE, e.getMessage()));
        }
    }


    public void initiateSMSVerification(@NonNull final String sub, @NonNull final String requestId,
                                        final Result<InitiateAccountVerificationResponseEntity> Result) {
        AccountVerificationController.getShared(context).initiateAccountVerificationService(sub, requestId, "sms", Result);
    }

    public void initiateIVRVerification(@NonNull final String sub, final Result<InitiateAccountVerificationResponseEntity> Result,
                                        final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            initiateIVRVerification(sub, result.getData().getRequestId(), Result);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            Result.failure(error);
                        }
                    }, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    Result.failure(error);
                }
            });
        } catch (Exception e) {
            Result.failure(WebAuthError.getShared(context).methodException("initiateIVRVerification", WebAuthErrorCode.INITIATE_IVR_MFA_FAILURE, e.getMessage()));
        }
    }

    public void initiateIVRVerification(@NonNull final String sub, @NonNull final String requestId, final Result<InitiateAccountVerificationResponseEntity> Result) {
        AccountVerificationController.getShared(context).initiateAccountVerificationService(sub, requestId, "ivr", Result);
    }

    public void verifyAccount(@NonNull final String code, @NonNull final String accvid, final Result<VerifyAccountResponseEntity> result) {
        AccountVerificationController.getShared(context).verifyAccountVerificationService(code, accvid, result);
    }

    public void getAccountVerificationList(@NonNull final String sub, final Result<AccountVerificationListResponseEntity> result) {
        AccountVerificationController.getShared(context).getAccountVerificationList(sub, result);
    }

    //----------------------------------DEDEUPLICATION------------------------------------------------------------------------------------------------------

    public void getDeduplicationDetails(@NonNull final String trackId, final Result<DeduplicationResponseEntity> deduplicaionResult) {
        DeduplicationController.getShared(context).getDeduplicationList(trackId, deduplicaionResult);
    }

    public void registerDeduplication(@NonNull final String trackId, final Result<RegisterDeduplicationEntity> deduplicaionResult) {
        DeduplicationController.getShared(context).registerDeduplication(CidaasHelper.baseurl, trackId, deduplicaionResult);
    }

    public void loginWithDeduplication(@NonNull final String sub, @NonNull final String password,
                                       final Result<LoginCredentialsResponseEntity> loginresult, final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            loginWithDeduplication(result.getData().getRequestId(), sub, password, loginresult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            loginresult.failure(error);
                        }
                    }, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty", "Error :Cidaas :loginWithDeduplication()"));
                }
            });
        } catch (Exception e) {

        }
    }

    public void loginWithDeduplication(final String requestId, @NonNull final String sub, @NonNull final String password,
                                       final Result<LoginCredentialsResponseEntity> deduplicaionResult) {
        DeduplicationController.getShared(context).loginDeduplication(requestId, sub, password, deduplicaionResult);

    }

    //----------------------------------Forgot password------------------------------------------------------------------------------------------------------

    public void initiateResetPasswordByEmail(final String email,
                                             final Result<ResetPasswordResponseEntity> resetPasswordResponseEntityResult, final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            initiateResetPasswordByEmail(result.getData().getRequestId(), email, resetPasswordResponseEntityResult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            resetPasswordResponseEntityResult.failure(error);
                        }
                    }, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty", "Error :Cidaas :initiateResetPasswordByEmail()"));
                }
            });
        } catch (Exception e) {
            resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :initiateResetPasswordByEmail()", WebAuthErrorCode.INITIATE_RESET_PASSWORD_FAILURE, e.getMessage()));
        }
    }

    // chang
    public void initiateResetPasswordByEmail(final String requestId, final String email,
                                             final Result<ResetPasswordResponseEntity> resetPasswordResponseEntityResult) {
        ResetPasswordController.getShared(context).initiateresetPasswordService(requestId, email, "email", resetPasswordResponseEntityResult);
    }


    public void initiateResetPasswordBySMS(final String mobileNumber,
                                           final Result<ResetPasswordResponseEntity> resetPasswordResponseEntityResult, final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            initiateResetPasswordBySMS(result.getData().getRequestId(), mobileNumber, resetPasswordResponseEntityResult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            resetPasswordResponseEntityResult.failure(error);
                        }
                    }, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty", "Exception :Cidaas :initiateResetPasswordBySMS()"));
                }
            });
        } catch (Exception e) {
            resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :initiateResetPasswordBySMS()", WebAuthErrorCode.INITIATE_RESET_PASSWORD_FAILURE, e.getMessage()));
        }
    }

    public void initiateResetPasswordBySMS(final String requestId, final String mobileNumber,
                                           final Result<ResetPasswordResponseEntity> resetPasswordResponseEntityResult) {
        ResetPasswordController.getShared(context).initiateresetPasswordService(requestId, mobileNumber, "sms", resetPasswordResponseEntityResult);
    }


    public void handleResetPassword(@NonNull final String verificationCode, final String rprq,
                                    final Result<ResetPasswordValidateCodeResponseEntity> resetpasswordResult) {
        ResetPasswordController.getShared(context).resetPasswordValidateCode(verificationCode, rprq, resetpasswordResult);
    }

    //done Change to entity
    public void resetPassword(@NonNull final ResetPasswordEntity resetPasswordEntity, final Result<ResetNewPasswordResponseEntity> resetpasswordResult) {
        ResetPasswordController.getShared(context).resetNewPassword(resetPasswordEntity, resetpasswordResult);

    }

    //----------------------------------Change password------------------------------------------------------------------------------------------------------

    // change the sub to access token
    public void changePassword(String sub, final ChangePasswordRequestEntity changePasswordRequestEntity, final Result<ChangePasswordResponseEntity> result) {
        ChangePasswordController.getShared(context).changePassword(changePasswordRequestEntity, result);
    }

    //----------------------------------------------------------------------------------------------------------------------------------------


    //1. Get user Activity and User info

}
