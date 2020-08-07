package de.cidaas.sdk.android.cidaasnative.view;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Dictionary;
import java.util.HashMap;

import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.AccountVerificationListResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.InitiateAccountVerificationResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.accountverification.VerifyAccountResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.authrequest.AuthRequestResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.clientinfo.ClientInfoEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.deduplication.DeduplicationResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.deduplication.registerdeduplication.RegisterDeduplicationEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.login.LoginEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.register.RegistrationEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.register.registeruser.RegisterNewUserResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.register.registrationsetup.RegistrationSetupResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.ResetPasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.changepassword.ChangePasswordRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.changepassword.ChangePasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetnewpassword.ResetNewPasswordResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetnewpassword.ResetPasswordEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.resetpasswordvalidatecode.ResetPasswordValidateCodeResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.tenantinfo.TenantInfoEntity;
import de.cidaas.sdk.android.cidaasnative.domain.controller.accountverification.AccountVerificationController;
import de.cidaas.sdk.android.cidaasnative.domain.controller.changepassword.ChangePasswordController;
import de.cidaas.sdk.android.cidaasnative.domain.controller.client.ClientController;
import de.cidaas.sdk.android.cidaasnative.domain.controller.deduplication.DeduplicationController;
import de.cidaas.sdk.android.cidaasnative.domain.controller.login.NativeLoginController;
import de.cidaas.sdk.android.cidaasnative.domain.controller.registration.RegistrationController;
import de.cidaas.sdk.android.cidaasnative.domain.controller.requestid.RequestIdController;
import de.cidaas.sdk.android.cidaasnative.domain.controller.resetpassword.ResetPasswordController;
import de.cidaas.sdk.android.cidaasnative.domain.controller.tenant.TenantController;
import de.cidaas.sdk.android.entities.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
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
                             final EventResult<AuthRequestResponseEntity> result) {
        try {
            FileHelper.getShared(context).paramsToDictionaryConverter(DomainUrl, ClientId, RedirectURL, ClientSecret, new EventResult<Dictionary<String, String>>() {
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
                             final EventResult<AuthRequestResponseEntity> Primaryresult, final HashMap<String, String>... extraParams) {
        try {
            FileHelper.getShared(context).paramsToDictionaryConverter(DomainUrl, ClientId, RedirectURL, new EventResult<Dictionary<String, String>>() {
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
    public void getRequestId(final EventResult<AuthRequestResponseEntity> resulttoReturn, @Nullable final HashMap<String, String>... extraParams) {
        try {


            CidaasProperties.getShared(context).saveCidaasProperties(new EventResult<Dictionary<String, String>>() {
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

    public void getRequestId(final Dictionary<String, String> loginproperties, final EventResult<AuthRequestResponseEntity> Primaryresult,
                             @Nullable HashMap<String, String>... extraParams) {
        RequestIdController.getShared(context).getRequestId(loginproperties, Primaryresult, extraParams);
    }

    // -----------------------------------------------------------*******ClientID****----------------------------------------------------------------------

    //Client Id With out Passing RequestId
    public void getClientInfo(final EventResult<ClientInfoEntity> clientInfoEntityResult, final HashMap<String, String>... extraParams) {
        try {
            getRequestId(new EventResult<AuthRequestResponseEntity>() {
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
    public void getClientInfo(final String requestId, final EventResult<ClientInfoEntity> clientInfoEntityResult) {
        ClientController.getShared(context).getClientInfo(requestId, clientInfoEntityResult);
    }


    // -----------------------------------------------------***** TENANT INFO *****------------------------------------------------------------------------
    public void getTenantInfo(final EventResult<TenantInfoEntity> tenantresult) {
        TenantController.getShared(context).getTenantInfo(CidaasHelper.baseurl, tenantresult);
    }

    // -----------------------------------------------------***** LOGIN WITH CREDENTIALS *****---------------------------------------------------------------

    // Login With Credentials With out request Id
    public void loginWithCredentials(final LoginEntity loginEntity, final EventResult<LoginCredentialsResponseEntity> loginresult, final HashMap<String, String>... extraParams) {
        try {
            getRequestId(new EventResult<AuthRequestResponseEntity>() {
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
    public void loginWithCredentials(final String requestId, final LoginEntity loginEntity, final EventResult<LoginCredentialsResponseEntity> loginresult) {
        NativeLoginController.getShared(context).loginwithCredentials(requestId, loginEntity, loginresult);
    }

    // -----------------------------------------------------***** REGISTER *****---------------------------------------------------------------


    public void getRegistrationFields(final String locale, final EventResult<RegistrationSetupResponseEntity> registerFieldsresult,
                                      final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new EventResult<AuthRequestResponseEntity>() {
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

    public void registerUser(final RegistrationEntity registrationEntity, final EventResult<RegisterNewUserResponseEntity> registerFieldsresult,
                             final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new EventResult<AuthRequestResponseEntity>() {
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


    public void getRegistrationFields(@NonNull final String requestId, final String locale, final EventResult<RegistrationSetupResponseEntity> registerFieldsresult) {
        RegistrationController.getShared(context).getRegisterationFields(requestId, locale, registerFieldsresult);
    }


    public void registerUser(@NonNull final String requestId, final RegistrationEntity registrationEntity,
                             final EventResult<RegisterNewUserResponseEntity> registerFieldsresult) {
        RegistrationController.getShared(context).registerNewUser(requestId, registrationEntity, registerFieldsresult);
    }
    // -----------------------------------------------------***** Initiate Verification *****---------------------------------------------------------------


    public void initiateEmailVerification(@NonNull final String email, final EventResult<InitiateAccountVerificationResponseEntity> EventResult,
                                          final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new EventResult<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            initiateEmailVerification(email, result.getData().getRequestId(), EventResult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            EventResult.failure(error);
                        }
                    }, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    EventResult.failure(error);
                }
            });
        } catch (Exception e) {
            EventResult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :initiateEmailVerification()", WebAuthErrorCode.INITIATE_EMAIL_MFA_FAILURE, e.getMessage()));
        }
    }


    public void initiateEmailVerification(@NonNull final String email, @NonNull final String requestId,
                                          final EventResult<InitiateAccountVerificationResponseEntity> EventResult) {
        AccountVerificationController.getShared(context).initiateAccountVerificationService(email, requestId, "email", EventResult);
    }

    public void initiateSMSVerification(@NonNull final String sub, final EventResult<InitiateAccountVerificationResponseEntity> EventResult,
                                        final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new EventResult<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            initiateSMSVerification(sub, result.getData().getRequestId(), EventResult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            EventResult.failure(error);
                        }
                    }, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    EventResult.failure(error);
                }
            });
        } catch (Exception e) {
            EventResult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :initiateSMSVerification()", WebAuthErrorCode.INITIATE_SMS_MFA_FAILURE, e.getMessage()));
        }
    }


    public void initiateSMSVerification(@NonNull final String sub, @NonNull final String requestId,
                                        final EventResult<InitiateAccountVerificationResponseEntity> EventResult) {
        AccountVerificationController.getShared(context).initiateAccountVerificationService(sub, requestId, "sms", EventResult);
    }

    public void initiateIVRVerification(@NonNull final String sub, final EventResult<InitiateAccountVerificationResponseEntity> EventResult,
                                        final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new EventResult<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            initiateIVRVerification(sub, result.getData().getRequestId(), EventResult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            EventResult.failure(error);
                        }
                    }, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    EventResult.failure(error);
                }
            });
        } catch (Exception e) {
            EventResult.failure(WebAuthError.getShared(context).methodException("initiateIVRVerification", WebAuthErrorCode.INITIATE_IVR_MFA_FAILURE, e.getMessage()));
        }
    }

    public void initiateIVRVerification(@NonNull final String sub, @NonNull final String requestId, final EventResult<InitiateAccountVerificationResponseEntity> EventResult) {
        AccountVerificationController.getShared(context).initiateAccountVerificationService(sub, requestId, "ivr", EventResult);
    }

    public void verifyAccount(@NonNull final String code, @NonNull final String accvid, final EventResult<VerifyAccountResponseEntity> result) {
        AccountVerificationController.getShared(context).verifyAccountVerificationService(code, accvid, result);
    }

    public void getAccountVerificationList(@NonNull final String sub, final EventResult<AccountVerificationListResponseEntity> result) {
        AccountVerificationController.getShared(context).getAccountVerificationList(sub, result);
    }

    //----------------------------------DEDEUPLICATION------------------------------------------------------------------------------------------------------

    public void getDeduplicationDetails(@NonNull final String trackId, final EventResult<DeduplicationResponseEntity> deduplicaionResult) {
        DeduplicationController.getShared(context).getDeduplicationList(trackId, deduplicaionResult);
    }

    public void registerDeduplication(@NonNull final String trackId, final EventResult<RegisterDeduplicationEntity> deduplicaionResult) {
        DeduplicationController.getShared(context).registerDeduplication(CidaasHelper.baseurl, trackId, deduplicaionResult);
    }

    public void loginWithDeduplication(@NonNull final String sub, @NonNull final String password,
                                       final EventResult<LoginCredentialsResponseEntity> loginresult, final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new EventResult<AuthRequestResponseEntity>() {
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
                                       final EventResult<LoginCredentialsResponseEntity> deduplicaionResult) {
        DeduplicationController.getShared(context).loginDeduplication(requestId, sub, password, deduplicaionResult);

    }

    //----------------------------------Forgot password------------------------------------------------------------------------------------------------------

    public void initiateResetPasswordByEmail(final String email,
                                             final EventResult<ResetPasswordResponseEntity> resetPasswordResponseEntityResult, final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new EventResult<AuthRequestResponseEntity>() {
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
                                             final EventResult<ResetPasswordResponseEntity> resetPasswordResponseEntityResult) {
        ResetPasswordController.getShared(context).initiateresetPasswordService(requestId, email, "email", resetPasswordResponseEntityResult);
    }


    public void initiateResetPasswordBySMS(final String mobileNumber,
                                           final EventResult<ResetPasswordResponseEntity> resetPasswordResponseEntityResult, final HashMap<String, String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new EventResult<AuthRequestResponseEntity>() {
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
                                           final EventResult<ResetPasswordResponseEntity> resetPasswordResponseEntityResult) {
        ResetPasswordController.getShared(context).initiateresetPasswordService(requestId, mobileNumber, "sms", resetPasswordResponseEntityResult);
    }


    public void handleResetPassword(@NonNull final String verificationCode, final String rprq,
                                    final EventResult<ResetPasswordValidateCodeResponseEntity> resetpasswordResult) {
        ResetPasswordController.getShared(context).resetPasswordValidateCode(verificationCode, rprq, resetpasswordResult);
    }

    //done Change to entity
    public void resetPassword(@NonNull final ResetPasswordEntity resetPasswordEntity, final EventResult<ResetNewPasswordResponseEntity> resetpasswordResult) {
        ResetPasswordController.getShared(context).resetNewPassword(resetPasswordEntity, resetpasswordResult);

    }

    //----------------------------------Change password------------------------------------------------------------------------------------------------------

    // change the sub to access token
    public void changePassword(String sub, final ChangePasswordRequestEntity changePasswordRequestEntity, final EventResult<ChangePasswordResponseEntity> result) {
        ChangePasswordController.getShared(context).changePassword(changePasswordRequestEntity, result);
    }

    //----------------------------------------------------------------------------------------------------------------------------------------

}
